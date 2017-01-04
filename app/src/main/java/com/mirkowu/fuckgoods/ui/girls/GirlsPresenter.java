package com.mirkowu.fuckgoods.ui.girls;

import com.mirkowu.fuckgoods.app.Constants;
import com.mirkowu.fuckgoods.base.RxPresenter;
import com.mirkowu.fuckgoods.network.request.HomeRequest;
import com.mirkowu.fuckgoods.network.rtrofit.NetworkTransformerHelper;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/10/10 0010.
 */

public class GirlsPresenter extends RxPresenter<GirlsContract.View>
        implements GirlsContract.Presenter {
    HomeRequest request;

    @Inject
    public GirlsPresenter(HomeRequest request) {
        this.request = request;
    }

    @Override
    public void getGirls(int count, int page) {
        request.getGank(Constants.TYPE_GIRLS,count, page)
                .compose(new NetworkTransformerHelper<>(mView))
                .subscribe(mView::getGirls, mView::showEror);
    }
}
