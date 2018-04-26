package com.fif.iclass.me.presenter;

import android.util.ArrayMap;

import com.fif.baselib.base.BaseFragment;
import com.fif.iclass.common.bean.UpdateBean;
import com.fif.iclass.me.contract.MeContract.MeInterface;
import com.fif.iclass.me.model.MeModel;

/**
 * Created by
 */

public class MePresenter implements MeInterface.MeIPresenter, MeInterface.MeIListener{

    private MeInterface.MeIModel model;
    private MeInterface.MeIView view;

    public MePresenter(MeInterface.MeIView view) {
        this.view = view;
        this.model = new MeModel(this);
    }

    @Override
    public BaseFragment getInstance() {
        return view.getInstance();
    }
}
