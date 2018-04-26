package com.fif.iclass.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fif.baselib.utils.AppConstants;
import com.fif.baselib.utils.FileUtil;
import com.fif.baselib.utils.LogUtils;
import com.fif.baselib.utils.SDCardUtil;
import com.fif.baselib.utils.ToastUtil;
import com.fif.iclass.BuildConfig;
import com.fif.iclass.R;
import com.fif.iclass.common.bean.UpdateBean;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;

/**
 * 下载更新模块
 */
public class SoftwareUpdateActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String APK_DOWNLOAD_PATH = AppConstants.APK_DOWNLOAD_PATH;
    public static final String TAG = "SoftwareUpdateActivity";
    public static final int RESULT_DISMISS = 2;

    private static final int MSG_DOWNLOAD_UPDATE = 0; // 更新进度
    private static final int MSG_DOWNLOAD_COMPLETE = 1; // 下载完成
    private static final int MSG_DOWNLOAD_ERROR = 2; // 下载中异常
    private static final int MSG_DOWNLOAD_INPUT_NULL = 3; // 输入流为空
    private static final int MSG_DOWNLOAD_NOT_ENOUTH_SPACE = 4; // sd卡空间不足


    private static final String APK_NAME = "fifCampus.apk";
    /** 更新弹窗2种样式, 按钮和进度条 */
    private LinearLayout uploadLayoutButton, uploadLayoutProgress;
    /** 更新Content，Title */
    private TextView uploadTitle;
    private EditText uploadContent;
    /** 更新按钮 */
    private Button uploadButtonLeft, uploadButtonRight;
    /** 更新进度条 */
    private ProgressBar uploadProgress;
    /** 更新进度 */
    private TextView uploadPercent;

    /** 更新Model */
    private UpdateBean update;

    // 点击返回是否可以取消
    private boolean isCancelable;

    private boolean invalidFlag = false; // 界面关闭标识

    private ActivityHandler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_software_update);
        initViewUI();
    }

    /**
     * 没有数据时初始化界面
     */
    @SuppressLint("WrongConstant")
    void initViewUI(){

        mHandler = new ActivityHandler(this);

        uploadLayoutButton = (LinearLayout) this.findViewById(R.id.buttonlayout);
        uploadLayoutProgress = (LinearLayout) this.findViewById(R.id.progresslayout);
        uploadTitle = (TextView) this.findViewById(R.id.title);
        uploadContent = (EditText) this.findViewById(R.id.content);
        uploadButtonLeft = (Button) this.findViewById(R.id.no);
        uploadButtonRight = (Button) this.findViewById(R.id.yes);
        uploadProgress = (ProgressBar) this.findViewById(R.id.progress);
        uploadPercent = (TextView) this.findViewById(R.id.percent);

        uploadLayoutButton.setVisibility(View.VISIBLE);
        uploadLayoutProgress.setVisibility(View.GONE);

        setFinishOnTouchOutside(false);

        update = (UpdateBean) getIntent().getSerializableExtra("updateBean");

        if (update != null) {
            // 是否强制更新
            boolean isMustUpgrade = getIntent().getBooleanExtra("is_must_upgrade", true);
            doNewVersionUpdate(isMustUpgrade);
        }

    }



    /**
     * 版本更新
     *
     * @param mustUpdate 是否强制更新
     */
    private void doNewVersionUpdate(boolean mustUpdate) {
        StringBuilder sb = new StringBuilder();
        // 显示给用户看的信息 只显示版本名 Code号用来做判断，不给用户看
        uploadTitle.setText(getResources().getString(R.string.login_update_str_newversion) + update.getData().getLatest_version().getVersionNumber());
        if (mustUpdate) {
            // 强制更新
            isCancelable = false;
            sb.append(update.getData().getLatest_version().getVersionLog().replaceAll("\\\\n", "\n") + "\n");
            sb.append(getResources().getString(R.string.login_update_str_mustupdate));
            uploadContent.setText(sb.toString());
            uploadButtonLeft.setText(getResources().getString(R.string.login_update_cancle));
            uploadButtonRight.setText(getResources().getString(R.string.login_update_right));

        } else {
            // 普通更新
            isCancelable = true;
            sb.append(update.getData().getLatest_version().getVersionLog().replaceAll("\\\\n", "\n"));
            uploadContent.setText(sb.toString());
            uploadButtonLeft.setText(getResources().getString(R.string.login_update_left));
            uploadButtonRight.setText(getResources().getString(R.string.login_update_right));
        }
        uploadButtonRight.setOnClickListener(this);
        uploadButtonLeft.setOnClickListener(this);
    }

    /**
     * 下载子线程
     */
    public class DownloadThread extends Thread {
        @Override
        public void run() {
            InputStream is = null;

            try {
                // 创建、打开连接
                URL myUrl = new URL(update.getData().getLatest_version().getVersionUrl());
                URLConnection connection = myUrl.openConnection();
                connection.connect();
                // 得到访问内容并保存在输入流中。
                is = connection.getInputStream();
                // 得到文件的总长度。注意这里有可能因得不到文件大小而抛出异常
                int totalLength = connection.getContentLength();
                if (SDCardUtil.getSDAvailableSize() <= totalLength) {
                    if (getHandler() != null) {
                        getHandler().sendEmptyMessage(MSG_DOWNLOAD_NOT_ENOUTH_SPACE);
                    }
                    return;
                }
                int downloadCount = 0; // 历史进度
                int currentLength = 0;
                if (is != null) {
                    FileUtil.makeDir(APK_DOWNLOAD_PATH + APK_NAME);
                    // 如果文件存在，则删除该文件。
                    FileUtil.delete(new File(APK_DOWNLOAD_PATH + APK_NAME));
                    FileUtil.delete(new File(APK_DOWNLOAD_PATH + APK_NAME + ".temp"));
                } else {
                    if (getHandler() != null) {
                        getHandler().sendEmptyMessage(MSG_DOWNLOAD_INPUT_NULL);
                    }
                    return;
                }
                // RandomAccessFile随机访问的文件类，可以从指定访问位置，为以后实现断点下载提供支持
                RandomAccessFile randomAccessFile = new RandomAccessFile(APK_DOWNLOAD_PATH + APK_NAME + ".temp", "rwd"); // 临时文件
                byte[] buffer = new byte[4096];
                int length = 0;
                while (currentLength < totalLength) {
                    if (invalidFlag) {
                        // 界面关闭，关闭文件
                        is.close();
                        randomAccessFile.close();
                        return;
                    }
                    length = is.read(buffer);
                    if (length != -1) {
                        randomAccessFile.write(buffer, 0, length);
                        currentLength += length;
                        int progress = (int) (currentLength * 100 / (float) (totalLength));
                        // 控制消息发送次数，比例增长1才更新，防止app吃不消
                        if (progress - downloadCount > 0) {
                            downloadCount = progress;
                            if (getHandler() != null) {
                                Message msg = Message.obtain();
                                msg.what = MSG_DOWNLOAD_UPDATE;
                                // arg1标示实时进度
                                msg.arg1 = progress;
                                getHandler().sendMessage(msg);
                            }
                        }
                   } else {
                        LogUtils.e(TAG, "inputstream close, currentLength = " + currentLength + ", totalLength = " + totalLength);
                        break;
                    }
                }

                // 释放资源
                is.close();
                randomAccessFile.close();
                // 结束以后，标记为结束状态。
                // 这里应该检测下载包的完整性，但是RXXU说服务器返回长度与下载长度不同，影响判断
                getHandler().sendEmptyMessage(MSG_DOWNLOAD_COMPLETE);

            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.e("down", e.toString());
                // 添加异常处理
                getHandler().sendEmptyMessage(MSG_DOWNLOAD_ERROR);
            }

        }

    }

    /**
     * 消息的处理
     */
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_DOWNLOAD_UPDATE:
                // 下载进行中，更新进度条
                // 设置进度条最大值
                // activity.uploadProgress.setMax(activity.update.maxSize);
                // update.nowSize += msg.arg1;
                // int progress = (int) (update.nowSize * 100 / (float)
                // (update.maxSize));
                uploadProgress.setProgress(msg.arg1);
                uploadPercent.setText(String.valueOf(msg.arg1) + "%");
                break;
            case MSG_DOWNLOAD_COMPLETE:
                // 下载完成操作
                updateApk();
                setResult(RESULT_OK);
                finish();
                break;
            case MSG_DOWNLOAD_ERROR:
                // 下载失败
                ToastUtil.showShortToast(this, "下载更新包失败");
                setResult(RESULT_DISMISS);
                finish();
                break;
            case MSG_DOWNLOAD_INPUT_NULL:
                // 流为空
                ToastUtil.showShortToast(this, "服务错误，下载失败");
                setResult(RESULT_DISMISS);
                finish();
                break;
            case MSG_DOWNLOAD_NOT_ENOUTH_SPACE:
                // 流为空
                ToastUtil.showShortToast(this, "手机存储空间不足，无法下载安装包");
                setResult(RESULT_DISMISS);
                finish();
                break;
            default:
                break;
        }

    }

    /**
     * 安装更新
     */
    private void updateApk() {
        File f = new File(APK_DOWNLOAD_PATH + APK_NAME + ".temp");
        if (f.exists()) {
            f.renameTo(new File(APK_DOWNLOAD_PATH + APK_NAME));
            //判断是否是AndroidN以及更高的版本
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", new File(APK_DOWNLOAD_PATH + APK_NAME));
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(new File(APK_DOWNLOAD_PATH + APK_NAME)), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
            //原始代码
            /*f.renameTo(new File(APK_DOWNLOAD_PATH + APK_NAME));
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(APK_DOWNLOAD_PATH + APK_NAME)), "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
            */
        }

    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.yes: // 确定
                uploadPercent.setText("0%");
                uploadProgress.setProgress(0);
                uploadLayoutButton.setVisibility(View.GONE);
                uploadLayoutProgress.setVisibility(View.VISIBLE);
                if (SDCardUtil.isSDCardExist()) {
                    new DownloadThread().start(); // 开始下载
                } else {
                    ToastUtil.showShortToast(this, getResources().getString(R.string.login_update_no_sdcard));
                    setResult(RESULT_CANCELED);
                    finish();
                }
                break;
            case R.id.no: // 取消
                if (isCancelable) {
                    setResult(RESULT_DISMISS);
                } else {
                    setResult(RESULT_CANCELED);
                }
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (isCancelable) {
            invalidFlag = true; // 界面关闭标识
            setResult(RESULT_DISMISS);
            finish();
        }
    }

    /**
     * 处理消息的Handler，静态内部类，防止内存泄露
     *
     * @author hhsun@iflytek.com
     * @version Create Time：Jul 6, 2015 3:07:32 PM
     */
    public static class ActivityHandler extends Handler {
        WeakReference<SoftwareUpdateActivity> reference;

        public ActivityHandler(SoftwareUpdateActivity activity) {
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SoftwareUpdateActivity bf = reference.get();
            bf.handleMessage(msg);
        }

    }

    /**
     * 获得Handler对象
     */
    public Handler getHandler() {
        return mHandler;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }
}
