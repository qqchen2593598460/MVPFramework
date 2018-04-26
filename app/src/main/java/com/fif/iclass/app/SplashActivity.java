package com.fif.iclass.app;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.SPUtils;
import com.fif.baselib.utils.ActivityUtils;
import com.fif.iclass.main.LoginActivity;
import com.fif.iclass.main.MainActivity;

import java.util.List;

import greendao.UserVO;
import greendao.UserVODao;

/**
 * Created by chen on 2017-11-01. 启动页面
 */

public class SplashActivity extends AppCompatActivity{



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(R.layout.activity_splash);
/*        findViewById(R.id.iv_splash).setOnClickListener(v -> {
            ActivityUtils.startActivity(this, LoginActivity.class);
            this.finish();
        });*/
        if (!isTaskRoot()) {
            finish();
            return;
        }
        new Handler().postDelayed(() -> {
            //判断数据库中是否含有用户信息，并且用户是否已经登录
            String userId = SPUtils.getInstance().getString("userId");
            List<UserVO> userVOs = FIFApplication.getInstance().getDaoSession().getUserVODao().queryBuilder()
                    .where(UserVODao.Properties.UserId.eq(userId))
                    .build()
                    .list();
            if (userVOs.size() > 0 && userVOs.get(0).getLogin().equals("true")) {
                ActivityUtils.startActivity(this, MainActivity.class);
            } else {
                ActivityUtils.startActivity(this, LoginActivity.class);
            }
            this.finish();
        }, 1000);
    }
}
