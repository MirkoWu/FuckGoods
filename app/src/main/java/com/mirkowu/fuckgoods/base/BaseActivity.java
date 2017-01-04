package com.mirkowu.fuckgoods.base;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mirkowu.fuckgoods.BuildConfig;
import com.mirkowu.fuckgoods.R;
import com.mirkowu.fuckgoods.app.App;
import com.mirkowu.fuckgoods.di.component.ActivityComponent;
import com.mirkowu.fuckgoods.di.component.DaggerActivityComponent;
import com.mirkowu.fuckgoods.di.module.ActivityModule;
import com.mirkowu.fuckgoods.util.SPUtils;
import com.mirkowu.fuckgoods.util.ToastUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.mirkowu.fuckgoods.app.Constants.SP_NIGHT_MODE;

/**
 * Created by Administrator on 2016/9/10 0010.
 * MVP activity 基类
 */
public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity implements BaseView {
    public final String TAG = getClass().getSimpleName();
    @Inject
    protected T mPresenter;
    protected Activity mContext;
    private Unbinder mUnbinder;
    private boolean mNightMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        initInject();
        if (mPresenter != null) mPresenter.attachView(this);
        App.getInstance().addActivity(this);
        initialize();
        mNightMode = (boolean) SPUtils.get(SP_NIGHT_MODE, false);
        if (mNightMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    public void initContentView() {
        //添加标题栏
        ViewGroup view = (ViewGroup) View.inflate(this, R.layout.layout_base_toolbar, null);
        LinearLayout appBarLayout = (LinearLayout) view.findViewById(R.id.mTitleLayout);

        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
            layoutParams.weight = 1;
            layoutParams.topMargin = 0;
            appBarLayout.addView(toolbar);
            view.addView(View.inflate(this, getLayout(), null), layoutParams);

            appBarLayout.setPadding(0, 0, 0, 0);
            toolbar.setPadding(0, 0, 0, 0);
            setContentView(view);
        } else setContentView(getLayout());
    }

    public ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    public ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    @Override
    protected void onDestroy() {
        App.getInstance().removeActivity(this);
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
        mUnbinder.unbind();
    }

    @Override
    public void useNightMode(boolean isNight) {
        boolean night = (boolean) SPUtils.get(SP_NIGHT_MODE, false);
        if (night) {
            SPUtils.put(SP_NIGHT_MODE, false);
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            SPUtils.put(SP_NIGHT_MODE, true);
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        }
       // recreate();
        reload();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //检查日夜模式
        boolean night = (boolean) SPUtils.get(SP_NIGHT_MODE, false);
        if (night != mNightMode) {
            if (night) {
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO);
            }
             recreate();
           // reload();
        }
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public void startActivity(Class<? extends Activity> cls) {
        this.startActivity(new Intent(this, cls));
    }

    public Activity getActivity() {
        return mContext;
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showEror(Throwable throwable) {
        if (BuildConfig.DEBUG) throwable.printStackTrace();
        ToastUtil.s(throwable.getMessage());
    }

    /**
     * 权限提示对话框
     */
    public void showPermissionDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.tips_message)
                .setMessage(R.string.permission_lack)
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                })
                .setPositiveButton(R.string.ok, (dialog, which) -> startAppSettings()).show();
    }

    /**
     * 启动当前应用设置页面
     */
    public void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    protected abstract int getLayout();

    protected abstract Toolbar getToolbar();

    protected abstract void initInject();

    protected abstract void initialize();


}
