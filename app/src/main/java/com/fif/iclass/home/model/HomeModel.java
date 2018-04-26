package com.fif.iclass.home.model;

import android.util.ArrayMap;
import com.fif.baselib.base.ExceptionHandle;
import com.fif.iclass.common.bean.AppInfoBean;
import com.fif.iclass.common.http.NetWorkRequest;
import com.fif.iclass.common.http.NetWorkSubscriber;
import com.fif.iclass.home.contract.HomeContract.HomeInterface;

/**
 * Created by chen on 2017-06-28. homem
 */

public class HomeModel implements HomeInterface.HomeIModel {

    private HomeInterface.HomeIListener listener;
    public HomeModel(HomeInterface.HomeIListener listener){
        this.listener = listener;
    }
}
