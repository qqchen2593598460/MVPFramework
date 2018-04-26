package com.fif.baselib.utils;

import android.os.Environment;

import java.io.File;

/**
 * 常用路径变量值
 *
 * @author qqchen
 */
public class AppConstants {

    private static String APP_PATH = Environment.getExternalStorageDirectory() + File.separator + "FiF" +  File.separator;
    /**
     * logs 文件
     */
    public static String LOGS_PATH = "";




    public static void initPath(String string){
        DOWNLOAD_PATH = APP_PATH + "download" + File.separator;
        LOGS_PATH = APP_PATH + "logs" + File.separator;
        //download
        PIC_DOWNLOAD_PATH = APP_PATH + "image" + File.separator;
        FILE_DOWNLOAD_PATH = APP_PATH + "files" + File.separator;
        UNFILE_DOWNLOAD_PATH = APP_PATH + "unzip" + File.separator;
        APK_DOWNLOAD_PATH = APP_PATH + "apk" + File.separator;

    }

    //--------------------------------------------------------------------------//
    //下载相关
    /**
     * 下载文件的存储地址
     */
    public static String DOWNLOAD_PATH = "";
    /**
     * 图片下载地址
     */
    public static  String PIC_DOWNLOAD_PATH = "";
    /**
     * 普通文件下载存储地址
     */
    public static  String FILE_DOWNLOAD_PATH = "";
    /**
     * 解压文件存储地址
     */
    public static  String UNFILE_DOWNLOAD_PATH = "";

    /**
     * APK文件下载地址
     */
    public static  String APK_DOWNLOAD_PATH = "";

}
