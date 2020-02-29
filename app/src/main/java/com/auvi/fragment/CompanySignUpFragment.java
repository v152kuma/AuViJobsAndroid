package com.auvi.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.auvi.R;
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


public class CompanySignUpFragment extends CSHeaderFragmentFragment {

    final String TAG = "SignUp";
    private EditText nameEditText,emailEditText,passEditText,phoneEditText;
    private CheckBox acceptCheckBox;
    private View signUpBtn;
    private TextView termCondition;



    @Override
    public void getArgs() {

    }

    @Override
    public int layoutResource() {
        return R.layout.fragment_signup;
    }

    @Override
    public void init() {

        nameEditText = fragmentView.findViewById(R.id.name);
        emailEditText = fragmentView.findViewById(R.id.email);
        phoneEditText = fragmentView.findViewById(R.id.phn_nmbr);
        passEditText = fragmentView.findViewById(R.id.password);
        signUpBtn = fragmentView.findViewById(R.id.sign_up);
        acceptCheckBox = fragmentView.findViewById(R.id.accept_check_box);
        termCondition = fragmentView.findViewById(R.id.term_condition);

    }

    @Override
    public void setClickOnListener() {
        setClickListener(signUpBtn,termCondition);
    }

    @Override
    public void setOnData() {
        CSUIUtil.applyKeyBoardEvent(signUpBtn,passEditText);
        CSUIUtil.applyEditTextListener(nameEditText,phoneEditText,emailEditText,passEditText);
        CSUIUtil.applyDrawable(passEditText);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.sign_up:
                v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.click_anim));
                signUp();
                break;
            case R.id.term_condition:
                TermsConditionFragment aboutUS = new TermsConditionFragment();
                CSAppUtil.openAddFragment(aboutUS,"TAG");
//                CSDialogUtil.showInfoDialog(csActivity,this,R.string.privacy_policy,R.string.ok,true);
                break;
        }
    }

    private void signUp(){

        if(CSStringUtil.isNonEmptyView(nameEditText,R.string.error_enter_name)
                &&CSStringUtil.isNonEmptyView(emailEditText,R.string.error_enter_mail)
                &&CSStringUtil.isNonEmptyView(phoneEditText,R.string.error_enter_phone)
                &&CSStringUtil.isNonEmptyView(passEditText,R.string.error_enter_pass)){

            if(CSStringUtil.isNonValidEmail(CSStringUtil.getTextFromView(emailEditText))) {
                CSAppUtil.showError(emailEditText,R.string.error_enter_valid_mail);
            }
            else if(CSStringUtil.isNonValidPhone(CSStringUtil.getTextFromView(phoneEditText)) || CSStringUtil.getTextFromView(phoneEditText).length() !=10){
                CSAppUtil.showError(phoneEditText,R.string.error_valid_phone);
            }
            else if(CSStringUtil.getTextFromView(passEditText).length()<Constant.PASSWORD_SIZE){
                CSAppUtil.showToast(CSStringUtil.getString(R.string.password_lenght));
            }
            else if(CSStringUtil.isNonAlphaNumeric(CSStringUtil.getTextFromView(passEditText))){
                CSAppUtil.showToast(CSStringUtil.getString(R.string.error_pass_alpha));
            }
            else if(!acceptCheckBox.isChecked()){
                CSAppUtil.showToast(R.string.accept_term_condition);
            }
            else {
                OTPVerificationsFragment otpVerificationsFragment = new OTPVerificationsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("mobile_number","91"+CSStringUtil.getTextFromView(phoneEditText));
                bundle.putString("email",CSStringUtil.getTextFromView(emailEditText));
                otpVerificationsFragment.setArguments(bundle);
                CSAppUtil.openAddFragment(otpVerificationsFragment,"TAG");
              /*  CSNetworkUtil.signUpRequest(getContext(),
                        this,
                        CSStringUtil.getTextFromView(nameEditText),
                        CSStringUtil.getTextFromView(emailEditText),
                        CSStringUtil.getTextFromView(phoneEditText),
                        CSStringUtil.getTextFromView(passEditText),
                        "",
                        "");*/
            }
        }
    }

    @Override
    public void onGetResponse(JSONObject jsonObject, int requestCode) {
        if(requestCode == Constant.signUpRequestCode){
            try {

                if(jsonObject.has("user_data")) {
                    User user = CSGson.gson().getObject(User.class, jsonObject.getString("user_data"));
                    CSShearedPrefence.setUser(jsonObject.getString("user_data"));
                    CSApplicationHelper.application().setCurrentLoggedInUser(user);
                    OTPVerificationsFragment otpVerificationsFragment = new OTPVerificationsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("mobile_number","91"+CSStringUtil.getTextFromView(phoneEditText));
                    bundle.putString("email",CSStringUtil.getTextFromView(emailEditText));
                    otpVerificationsFragment.setArguments(bundle);
                    CSAppUtil.openAddFragment(otpVerificationsFragment,"TAG");
                }
                else if(jsonObject.has("message")) {
                    CSAppUtil.showToast(jsonObject.getString("message"));
                    OTPVerificationsFragment otpVerificationsFragment = new OTPVerificationsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("mobile_number","91"+CSStringUtil.getTextFromView(phoneEditText));
                    bundle.putString("email",CSStringUtil.getTextFromView(emailEditText));
                    otpVerificationsFragment.setArguments(bundle);
                    CSAppUtil.openAddFragment(otpVerificationsFragment,"TAG");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int headerViewTitle() {
        return R.string.sign_up;
    }

    @Override
    public int backButtonVisibility() {
        return View.VISIBLE;
    }

    @Override
    public void onClickOk(int requestCode, Object object) {
        acceptCheckBox.setChecked(true);
    }

    @Override
    public void onCancel() {

    }
}