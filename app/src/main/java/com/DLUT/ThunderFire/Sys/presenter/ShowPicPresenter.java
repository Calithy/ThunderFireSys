package com.DLUT.ThunderFire.Sys.presenter;

import android.graphics.Bitmap;

import com.DLUT.ThunderFire.Sys.activity.ShowPicActivity;
import com.DLUT.ThunderFire.Sys.base.BasePresenter;
import com.DLUT.ThunderFire.Sys.contract.ShowPicContract;
import com.DLUT.ThunderFire.Sys.model.ShowPicModel;
import com.DLUT.ThunderFire.Sys.mvp.IModel;

import java.util.HashMap;
import java.util.List;

public class ShowPicPresenter extends BasePresenter<ShowPicActivity> implements ShowPicContract.ShowPicPresenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new ShowPicModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {

        HashMap<String, IModel> map = new HashMap<>();
        map.put("showPic", models[0]);
        return map;
    }

    @Override
    public void showItemList(String path) {
        ( (ShowPicModel)getiModelMap().get("showPic")).getPic(path, new ShowPicModel.InfoHint() {
            @Override
            public void getLisPic(List<Bitmap> picList) {
               getIView().getItemList(picList);
            }
        });
    }


}
