package com.fif.baselib.utils;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;

/**
 * Created by chen on 2017-09-26. fragment管理类
 */

public class FragmentUtils {
    // 声明一个集合用于记录所有打开的活动
    private static ArrayMap<String, Fragment> fragments = new ArrayMap<>();

    // 加入活动对象--------->onCreate
    public static void add(@Nullable Fragment fragment) {
        fragments.put(fragment.getClass().getName(), fragment);
    }

    public static ArrayMap<String, Fragment> getFragments(){
        return fragments;
    }
}
