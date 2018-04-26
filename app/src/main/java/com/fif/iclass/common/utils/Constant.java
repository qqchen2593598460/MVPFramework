package com.fif.iclass.common.utils;


import com.fif.iclass.R;

/**
 * Created by chen on 2017/5/3. 常量
 */

public class Constant {

    public static final class ConValue {

        public static int mAppText[] = {R.string.app_home,
                R.string.app_message, R.string.app_me};
        public static int mImageViewArray[] = { R.drawable.selector_btn_tab_home,R.drawable.selector_btn_tab_message,R.drawable.selector_btn_tab_me, };
        public static int mTabText[] = {R.string.home,
                 R.string.message, R.string.me};
    }

    public static final class ConLineState {
        // 异常
        public static final int LOGINFRAME = -10;
        public static final int ERROR_OTHER = -8;
        public static final int ERROR_INTERNET_TIMEOUT = -4;
        public static final int ERROR_USERANDPASSWORD = -5;
        public static final int ERROR_LOGINWRONG = -6;
        public static final int ERROR_CONNECT = -7;
        public static final int SUCCESS = 200;
    }
    public static final String  DB_NAME = "fif-iclass";

}
