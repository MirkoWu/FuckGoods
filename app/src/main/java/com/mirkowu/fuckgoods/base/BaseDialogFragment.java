package com.mirkowu.fuckgoods.base;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Lightwave on 2015/12/3.
 */
public abstract class BaseDialogFragment extends DialogFragment {
    public final String TAG = getClass().getSimpleName();
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(getContentView(), container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, getView());
        initContentView();
    }

//    @NonNull
//    public <T extends View> T $(@IdRes int resId) {
//        return (T) getView().findViewById(resId);
//    }
//
//    public <T extends View> T inflate(@LayoutRes int resId) {
//        return (T) View.inflate(getActivity(), resId, null);
//    }

    public abstract int getContentView();

    protected void initContentView() {
        if (!isAdded()) return;
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable());
        setStyle(DialogFragment.STYLE_NO_INPUT, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
