package com.mirkowu.fuckgoods.app;

import android.os.Environment;

import java.io.File;

/**
 * Created by codeest on 2016/8/3.
 */
public class Constants {


    public static final String KEY_API = "f95283476506aa756559dd28a56f0c0b"; //需要APIKEY请去 http://apistore.baidu.com/ 申请,复用会减少访问可用次数


    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "codeest" + File.separator + "GeekNews";


    public static final String SP_NIGHT_MODE = "night_mode";

    //接口
    // public static final String IMAGE_HEAD = "http://gank.io/";
    public static final String HOST = "http://gank.io/";
    public static final String API = "api/data/{type}/{count}/{page}";//Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
    public static final String TYPE_ALL = "all";
    public static final String TYPE_GIRLS = "福利";
    public static final String TYPE_ANDROID = "Andorid";
    public static final String TYPE_IOS = "IOS";
    public static final String TYPE_Video = "休息视频";
    public static final String TYPE_EXPAND = "拓展资源";
    public static final String TYPE_WEB = "前端";
    public static final String TYPE_recommend = "瞎推荐";
    public static final String TYPE_APP = "App";


    public static final String Image1 = "http://h.hiphotos.baidu.com/zhidao/pic/item/6d81800a19d8bc3ed69473cb848ba61ea8d34516.jpg";
    public static final String Gif1 = "http://img.weixinyidu.com/160120/8ad81392.jpg";
    public static final String Gif2 = "http://f1.diyitui.com/3c/c4/57/3e/b7/d3/69/a7/88/9f/66/97/7d/ec/75/77.jpg";
    public static final String Gif3 = "http://img3.duitang.com/uploads/item/201408/16/20140816180132_zkNWv.thumb.700_0.gif";
    public static final String Gif4 = "http://tupian.enterdesk.com/2014/lxy/2014/12/03/5/10.gif";


}
