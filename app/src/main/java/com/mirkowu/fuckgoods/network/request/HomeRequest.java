package com.mirkowu.fuckgoods.network.request;

import com.mirkowu.fuckgoods.network.api.HomeService;
import com.mirkowu.fuckgoods.network.bean.GankBean;
import com.mirkowu.fuckgoods.network.rtrofit.BaseBean;
import com.mirkowu.fuckgoods.network.rtrofit.RetrofitManager;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2016/12/30 0030.
 */

public class HomeRequest {

    HomeService service;

    @Inject
    public HomeRequest() {
        service = RetrofitManager.getRetrofit().create(HomeService.class);
    }

    public Observable<BaseBean<List<GankBean>>> getGank(String type,int count, int page) {

        return service.getGank(type, count, page);

    }
}
