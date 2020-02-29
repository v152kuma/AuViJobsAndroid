package com.auvi.util;

import com.auvi.listener.CSResponseListener;

import org.json.JSONObject;

public class CSNetworkUtil implements CSResponseListener{

    public static CSNetworkUtil  csNetworkUtil;

    private CSNetworkUtil(){

    }

    public static CSNetworkUtil getInstance(){
        if(csNetworkUtil == null){
            csNetworkUtil = new CSNetworkUtil();
        }
        return csNetworkUtil;
    }

   /* public static void loginRequest(final Context context,
                                    final CSResponseListener responseListener,
                                    final String mobileNumber,
                                    final String password) {

        HashMap<String,String> hashMap = new HashMap<String, String>();
        hashMap.put("rquest", ConstantURL.loginUrl);
        hashMap.put("email", mobileNumber);
        hashMap.put("password", password);

        CSRequestUtil.getInstance().doPostStringRequest(context,
                ConstantURL.BaseUrl, hashMap,
                responseListener, Constant.loginRequestCode,true);
    }

    public static void loginSocial(final Context context,
                                   final CSResponseListener responseListener,
                                   final String socialId,
                                   final String userFirstName,
                                   final String userLastName,
                                   final String mobileNumber,
                                   final String email,
                                   final String gender,
                                   final String dob,
                                   final String dp_profile_pic_url,
                                   final String dp_user_name,
                                   final String imei,
                                   final String device_model,
                                   final String onesignal_id,
                                   final String os,
                                   final String country) {

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("rquest", ConstantURL.socialLogin);
        hashMap.put("social_id", socialId);
        hashMap.put("email", email);
        hashMap.put("mobile", mobileNumber);
        hashMap.put("first_name", userFirstName);
        hashMap.put("last_name", userLastName);
        hashMap.put("gender", gender);
        hashMap.put("dob", dob);
        hashMap.put("dp_profile_pic_url", dp_profile_pic_url);
        hashMap.put("dp_user_name", dp_user_name);
        hashMap.put("imei", imei);
        hashMap.put("device_model", device_model);
        hashMap.put("onesignal_id", onesignal_id);
        hashMap.put("os", os);
        hashMap.put("country", country);
        CSRequestUtil.getInstance().doPostStringRequest(context, ConstantURL.BaseUrl, hashMap, responseListener, Constant.socialRequestCode,true);
    }

    public static void signUpRequest(final Context context,
                                     final CSResponseListener responseListener,
                                     final String name,
                                     final String email,
                                     final String mobileNumber,
                                     final String password,
    final String userType,final String infoUrl) {

        HashMap<String,String> hashMap = new HashMap<String, String>();
        hashMap.put("rquest", ConstantURL.registerUrl);
        hashMap.put("mobile", "91"+mobileNumber);
        hashMap.put("password", password);
        hashMap.put("confirm_password", password);
        hashMap.put("name", name);
        hashMap.put("email", email);
        hashMap.put("info_url", infoUrl);
        hashMap.put("user_type", userType);

        CSRequestUtil.getInstance().doPostStringRequest(context, ConstantURL.BaseUrl, hashMap, responseListener, Constant.signUpRequestCode,true);
    }

    public static void verifyOtpRequest(final Context context,
                                        final CSResponseListener responseListener,
                                        final String otp,
                                        final String email) {

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("rquest", ConstantURL.verifyOTP);
        hashMap.put("OTP", otp);
        hashMap.put("email", email);
        CSRequestUtil.getInstance().doPostStringRequest(context, ConstantURL.BaseUrl, hashMap, responseListener, Constant.verifyOtpRequestCode,true);
    }

    public static void verifyOTPMobileNumberRequest(final Context context,
                                        final CSResponseListener responseListener,
                                        final String otp,
                                        final String mobile) {

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("rquest", ConstantURL.verifyOTP);
        hashMap.put("OTP", otp);
        hashMap.put("mobile_no", mobile);
        hashMap.put("email", CSApplicationHelper.application().getCurrentLoggedInUser().getEmail());
        hashMap.put("user_id", CSApplicationHelper.application().getCurrentLoggedInUser().getUserId());
        CSRequestUtil.getInstance().doPostStringRequest(context, ConstantURL.BaseUrl, hashMap, responseListener, Constant.verifyOtpRequestCode,true);
    }

    public static void ResetPassword(final Context context,
                                     final CSResponseListener responseListener,
                                     final String email,
                                     final String password) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("rquest", ConstantURL.changePasswordUrl);
        hashMap.put("email", email);
        hashMap.put("password", password);
        hashMap.put("confirm_password", password);
        hashMap.put("action", "set");
        CSRequestUtil.getInstance().doPostStringRequest(context, ConstantURL.BaseUrl, hashMap, responseListener, Constant.changePasswordCode,true);
    }

    public static void forgetPassword(final Context context,
                                      final CSResponseListener responseListener,
                                      final String email) {

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("rquest", ConstantURL.forgotUsingEmailUrl);
        hashMap.put("email", email);
        CSRequestUtil.getInstance().doPostStringRequest(context, ConstantURL.BaseUrl, hashMap, responseListener, Constant.forgetPasswordRequestCode,true);
    }

    public static void genrateOTP(final Context context,
                                  final CSResponseListener responseListener,
                                  final String email,
                                  final String phone) {

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("rquest", ConstantURL.genrateOTP);
        hashMap.put("email", email);
        hashMap.put("mobile_no", phone);
        CSRequestUtil.getInstance().doPostStringRequest(context, ConstantURL.BaseUrl, hashMap, responseListener, Constant.genrateOTP,true);
    }

    public static void ChangePassword(final Context context,
                                      final CSResponseListener responseListener,
                                      final String email,
                                      final String oldPassword,
                                      final String newPassword) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("rquest", ConstantURL.changePasswordUrl);
        hashMap.put("email", email);
        hashMap.put("password", newPassword);
        hashMap.put("confirm_password", newPassword);
        hashMap.put("old_password", oldPassword);
        hashMap.put("action", "change");
        CSRequestUtil.getInstance().doPostStringRequest(context, ConstantURL.BaseUrl, hashMap, responseListener, Constant.changePasswordCode,true);
    }

    public static void updateProfile(final Context context,
                                     final CSResponseListener responseListener,
                                     final User tempUser,
                                     final Uri selectedUri) {

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("rquest", ConstantURL.updateProfile);

        hashMap.put("user_id", tempUser.getUserId());

        hashMap.put("first_name", tempUser.getName());
        hashMap.put("dp_user_name", tempUser.getName());

        hashMap.put("email", tempUser.getEmail());
        hashMap.put("mobile", "91"+tempUser.getMobileNumber());
        hashMap.put("gender", tempUser.getGender());
        hashMap.put("dob", tempUser.getDob());
        hashMap.put("country", tempUser.getAddress());
        hashMap.put("info_url", tempUser.getInfoUrl());
        hashMap.put("user_type", tempUser.getUserType());

        hashMap.put("last_name", "");
        hashMap.put("imei", "");
        hashMap.put("device_model", "");
        hashMap.put("onesignal_id", "");
        hashMap.put("os", "Android");

*//*
        try {
            if(selectedUri!=null) {
                String encodedImage = Base64.encodeToString(getBytes(context, selectedUri), Base64.DEFAULT);
                hashMap.put("profile_pic", encodedImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
*//*


        CSMultiPartRequest.updateProfile(context,
                ConstantURL.BaseUrl,responseListener,hashMap,selectedUri,Constant.updateProfileRequestCode,true);

//        CSRequestUtil.getInstance().doPostStringRequest(context, ConstantURL.BaseUrl, hashMap, responseListener, Constant.updateProfileRequestCode,true);
    }


    */

    @Override
    public void onGetResponse(JSONObject jsonObject, int requestCode) {

    }

    @Override
    public void onFail(int requestCode) {

    }
}
