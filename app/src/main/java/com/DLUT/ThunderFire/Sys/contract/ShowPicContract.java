package com.DLUT.ThunderFire.Sys.contract;

import android.graphics.Bitmap;

import java.util.List;

public class ShowPicContract {
    public interface ShowPicView{
      void  getItemList(List<Bitmap> itemList);
    }
    public interface ShowPicPresenter{
        void showItemList(String path);
    }
}
