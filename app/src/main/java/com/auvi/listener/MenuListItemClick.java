package com.auvi.listener;

import android.view.View;
import android.view.animation.AnimationUtils;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.auvi.R;
import com.auvi.activity.base.CSActivity;
import com.auvi.application.CSApplicationHelper;
import com.auvi.fragment.ActivityFragment;
import com.auvi.fragment.ChangePasswordFragment;
import com.auvi.fragment.HomeFragment;
import com.auvi.fragment.PostCreationFragment;
import com.auvi.util.CSAppUtil;
import com.auvi.util.CSDialogUtil;
import com.auvi.util.CSShearedPrefence;

public class MenuListItemClick implements RecyclerItemClickListener.OnItemClickListener  {

    private CSActivity csActivity;
    private DrawerLayout drawer;
    private int accountRole;

    public MenuListItemClick(CSActivity csActivity,DrawerLayout drawer,int accountRole){
        this.csActivity = csActivity;
        this.drawer = drawer;
        this.accountRole =accountRole;
    }

    @Override
    public void onItemClick(View view, int position) {
        view.requestFocus();
        view.startAnimation(AnimationUtils.loadAnimation(CSApplicationHelper.application(), R.anim.click_anim));

        switch (accountRole){
            case 0:
                performMenu(position);
                break;
            case 1:
                performMenuEmployee(position);
                break;
            case 2:
                performMenuAdmin(position);
                break;
        }
        closeDrawer();
    }


    private void performMenu(int position){
        switch (position){
            case 0:
                CSAppUtil.openFragmentNoAnim(new HomeFragment());
                break;
            case 1:
                CSAppUtil.openFragmentAfterLoginNoAnim(new ActivityFragment());
                break;
            case 2:
                CSAppUtil.openFragmentAfterLoginNoAnim(new ChangePasswordFragment());
                break;
            case 3:
                if(CSShearedPrefence.isLoggedIn()) {
                    CSDialogUtil.showInfoDialog(csActivity, csActivity,R.string.do_you_want_logout,R.string.logout,false);
                }
                else {
                    CSAppUtil.showToast(R.string.without_login);
                }
                break;
            default:
                CSAppUtil.showToast(R.string.coming_soon);
                break;
        }
    }

    private void performMenuEmployee(int position){
        switch (position){
            case 0:
                CSAppUtil.openFragmentNoAnim(new PostCreationFragment());
                break;
            case 1:
                CSAppUtil.openFragmentAfterLoginNoAnim(new ActivityFragment());
                break;
            case 2:
                CSAppUtil.openFragmentAfterLoginNoAnim(new ChangePasswordFragment());
                break;
            case 3:
                if(CSShearedPrefence.isLoggedIn()) {
                    CSDialogUtil.showInfoDialog(csActivity, csActivity,R.string.do_you_want_logout,R.string.logout,false);
                }
                else {
                    CSAppUtil.showToast(R.string.without_login);
                }
                break;
            default:
                CSAppUtil.showToast(R.string.coming_soon);
                break;
        }
    }

    private void performMenuAdmin(int position){
        switch (position){
            case 0:
                CSAppUtil.openFragmentNoAnim(new HomeFragment());
                break;
            case 1:
                CSAppUtil.openFragmentAfterLoginNoAnim(new ChangePasswordFragment());
                break;
            case 2:
                if(CSShearedPrefence.isLoggedIn()) {
                    CSDialogUtil.showInfoDialog(csActivity, csActivity,R.string.do_you_want_logout,R.string.logout,false);
                }
                else {
                    CSAppUtil.showToast(R.string.without_login);
                }
                break;
        }
    }

    private void closeDrawer(){
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
    }

}
