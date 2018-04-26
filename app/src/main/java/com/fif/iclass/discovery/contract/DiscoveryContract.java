package com.fif.iclass.discovery.contract;

import android.util.ArrayMap;

import com.fif.baselib.base.BaseActivity;
import com.fif.baselib.base.BaseFragment;
import com.fif.iclass.common.bean.UserTypeBean;

/**
 * Created by chen on 2017-06-20. 发现界面接口
 */

public interface DiscoveryContract {
    /**
     * 发现主界面
     */
    interface DiscoveryInterface {

        interface DiscoveryIModel {
        }

        interface DiscoveryIView {
            BaseFragment getInstance();
        }

        interface DiscoveryIPresenter {
        }
        interface DiscoveryIListener {
            BaseFragment getInstance();
        }
    }
}
