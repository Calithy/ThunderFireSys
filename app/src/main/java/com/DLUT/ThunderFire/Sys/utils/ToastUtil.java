package com.DLUT.ThunderFire.Sys.utils;

import android.widget.Toast;

import com.DLUT.ThunderFire.Sys.ProApplication;


/**
 */

public class ToastUtil {
    public static Toast toast;

    public static void setToast(String str) {

        if (toast == null) {
            toast = Toast.makeText(ProApplication.getmContext(), str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
        }
        toast.show();
    }
}
