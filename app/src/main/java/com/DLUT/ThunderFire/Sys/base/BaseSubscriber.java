package com.DLUT.ThunderFire.Sys.base;


import com.DLUT.ThunderFire.Sys.exception.ApiException;

import rx.Subscriber;



public abstract class BaseSubscriber<T> extends Subscriber<T> {

    @Override
    public void onError(Throwable e) {
        ApiException apiException = (ApiException) e;
        onError(apiException);
    }


    /**
     * @param e 错误的一个回调
     */
    protected abstract void onError(ApiException e);

}
