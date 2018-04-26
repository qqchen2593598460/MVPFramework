package com.fif.iclass.message.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fif.baselib.base.BaseFragment;
import com.fif.iclass.R;
import com.fif.iclass.message.contract.MessageContract.MessageInterface;

/**
 * Created by 消息列表 2017-11-02.
 */

public class MessageFragment extends BaseFragment implements MessageInterface.MessageIView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message, null);
        return view;
    }

    @Override
    protected void loadData() {
    }

    @Override
    public void retryLoadData() {
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public BaseFragment getInstance() {
        return this;
    }




    @Override
    public void onLoadMoreRequested() {
    }
}
