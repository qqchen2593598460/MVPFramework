package com.fif.baselib.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by chen
 */

public class ToastUtil {

    public static void showShortToast(Context context , String str){
        if (!TextUtils.isEmpty(str)){
            Toast.makeText(context , str , Toast.LENGTH_SHORT).show();
        }
    }

    public static void showLongToast(Context context , String str){
        if (!TextUtils.isEmpty(str)){
            Toast.makeText(context , str , Toast.LENGTH_LONG).show();
        }
    }
}
