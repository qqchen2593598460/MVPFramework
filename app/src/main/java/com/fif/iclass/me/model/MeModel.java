package com.fif.iclass.me.model;

import android.util.ArrayMap;
import android.widget.Toast;
import com.fif.baselib.widget.toasty.Toasty;
import com.fif.iclass.common.bean.UpdateBean;
import com.fif.iclass.common.http.NetWorkRequest;
import com.fif.iclass.common.http.NetWorkSubscriber;
import com.fif.iclass.me.contract.MeContract;

/**
 * Created by
 */

public class MeModel implements MeContract.MeInterface.MeIModel {
    private MeContract.MeInterface.MeIListener listener;

    public MeModel(MeContract.MeInterface.MeIListener listener) {
      this.listener = listener;
    }
}
