package com.auvi.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.appcompat.app.AlertDialog;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONException;
import org.json.JSONObject;
import com.auvi.R;
import com.auvi.activity.MainActivity;
import com.auvi.application.CSApplicationHelper;
import com.auvi.constant.Constant;
import com.auvi.constant.ConstantURL;
import com.auvi.entity.User;
import com.auvi.fragment.base.CSHeaderFragmentFragment;
import com.auvi.parse.CSGson;
import com.auvi.util.CSAppUtil;
import com.auvi.util.CSDialogUtil;
import com.auvi.util.CSShearedPrefence;
import com.auvi.util.CSStringUtil;
import com.auvi.util.CSUIUtil;
import com.auvi.view.CSDrawable;
import com.auvi.view.CircleTransform;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ProfileFragment extends CSHeaderFragmentFragment {

    User userProfile;

    EditText email,name,mobileNumber,dob,address,webSiteUrl;
    RadioGroup genderButton;
    RadioButton maleRadiobtn;
    RadioButton femaleRadiobtn;

    RadioGroup typeButton;
    RadioButton individudalRadiobtn;
    RadioButton companyRadiobtn;

    ImageView selectedImage;

    Uri selectedImageUri;
    Button submitBtn;
    final Calendar myCalendar = Calendar.getInstance();
    AlertDialog mBottomSheetDialog;

    @Override
    public void getArgs() {

    }

    @Override
    public int layoutResource() {
        return R.layout.fragment_profile;
    }

    @Override
    public int headerViewTitle() {
        return R.string.profile;
    }

    @Override
    public void init() {

        email = fragmentView.findViewById(R.id.email);
        name=fragmentView.findViewById(R.id.name);
        mobileNumber=fragmentView.findViewById(R.id.mobile_no);
        int maxLength = 10;
        mobileNumber.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        dob =fragmentView.findViewById(R.id.dob);
        selectedImage = fragmentView.findViewById(R.id.image);

        dob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dob.setClickable(false);
                dob.setEnabled(false);
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dob.setClickable(true);
                        dob.setEnabled(true);
                    }
                },1000);
                showDatePicker(v);
                return false;
            }
        });
        address=fragmentView.findViewById(R.id.address);
        /*applyDrawable(dob);*/
        CSUIUtil.applyEditTextListener(dob);
        genderButton=fragmentView.findViewById(R.id.gender_button);
        maleRadiobtn=fragmentView.findViewById(R.id.male);
        femaleRadiobtn=fragmentView.findViewById(R.id.female);

        submitBtn=fragmentView.findViewById(R.id.update_profile);
        webSiteUrl = fragmentView.findViewById(R.id.website_url);

        myCalendar.set(Calendar.YEAR,myCalendar.get(Calendar.YEAR) - 18);
        updateUi();
    }

    @Override
    public void setClickOnListener() {
        setClickListener(submitBtn,fragmentView.findViewById(R.id.select_photo),selectedImage);
    }

    @Override
    public void setOnData() {
    }

    @Override
    public void onGetResponse(JSONObject jsonObject, int requestCode) {
        try {
            if(requestCode == Constant.updateProfileRequestCode) {

                if (jsonObject.has("errCode")) {
                    if (jsonObject.getString("errCode").equalsIgnoreCase("-1")) {
                        userProfile = CSGson.gson().getObject(User.class, jsonObject.getString("user_data"));
                        CSShearedPrefence.setUser(jsonObject.getString("user_data"));
                        CSApplicationHelper.application().setCurrentLoggedInUser(userProfile);
                        if (CSApplicationHelper.application().getActivity() instanceof MainActivity) {
                            ((MainActivity) CSApplicationHelper.application().getActivity()).setUpDetailsMenu();
                        }
                        CSAppUtil.openFragmentNoAnim(new HomeFragment());
                        CSAppUtil.showToast(R.string.profile_updated_message);
                    } else if (jsonObject.getString("errCode").equalsIgnoreCase("2")){
                        OTPVerificationsFragment otpVerificationsFragment = new OTPVerificationsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("form_profile_fragment",true);
                        bundle.putString("mobile_number","91"+CSStringUtil.getTextFromView(mobileNumber));
                        bundle.putString("email",CSStringUtil.getTextFromView(email));
                        otpVerificationsFragment.setArguments(bundle);
                        CSAppUtil.openAddFragment(otpVerificationsFragment,"TAG");
                    }
                    else {
                        if(jsonObject.has(("message"))){
                            CSAppUtil.showToast(jsonObject.getString("message"));
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        }
        else {
            actionAfterPermission();
        }
    }

    @Override
    public void actionAfterPermission() {

        showSelectedImageDialog();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constant.PERMISSIONS_PHOTO && resultCode == Activity.RESULT_OK){
            selectedImageUri = data.getData();
            Picasso.get()
                    .load(selectedImageUri)
                    .error(CSDrawable.getDrawable(R.drawable.ic_account_circle))
                    .centerCrop()
                    .fit()
                    .transform(new CircleTransform())
                    .into(selectedImage);
        }
        else if(requestCode == Constant.PERMISSIONS_CAMERA && resultCode == Activity.RESULT_OK) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File destination = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".png");
            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectedImageUri = Uri.fromFile(destination);
        }
        loadImage();
    }


    private void loadImage(){
        Picasso.get()
                .load(selectedImageUri)
                .error(CSDrawable.getDrawable(R.drawable.ic_account_circle))
                .centerCrop()
                .fit()
                .transform(new CircleTransform())
                .into(selectedImage);
    }
    @Override
    public void onFail(int requestCode) {
        csActivity.onBackPressed();
    }

    private void updateUi() {
        userProfile = CSApplicationHelper.application().getCurrentLoggedInUser();
        if (userProfile != null) {
            if(CSStringUtil.isNonEmptyStr(userProfile.getEmail())) {
                fillValue(email,userProfile.getEmail());
            }
            if(CSStringUtil.isNonEmptyStr(userProfile.getName())) {
                fillValue(name,userProfile.getName());
            }
            if(CSStringUtil.isNonEmptyStr(userProfile.getMobileNumber())) {
                fillValue(mobileNumber,userProfile.getMobileNumber().replaceFirst("91",""));
            }
            if(CSStringUtil.isNonEmptyStr(userProfile.getDob()) && !userProfile.getDob().contains("0000")) {
                fillValue(dob,CSStringUtil.dataString(userProfile.getDob()));
            }
            if(CSStringUtil.isNonEmptyStr(userProfile.getAddress())) {
                fillValue(address,userProfile.getAddress());
            }

            if (CSStringUtil.isNonEmptyStr(userProfile.getGender()) && userProfile.getGender().equalsIgnoreCase("M")) {
                maleRadiobtn.setChecked(true);
            } else if (CSStringUtil.isNonEmptyStr(userProfile.getGender()) && userProfile.getGender().equalsIgnoreCase("F")) {
                femaleRadiobtn.setChecked(true);
            }

            if(CSStringUtil.isNonEmptyStr(userProfile.getInfoUrl())){
                fillValue(webSiteUrl,userProfile.getInfoUrl());
            }

            if(CSStringUtil.isNonEmptyStr(userProfile.getDpProfilePicUrl())){
                Picasso.get()
                        .load(ConstantURL.BaseImageUrl + userProfile.getDpProfilePicUrl())
                        .error(CSDrawable.getDrawable(R.drawable.ic_account_circle))
                        .centerCrop()
                        .fit()
                        .transform(new CircleTransform())
                        .into(selectedImage);
            }
        }
    }

    private void showDatePicker(View v){
        CSAppUtil.closeKeyboard(getContext(), v);
        DatePickerDialog datePicker = new DatePickerDialog(csActivity, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePicker.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
        datePicker.show();
    }
    DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
        // TODO Auto-generated method stub
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
    };

    private void updateLabel() {
        String myFormat = "dd-MMM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dob.setText(sdf.format(myCalendar.getTime()));
        dob.setSelection(dob.length());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.update_profile:
                updateProfile();
                CSAppUtil.closeKeyboard(getContext(),v);
                break;
            case R.id.select_photo:
                checkPermission();
                break;
            case R.id.image:
                if(selectedImageUri!=null) {
                    CSDialogUtil.showImageDialog(csActivity,selectedImageUri,null);
                }
                else {
                    if(userProfile!=null && CSStringUtil.isNonEmptyStr(userProfile.getDpProfilePicUrl()))
                        CSDialogUtil.showImageDialog(csActivity,null,ConstantURL.BaseImageUrl+userProfile.getDpProfilePicUrl());
                }
                break;
            case R.id.cam_image:
                if(mBottomSheetDialog!=null){
                    mBottomSheetDialog.dismiss();
                }
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, Constant.PERMISSIONS_CAMERA);
                break;
            case R.id.gallery_image:
                if(mBottomSheetDialog!=null){
                    mBottomSheetDialog.dismiss();
                }
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, Constant.PERMISSIONS_PHOTO);
                break;
        }
    }

    private void updateProfile(){
        if(CSStringUtil.isNonEmptyView(name,R.string.error_enter_name)
                && CSStringUtil.isNonEmptyView(mobileNumber,R.string.error_enter_phone)
                &&CSStringUtil.isNonEmptyView(email,R.string.error_email_empty)
                &&CSStringUtil.isNonEmptyView(dob,R.string.error_dob)
                &&CSStringUtil.isNonEmptyView(address,R.string.error_address)){

            if(CSStringUtil.isNonValidEmail(CSStringUtil.getTextFromView(email))) {
                CSAppUtil.showError(email,R.string.error_enter_valid_mail);
            }
            else if(CSStringUtil.isNonValidPhone(CSStringUtil.getTextFromView(mobileNumber)) || CSStringUtil.getTextFromView(mobileNumber).length() !=10){
                CSAppUtil.showError(mobileNumber,R.string.error_valid_phone);
            }
            else {

                User userTemp = new User();

                userTemp.setUserId(userProfile.getUserId());

                userTemp.setName(CSStringUtil.getTextFromView(name));
                userTemp.setMobileNumber(CSStringUtil.getTextFromView(mobileNumber));
                userTemp.setEmail(CSStringUtil.getTextFromView(email));
                userTemp.setDob(CSStringUtil.dataStringToServer(CSStringUtil.getTextFromView(dob)));
                userTemp.setAddress(CSStringUtil.getTextFromView(address));
                userTemp.setInfoUrl(CSStringUtil.getTextFromView(webSiteUrl));

                if (maleRadiobtn.isChecked() || femaleRadiobtn.isChecked()) {
                    if (maleRadiobtn.isChecked()) {
                        userTemp.setGender("M");
                    } else {
                        userTemp.setGender("F");
                    }

                }
                else {
                    CSAppUtil.showToast(R.string.select_gender);
                }
            }
        }
    }

    private void fillValue(EditText target,String text){
        text =  text.trim();
        target.setText(text);
        target.setSelection(text.length());
    }

    private void showSelectedImageDialog(){
        if(mBottomSheetDialog == null) {

            AlertDialog.Builder ad = new AlertDialog.Builder(csActivity, R.style.DialogBox);
            LayoutInflater inflater = csActivity.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.layout_dialog_option, null);
            ad.setView(dialogView);
            mBottomSheetDialog  = ad.create();

/*            mBottomSheetDialog = new Dialog(csActivity, R.style.DialogBox);
            View sheetView = csActivity.getLayoutInflater().inflate(R.layout.layout_dialog_option, null);
            mBottomSheetDialog.setContentView(sheetView);*/
            dialogView.findViewById(R.id.cam_image).setOnClickListener(this::onClick);
            dialogView.findViewById(R.id.gallery_image).setOnClickListener(this::onClick);
        }
        mBottomSheetDialog.show();
    }
}