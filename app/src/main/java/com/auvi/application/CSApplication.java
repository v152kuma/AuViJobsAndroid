package com.auvi.application;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.auvi.constant.Constant;
import com.auvi.entity.CSMenu;
import com.auvi.entity.User;
import com.auvi.util.CSStringUtil;
import com.auvi.util.CSShearedPrefence;

/*import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;*/

import java.util.ArrayList;

//@ReportsCrashes(mailTo = "hershitaggarwal95@gmail.com",mode = ReportingInteractionMode.SILENT)
public class CSApplication extends Application{

    AppCompatActivity _activity;
    private String packageName;
    private RequestQueue mRequestQueue;
    private DrawerLayout drawerLayout;
    private ArrayList<CSMenu> menuList = new ArrayList<>();
    private User currentLoggedInUser;
    private Typeface _sTypeface;

    @Override
    public void onCreate() {
        super.onCreate();
        CSApplicationHelper.setCSApplication(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//        ACRA.init(this);
    }

    public Typeface getTypeface() {

        if (this._sTypeface == null) {
            this.initIcons();
        }
        return this._sTypeface;
    }

    public void initIcons() {
        this._sTypeface = Typeface.createFromAsset(this.getAssets(), Constant.ICON_FILE);
    }


    public AppCompatActivity getActivity() {
        return _activity;
    }

    public void setActivity(AppCompatActivity activity) {
        this._activity = activity;
        CSShearedPrefence.getInstance();
    }

    public int getResourceId(String resourceName, String defType) {
        return getResourceId(resourceName, defType, this.packageName());
    }

    public int getResourceId(String resourceName, String defType, String packageName) {
        return CSStringUtil.isNonEmptyStr(resourceName) ? this.getResources().getIdentifier(resourceName, defType, packageName) : 0;
    }

    public String packageName() {
        if (this.packageName == null) {
            PackageInfo info = this.packageInfo();
            this.packageName = info != null ? info.packageName : "com.coachingsolution.coaching";
        }
        return this.packageName;
    }

    public PackageInfo packageInfo() {
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
        }
        return info;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.setShouldCache(false);
        req.setTag(TextUtils.isEmpty(tag) ? "TAG" : tag);
        getRequestQueue().add(req);
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    public ArrayList<CSMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(ArrayList<CSMenu> menuList) {
        this.menuList = menuList;
    }

    public User getCurrentLoggedInUser() {
        return currentLoggedInUser;
    }

    public void setCurrentLoggedInUser(User currentLoggedInUser) {
        this.currentLoggedInUser = currentLoggedInUser;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    //First Sprint Successfully Done
}