package com.fif.iclass.message.contract;

import android.util.ArrayMap;

import com.fif.baselib.base.BaseFragment;

/**
 * Created by chen on 2017-06-20. 消息界面接口
 */
public interface MessageContract {
    /**
     * 消息主界面
     */
    interface MessageInterface {
        interface MessageIModel {
        }
        interface MessageIView {
            BaseFragment getInstance();
        }
        interface MessageIPresenter {
        }
        interface MessageIListener {
            BaseFragment getInstance();
        }
    }
}
