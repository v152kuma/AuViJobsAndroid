package com.auvi.fragment;

import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;
import com.auvi.R;
import com.auvi.constant.Constant;
import com.auvi.fragment.base.CSHeaderFragmentFragment;
import com.auvi.util.CSAppUtil;
import com.auvi.util.CSStringUtil;
import com.auvi.util.CSUIUtil;

public class ChangePasswordFragment extends CSHeaderFragmentFragment {

    final String TAG = "ChangePassword";
    EditText oldPassword;
    EditText newPassword;
    EditText confirmPassword;
    View changePasswordbtn;

    @Override
    public void getArgs() {
    }


    @Override
    public int layoutResource() {
        return R.layout.fragment_change_password;
    }

    @Override
    public void init() {

        changePasswordbtn=fragmentView.findViewById(R.id.change_password);
        oldPassword=fragmentView.findViewById(R.id.old_password);
        oldPassword.setVisibility(View.VISIBLE);
        newPassword=fragmentView.findViewById(R.id.new_password);
        confirmPassword=fragmentView.findViewById(R.id.confirm_pass);
        CSUIUtil.applyDrawable(oldPassword);
        CSUIUtil.applyDrawable(newPassword);
        CSUIUtil.applyDrawable(confirmPassword);
    }

    @Override
    public int headerViewTitle() {
        return R.string.change_pass;
    }

    @Override
    public void setClickOnListener() {
        setClickListener(changePasswordbtn);
    }

    @Override
    public void setOnData() {
        CSUIUtil.applyKeyBoardEvent(changePasswordbtn,confirmPassword);
        CSUIUtil.applyEditTextListener(confirmPassword);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.change_password:
                changePassword();
                break;
        }
    }

    public void changePassword() {
        if(CSStringUtil.isNonEmptyView(oldPassword,R.string.error_enter_pass)
                &&CSStringUtil.isNonEmptyView(newPassword,R.string.error_new_password)
                &&CSStringUtil.isNonEmptyView(confirmPassword,R.string.error_enter_confirm_pass)){
            if(CSStringUtil.getTextFromView(newPassword).length()<Constant.PASSWORD_SIZE){
                CSAppUtil.showToast(CSStringUtil.getString(R.string.new_password_lenght));
            }
            else if(CSStringUtil.isNonAlphaNumeric(CSStringUtil.getTextFromView(newPassword))){
                CSAppUtil.showToast(CSStringUtil.getString(R.string.error_pass_alpha));
            }
            else if(!CSStringUtil.getTextFromView(newPassword).equals(CSStringUtil.getTextFromView(confirmPassword))){
                CSAppUtil.showToast(CSStringUtil.getString(R.string.error_pass_match));
            }
            else {
/*                CSNetworkUtil.ChangePassword(getContext(),
                        this,
                        CSApplicationHelper.application().getCurrentLoggedInUser().getEmail(),
                        CSStringUtil.getTextFromView(oldPassword),CSStringUtil.getTextFromView(newPassword));*/
            }
        }
    }
    @Override
    public void onGetResponse(JSONObject jsonObject, int requestCode) {
        if(requestCode == Constant.changePasswordCode){
            CSAppUtil.showToast(R.string.password_change_success);
            CSAppUtil.openFragmentNoAnim(new HomeFragment());
//            CSShearedPrefence.setIsLoggedIn(false);
//            CSAppUtil.openActivity(LoginActivity.class);
        }
    }

}