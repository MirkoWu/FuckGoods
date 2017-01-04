package com.mirkowu.fuckgoods.app;

import android.app.Activity;
import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.karumi.dexter.Dexter;
import com.mirkowu.fuckgoods.di.component.AppComponent;
import com.mirkowu.fuckgoods.di.component.DaggerAppComponent;
import com.mirkowu.fuckgoods.di.module.AppModule;
import com.squareup.leakcanary.RefWatcher;

import java.util.Stack;

/**
 * Created by Administrator on 2016/9/10 0010.
 */
public class App extends Application {
    private Stack<Activity> activities;
    private static App instance;

    public static synchronized App getInstance() {
        return instance;
    }

    public static RefWatcher refWatcher;

    /**
     * 设置日夜模式
     */
    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        activities = new Stack<>();

        //初始化权限
        Dexter.initialize(getApplicationContext());
        //初始化内存泄漏检测
        //  refWatcher= LeakCanary.install(this);
    }


    /**
     * 添加
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activities != null && activity != null)
            activities.add(activity);
    }

    /**
     * 移除
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activities != null && activity != null && activities.contains(activity))
            activities.remove(activity);
    }

    /**
     * 结束指定Class的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activities) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                return;
            }
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activities.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束app
     */
    public void exitApp() {
        if (activities != null) {
            synchronized (activities) {
                for (Activity act : activities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();
    }
}
