package com.fif.iclass.common.http;

import rx.Subscriber;

/**
 * by chen
 */
public class NetWorkSubscriber<T> extends Subscriber<T> {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {
    }
    /**
     * 权限错误，需要实现重新登录操作
     */
    public void onPermissionError(NetWorkException ex){
    }

    /**
     * 服务器返回的错误
     */
    public void onResultError(NetWorkException ex){
    }
}
