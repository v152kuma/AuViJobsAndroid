package com.auvi.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.view.View;
import com.auvi.R;
import com.auvi.activity.base.CSActivity;
import com.auvi.application.CSApplicationHelper;
import com.auvi.entity.CSMenu;
import com.auvi.entity.User;
import com.auvi.parse.CSGson;
import com.auvi.util.CSAppUtil;
import com.auvi.util.CSShearedPrefence;
import com.auvi.util.CSStringUtil;
import com.auvi.util.CSUtil;
import java.util.TimeZone;

//Splash Activity ........
public class SplashScreenActivity extends CSActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_splash);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        CSApplicationHelper.application().setActivity(this);
//        fetchHeaderData();
        fetchUser();
        TimeZone tz = TimeZone.getDefault();
        System.out.println("TimeZone   "+tz.getDisplayName(false, TimeZone.SHORT)+" Time zone id :: " +tz.getID());
        openPreferredActivity();
    }

    private void fetchUser(){
        String userJson =  CSShearedPrefence.getUser();
        if(CSStringUtil.isNonEmptyStr(userJson)){
            User user = CSGson.gson().getObject(User.class,userJson);
            CSApplicationHelper.application().setCurrentLoggedInUser(user);
        }
//        CSNetworkUtil.getInstance().getAllKeyword(this);
    }

/*
    private void fetchHeaderData(){
        try{
            String menuList = CSUtil.getFileContent("menu.json");
            CSApplicationHelper.application().setMenuList(CSGson.gson().getList(CSMenu.class,menuList));
            openPreferredActivity();
        }catch (Exception ex){
            ex.printStackTrace();
            this.finish();
        }
    }
*/


    private void openPreferredActivity(){

        new Handler().postDelayed(() -> CSAppUtil.openActivity(CSShearedPrefence.isLoggedIn()?MainActivity.class:LoginActivity.class), 1000);

    }


}


/*
{
        "id":4,
        "icon_name": "",
        "name":"Change password",
        "class_name":""
        },
        {
        "id":2,
        "icon_name": "",
        "name":"Privacy Policy",
        "class_name":""
        },
        {
        "id":5,
        "icon_name": "",
        "name":"Terms and Conditions",
        "class_name":""
        },
        {
        "id":6,
        "icon_name": "",
        "name":"Rate Us",
        "class_name":""
        },*/
