package com.mirkowu.fuckgoods.ui.article;

import android.support.v7.widget.Toolbar;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mirkowu.fuckgoods.R;
import com.mirkowu.fuckgoods.base.SimpleContract;
import com.mirkowu.fuckgoods.base.SimplePresenter;
import com.mirkowu.fuckgoods.base.SwipeBackActivity;
import com.mirkowu.fuckgoods.widget.CommonToolbar;

import butterknife.BindView;

public class AircleActivity extends SwipeBackActivity<SimplePresenter> implements SimpleContract.View {

    @BindView(R.id.mWebView)
    WebView mWebView;

    @Override
    protected int getLayout() {
        return R.layout.activity_aircle;
    }

    @Override
    protected Toolbar getToolbar() {
        return new CommonToolbar.Builder().build(this);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initialize() {
        String url = getIntent().getStringExtra("url");
        loadWebView(url);
    }

    private void loadWebView(String url) {
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); //启用支持javascript
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局

        webSettings.setBuiltInZoomControls(true);//设置支持缩放
        webSettings.setSupportZoom(true);//支持缩放
        // webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存
        // mWebView.setWebChromeClient(new WebChromeClient());//支持各种事件
        mWebView.setWebChromeClient(new WebChromeClient());//支持各种事件
    }
}
