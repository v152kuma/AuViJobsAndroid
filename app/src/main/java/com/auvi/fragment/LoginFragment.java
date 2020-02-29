package com.auvi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.auvi.R;
import com.auvi.activity.MainActivity;
import com.auvi.application.CSApplicationHelper;
import com.auvi.constant.Constant;
import com.auvi.entity.User;
import com.auvi.fragment.base.CSHeaderFragmentFragment;
import com.auvi.parse.CSGson;
import com.auvi.util.CSAppUtil;
import com.auvi.util.CSShearedPrefence;
import com.auvi.util.CSStringUtil;
import com.auvi.util.CSUIUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends CSHeaderFragmentFragment {

    final String TAG = "Login";
    private EditText numberEditText;
    private EditText passwordEditText;
    private View signInbtn,facebook,google;
    boolean isPartner;

    @Override
    public void getArgs() {
        if(getArguments() != null){
            isPartner = getArguments().getBoolean("partner",false);
        }
    }

    @Override
    public int layoutResource() {
        return R.layout.fragment_login;
    }

    @Override
    public void init() {
        numberEditText = fragmentView.findViewById(R.id.email);
        passwordEditText = fragmentView.findViewById(R.id.password);
        signInbtn = fragmentView.findViewById(R.id.sign_in);
        facebook = fragmentView.findViewById(R.id.facebook);
        google = fragmentView.findViewById(R.id.goole_btn);
        if(isPartner){
            fragmentView.findViewById(R.id.or_with_layout).setVisibility(View.GONE);
            facebook.setVisibility(View.GONE);
            google.setVisibility(View.GONE);
        }else {
            facebook.setVisibility(View.VISIBLE);
            google.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void setClickOnListener() {
        setClickListener(
                signInbtn,
                facebook,
                google,
                fragmentView.findViewById(R.id.forgot),
                fragmentView.findViewById(R.id.skip),
                fragmentView.findViewById(R.id.createAccount));
    }

    @Override
    public void setOnData() {
        CSUIUtil.applyKeyBoardEvent(signInbtn,passwordEditText);
        CSUIUtil.applyEditTextListener(passwordEditText);
        CSUIUtil.applyDrawable(passwordEditText);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.facebook:
                CSAppUtil.showToast(R.string.coming_soon);
                break;
            case R.id.goole_btn:
                CSAppUtil.showToast(R.string.coming_soon);
                break;
            case R.id.sign_in:
                signIn();
                break;
            case R.id.skip:
                break;
            case R.id.forgot:
                v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.click_anim));
                CSAppUtil.openAddFragment(new ForgetFragment(),"TAG");
                break;

            case R.id.createAccount:
                v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.click_anim));
                SignUpFragment signUpFragment = new SignUpFragment();
                Bundle  bundle = new Bundle();
                bundle.putBoolean("partner",isPartner);
                signUpFragment.setArguments(bundle);
                CSAppUtil.openAddFragment(signUpFragment,"Login");
                break;
        }
    }

    private void signIn() {
        if (CSStringUtil.isNonEmptyView(numberEditText, R.string.error_email_empty)
                && CSStringUtil.isNonEmptyView(passwordEditText, R.string.error_enter_pass)) {
            if (CSStringUtil.isValidEmail(CSStringUtil.getTextFromView(numberEditText)) ||
                    CSStringUtil.isValidPhone(CSStringUtil.getTextFromView(numberEditText))) {
                if(CSStringUtil.getTextFromView(numberEditText).equalsIgnoreCase("admin@gmail.com")){
                    CSShearedPrefence.setAccountRole(2);
                }else if(CSStringUtil.getTextFromView(numberEditText).equalsIgnoreCase("employer@gmail.com")){
                    CSShearedPrefence.setAccountRole(1);
                }else {
                    CSShearedPrefence.setAccountRole(0);
                }
                CSAppUtil.openActivity(MainActivity.class);
                CSShearedPrefence.setIsLoggedIn(true);
//                CSNetworkUtil.loginRequest(getContext(), this, CSStringUtil.getTextFromView(numberEditText),
//                CSStringUtil.getTextFromView(passwordEditText));
            } else {
                CSAppUtil.showToast(R.string.error_valid_phone_email);
            }
        }
    }

    @Override
    public void onGetResponse(JSONObject jsonObject, int requestCode) {

        if(requestCode == Constant.socialRequestCode || requestCode == Constant.loginRequestCode){
            if(jsonObject.has("user_data")) {
                try {
                    User user = CSGson.gson().getObject(User.class, jsonObject.getString("user_data"));
                    CSShearedPrefence.setUser(jsonObject.getString("user_data"));
                    CSApplicationHelper.application().setCurrentLoggedInUser(user);
                    if(user.getIsBlock().equalsIgnoreCase("1")){
                        CSAppUtil.showToast(jsonObject.getString("message"));
                        OTPVerificationsFragment otpVerificationsFragment = new OTPVerificationsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("mobile_number",user.getMobileNumber());
                        bundle.putString("email",user.getEmail());
                        otpVerificationsFragment.setArguments(bundle);
                        CSAppUtil.openAddFragment(otpVerificationsFragment,"TAG");
                        if(jsonObject.has("message")){
                            CSAppUtil.showToast(jsonObject.getString("message"));
                        }
                    }
                    else {
                        CSAppUtil.openActivity(MainActivity.class);
                        CSShearedPrefence.setIsLoggedIn(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onFail(int requestCode) {
        CSUIUtil.clearText(numberEditText);
        CSUIUtil.clearText(passwordEditText);
    }

    @Override
    public int headerViewVisibility() {
        return View.GONE;
    }
}
