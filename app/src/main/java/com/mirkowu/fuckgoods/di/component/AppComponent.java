package com.mirkowu.fuckgoods.di.component;


import com.mirkowu.fuckgoods.app.App;
import com.mirkowu.fuckgoods.di.ContextLife;
import com.mirkowu.fuckgoods.di.module.AppModule;
import com.mirkowu.fuckgoods.network.request.HomeRequest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2016/9/10 0010.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ContextLife("Application")
    App getContext();  // 提供App的Context

  //  RetrofitHelper retrofitHelper();  //提供http的帮助类

  //  RealmHelper realmHelper();    //提供数据库帮助类
    HomeRequest homeRequest();    //提供数据库帮助类

}
