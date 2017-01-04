package com.mirkowu.fuckgoods.util;

import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

import com.mirkowu.fuckgoods.app.App;


/**
 * Created by codeest on 2016/8/4.
 */
public class ToastUtil {
    public static Toast mToast;

    public static void show(String message, int duration) {
        if (mToast == null) {

            mToast = Toast.makeText(App.getInstance(), message, duration);
        } else {
            mToast.setDuration(duration);
            mToast.setText(message);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    /**
     * short
     *
     * @param msg
     */
    public static void s(String msg) {
        show(msg, Toast.LENGTH_SHORT);
    }

    public static void s(@StringRes int msgId) {
        show(App.getInstance().getResources().getString(msgId), Toast.LENGTH_SHORT);
    }

    /**
     * long
     *
     * @param msg
     */
    public static void l(String msg) {
        show(msg, Toast.LENGTH_LONG);
    }

    public static void l(@StringRes int msgId) {
        show(App.getInstance().getResources().getString(msgId), Toast.LENGTH_LONG);
    }
}
