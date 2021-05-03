package com.DLUT.ThunderFire.Sys.http;


import android.graphics.Bitmap;

import com.DLUT.ThunderFire.Sys.base.BaseHttpResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 网络请求的接口都在这里
 */

public interface HttpService {


    //获取图片接口
    @GET
    Observable<BaseHttpResult<List<Bitmap>>> getPicList(@Url String path);
}
