package com.DLUT.ThunderFire.Sys.bean;

import android.graphics.Bitmap;

/**
 * 图片的实体类
 */
public class ImageBean {
    private Bitmap image;
    private Boolean state;

    public ImageBean(Bitmap image, Boolean state) {
        this.image = image;
        this.state = state;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
