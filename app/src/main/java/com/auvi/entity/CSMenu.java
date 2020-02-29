package com.auvi.entity;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import com.auvi.entity.base.CSObject;

@SuppressLint("ParcelCreator")
public class CSMenu extends CSObject {

    @SerializedName("name")
    public String itemName;

    @SerializedName("icon_name")
    public String iconName;

    @SerializedName("class_name")
    public String itemFragmentName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

}
