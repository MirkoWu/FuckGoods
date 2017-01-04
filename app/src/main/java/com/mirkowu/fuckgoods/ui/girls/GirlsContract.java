package com.mirkowu.fuckgoods.ui.girls;

import com.mirkowu.fuckgoods.base.BasePresenter;
import com.mirkowu.fuckgoods.base.BaseView;
import com.mirkowu.fuckgoods.network.bean.GankBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/10 0010.
 */

public interface GirlsContract {
    interface View extends BaseView {
        void  getGirls(List<GankBean> data);
    }

    interface Presenter extends BasePresenter<View> {
       void getGirls(int pageNum,int page);
    }
}
