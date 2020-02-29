package com.auvi.listener;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.View;
import android.view.animation.AnimationUtils;

import com.auvi.R;
import com.auvi.activity.base.CSActivity;
import com.auvi.fragment.ProfileFragment;
import com.auvi.util.CSAppUtil;
import com.auvi.util.CSShearedPrefence;

public class MenuItemClick implements View.OnClickListener {

    private CSActivity csActivity;
    private DrawerLayout drawer;

    public MenuItemClick(CSActivity csActivity,DrawerLayout drawer){
        this.csActivity = csActivity;
        this.drawer = drawer;
    }


    @Override
    public void onClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(csActivity, R.anim.click_anim));
        view.requestFocus();
        CSAppUtil.closeKeyboard(csActivity,view);
        switch (view.getId()){
            case R.id.header_menu_button:
                if(drawer!=null)
                    drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.user_name_header:
                if(CSShearedPrefence.getAccountRole() == 2){
                    return;
                }
                CSAppUtil.openFragmentAfterLoginNoAnim(new ProfileFragment());
                break;
        }
        closeDrawer();
    }

    private void closeDrawer(){
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
    }

}
