package com.mirkowu.fuckgoods.network.rtrofit;

/**
 * Created by Administrator on 2016/11/11 0011.
 */

public class BaseBean<T> {


    public boolean error;//返回是否正确
    public T results;//返回内容

    @Override
    public String toString() {
        return "BaseBean{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
