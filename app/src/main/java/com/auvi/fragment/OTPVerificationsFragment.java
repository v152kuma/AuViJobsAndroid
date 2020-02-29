package com.auvi.fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import com.auvi.R;
import com.auvi.application.CSApplicationHelper;
import com.auvi.constant.Constant;
import com.auvi.fragment.base.CSHeaderFragmentFragment;
import com.auvi.util.CSAppUtil;
import com.auvi.util.CSShearedPrefence;
import com.auvi.util.CSStringUtil;
import com.auvi.util.CSUIUtil;


public class OTPVerificationsFragment extends CSHeaderFragmentFragment {

    final String TAG = "Login";
    EditText otpNumberEditText;
    TextView resendOtpTextView;
    TextView timerTextView;
    Handler mHandler=new Handler();
    CountDownTimer countDownTimer;

    boolean formProfileFragment;
    String mobileNumber;
    String email;

    @Override
    public void getArgs() {
        if(getArguments() != null){
            formProfileFragment = getArguments().getBoolean("form_profile_fragment");
            mobileNumber = getArguments().getString("mobile_number");
            email = getArguments().getString("email");
        }
    }

    @Override
    public int layoutResource() {
        return R.layout.varify_otp_fragment;
    }


    @Override
    public void init() {
        otpNumberEditText = fragmentView.findViewById(R.id.otp_number);
        int maxLength = 6;
        otpNumberEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        resendOtpTextView = fragmentView.findViewById(R.id.resend);
        timerTextView = fragmentView.findViewById(R.id.timer);
        resendOtpTextView.setTextColor(csActivity.getResources().getColor(R.color.black));
        timer();
    }


    @Override
    public int headerViewTitle() {
        return R.string.otp_verify;
    }

    @Override
    public int backButtonVisibility() {
        return View.VISIBLE;
    }

    @Override
    public void setClickOnListener() {
        setClickListener(
                fragmentView.findViewById(R.id.token_submit),
                fragmentView.findViewById(R.id.resend));
    }

    @Override
    public void setOnData() {
        CSUIUtil.applyEditTextListener(otpNumberEditText);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.token_submit:
                verifyOtp();
                break;

            case R.id.resend:
                resendOtp();
                break;
        }
    }

    private void resendOtp() {
//        CSNetworkUtil.genrateOTP(getContext(), this, email,mobileNumber);
    }

    public void timer(){
        try {
            countDownTimer =  new CountDownTimer(120000, 1000) {
                public void onTick(long millisUntilFinished) {
                    long sec = millisUntilFinished / 1000;
                    String timerValue = CSStringUtil.getString(R.string.time_zero);
                    if(sec >= 60){
                        sec -= 60;
                        timerValue = CSStringUtil.getString(R.string.time_one);
                    }
                    if(sec < 10){
                        timerValue += " 0" + sec;
                    }
                    else {
                        timerValue += " " + sec;
                    }

                    timerTextView.setText(timerValue);
                    resendOtpTextView.setClickable(false);
                }

                public void onFinish() {
                    resendOtpTextView.setClickable(true);
                    resendOtpTextView.setEnabled(true);
                    resendOtpTextView.setTextColor(csActivity.getResources().getColor(R.color.colorPrimary));
                    timerTextView.setVisibility(View.GONE);
                }
            };
            countDownTimer.start();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void verifyOtp(){

        String otp = otpNumberEditText.getText().toString();
        Log.d(TAG, "verifyOtp: "+otp+" email "+email);
        Log.d(TAG, "verifyOtp: "+otp);
        if(!formProfileFragment) {
//            CSNetworkUtil.verifyOtpRequest(getContext(), this, otp, email);
        }
        else {
//            CSNetworkUtil.verifyOTPMobileNumberRequest(getContext(), this, otp, mobileNumber);
        }

    }

    @Override
    public void onGetResponse(JSONObject jsonObject, int requestCode) {
        if(requestCode == Constant.verifyOtpRequestCode) {
            CSAppUtil.openFragment(new LoginFragment());
            CSShearedPrefence.setIsLoggedIn(false);
            if(jsonObject.has("message")){
                try {
                    CSAppUtil.showToast(jsonObject.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(requestCode == Constant.genrateOTP){
            resendOtpTextView.setClickable(false);
            resendOtpTextView.setEnabled(false);
            resendOtpTextView.setTextColor(csActivity.getResources().getColor(R.color.white));
            resendOtpTextView.setTextColor(CSApplicationHelper.application().getResources().getColor(R.color.light_gray));
            timer();
            timerTextView.setVisibility(View.VISIBLE);
            if(jsonObject.has("message")){
                try {
                    CSAppUtil.showToast(jsonObject.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
    }
}
