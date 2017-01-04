package com.mirkowu.fuckgoods.network.rtrofit;


import com.mirkowu.fuckgoods.base.BaseView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.schedulers.Schedulers;

/**
 * Created by Lightwave on 2016/6/28.
 */
public class NetworkTransformerHelper<T> implements Observable.Transformer<BaseBean<T>, T> {
    private BaseView view;

    public NetworkTransformerHelper(BaseView view) {
        this.view = view;
    }

    @Override
    public Observable<T> call(Observable<BaseBean<T>> baseBeanObservable) {
        return baseBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(view::showProgressDialog)
                .doOnTerminate(view::hideProgressDialog)
                .map(baseBean -> {
                    if (!baseBean.error) {
                        return baseBean;
                    } else {
                        view.hideProgressDialog();
                        //TODO   ToastUtil.s(baseBean.errorMsg); 还是放到activity 和fragment 显示吧
                        throw Exceptions.propagate(new RuntimeException("返回数据出错"));
                    }
                })
                .map(baseBean -> baseBean.results);
        // .compose(view.getLifecycle());//这里还要想办法来关联view的生命周期
    }
}
