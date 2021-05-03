package com.DLUT.ThunderFire.Sys.transformer;


import com.DLUT.ThunderFire.Sys.base.BaseHttpResult;
import com.DLUT.ThunderFire.Sys.exception.ErrorType;
import com.DLUT.ThunderFire.Sys.exception.ExceptionEngine;
import com.DLUT.ThunderFire.Sys.exception.ServerException;
import com.DLUT.ThunderFire.Sys.utils.LogUtils;

import rx.Observable;
import rx.functions.Func1;

/**
 */

public class ErrorTransformer<T> implements Observable.Transformer<BaseHttpResult<T>, T> {

    private static ErrorTransformer errorTransformer = null;
    private static final String TAG = "ErrorTransformer";

    @Override
    public Observable<T> call(Observable<BaseHttpResult<T>> responseObservable) {

        return responseObservable.map(new Func1<BaseHttpResult<T>, T>() {
            @Override
            public T call(BaseHttpResult<T> httpResult) {

                if (httpResult == null)
                    throw new ServerException(ErrorType.EMPTY_BEAN, "解析对象为空");

                LogUtils.e(TAG, httpResult.toString());

                if (httpResult.getStatus() != ErrorType.SUCCESS)
                    throw new ServerException(httpResult.getStatus(), httpResult.getMessage());
                return httpResult.getData();
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                //ExceptionEngine为处理异常的驱动器throwable
                throwable.printStackTrace();
                return Observable.error(ExceptionEngine.handleException(throwable));
            }
        });

    }

    /**
     * @return 线程安全, 双层校验
     */
    public static <T> ErrorTransformer<T> getInstance() {

        if (errorTransformer == null) {
            synchronized (ErrorTransformer.class) {
                if (errorTransformer == null) {
                    errorTransformer = new ErrorTransformer<>();
                }
            }
        }
        return errorTransformer;

    }
}
