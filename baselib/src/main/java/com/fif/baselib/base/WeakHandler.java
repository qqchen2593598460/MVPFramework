package com.fif.baselib.base;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.fif.baselib.config.PageStateConfig;
import com.fif.baselib.pagestate.PageManager;

import java.lang.ref.WeakReference;

/**
 * Created by chen on 2017/5/22.防止内存泄漏，处理消息
 */

public class WeakHandler extends Handler {

    private WeakReference<Object> mActivityReference;

    private Context context;

    private PageManager pageManager;

    WeakHandler(Context context , PageManager pageManager ) {
        this.context = context;
        this.pageManager = pageManager;
    }

    /**
     * 根据不同状态进行处理
     */
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case PageStateConfig.LOADING:
                pageManager.sendMsg(PageStateConfig.LOADING);
                break;
            case PageStateConfig.EMPTY:
                pageManager.sendMsg(PageStateConfig.EMPTY);
                break;
            case PageStateConfig.ERROR:
                pageManager.sendMsg(PageStateConfig.ERROR);
                break;
            case PageStateConfig.NO_NET:
                pageManager.sendMsg(PageStateConfig.NO_NET);
                break;
            case PageStateConfig.SUCCESS:
                pageManager.sendMsg(PageStateConfig.SUCCESS);
                break;
        }
        super.handleMessage(msg);
    }
}
