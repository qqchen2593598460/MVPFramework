package com.fif.baselib.crash;

import android.content.Context;
import android.os.Looper;
import android.os.Process;
import android.os.SystemClock;

import com.fif.baselib.R;
import com.fif.baselib.utils.LogUtils;
import com.fif.baselib.utils.ToastUtil;

import java.io.File;

/**
 * Created by TaiJL on 2016/8/2.
 */
public class AECrashHandler implements Thread.UncaughtExceptionHandler {
    private static AECrashHandler ourInstance = null;
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;
    public static final String TAG = "AECcrash";
    private Context mContext;
    private AECHConfiguration config;

    public static AECrashHandler getInstance(Context appContext, AECHConfiguration config) {
        synchronized (AECrashHandler.class) {
            if (ourInstance == null) {
                ourInstance = new AECrashHandler();
                ourInstance.defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
                ourInstance.mContext = appContext;
                ourInstance.config = config;
            }
        }
        return ourInstance;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && null != defaultUncaughtExceptionHandler) {
            defaultUncaughtExceptionHandler.uncaughtException(thread, ex);
        } else {
            SystemClock.sleep(1000);
            Process.killProcess(Process.myPid());
            System.exit(0);
        }
    }

    private boolean handleException(Throwable ex) {
        if (null == ex) {
            return false;
        } else {
            if (null == config || (!config.isReportToServer() && !config.isSaveToLocal())) {
                return false;
            }
            sendCrashToServer(ex);
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    ToastUtil.showShortToast(mContext, mContext.getResources().getString(R.string.error_toast));
                    Looper.loop();
                }
            }.start();
            saveCrashToLocal(ex);
        }
        return true;
    }

    /**
     * save crash to local
     */
    private void saveCrashToLocal(Throwable ex) {
        LogUtils.d(AECrashHandler.TAG  , "saveCrashToLocal");
        if (config.isSaveToLocal()) {
            String localFolderPath = config.getLocalFolderPath();
            if (null != localFolderPath && localFolderPath.length() > 2) {
                File file = new File(localFolderPath);
                file.mkdirs();
                if (file.exists() && file.isDirectory()) {
                    AECHFileWriter.getInstance(mContext).writeEx2File(ex, localFolderPath);
                } else {
                    AECHFileWriter.getInstance(mContext).writeEx2File(ex);
                }
            } else {
                AECHFileWriter.getInstance(mContext).writeEx2File(ex);
            }
        }
    }

    /**
     * report crash to Server
     */
    private void sendCrashToServer(Throwable ex) {
        LogUtils.d(AECrashHandler.TAG  , "sendCrashToServer");
        if (config.isReportToServer()) {
            if (null != config) {
                config.getReporter().report(ex);
            }
        }
    }
}
