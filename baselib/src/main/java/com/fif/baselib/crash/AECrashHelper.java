package com.fif.baselib.crash;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.fif.baselib.utils.LogUtils;

/**
 * Created by TaiJL on 2016/8/2.
 */
public class AECrashHelper {

    public static void initCrashHandler(Application app) {
        LogUtils.d("initCrashHandler" , "");
        Context appContext = app.getApplicationContext();

        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                if (appProcess.processName.equalsIgnoreCase(appContext.getPackageName())) {
                    Thread.setDefaultUncaughtExceptionHandler(AECrashHandler.getInstance(appContext, new AECHConfiguration.Builder().build()));
                    LogUtils.d(AECrashHandler.TAG  , "setDefaultUncaughtExceptionHandler");
                }
            }
        }
    }

    public static void initCrashHandler(Application app,AECHConfiguration config) {
        LogUtils.d(AECrashHandler.TAG  , "initCrashHandler");
        Context appContext = app.getApplicationContext();

        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                if (appProcess.processName.equalsIgnoreCase(appContext.getPackageName())) {
                    Thread.setDefaultUncaughtExceptionHandler(AECrashHandler.getInstance(appContext,config));
                    LogUtils.d(AECrashHandler.TAG  , "setDefaultUncaughtExceptionHandler");
                }
            }
        }
    }

}
