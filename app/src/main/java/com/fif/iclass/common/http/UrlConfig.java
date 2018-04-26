package com.fif.iclass.common.http;

/**
 * by y on 2016/4/28.
 */
public class UrlConfig {

    //正式服务器 一级域名
    //public static final String BASE_API = "http://show.xfcampus.com/";
    public static final String BASE_API = "http://testdemo.xfcampus.com/";
    //文档服务器：
    public static final String BASE_API_DOC = "http://doc.xfcampus.com/";
    //正式服务器 二级域名
    private static final String BASE_API_DES = "school-mobile/";
    //成功
    public static final String SUCCESS = "success";
    //登录
    static final String APP_LOGIN = BASE_API_DES + "mobile/user/login.do";
    //获取APP版本信息
    static final String GET_APP_VERSION = BASE_API_DES + "mobile/getAppVersion.do";
}
