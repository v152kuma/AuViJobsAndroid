package com.auvi.entity;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import com.auvi.entity.base.CSObject;

@SuppressLint("ParcelCreator")
public class User extends CSObject {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("social_id")
    private String socialId;

    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("mobile")
    private String mobileNumber;

    @SerializedName("gender")
    private String gender;

    @SerializedName("dob")
    private String dob;

    @SerializedName("country")
    private String address;

    @SerializedName("dp_profile_pic_url")
    private String dpProfilePicUrl;

    @SerializedName("creation_time")
    private String creationTime;

    @SerializedName("dp_user_name")
    private String dpUserName;

    @SerializedName("imei")
    private String imei;

    @SerializedName("device_model")
    private String deviceModel;

    @SerializedName("onesignal_id")
    private String oneSignalId;

    @SerializedName("os")
    private String os;

    @SerializedName("is_block")
    private String isBlock;

    @SerializedName("created_on")
    private String createdOn;

    @SerializedName("updated_on")
    private String updatedOn;

    @SerializedName("user_type")
    private String userType;

    @SerializedName("info_url")
    private String infoUrl;



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDpProfilePicUrl() {
        return dpProfilePicUrl;
    }

    public void setDpProfilePicUrl(String dpProfilePicUrl) {
        this.dpProfilePicUrl = dpProfilePicUrl;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getDpUserName() {
        return dpUserName;
    }

    public void setDpUserName(String dpUserName) {
        this.dpUserName = dpUserName;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getOneSignalId() {
        return oneSignalId;
    }

    public void setOneSignalId(String oneSignalId) {
        this.oneSignalId = oneSignalId;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getIsBlock() {
        return isBlock;
    }

    public void setIsBlock(String isBlock) {
        this.isBlock = isBlock;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }
}
