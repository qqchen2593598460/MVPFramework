package com.fif.iclass.discovery.model;

import android.util.ArrayMap;

import com.fif.baselib.base.ExceptionHandle;
import com.fif.iclass.common.bean.UserTypeBean;
import com.fif.iclass.common.http.NetWorkRequest;
import com.fif.iclass.common.http.NetWorkSubscriber;
import com.fif.iclass.discovery.contract.DiscoveryContract;

/**
 * Created by chen on 2017-06-20.
 */

public class DiscoveryModel implements DiscoveryContract.DiscoveryInterface.DiscoveryIModel {

    private DiscoveryContract.DiscoveryInterface.DiscoveryIListener listener;

    public DiscoveryModel(DiscoveryContract.DiscoveryInterface.DiscoveryIListener listener) {
        this.listener = listener;
    }
}
