package com.auvi.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.auvi.R;
import com.auvi.fragment.base.CSHeaderFragmentFragment;
import com.auvi.util.CSAppUtil;
import com.auvi.util.CSStringUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetFragment extends CSHeaderFragmentFragment {

    EditText email;
    RadioGroup radioGroup;
    RadioButton link;
    RadioButton otp;


    @Override
    public void getArgs() {

    }

    @Override
    public int layoutResource() {
        return R.layout.fragment_forget;
    }

    @Override
    public void init() {
        email = fragmentView.findViewById(R.id.email);
        radioGroup = fragmentView.findViewById(R.id.option);
        link = fragmentView.findViewById(R.id.using_link);
        otp = fragmentView.findViewById(R.id.using_otp);
    }

    @Override
    public int headerViewTitle() {
        return R.string.forget_password;
    }

    @Override
    public int backButtonVisibility() {
        return View.VISIBLE;
    }

    @Override
    public void setClickOnListener() {
        setClickListener(fragmentView.findViewById(R.id.ok_action));
    }

    @Override
    public void setOnData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ok_action:
//                forget();
                break;
        }
    }

    private void forget() {

        if (CSStringUtil.isNonEmptyView(email, R.string.error_email_empty)) {
            if (CSStringUtil.isValidEmail(CSStringUtil.getTextFromView(email))) {
               /* CSNetworkUtil.forgetPassword(getContext(),this,CSStringUtil.getTextFromView(email));*/
            } else {
                CSAppUtil.showToast(R.string.error_valid_phone_email);
            }
        }
    }

    @Override
    public void onGetResponse(JSONObject jsonObject, int requestCode) {
        if(jsonObject!=null && jsonObject.has("message")){
            try {
                CSAppUtil.showToast(jsonObject.getString("message"));
                if(email!=null){
                    email.setText("");
                }
                csActivity.onBackPressed();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
