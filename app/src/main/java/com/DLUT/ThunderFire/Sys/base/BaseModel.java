package com.DLUT.ThunderFire.Sys.base;

import com.DLUT.ThunderFire.Sys.http.Http;
import com.DLUT.ThunderFire.Sys.http.HttpService;
import com.DLUT.ThunderFire.Sys.mvp.IModel;




public class BaseModel implements IModel {
    protected static HttpService httpService;

    //初始化httpService
    static {
        httpService = Http.getHttpService();
    }

}
