package com.mirkowu.fuckgoods.di.component;

import android.app.Activity;

import com.mirkowu.fuckgoods.ui.article.AircleActivity;
import com.mirkowu.fuckgoods.ui.girls.GirlsActivity;
import com.mirkowu.fuckgoods.ui.home.MainActivity;
import com.mirkowu.fuckgoods.di.ActivityScope;
import com.mirkowu.fuckgoods.di.module.ActivityModule;
import com.mirkowu.fuckgoods.ui.SplashActivity;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(SplashActivity activity);

    void inject(MainActivity activity);


    void inject(GirlsActivity girlsActivity);

    void inject(AircleActivity aircleActivity);
}
