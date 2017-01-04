package com.mirkowu.fuckgoods.network.rtrofit;

import android.support.annotation.NonNull;

import com.mirkowu.fuckgoods.BuildConfig;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2016/10/10.
 */

public class ParameterInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        FormBody newBody = makeBody(oldRequest);

        Request newRequest = oldRequest.newBuilder().post(newBody).build();
        Response response = chain.proceed(newRequest);
        if (BuildConfig.DEBUG) {
            ResponseBody resultBody = response.body();
            String result = resultBody.string();
            try {
                Logger.json(result);
            } catch (Exception e) {
                Logger.d(result);
            }

         /*   * 因为调用ResponseBody.string()后即关闭，后续无法获取内容 */
            response = response.newBuilder()
                    .body(ResponseBody.create(resultBody.contentType(), result))
                    .build();
        }
        return response;
    }

    @NonNull
    private FormBody makeBody(Request oldRequest) {
        HttpUrl oldUrl = oldRequest.url();

        FormBody.Builder newBodyBuilder = new FormBody.Builder();
        JSONObject data = new JSONObject();
        try {
            /**
             * 添加mid    rid  language
             */
//
//            String mid = LoginResBean.getData().mid;
//            if (!TextUtils.isEmpty(mid)) data.put("mid", LoginResBean.getData().mid);

            /** 链接上的参数 */
            for (int i = oldUrl.querySize() - 1; i >= 0; i--) {
                String name = oldUrl.queryParameterName(i);
                String value = oldUrl.queryParameterValue(i);
                newBodyBuilder.add(name, value);
                data.put(name, value);

            }

            /** Body上的参数 */
            //这里要判断下  不然参数为空时会classCastException
            if (oldRequest.body() instanceof FormBody) {
                FormBody body = (FormBody) oldRequest.body();
                if (body != null) for (int i = body.size() - 1; i >= 0; i--) {
                    String name = body.name(i);
                    String value = body.value(i);
                    newBodyBuilder.add(name, value);
                    data.put(name, value);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        /** * 添加Sign参数 */
        newBodyBuilder.add("data", data.toString());
        newBodyBuilder.add("apisign", MD5Util.ToMD5(data.toString()));
        if (BuildConfig.DEBUG) Logger.json(data.toString());//打印请求log
        return newBodyBuilder.build();
    }

//    private String bodyToString(FormBody body) {
//        JSONObject data = new JSONObject();
//        // LinkedHashMap<String, String> bodyMap = new LinkedHashMap<>();
//        for (int i = body.size() - 1; i >= 0; i--) {
//            String name = body.name(i);
//            String value = body.value(i);
//            try {
//                data.put(name, value);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            // bodyMap.put(name, value);
//        }
//        return data.toString();
//    }
}
