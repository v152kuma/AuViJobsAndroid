package com.auvi.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.widget.ImageView;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import com.auvi.R;
import com.auvi.activity.base.CSActivity;
import com.auvi.application.CSApplicationHelper;
import com.auvi.constant.Constant;
import com.auvi.util.CSAppUtil;
import com.auvi.util.CSImageUtils;
import com.auvi.util.CSStringUtil;

public class FullImageActivity extends CSActivity {

    ImageView imgView;
    String imageUrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        imgView = findViewById(R.id.img_full_view);
        if (getIntent() != null) {
            imageUrl = getIntent().getStringExtra("url");
        }
        findViewById(R.id.btn_download_count).setOnClickListener(v -> downloadImage());
        updateUi();
    }

    private void updateUi() {
        if (imageUrl != null) {
            Picasso.get()
                    .load(imageUrl)
                    .fit()
                    .centerInside()
                    .into(imgView,new Callback() {
                        @Override
                        public void onSuccess() {
                            imgView.setOnTouchListener(new ImageMatrixTouchHandler(FullImageActivity.this));
                        }
                        @Override
                        public void onError(Exception e) {
                            Log.d("TAG", "onError: ");
                        }
                    });
        }
    }

    private boolean downloadImage(){
        if (!(ActivityCompat.checkSelfPermission(CSApplicationHelper.application().getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&  Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constant.PERMISSIONS_REQUEST);
        }
        else {
            if (CSStringUtil.isNonEmptyStr(imageUrl)) {
                String[] pathArray = imageUrl.split("/");
                CSImageUtils.downloadImage(this, imgView, pathArray[pathArray.length - 1]);
                CSAppUtil.showToast(R.string.downloading_image);
                return true;
            }
            CSAppUtil.showToast(R.string.image_not_exist);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Constant.PERMISSIONS_REQUEST && grantResults.length > 0 && permissions.length == grantResults.length) {
            downloadImage();
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        recreate();
    }
}
