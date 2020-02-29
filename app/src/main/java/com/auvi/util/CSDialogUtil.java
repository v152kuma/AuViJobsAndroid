package com.auvi.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvi.R;
import com.auvi.view.CSDrawable;
import com.auvi.view.CircleTransform;
import com.auvi.constant.Constant;
import com.auvi.constant.ConstantURL;
import com.auvi.entity.User;
import com.auvi.fragment.base.CSHeaderFragmentFragment;
import com.auvi.listener.DialogCallback;
import com.squareup.picasso.Picasso;

import java.io.File;


public class CSDialogUtil {

    public static void showImageDialog(AppCompatActivity context,Uri selectImageURI,String url) {

        AlertDialog.Builder ad = new AlertDialog.Builder(context, R.style.DialogBox);
        LayoutInflater inflater = context.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.image_dialog, null);
        ad.setView(dialogView);
        ImageView selectedImage = dialogView.findViewById(R.id.image_view);

        if(selectImageURI!=null) {
            Picasso.get()
                    .load(selectImageURI)
                    .error(CSDrawable.getDrawable(R.drawable.ic_account_circle))
/*                    .centerCrop()
                    .fit()
                    .transform(new CircleTransform())*/
                    .into(selectedImage);
        }else {
            Picasso.get()
                    .load(url)
                    .error(CSDrawable.getDrawable(R.drawable.ic_account_circle))
/*                    .centerCrop()
                    .fit()
                    .transform(new CircleTransform())*/
                    .into(selectedImage);
        }

        final AlertDialog alertDialog = ad.create();
        alertDialog.show();
    }

    public static void showInfoDialog(AppCompatActivity context,
                                      User user, String userAgentTitle) {

        AlertDialog.Builder ad = new AlertDialog.Builder(context, R.style.DialogBox);
        LayoutInflater inflater = context.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_dialog_box, null);
        ad.setView(dialogView);

        final TextView title = dialogView.findViewById(R.id.dialog_title);
        title.setText(userAgentTitle);
        final TextView name = dialogView.findViewById(R.id.name);
        final TextView email = dialogView.findViewById(R.id.email);
        final TextView phoneNumber = dialogView.findViewById(R.id.phn_nmbr);
        final TextView websiteUrl = dialogView.findViewById(R.id.website_url);
        final ImageView userImage = dialogView.findViewById(R.id.user_image);

        if(CSStringUtil.isNonEmptyStr(user.getDpProfilePicUrl())){
            Picasso.get()
                    .load(ConstantURL.BaseImageUrl + user.getDpProfilePicUrl())
                    .error(CSDrawable.getDrawable(R.drawable.ic_account_circle))
                    .placeholder(CSDrawable.getDrawable(R.drawable.ic_account_circle))
                    .centerCrop()
                    .fit()
                    .transform(new CircleTransform())
                    .into(userImage);
        }
        if(CSStringUtil.isNonEmptyStr(user.getName())){
            name.setText(user.getName());
        }

        if(CSStringUtil.isNonEmptyStr(user.getEmail())){
            email.setText(user.getEmail());
        }
        if(CSStringUtil.isNonEmptyStr(user.getMobileNumber())){
            phoneNumber.setText(user.getMobileNumber());
        }

        if(CSStringUtil.isNonEmptyStr(user.getInfoUrl())){
            websiteUrl.setText(user.getInfoUrl());
            websiteUrl.setVisibility(View.VISIBLE);
        }
        else {
            websiteUrl.setVisibility(View.GONE);
        }

        final AlertDialog alertDialog = ad.create();
        alertDialog.setOnKeyListener((arg0, keyCode, event) -> {
            // TODO Auto-generated method stub
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                alertDialog.dismiss();
            }
            return true;
        });
        View closeDialog = dialogView.findViewById(R.id.close_dialog);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public static void showInfoDialog(AppCompatActivity context,DialogCallback dialogCallback,String userAgentTitle,boolean isNotCancelVisible) {

        AlertDialog.Builder ad = new AlertDialog.Builder(context, R.style.DialogBox);
        LayoutInflater inflater = context.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_dialog, null);
        ad.setView(dialogView);

        final TextView title = dialogView.findViewById(R.id.dialog_title);
        title.setText(userAgentTitle);
        final TextView okAction = dialogView.findViewById(R.id.ok_action);
        okAction.setText(R.string.ok);
        final TextView cancelAction = dialogView.findViewById(R.id.cancel_action);
        if(isNotCancelVisible)
            cancelAction.setVisibility(View.GONE);
        final AlertDialog alertDialog = ad.create();
        alertDialog.setOnKeyListener((arg0, keyCode, event) -> {
            // TODO Auto-generated method stub
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                alertDialog.dismiss();
            }
            return true;
        });
        okAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCallback.onClickOk(Constant.deleteAgent,null);
                alertDialog.dismiss();
            }
        });

        cancelAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public static void showInfoDialog(AppCompatActivity context,DialogCallback dialogCallback,int userAgentTitle,int okMessage,boolean isNotCancelVisible) {

        AlertDialog.Builder ad = new AlertDialog.Builder(context, R.style.DialogBox);
        LayoutInflater inflater = context.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_dialog, null);
        ad.setView(dialogView);

        final TextView title = dialogView.findViewById(R.id.dialog_title);
        title.setText(userAgentTitle);
        final TextView okAction = dialogView.findViewById(R.id.ok_action);
        okAction.setText(okMessage);
        final TextView cancelAction = dialogView.findViewById(R.id.cancel_action);
        if(isNotCancelVisible)
            cancelAction.setVisibility(View.GONE);
        final AlertDialog alertDialog = ad.create();
        alertDialog.setOnKeyListener((arg0, keyCode, event) -> {
            // TODO Auto-generated method stub
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                alertDialog.dismiss();
            }
            return true;
        });
        okAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCallback.onClickOk(Constant.deleteAgent,null);
                alertDialog.dismiss();
            }
        });

        cancelAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }


    public static void searchDialog(AppCompatActivity context,
                                    CSHeaderFragmentFragment csHeaderFragmentFragment,
                                    String agentName) {

        AlertDialog.Builder ad = new AlertDialog.Builder(context, R.style.DialogBox);
        LayoutInflater inflater = context.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_search_box, null);
        ad.setView(dialogView);

        final EditText email = dialogView.findViewById(R.id.email);
        final EditText phoneNumber = dialogView.findViewById(R.id.phn_nmbr);

        View search = dialogView.findViewById(R.id.search_button);

        View closeDialog = dialogView.findViewById(R.id.close_dialog);

        final AlertDialog alertDialog = ad.create();
/*        alertDialog.setOnKeyListener((arg0, keyCode, event) -> {
            // TODO Auto-generated method stub
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                alertDialog.dismiss();
            }
            return true;
        });*/

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempString = CSStringUtil.getTextFromView(email);
                if(CSStringUtil.isNonEmptyStr(tempString)
                        || CSStringUtil.isNonEmptyStr(CSStringUtil.getTextFromView(phoneNumber))){
                    if(CSStringUtil.isNonEmptyStr(tempString) && CSStringUtil.isNonValidEmail(tempString)){
                        CSAppUtil.showToast(R.string.error_enter_valid_mail);
                        return;
                    }
/*
                    CSNetworkUtil.searchByEmailPhone(context,
                            csHeaderFragmentFragment,
                            tempString,
                            agentName,
                            CSStringUtil.getTextFromView(phoneNumber));
*/

                    CSAppUtil.closeKeyboard(context,v);
                    alertDialog.dismiss();
                }
                else {
                    CSAppUtil.showToast(R.string.error_enter_valid_mail_or_phone);
                    return;
                }
                alertDialog.dismiss();
            }
        });

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CSAppUtil.closeKeyboard(context,v);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public static void audioDialoag(Context context,
                                    File file) {

        try {
            Intent openIntent = new Intent(Intent.ACTION_VIEW);

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                openIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, "com.nocv", file);
                openIntent.setDataAndType(contentUri, "audio/mpeg");
            } else {
                openIntent.setDataAndType(Uri.fromFile(file), "audio/mpeg");
            }
            context.startActivity(openIntent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void audioDialoag(Context context,
                                    Uri uri,File file) {

        try {
            Intent openIntent = new Intent(Intent.ACTION_VIEW);

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                openIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                openIntent.setDataAndType(uri, "audio/mpeg");
            } else {
                openIntent.setDataAndType(uri, "audio/mpeg");
            }
            context.startActivity(openIntent);
        } catch (Exception ex) {
            try {
                Intent openIntent = new Intent(Intent.ACTION_VIEW);

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    openIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(context, "com.nocv", file);
                    openIntent.setDataAndType(contentUri, "audio/mpeg");
                } else {
                    openIntent.setDataAndType(Uri.fromFile(file), "audio/mpeg");
                }
                context.startActivity(openIntent);
            } catch (Exception exE) {
                exE.printStackTrace();
            }
        }
    }

}

