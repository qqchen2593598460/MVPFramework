package com.fif.iclass.message.model;

import android.util.ArrayMap;
import android.widget.Toast;

import com.fif.baselib.base.ExceptionHandle;
import com.fif.baselib.widget.toasty.Toasty;
import com.fif.iclass.common.http.NetWorkRequest;
import com.fif.iclass.common.http.NetWorkSubscriber;
import com.fif.iclass.message.contract.MessageContract;

/**
 * Created by chen on 2017-06-20.
 */

public class MessageModel implements MessageContract.MessageInterface.MessageIModel {

    private MessageContract.MessageInterface.MessageIListener listener;

    public MessageModel(MessageContract.MessageInterface.MessageIListener listener) {
        this.listener = listener;
    }
}
