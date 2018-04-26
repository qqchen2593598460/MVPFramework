package com.fif.iclass.me.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fif.baselib.base.BaseFragment;
import com.fif.iclass.R;
import com.fif.iclass.me.contract.MeContract.MeInterface;

/**
 * Created by chen
 */

public class MeFragment extends BaseFragment implements MeInterface.MeIView{


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, container, false);
        return view;
    }

    @Override
    protected void loadData() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public BaseFragment getInstance() {
        return this;
    }
}
