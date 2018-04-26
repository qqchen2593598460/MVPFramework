package com.fif.baselib.config;

/**
 * Created by chen on 2017/5/22. 不同的页面状态
 */

public class PageStateConfig {
    /**
     *  -1 取消  0 : 加载中 1：空内容状态 2：发生错误重试
     */
    public static final int LOADING = 0;

    public static final int EMPTY = 1;

    public static final int ERROR = 2;

    public static final int NO_NET = 3;

    public static final int SUCCESS = -1;
}
