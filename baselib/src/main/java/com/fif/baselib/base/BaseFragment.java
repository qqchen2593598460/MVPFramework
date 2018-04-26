package com.fif.baselib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fif.baselib.pagestate.PageManager;
import com.fif.baselib.utils.FragmentUtils;

/**
 * Created by chen on 2017/5/15. fragment基类
 */

public abstract class BaseFragment extends Fragment {

    protected View view;
    protected BaseActivity baseActivity;
    protected WeakHandler weakHandler;
    public PageManager pageManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBaseActivity();
         if(view==null){
            view = initView(inflater , container , savedInstanceState);
            initLib();
            loadData();
            initTitle();
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        if (view == null){
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        FragmentUtils.add(this);
        if (pageManager != null) pageManager.setView(this);
        return view;
    }

    public void initTitle() {

    }

    /**
     * 数据
     */
    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void loadData();
    /**
     * 重新加载数据
     */
    public void retryLoadData(){
    }
    /**
     * 初始化库
     */
    protected void initLib(){

    }

    /**
     * 初始化Activity,供子类使用
     */
    private void initBaseActivity() {
        if (baseActivity == null){
            baseActivity = (BaseActivity) getActivity();
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

    public void setStateLayout(ViewGroup container) {
        //初始化Handler
        //初始化PageStateManager
        if (pageManager == null) {
            pageManager = new PageManager();
            pageManager.setView(this);
        }
        pageManager.init(container);
        weakHandler = new WeakHandler(getActivity() , pageManager);
    }
}
