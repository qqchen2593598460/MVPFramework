package com.fif.baselib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Window;

import com.fif.baselib.R;
import com.fif.baselib.config.PageStateConfig;
import com.fif.baselib.pagestate.PageManager;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.umeng.message.PushAgent;

public abstract class BaseActivity extends AppCompatActivity {

    protected WeakHandler weakHandler;
    //页面管理
    public PageManager pageManager;
    //状态栏管理
    public ImmersionBar immersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        immersionBar = ImmersionBar.with(this);
        setStatusBar();
        initView();
        initTitle();
        initLib();
        loadData();
        //ActivityUtils.add(this);
    }

    /**
     * 初始化界面
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void loadData();
    /**
     * 重新记载数据
     */
    public void retryLoadData(){
        sendHandlerMeg(PageStateConfig.LOADING);
        loadData();
    }

    /**
     * 初始化库
     */
    protected void initLib() {
        //友盟统计
        PushAgent.getInstance(this).onAppStart();
    }

    public void initTitle(){
/*        findViewById(R.id.tv_finish).setVisibility(View.GONE);
        ((TextView)findViewById(R.id.tv_title)).setText(getResources().getString(R.string.about_us));
        findViewById(R.id.iv_finish).setOnClickListener(v -> this.finish());
        findViewById(R.id.tv_save).setVisibility(View.GONE);*/
    }

    /**
     * 设置状态栏颜色状态栏， 默认白色
     */
    public void setStatusBar() {
        immersionBar.fitsSystemWindows(true)
                    .statusBarColor(R.color.white)//使用该属性,必须指定状态栏颜色
                    .statusBarDarkFont(true, 0.2f)
                    .init();   //所有子类都将继承这些相同的属性
    }

    /**
     * 状态栏透明
     * @param isBarDark 字体是否为黑色  true 是
     */
    public void transStatusBar(boolean isBarDark) {
        if(ImmersionBar.isSupportStatusBarDarkFont()){
            immersionBar.fitsSystemWindows(false)
                        .fixMarginAtBottom(true)   //已过时，当xml里使用android:fitsSystemWindows="true"属性时,解决4.4和emui3.1手机底部有时会出现多余空白的问题，默认为false，非必须
                        .statusBarColor(R.color.transparent)
                        .statusBarDarkFont(isBarDark, 0.2f)
                        .init();
        }
    }

    /**
     * 发送消息
     *
     * @param pageState 页面状态
     *  0 : 加载中 1：空内容状态 2：发生错误重试
     */
    public void sendHandlerMeg(int pageState) {
        if (weakHandler != null){
            weakHandler.sendEmptyMessage(pageState);
        }
    }

    public void setStateLayout(@Nullable  ViewGroup container) {
        //初始化Handler
        //初始化PageStateManager
        if (pageManager == null) {
            pageManager = new PageManager();
            pageManager.setView(this);
        }
        pageManager.init(container);
        weakHandler = new WeakHandler(this , pageManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (immersionBar != null)
            immersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }
}
