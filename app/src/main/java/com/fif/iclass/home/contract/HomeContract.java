package com.fif.iclass.home.contract;

import com.fif.baselib.base.BaseFragment;

/**
 * Created by chen on 2017-06-20. home界面接口
 */
public interface HomeContract {
    /**
     * home主界面
     */
    interface HomeInterface {
        interface HomeIModel {
        }
        interface HomeIView {

            BaseFragment getInstance();
        }
        interface HomeIPresenter {
        }
        interface HomeIListener {
            BaseFragment getInstance();
        }
    }
}
