package com.auvi.listener;

public interface DialogCallback {

    void onClickOk(int requestCode, Object object);
    void onCancel();

}
