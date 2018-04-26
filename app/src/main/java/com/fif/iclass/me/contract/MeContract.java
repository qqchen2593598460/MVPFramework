package com.fif.iclass.me.contract;

import com.fif.baselib.base.BaseFragment;

/**
 * Created by chen on 2017-06-20. Me界面接口层
 */

public interface MeContract {
    /**
     * me界面
     */
    interface MeInterface {
        interface MeIModel {
        }

        interface MeIView {
            BaseFragment getInstance();
        }

        interface MeIPresenter {

        }

        interface MeIListener {
            BaseFragment getInstance();
        }
    }
}
