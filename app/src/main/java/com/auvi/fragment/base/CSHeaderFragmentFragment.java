package com.auvi.fragment.base;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.auvi.R;
import com.auvi.application.CSApplicationHelper;
import com.auvi.entity.base.CSObject;
import com.auvi.listener.DialogCallback;
import com.auvi.listener.OnItemClickPerform;
import com.auvi.listener.RecyclerItemClickListener;
import com.auvi.util.CSAppUtil;
import com.auvi.util.CSStringUtil;

abstract public class CSHeaderFragmentFragment extends CSFragment
        implements DrawerLayout.DrawerListener,
        RecyclerItemClickListener.OnItemClickListener,
        DialogCallback,OnItemClickPerform{

    public View headerMainView;
    private View headerBackButton;
    public View headerMenuButton;
    private TextView headerViewTitle;
    public HeaderItemClick headerItemClick;
    public View searchButton;
    public SearchView headerSearchView;
    public ImageView likeButton;

    public int headerViewTitle() {
        return R.string.app_name;
    }

    public String headerTitle(){
        return CSStringUtil.getString(headerViewTitle());
    }

    public void setUpMainHeaderView() {
        headerMainView = csActivity.findViewById(R.id.header);
        if (headerMainView != null) {
            headerMainView.setVisibility(headerViewVisibility());
            setUpHeaderItem();
        }
        if(CSApplicationHelper.application().getDrawerLayout()!=null){
            CSApplicationHelper.application().getDrawerLayout().addDrawerListener(this);
        }
    }


    private void setUpHeaderItem(){
        initHeaderViewItems();
        setUpHeaderTitle();
    }

    private void initHeaderViewItems(){

        headerMenuButton = csActivity.findViewById(R.id.header_menu_button);
        headerBackButton = csActivity.findViewById(R.id.header_back_button);
        headerViewTitle = csActivity.findViewById(R.id.header_title_text);
        searchButton = csActivity.findViewById(R.id.search_button);
        likeButton = csActivity.findViewById(R.id.interest_button);
        headerSearchView = csActivity.findViewById(R.id.search_view);
        setUpHeaderItemVisibleState();
        setUpHeaderItemOnTouchListener();
    }

    private void setUpHeaderTitle(){
        headerViewTitle.setText(headerTitle());
    }

    private void setUpHeaderItemOnTouchListener(){
        headerItemClick = new HeaderItemClick();
        headerBackButton.setOnClickListener(headerItemClick);
        searchButton.setOnClickListener(headerItemClick);
        likeButton.setOnClickListener(headerItemClick);
    }

    private void setUpHeaderItemVisibleState(){
        headerBackButton.setVisibility(backButtonVisibility());
        headerMenuButton.setVisibility(backButtonVisibility() == View.VISIBLE ? View.GONE:View.VISIBLE);
        searchButton.setVisibility(searchButtonVisibility());
        likeButton.setVisibility(likeButtonVisibility());
    }

    public int backButtonVisibility(){
        return View.GONE;
    }

    public int headerViewVisibility(){
        return View.VISIBLE;
    }

    public int searchButtonVisibility(){
        return View.GONE;
    }

    public int likeButtonVisibility(){
        return View.GONE;
    }


    @Override
    public void getArgs() {

    }
    @Override
    public void onDrawerOpened(@NonNull View arg0) {
    }

    @Override
    public void onDrawerClosed(@NonNull View arg0) {

    }

    @Override
    public void onDrawerSlide(@NonNull View arg0, float arg1) {
        if(fragmentView!=null) {
            fragmentView.clearFocus();
            CSAppUtil.closeKeyboard(csActivity, fragmentView);
        }
    }

    @Override
    public void onDrawerStateChanged(int arg0) {

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public class HeaderItemClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.header_back_button:
                    if(headerSearchView.getVisibility() == View.VISIBLE){
                        headerSearchView.setVisibility(View.GONE);
                        return;
                    }
                    csActivity.onBackPressed();
                    break;
                case R.id.search_button:
                    searchActionPerform();
                    break;
                case R.id.interest_button:
                    likeActionPerform();
                    break;
            }
        }
    }

    @Override
    public void onClickOk(int requestCode, Object object) {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void OnItemClickPerform(CSObject csObject,int requestCode) {

    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        int notPermission = 0;

        if (requestCode == 1000) {
            for (int i = 0, len = permissions.length; i < len; i++) {
                String permission = permissions[i];
                if (grantResults.length > 0 && grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    notPermission ++;
                }
            }
            if(notPermission!=0){
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    try {
                        new AlertDialog.Builder(csActivity)
                                .setCancelable(false)
                                .setTitle("Permission Required")
                                .setMessage("Allow Permission use this service")
                                .setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivityForResult(new Intent(android.provider.Settings.ACTION_APPLICATION_SETTINGS), 100);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                actionAfterPermission();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == Activity.RESULT_OK){
            checkPermission();
        }
    }

    public void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        }
        else {
            actionAfterPermission();
        }
    }

    public void actionAfterPermission(){

    }

    public void searchActionPerform(){

    }

    public void likeActionPerform(){

    }

    protected <T extends View> T findViewById(int id){
        if(fragmentView != null){
            return fragmentView.findViewById(id);
        }
        return null;
    }

}