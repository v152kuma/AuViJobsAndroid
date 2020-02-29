package com.auvi.activity.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.view.View;

import org.json.JSONObject;

import com.auvi.R;
import com.auvi.application.CSApplicationHelper;
import com.auvi.listener.DialogCallback;
import com.auvi.listener.LoginCallBack;
import com.auvi.listener.CSResponseListener;

abstract public class CSActivity extends AppCompatActivity implements View.OnClickListener,CSResponseListener,LoginCallBack,DialogCallback {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppThemeBlue);
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        CSApplicationHelper.application().setActivity(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        CSApplicationHelper.application().setActivity(this);
        super.onResume();
    }

    public void setListener(View... params) {
        for (View view : params) {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onGetResponse(JSONObject jsonObject, int requestCode) {

    }

    @Override
    public void onFail(int requestCode) {

    }

    @Override
    public void onFacebookSuccess(JSONObject json) {
    }

    public void LoginUsingOther(int requestCode){
    }

    @Override
    public void onClickOk(int requestCode, Object object) {

    }

    @Override
    public void onCancel() {

    }
}
