package com.mirkowu.fuckgoods.di.component;

import android.app.Activity;

import com.mirkowu.fuckgoods.di.FragmentScope;
import com.mirkowu.fuckgoods.di.module.FragmentModule;
import com.mirkowu.fuckgoods.ui.home.fragment.HomeFragment;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(HomeFragment fragment);

}
