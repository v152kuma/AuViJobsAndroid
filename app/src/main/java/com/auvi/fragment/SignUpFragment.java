package com.auvi.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import com.auvi.R;
import com.auvi.application.CSApplicationHelper;
import com.auvi.constant.Constant;
import com.auvi.entity.Category;
import com.auvi.entity.User;
import com.auvi.fragment.base.CSHeaderFragmentFragment;
import com.auvi.listener.OnItemSelection;
import com.auvi.parse.CSGson;
import com.auvi.util.CSAppUtil;
import com.auvi.util.CSShearedPrefence;
import com.auvi.util.CSStringUtil;
import com.auvi.util.CSUIUtil;
import com.auvi.view.CSDynamicView;

import java.util.ArrayList;


public class SignUpFragment extends CSHeaderFragmentFragment {

    final String TAG = "SignUp";
    private EditText nameEditText,emailEditText,passEditText,phoneEditText,governmentIdProof;
    private CheckBox acceptCheckBox;
    private View signUpBtn;
    private TextView termCondition;

    private RecyclerView parentCategoryRecyclerView,subCategoryRecyclerView;
    private boolean isPartner;

    @Override
    public void getArgs() {
        if(getArguments() != null){
            isPartner = getArguments().getBoolean("partner",false);
        }
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

        governmentIdProof = fragmentView.findViewById(R.id.government_id_number);
        parentCategoryRecyclerView = fragmentView.findViewById(R.id.parent_cat);
        subCategoryRecyclerView = findViewById(R.id.sub_cat);

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

        if(!isPartner) {
            governmentIdProof.setVisibility(View.VISIBLE);
            parentCategoryRecyclerView.setVisibility(View.VISIBLE);
            subCategoryRecyclerView.setVisibility(View.VISIBLE);
            ArrayList<Category> targetSelection1 = new ArrayList<>();
            targetSelection1.add(new Category("Staff", true));
            targetSelection1.add(new Category("Employee", false));

            CSDynamicView.setUpRecyclerViewHorizontal(csActivity,
                    parentCategoryRecyclerView, new OnItemSelection() {
                        @Override
                        public void onItemSelection(int position) {

                        }
                    }, targetSelection1);

            ArrayList<Category> targetSelection = new ArrayList<>();
            targetSelection.add(new Category("Office Boy", true));
            targetSelection.add(new Category("Cleaner", false));
            targetSelection.add(new Category("Guard", false));


            CSDynamicView.setUpRecyclerViewHorizontal(csActivity, subCategoryRecyclerView, new OnItemSelection() {
                @Override
                public void onItemSelection(int position) {

                }
            }, targetSelection);
        }
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