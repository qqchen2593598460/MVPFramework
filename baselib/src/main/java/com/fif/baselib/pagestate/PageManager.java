package com.fif.baselib.pagestate;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fif.baselib.R;
import com.fif.baselib.base.BaseActivity;
import com.fif.baselib.base.BaseFragment;
import com.fif.baselib.config.PageStateConfig;

/**
 * Created by chen on 2017-08-30. 用于管理界面的不同状态
 */

public class PageManager {
    //加载中
    private  int PAGE_STATE_LOADING = R.layout.pager_loading;
    //空内容状态
    private  int PAGE_STATE_EMPTY = R.layout.pager_empty;
    //加载出错
    private  int PAGE_STATE_ERROR = R.layout.pager_error;
    //无网络
    private  int PAGE_STATE_NO_NET = R.layout.pager_error;

    private ViewGroup container;

    private int currentPage;

    /**
     * 设置成单例模式
     */
    @SuppressLint("StaticFieldLeak")
    private Object parent;
    private View vLoading;
    private View vEmpty;
    private View vError;
    private View vNoNet;
    public PageManager(){}
    /**
     * 对界面进行初始化
     */
    public void init(ViewGroup container){

        this.container = container;
        container.removeAllViews();
        vLoading = LayoutInflater.from(container.getContext()).inflate(PAGE_STATE_LOADING , null);
        vEmpty = LayoutInflater.from(container.getContext()).inflate(PAGE_STATE_EMPTY , null);
        vError = LayoutInflater.from(container.getContext()).inflate(PAGE_STATE_ERROR , null);
        vNoNet = LayoutInflater.from(container.getContext()).inflate(PAGE_STATE_NO_NET, null);
        initListener();
    }

    private void initListener() {
        vNoNet.findViewById(R.id.error_btn_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parent != null) {
                   if (parent instanceof BaseActivity) {
                       ((BaseActivity)parent).retryLoadData();
                   }
                   if (parent instanceof BaseFragment) {
                       ((BaseFragment)parent).retryLoadData();
                   }
                }
            }
        });
    }

    /**
     * 自定义界面
     */
    public void customLayout(int loading , int error , int empty , int no_net){

        PAGE_STATE_LOADING = loading;
        PAGE_STATE_EMPTY = empty;
        PAGE_STATE_ERROR = error;
        PAGE_STATE_NO_NET = no_net;
    }

    /**
     * 根据不同的状态显示和隐藏布局
     * @param stateConfig 页面状态
     */
    public void sendMsg(int stateConfig) {
        if(container == null) return;
        container.removeAllViews();
        container.setVisibility(View.VISIBLE);
        currentPage = stateConfig;
        switch (stateConfig) {
            case PageStateConfig.LOADING:
                container.addView(vLoading);
                break;
            case PageStateConfig.EMPTY:
                container.addView(vEmpty);
                break;
            case PageStateConfig.ERROR:
                container.addView(vError);
                break;
            case PageStateConfig.NO_NET:
                container.addView(vNoNet);
                break;
            case PageStateConfig.SUCCESS:
                container.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 设置上下文
     */
    public void setView(Object parent){
        this.parent = parent;
    }
    /**
     * 根据msg获取当前的布局文件
     */
    public View getContainer(int stateConfig){
        switch (stateConfig) {
            case PageStateConfig.LOADING:
                return vLoading;
            case PageStateConfig.EMPTY:
                return vEmpty;
            case PageStateConfig.ERROR:
                return vError;
            case PageStateConfig.NO_NET:
                return vNoNet;
        }
        return null;
    }

    /**
     * 获取当前界面展示的状态
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 设置空内容状态下，布局界面显示
     */
    public void setEmptyDes(@Nullable String des, @Nullable int img) {
        if (vEmpty != null) {
            vEmpty.findViewById(R.id.tv_msg_empty).setVisibility(View.VISIBLE);
            ((ImageView)vEmpty.findViewById(R.id.empty_icon)).setImageResource(img);
            ((TextView)vEmpty.findViewById(R.id.tv_msg_empty)).setText(des);
            vEmpty.findViewById(R.id.tv_empty_title).setVisibility(View.GONE);
        }
    }
    /**
     * 设置空内容状态下，布局界面显示标题
     */
    public void setEmptyTitle(@Nullable String des, @Nullable int img) {
        if (vEmpty != null) {
            vEmpty.findViewById(R.id.tv_empty_title).setVisibility(View.VISIBLE);
            ((ImageView)vEmpty.findViewById(R.id.empty_icon)).setImageResource(img);
            ((TextView)vEmpty.findViewById(R.id.tv_empty_title)).setText(des);
            vEmpty.findViewById(R.id.tv_msg_empty).setVisibility(View.GONE);
        }
    }
}
