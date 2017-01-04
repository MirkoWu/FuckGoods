package com.mirkowu.fuckgoods.ui.home.fragment;

import com.mirkowu.fuckgoods.base.RxPresenter;
import com.mirkowu.fuckgoods.network.request.HomeRequest;
import com.mirkowu.fuckgoods.network.rtrofit.NetworkTransformerHelper;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/10/10 0010.
 */

public class HomePresenter extends RxPresenter<HomeContract.View>
        implements HomeContract.Presenter {
    HomeRequest request;

    @Inject
    public HomePresenter(HomeRequest request) {
        this.request = request;
    }

    @Override
    public void getGank(String type,int count, int page) {
        request.getGank(type,count, page)
                .compose(new NetworkTransformerHelper<>(mView))
                .subscribe(mView::getGank, mView::showEror);
    }
}
