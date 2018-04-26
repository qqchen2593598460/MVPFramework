package com.fif.iclass.common.http;

import android.util.ArrayMap;

import com.fif.iclass.common.bean.AppInfoBean;
import com.fif.iclass.common.bean.LoginBean;
import com.fif.iclass.common.bean.UpdateBean;
import com.fif.iclass.common.bean.UserPortraitBean;
import com.fif.iclass.common.bean.UserTypeBean;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * by chen
 */
public class NetWorkRequest {

    private static <M> void addObservable(Observable<M> observable, Subscriber<M> subscriber) {
        RxUtils.getInstance().addSubscription(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    public static void login(NetWorkSubscriber<LoginBean> loginSubscriber , ArrayMap<String, String> map) {
        addObservable(Network.getObservableApi().login(map), loginSubscriber);
    }
}
