package com.auvi.fragment;

import android.view.View;

import com.auvi.R;
import com.auvi.constant.Constant;
import com.auvi.util.CSAppUtil;
import com.auvi.util.CSStringUtil;

public class SetPasswordFragment extends ChangePasswordFragment {

    @Override
    public int headerViewTitle() {
        return R.string.reset_pass;
    }

    @Override
    public void setOnData() {
        super.setOnData();
        oldPassword.setVisibility(View.GONE);
    }

    public void changePassword() {
        String password = newPassword.getText().toString();
        if(CSStringUtil.isNonEmptyView(newPassword,R.string.error_new_password)
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
/*                CSNetworkUtil.ResetPassword(getContext(),
                        this,
                        "h@g.com",password );*/
            }
        }
    }
}