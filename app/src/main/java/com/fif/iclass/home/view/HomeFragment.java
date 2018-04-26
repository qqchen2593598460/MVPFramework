package com.fif.iclass.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fif.baselib.base.BaseFragment;
import com.fif.iclass.R;
import com.fif.iclass.home.contract.HomeContract;

/**
 * Created by chen on 2017/5/2. home界面
 */

public class HomeFragment extends BaseFragment implements HomeContract.HomeInterface.HomeIView, SwipeRefreshLayout.OnRefreshListener {

    private HomeContract.HomeInterface.HomeIPresenter presenter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }
    @Override
    protected void loadData() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    /**
     * 获取activity回调数据
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public BaseFragment getInstance() {
        return null;
    }
}
