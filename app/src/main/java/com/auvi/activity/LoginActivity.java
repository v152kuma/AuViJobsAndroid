package com.auvi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.auvi.R;
import com.auvi.activity.base.CSActivity;
import com.auvi.application.CSApplicationHelper;
import com.auvi.fragment.AccountTypeSelectionFragment;
import com.auvi.util.CSAppUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends CSActivity {

    final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_login);
        CSApplicationHelper.application().setActivity(this);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        CSAppUtil.openFragmentNoAnim(new AccountTypeSelectionFragment());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Main", "onActivityResult: "+data);
    }


    @Override
    public void onFacebookSuccess(JSONObject json) {
        try {
            String email = json.getString("email");
            String id = json.getString("id");
            Log.e("FB", email);

            String name = json.getString("name");
            Log.d("FBSuccess", "onCompleted: " + name);
            Log.d("FBSuccess", "onCompleted: " + email);
            Log.d("FBSuccess", "onCompleted: " + id);
//            CSNetworkUtil.loginSocial(this, this, name, id, email, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()>0) {
            getSupportFragmentManager().popBackStack();
        }
        else if(getSupportFragmentManager().findFragmentById(R.id.container) instanceof AccountTypeSelectionFragment){
            super.onBackPressed();
        }
        else {
            CSAppUtil.openFragmentNoAnim(new AccountTypeSelectionFragment());
        }
    }
}