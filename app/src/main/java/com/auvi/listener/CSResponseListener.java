package com.auvi.listener;

import org.json.JSONObject;

public interface CSResponseListener {

    void onGetResponse(JSONObject jsonObject, int requestCode);
    void onFail(int requestCode);
}
