package com.mirkowu.fuckgoods.ui;

import android.support.v7.widget.Toolbar;

import com.mirkowu.fuckgoods.R;
import com.mirkowu.fuckgoods.base.SimpleActivity;
import com.mirkowu.fuckgoods.ui.home.MainActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;

public class SplashActivity extends SimpleActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initialize() {
        Observable.timer(2, TimeUnit.SECONDS).subscribe(aLong -> {
            startActivity(MainActivity.class);
            finish();
        });
    }
}
