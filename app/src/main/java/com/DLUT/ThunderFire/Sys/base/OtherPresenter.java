package com.DLUT.ThunderFire.Sys.base;

import com.DLUT.ThunderFire.Sys.mvp.IModel;
import com.DLUT.ThunderFire.Sys.mvp.IPresenter;
import com.DLUT.ThunderFire.Sys.mvp.IView;

import java.lang.ref.WeakReference;



public abstract class OtherPresenter<M extends IModel, V extends IView> implements IPresenter {
    private WeakReference actReference;
    protected V iView;
    protected M iModel;

    public M getiModel() {
        iModel = loadModel(); //使用前先进行初始化
        return iModel;
    }

    @Override
    public void attachView(IView iView) {
        actReference = new WeakReference(iView);
    }

    @Override
    public void detachView() {
        if (actReference != null) {
            actReference.clear();
            actReference = null;
        }
    }

    @Override
    public V getIView() {
        return (V) actReference.get();
    }

    public abstract M loadModel();
}
