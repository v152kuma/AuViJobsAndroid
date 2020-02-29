package com.auvi.listener;

import org.json.JSONObject;

public interface LoginCallBack {
    void onFacebookSuccess(JSONObject response);
    void LoginUsingOther(int requestCode);
}
