package com.mirkowu.fuckgoods.network.rtrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.internal.ConstructorConstructor;
import com.mirkowu.fuckgoods.app.App;
import com.mirkowu.fuckgoods.app.Constants;
import com.mirkowu.fuckgoods.network.rtrofit.typeAdapter.ListTypeAdapterFactory;
import com.mirkowu.fuckgoods.util.LogUtil;
import com.mirkowu.fuckgoods.util.NetworkUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/11/12 0012.
 */

public class RetrofitManager {
    private static final long CACHE_PERIOD_SHORT = 60;
    private static final long CACHE_PERIOD_LONG = 60 * 60 * 24;


    public volatile static OkHttpClient okHttpClient = null;
    public static Gson mGson = null;

    public static OkHttpClient getClientInstance() {
        if (okHttpClient == null)
            synchronized (RetrofitManager.class) {
                if (okHttpClient == null) {
                    okHttpClient = getOkHttpClient();
                }
            }
        return okHttpClient;
    }

    public static OkHttpClient getOkHttpClient() {
        // 指定缓存路径,缓存大小10Mb
        Cache cache = new Cache(new File(App.getInstance().getCacheDir(), "HttpCache"),
                1024 * 1024 * 10);
        return new OkHttpClient.Builder()

                //设置缓存
                .cache(cache)
                //设置超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                //错误重连
                .retryOnConnectionFailure(true)
                // .addInterceptor(new ParameterInterceptor())// 拦截器
                .addInterceptor(mCacheInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder().baseUrl(Constants.HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .client(getClientInstance())
                .build();
    }

    public static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> {
        //打印retrofit日志
        LogUtil.d("RetrofitLog", message + "");
    }).setLevel(HttpLoggingInterceptor.Level.BODY);


    /**
     * 设置缓存
     */
    public static Interceptor mCacheInterceptor = chain -> {
        Request request = chain.request();
        if (!NetworkUtil.isConn(App.getInstance())) {
            //没网时只使用缓存
            //自定义请求头，可以在响应头对请求头的header进行拦截，配置不同的缓存策略
            request = request.newBuilder()
                    .header("head-request", request.toString())
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        if (NetworkUtil.isConn(App.getInstance())) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            LogUtil.e("Interceptor", "response: " + response.toString());
            //添加头信息，配置Cache-Control
            //removeHeader("Pragma") 使缓存生效
            return response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + CACHE_PERIOD_SHORT)
                    .removeHeader("Pragma")
                    .build();
        } else {
            LogUtil.e("Interceptor", "net not connect");
            return response.newBuilder()
                    .header("Cache-Control", "public,only-if-cached, max-stale=" + CACHE_PERIOD_LONG)
                    .removeHeader("Pragma")
                    .build();
        }
    };

    /**
     * 解决数组因为后台给空字符串“”时导致异常 todo 貌似没什么用  先放这里
     *
     * @return
     */
    public static Gson buildGson() {
        if (mGson == null) {
            GsonBuilder gsonBulder = new GsonBuilder()/*.setLenient()*/;
//            gsonBulder.registerTypeAdapter(String.class, STRING);   //所有String类型null替换为字符串“”
//            gsonBulder.registerTypeAdapter(int.class, INTEGER); //int类型对float做兼容

            //通过反射获取instanceCreators属性
            try {
                Class builder = (Class) gsonBulder.getClass();
                Field f = builder.getDeclaredField("instanceCreators");
                f.setAccessible(true);
                Map<Type, InstanceCreator<?>> val = (Map<Type, InstanceCreator<?>>) f.get(gsonBulder);//得到此属性的值
                //注册数组的处理器
                gsonBulder.registerTypeAdapterFactory(new ListTypeAdapterFactory(new ConstructorConstructor(val)));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //将“” 字符串 转换为null
            // gsonBulder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory());
            //不对数据为null的数据继续解析处理
            // gsonBulder.serializeNulls();
            mGson = gsonBulder.create();
        }
        return mGson;
    }


}
