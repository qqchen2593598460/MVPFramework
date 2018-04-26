package com.fif.baselib.crash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import com.fif.baselib.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

/**
 * Created by TaiJL on 2016/8/2.
 */
public class AECHFileWriter {
    private static AECHFileWriter ourInstance = null;
    private static Context mContext;
    private StorageStrategy storageState;
    private String crashStorePath;
    private String directory = "";

    public static AECHFileWriter getInstance(Context context) {
        synchronized (AECHFileWriter.class) {
            if (ourInstance == null) {
                ourInstance = new AECHFileWriter(context);
            }
        }
        return ourInstance;
    }

    private AECHFileWriter(Context context) {
        mContext = context;
        this.storageState = StorageStrategy.STRATEGY_NO_RECOMMEND;
        crashStorePath = judgePath();
    }

    private String judgePath() {
        String appAbsolutePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            try {
                appAbsolutePath = mContext.getExternalFilesDir("crash").toString();
                storageState = StorageStrategy.STRATEGY_EXTERNAL;
            } catch (NullPointerException e) {
                appAbsolutePath = mContext.getDir("crash", Context.MODE_PRIVATE).toString();
                storageState = StorageStrategy.STRATEGY_NO_RECOMMEND;
            }
        } else {
            appAbsolutePath = mContext.getDir("crash", Context.MODE_PRIVATE).toString();
            storageState = StorageStrategy.STRATEGY_INTERNAL;
        }
        return appAbsolutePath;
    }

    public void writeEx2File(Throwable throwable) {
        writeEx2File(throwable, null);
    }

    public void writeEx2File(Throwable throwable, String intentPath) {
        LogUtils.d(AECrashHandler.TAG , crashStorePath);
        directory = intentPath;
        if (null == directory || directory.length() < 2) {
            directory = crashStorePath;
        }
        if (throwable == null) {
            return;
        }

        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        try {
            Throwable cause = throwable.getCause();
            cause.printStackTrace(printWriter);
        } catch (NullPointerException e) {
            LogUtils.e("AECHFileWriter", "NullPointerException");
        } finally {
            printWriter.close();
        }
        final String result = writer.toString();
        final String message = throwable.getMessage();
        new Thread() {
            @Override
            public void run() {
                String fileName = obtainFileName();
                File file = new File(directory, fileName);
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                        file.setWritable(Boolean.TRUE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file, true);
                    fos.write(("=>" + "date = " + getPrintToTextTime() + "\r\n" + "=>msgs = " + message).getBytes());
                    fos.write(result.getBytes());
                    fos.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    private String obtainFileName() {
        return "crash_" + getPrintToFileTime() + ".log";
    }

    /**
     * get the current system date<br>
     * 2015-4-28
     *
     * @return the current system date
     */
    @SuppressLint("SimpleDateFormat")
    public static String getPrintToFileTime() {
        String date = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        date = sdf.format(System.currentTimeMillis());
        return date;
    }

    /**
     * get the current system date<br>
     * 2015-4-28
     *
     * @return the current system date
     */
    @SuppressLint("SimpleDateFormat")
    public static String getPrintToTextTime() {
        String date = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        date = sdf.format(System.currentTimeMillis());
        return date;
    }

    /**
     * identify storage strategy
     * 2015-12-28
     *
     * @version 0.0.2
     */
    private enum StorageStrategy {
        STRATEGY_INTERNAL,
        STRATEGY_EXTERNAL,
        STRATEGY_NONE,
        STRATEGY_NO_RECOMMEND
    }
}
