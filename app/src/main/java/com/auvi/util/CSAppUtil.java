package com.auvi.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.auvi.adapter.SelectionAdapter;
import com.auvi.entity.Category;
import com.auvi.listener.OnItemSelection;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.auvi.R;
import com.auvi.activity.LoginActivity;
import com.auvi.application.CSApplicationHelper;

import java.util.ArrayList;

public class CSAppUtil {

    public static void openKeyBoard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    public static void closeKeyboard(final Context context, final View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm!=null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showToast(int resId) {
        Toast toast = Toast.makeText(CSApplicationHelper.application(), resId, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToast(String message) {
        Toast toast = Toast.makeText(CSApplicationHelper.application(), message, Toast.LENGTH_SHORT);
        /*toast.setGravity(Gravity.CENTER, 0, 0);*/
        if(!CSApplicationHelper.application().getActivity().isFinishing())
            toast.show();
    }

    public static void showError(EditText editText,int resId) {
        editText.setError(CSStringUtil.getString(resId));
        editText.requestFocus();
    }

    public static void setSnackBar(View coordinatorLayout, String snackTitle) {
        final Snackbar snackbar = Snackbar.make(coordinatorLayout, snackTitle, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.ok, view -> snackbar.dismiss());
        snackbar.show();
        View view = snackbar.getView();
        TextView txtv = view.findViewById(R.id.snackbar_text);
        txtv.setMaxLines(5);  // show multiple line
        txtv.setGravity(Gravity.CENTER_HORIZONTAL);

    }

    public static void setUpHeader(int id) {
//        ((TextView)(CSApplicationHelper.application().getActivity()).findViewById(R.id.header_text)).setText(CSApplicationHelper.application().getResources().getString(id));
    }

    public static void openFragmentNoAnim(Fragment fragment) {

        FragmentManager fragmentManager = CSApplicationHelper.application().getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStackImmediate();
        }

        fragmentTransaction.replace(R.id.container, fragment).commit();
    }

    public static void openAddFragmentNoAnim(Fragment fragment) {
        FragmentTransaction fragmentTransaction = CSApplicationHelper.application().getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).addToBackStack("").commit();
    }

    public static void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = CSApplicationHelper.application().getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.container, fragment).commit();
    }

    public static void openAddFragment(Fragment fragment, String TAG) {
        FragmentTransaction fragmentTransaction = CSApplicationHelper.application().getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.container, fragment).addToBackStack(TAG).commit();
    }

    public static void openFragmentAfterLoginNoAnim(Fragment fragment) {
        if(!openLogin())
            CSAppUtil.openFragmentNoAnim(fragment);
    }

    public static void openAddFragmentAfterLoginNoAnim(Fragment fragment) {
        if(!openLogin())
            CSAppUtil.openAddFragmentNoAnim(fragment);
    }



    public static void openFragmentAfterLoginWithAnim(Fragment fragment) {
        if(!openLogin())
            CSAppUtil.openFragment(fragment);
    }

    public static boolean openLogin(){
        if(!CSShearedPrefence.isLoggedIn()){
            CSAppUtil.openActivity(LoginActivity.class/*,true*/);
            return true;
        }
        return false;
    }

    public static void openActivity(Class target,boolean isCurrentFinish) {
        Intent intent = new Intent(CSApplicationHelper.application().getActivity(), target);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            CSApplicationHelper.application().getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
        }
        if(isCurrentFinish){
            CSApplicationHelper.application().getActivity().finish();
        }
        CSApplicationHelper.application().getActivity().startActivity(intent);

    }

    public static void openActivity(Class target) {
        AppCompatActivity appCompatActivity = CSApplicationHelper.application().getActivity();
        Intent intent = new Intent(appCompatActivity, target);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            appCompatActivity.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
        }
        appCompatActivity.startActivity(intent);
        appCompatActivity.finish();
    }

    public static void openActivity(Intent intent) {
        AppCompatActivity appCompatActivity = CSApplicationHelper.application().getActivity();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            appCompatActivity.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
        }
        appCompatActivity.startActivity(intent);
        appCompatActivity.finish();
    }


    public static boolean isNetworkAvailable(Context context,boolean isShowToast) {
        ConnectivityManager cm = null;
        if(context!=null) {
            cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        else {
            if(CSApplicationHelper.application().getApplicationContext()!=null){
                cm = (ConnectivityManager) CSApplicationHelper.application().getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
            }
        }

        if(cm!=null) {
            NetworkInfo netInfo;
            try {
                netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                    return true;
                } else if (isShowToast) {
                    CSAppUtil.showToast(R.string.internet_connection);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean isNetworkNotAvailable(Context context,boolean isShowToast) {
        return !isNetworkAvailable(context,isShowToast);
    }



}
