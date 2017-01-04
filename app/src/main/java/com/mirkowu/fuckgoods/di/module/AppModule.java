package com.mirkowu.fuckgoods.di.module;

import com.mirkowu.fuckgoods.app.App;
import com.mirkowu.fuckgoods.di.ContextLife;
import com.mirkowu.fuckgoods.network.request.HomeRequest;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by codeest on 16/8/7.
 */

@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ContextLife("Application")
    App provideApplicationContext() {
        return application;
    }
//
//    @Provides
//    @Singleton
//    RetrofitHelper provideRetrofitHelper() {
//        return new RetrofitHelper();
//    }

//    @Provides
//    @Singleton
//    RealmHelper provideRealmHelper() {
//        return new RealmHelper(application);
//    }


    @Provides
    @Singleton
    HomeRequest provideHomeRequest() {
        return new HomeRequest();
    }
}
