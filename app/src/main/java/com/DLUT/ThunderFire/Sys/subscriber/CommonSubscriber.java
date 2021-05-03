package com.DLUT.ThunderFire.Sys.subscriber;

import android.content.Context;

import com.DLUT.ThunderFire.Sys.base.BaseSubscriber;
import com.DLUT.ThunderFire.Sys.exception.ApiException;
import com.DLUT.ThunderFire.Sys.utils.LogUtils;
import com.DLUT.ThunderFire.Sys.utils.NetworkUtil;


/**

 */

public abstract class CommonSubscriber<T> extends BaseSubscriber<T> {

    private Context context;

    public CommonSubscriber(Context context) {
        this.context = context;
    }

    private static final String TAG = "CommonSubscriber";

    @Override
    public void onStart() {

        if (!NetworkUtil.isNetworkAvailable(context)) {
            LogUtils.e(TAG, "网络不可用");
        } else {
            LogUtils.e(TAG, "网络可用");
        }
    }



    @Override
    protected void onError(ApiException e) {
        LogUtils.e(TAG, "错误信息为 " + "code:" + e.code + "   message:" + e.message);
    }

    @Override
    public void onCompleted() {
        LogUtils.e(TAG, "成功了");
    }

}
