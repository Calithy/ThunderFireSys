package com.DLUT.ThunderFire.Sys;


import android.app.Application;
import android.content.Context;

/**
 * 应用,主要用来做一下初始化的操作
 */

public class ProApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    /**
     * @return
     * 全局的上下文
     */
    public static Context getmContext() {
        return mContext;

    }



}
