package com.mirkowu.fuckgoods.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.mirkowu.fuckgoods.R;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/27 0027.
 */

public class ImageUtil {
    public static void load(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.loading)
                .into(imageView);
    }

    public static void load(ImageView imageView, String url, RequestListener<String, GlideDrawable> listener) {
        Glide.with(imageView.getContext())
                .load(url)
                .listener(listener)
                .thumbnail(0.5f)
                .dontAnimate()
                .into(imageView);
    }

    public static void loadCircle(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .bitmapTransform(new GlideCircleTransform(imageView.getContext()))
                .placeholder(R.mipmap.header_white)
                .error(R.mipmap.header_white)
                .dontAnimate()
                .into(imageView);
    }

    public static void loadAsGif(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .asGif()
                .thumbnail(0.5f)
                .dontAnimate()
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.loading)
                .into(imageView);
    }

    public static void loadAsGif(ImageView imageView, String url, RequestListener<String, GifDrawable> listener) {
        Glide.with(imageView.getContext())
                .load(url)
                .asGif()
                .thumbnail(0.5f)
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.loading)
                .listener(listener)
                .into(imageView);
    }

    public static void clearCache(Context context) {
        Observable.create((Observable.OnSubscribe<Boolean>) subscriber -> Glide.get(context).clearDiskCache())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    ToastUtil.s(R.string.clearCache_successed);
                });
    }
}
