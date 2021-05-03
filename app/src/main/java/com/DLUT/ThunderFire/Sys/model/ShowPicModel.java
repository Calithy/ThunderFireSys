package com.DLUT.ThunderFire.Sys.model;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.DLUT.ThunderFire.Sys.ProApplication;
import com.DLUT.ThunderFire.Sys.base.BaseModel;
import com.DLUT.ThunderFire.Sys.subscriber.CommonSubscriber;
import com.DLUT.ThunderFire.Sys.transformer.CommonTransformer;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

/**
 * 实现网络请求
 */
public class ShowPicModel extends BaseModel {
    private boolean isGet;
    public boolean getPic(String path,@NonNull final  InfoHint infoHint){
        if (infoHint == null)
            throw new RuntimeException("InfoHint不能为空");

        httpService.getPicList(path)
                .compose(new CommonTransformer<List<Bitmap>>())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<List<Bitmap>>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(List<Bitmap> bitmaps)
                    {
                        infoHint.getLisPic(bitmaps);
                    }
                });

        return isGet;
    }


    //s数据回调接口
    public interface InfoHint {
        void getLisPic(List<Bitmap> picList);



    }

}
