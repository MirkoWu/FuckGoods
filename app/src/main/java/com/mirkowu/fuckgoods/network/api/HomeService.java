package com.mirkowu.fuckgoods.network.api;

import com.mirkowu.fuckgoods.app.Constants;
import com.mirkowu.fuckgoods.network.bean.GankBean;
import com.mirkowu.fuckgoods.network.rtrofit.BaseBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/12/30 0030.
 */

public interface HomeService {

    @GET(Constants.API)
    Observable<BaseBean<List<GankBean>>> getGank(@Path("type") String type,
                                                 @Path("count") int count,
                                                 @Path("page") int page);
}
