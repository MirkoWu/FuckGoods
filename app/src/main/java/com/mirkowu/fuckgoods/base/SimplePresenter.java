package com.mirkowu.fuckgoods.base;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/10/10 0010.
 */

public class SimplePresenter extends RxPresenter<SimpleContract.View>
        implements SimpleContract.Presenter {
    @Inject
    public SimplePresenter() {
    }
}
