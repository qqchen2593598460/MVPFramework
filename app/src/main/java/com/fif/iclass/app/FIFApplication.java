package com.fif.iclass.app;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Vibrator;
import com.baidu.mapapi.SDKInitializer;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ThreadPoolUtils;
import com.blankj.utilcode.util.Utils;
import com.fif.baselib.crash.AECHConfiguration;
import com.fif.baselib.crash.AECrashHelper;
import com.fif.baselib.utils.AppConstants;
import com.fif.baselib.utils.StringUtils;
import com.fif.baselib.widget.toasty.Toasty;
import com.fif.iclass.common.utils.Constant;
import com.fif.iclass.main.LoginActivity;
import com.fif.iclass.service.LocationService;
import com.iflytek.cloud.SpeechUtility;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import java.util.List;
import greendao.DaoMaster;
import greendao.DaoSession;
import greendao.UserVO;
import greendao.UserVODao;

/**
 * Created by chen
 */

public class FIFApplication extends Application {

    private static DaoMaster daoMaster;
    private static String userId;
    private UserVO userVO;
    private LocationService locationService;
    public Vibrator mVibrator;
    private static FIFApplication instance;
    private ThreadPoolUtils poolUtils;


    /**
     * 初始化
     */
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化数据参数
        initData();
        //初始化库配置
        initLib();
        //初始化路径
        initPath();
        //初始化日志工具
        //initCrash();
        //工具类
        Utils.init(getApplicationContext());
        //初始化dialog，toast
        initAlert();
    }

    /**
     * 初始化数据参数
     */
    public void initData() {
        userVO = null;
    }

    /**
     * 单例获取application实例
     *
     * @return FIFApplication
     */
    public static FIFApplication getInstance() {
        return instance;
    }


    /**
     * 初始化数据库
     */
    private void initLib() {
        //初始化数据库
        DaoMaster.DevOpenHelper dbHelper = new DaoMaster.DevOpenHelper(this, Constant.DB_NAME, null);
        daoMaster = new DaoMaster(dbHelper.getWritableDatabase());
        //初始化友盟+消息推送
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDebugMode(true);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token,将token保存在本地，在home界面将token进行上传
                SPUtils.getInstance().put("deviceToken", deviceToken);
            }
            @Override
            public void onFailure(String s, String s1) {

            }
        });
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {


            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
    }

    /**
     * 获取daoSession
     */
    public DaoSession getDaoSession() {
        if (daoMaster == null) {
            DaoMaster.DevOpenHelper dbHelper = new DaoMaster.DevOpenHelper(this, Constant.DB_NAME, null);
            daoMaster = new DaoMaster(dbHelper.getWritableDatabase());
        }
        return daoMaster.newSession();
    }

    /**
     * 初始化路径
     */
    private void initPath() {
        //设置缓存路径
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            // /storage/emulated/0/Android/data/com.fif.campus/cache
            cachePath = getExternalCacheDir().getPath();
        } else {
            // /data/user/0/com.fif.campus/cache/data/user/0/com.fif.campus/cache
            cachePath = getCacheDir().getPath();
        }
        AppConstants.initPath(cachePath);
    }

    private void initAlert() {
        Toasty.Config.getInstance().setTextSize(12).apply();
    }

    /**
     * 初始化日志工具
     */
    private void initCrash() {
        AECrashHelper.initCrashHandler(this, new AECHConfiguration.Builder()
                .setSaveToLocal(true)
                .setReportToServer(true)
                .setReporter((e) -> {
                    //TODO 是否需要将日志发送至服务器
                })
                .setLocalFolderPath(AppConstants.LOGS_PATH)//设置本地异常日志文件存储路径
                .build());
    }

    /**
     * 设置
     *
     * @param userId 用户id，用于在数据库中查找用户信息
     */
    public void setUserId(String userId) {
        FIFApplication.userId = userId;
    }

    /**
     * 获取用户信息
     */
    public UserVO getUserVO() {
        synchronized (FIFApplication.class) {
            //因为app会在权限进行改变的时候重启app，当在设置界面修改权限再返回app的时候，因为没有走登录界面，userId为null
            //其他界面在获取用户的时候导致空指针，在登录完成之后保存临时用户id , 在application重启的时候获取，具体参考http://www.jianshu.com/p/cb68ca511776
            if (!StringUtils.isNotBlank(userId)) {
                userId = SPUtils.getInstance().getString("userId");
            }
            List<UserVO> userVOs = getDaoSession().getUserVODao().queryBuilder()
                    .where(UserVODao.Properties.UserId.eq(userId))
                    .build()
                    .list();
            if (userVOs.size() > 0) {
                return userVOs.get(0);
            } else {
                //跳至登陆界面
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return new UserVO();
            }
        }
    }

    /**
     * 获取线程池对象
     */
    public ThreadPoolUtils getPoolUtils() {
        synchronized (this) {
            if (poolUtils == null) {
                poolUtils = new ThreadPoolUtils(0, 4);
            }
            return poolUtils;
        }
    }
}
