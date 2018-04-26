package com.fif.iclass.home.presenter;

import android.util.ArrayMap;
import com.fif.baselib.base.BaseFragment;
import com.fif.iclass.common.bean.AppInfoBean;
import com.fif.iclass.home.model.HomeModel;
import com.fif.iclass.home.contract.HomeContract;

/**
 * Created by chen on 2017-06-28. homep
 */

public class HomePresenter implements HomeContract.HomeInterface.HomeIPresenter, HomeContract.HomeInterface.HomeIListener {

    private HomeContract.HomeInterface.HomeIView homeFragment;
    private HomeContract.HomeInterface.HomeIModel homeModel;

    public HomePresenter(HomeContract.HomeInterface.HomeIView homeFragment){
        this.homeFragment = homeFragment;
        this.homeModel = new HomeModel(this);
    }

    @Override
    public BaseFragment getInstance() {
        return homeFragment.getInstance();
    }
}
