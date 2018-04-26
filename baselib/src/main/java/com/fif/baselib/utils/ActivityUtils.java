package com.fif.baselib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * by chen
 */

public class ActivityUtils {

    public static void startActivity(Context context , Class<?> clz) {
        context.startActivity(new Intent(context , clz));
    }

    public static void startActivity(Context context , Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(context, clz);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void offKeyboard(@NonNull EditText editText) {
        if (detectKeyboard(editText)) {
            InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    private static boolean detectKeyboard(@NonNull EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    //隐藏状态栏
    public static void hideStatusBar(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);
    }

    //显示状态栏
    public static void showStatusBar(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);
    }
    // 声明一个集合用于记录所有打开的活动
    private static ArrayList<Activity> list = new ArrayList<>();

    // 加入活动对象--------->onCreate
    public static void add(Activity activity) {
        list.add(activity);
    }

    // 移除活动对象--------->onDestroy
    public static void remove(Activity activity) {
        list.remove(activity);
    }

    // 关闭所有的活动--------->close
    public static void removeAll() {
        for (Activity activity : list) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
