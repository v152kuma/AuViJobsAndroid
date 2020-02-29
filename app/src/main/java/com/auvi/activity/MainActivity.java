package com.auvi.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvi.R;
import com.auvi.fragment.AccountTypeSelectionFragment;
import com.auvi.fragment.HomeAdminFragment;
import com.auvi.fragment.HomeFragment;
import com.auvi.fragment.PostCreationFragment;
import com.auvi.view.CSDrawable;
import com.auvi.view.CircleTransform;
import com.auvi.activity.base.CSActivity;
import com.auvi.adapter.MenuListAdapter;
import com.auvi.application.CSApplicationHelper;
import com.auvi.constant.Constant;
import com.auvi.constant.ConstantURL;
import com.auvi.entity.CSMenu;
import com.auvi.entity.User;
import com.auvi.listener.MenuItemClick;
import com.auvi.listener.MenuListItemClick;
import com.auvi.listener.OnBackPress;
import com.auvi.listener.RecyclerItemClickListener;
import com.auvi.parse.CSGson;
import com.auvi.util.CSAppUtil;
import com.auvi.util.CSShearedPrefence;
import com.auvi.util.CSStringUtil;
import com.auvi.util.CSUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends CSActivity implements View.OnClickListener,DrawerLayout.DrawerListener {

    DrawerLayout drawer;
    MenuListAdapter menuAdaptor;
    RecyclerView recyclerMenuList;
    TextView headerUserName;
    View userNameHeader;
    MenuItemClick menuItemClick;
    MenuListItemClick menuListItemClick;
    ImageView userImage;
    private int accountRole;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CSApplicationHelper.application().setActivity(this);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        accountRole = CSShearedPrefence.getAccountRole();
        init();
        setSelectionHomePage();
    }


    private void setSelectionHomePage(){
        switch (accountRole){
            case 0:
                CSAppUtil.openFragmentNoAnim(new HomeFragment());
                break;
            case 1:
                CSAppUtil.openFragmentNoAnim(new PostCreationFragment());
                break;
            case 2:
                CSAppUtil.openFragmentNoAnim(new HomeAdminFragment());
                break;
        }
    }

    private void init() {
        CSApplicationHelper.application().setActivity(this);
        drawer = findViewById(R.id.drawer_layout);
        CSApplicationHelper.application().setDrawerLayout(drawer);
        recyclerMenuList = drawer.findViewById(R.id.menu_list);
        headerUserName = drawer.findViewById(R.id.user_name);
        userNameHeader = drawer.findViewById(R.id.user_name_header);
        userImage = drawer.findViewById(R.id.user_image);
        setUpClickListener();
    }


    public void setUpDetailsMenu() {
        if (CSShearedPrefence.isLoggedIn()) {
            if (CSApplicationHelper.application().getCurrentLoggedInUser() != null) {
                User user = CSApplicationHelper.application().getCurrentLoggedInUser();
                String value = user.getName();
                if (CSStringUtil.isNonEmptyStr(value)) {
                    value = value.trim();
                    headerUserName.setText(value.split(" ")[0]);
                } else {
                    if(accountRole == 2){
                        headerUserName.setText("Admin");
                    }
                    headerUserName.setText(CSStringUtil.getString(R.string.guest));
                }
                userNameHeader.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(ConstantURL.BaseImageUrl + user.getDpProfilePicUrl())
                        .error(CSDrawable.getDrawable(R.drawable.ic_account_circle))
                        .placeholder(CSDrawable.getDrawable(R.drawable.ic_account_circle))
                        .centerCrop()
                        .fit()
                        .transform(new CircleTransform())
                        .into(userImage);
            } else {
                if(accountRole == 2){
                    headerUserName.setText("Admin");
                }
                headerUserName.setText(CSStringUtil.getString(R.string.guest));
                userNameHeader.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setUpAdapter(){

        String menuList = CSUtil.getFileContent(accountRole == 2 ? "menu_admin.json":"menu.json");
        ArrayList<CSMenu> list = new ArrayList<>(CSGson.gson().getList(CSMenu.class,menuList));
        CSApplicationHelper.application().setMenuList(list);
        menuAdaptor = new MenuListAdapter(this,list);
        recyclerMenuList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerMenuList.setAdapter(menuAdaptor);
    }

    private void setUpClickListener(){
        menuItemClick = new MenuItemClick(this,drawer);
        menuListItemClick = new MenuListItemClick(this,drawer,accountRole);
        if(findViewById(R.id.header_menu_button)!=null) {
            findViewById(R.id.header_menu_button).setVisibility(View.VISIBLE);
            findViewById(R.id.header_menu_button).setOnClickListener(menuItemClick);
        }
        drawer.addDrawerListener(this);
        userNameHeader.setOnClickListener(menuItemClick);
        recyclerMenuList.addOnItemTouchListener(new RecyclerItemClickListener(this,menuListItemClick));
    }

    @Override
    public void onBackPressed() {

        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if(getSupportFragmentManager().findFragmentById(R.id.container)!=null && ((OnBackPress)
                Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.container))).onBackPress()){
            return;
        }
        if(getSupportFragmentManager().getBackStackEntryCount()>0) {
            getSupportFragmentManager().popBackStack();
            return;
        }
        switch (accountRole){
            case 0:
                if(!(getSupportFragmentManager().findFragmentById(R.id.container) instanceof HomeFragment)){
                    CSAppUtil.openFragmentNoAnim(new HomeFragment());
                    return;
                }
                break;
            case 2:
                if(!(getSupportFragmentManager().findFragmentById(R.id.container) instanceof HomeAdminFragment)){
                    CSAppUtil.openFragmentNoAnim(new HomeAdminFragment());
                    return;
                }
                break;
        }

        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        closeDrawer();
        setUpDetailsMenu();
        setUpAdapter();
    }

    private void closeDrawer(){
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onDrawerSlide(@NonNull View view, float v) {

    }

    @Override
    public void onDrawerOpened(@NonNull View view) {

    }

    @Override
    public void onDrawerClosed(@NonNull View view) {

    }

    @Override
    public void onDrawerStateChanged(int i) {

    }

    @Override
    public void onClickOk(int requestCode, Object object) {
        if(requestCode == Constant.deleteAgent){
            CSAppUtil.showToast(R.string.logout_success);
            CSShearedPrefence.setIsLoggedIn(false);
            CSAppUtil.openLogin();
        }
    }

    @Override
    public void onCancel() {

    }
}
