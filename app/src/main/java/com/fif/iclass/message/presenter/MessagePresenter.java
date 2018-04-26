package com.fif.iclass.message.presenter;

import android.util.ArrayMap;

import com.fif.baselib.base.BaseFragment;
import com.fif.iclass.message.contract.MessageContract.MessageInterface;
import com.fif.iclass.message.model.MessageModel;

/**
 * Created by chen on 2017-06-20.
 */

public class MessagePresenter implements MessageInterface.MessageIPresenter, MessageInterface.MessageIListener {

    private MessageInterface.MessageIView view;

    private MessageInterface.MessageIModel model;

    public MessagePresenter(MessageInterface.MessageIView view) {
        this.view = view;
        this.model = new MessageModel(this);
    }
    @Override
    public BaseFragment getInstance() {
        return view.getInstance();
    }
}
