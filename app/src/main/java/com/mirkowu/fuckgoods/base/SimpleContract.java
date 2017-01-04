package com.mirkowu.fuckgoods.base;

/**
 * Created by Administrator on 2016/10/10 0010.
 */

public interface SimpleContract {
    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter<View> {
    }
}
