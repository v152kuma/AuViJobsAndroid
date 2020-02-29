package com.auvi.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model class for Product Category PO JO*/
public class Category {

    @SerializedName("CategoryName")
    @Expose
    private String CategoryName;

    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("addedBy")
    @Expose
    private String addedBy;

    private boolean isExpended;

    public Category(String categoryName,boolean isExpended){
        this.CategoryName = categoryName;
        this.isExpended = isExpended;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public boolean isExpended() {
        return isExpended;
    }

    public void setExpended(boolean expended) {
        isExpended = expended;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getCategoryName();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.getCategoryName().equalsIgnoreCase(((Category)obj).getCategoryName());
    }
}
