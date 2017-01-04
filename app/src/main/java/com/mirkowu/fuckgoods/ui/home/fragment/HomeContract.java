package com.mirkowu.fuckgoods.ui.home.fragment;

import com.mirkowu.fuckgoods.base.BasePresenter;
import com.mirkowu.fuckgoods.base.BaseView;
import com.mirkowu.fuckgoods.network.bean.GankBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/10 0010.
 */

public interface HomeContract {
    interface View extends BaseView {
        void  getGank(List<GankBean> data);
    }

    interface Presenter extends BasePresenter<View> {
       void getGank(String type,int pageNum, int page);
    }
}
