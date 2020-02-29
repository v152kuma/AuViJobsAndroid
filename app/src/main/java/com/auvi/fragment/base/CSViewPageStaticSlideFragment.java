package com.auvi.fragment.base;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

import com.auvi.R;
import com.auvi.entity.base.CSObject;
import com.auvi.listener.GetCurrentPosition;

import java.util.ArrayList;

abstract public class CSViewPageStaticSlideFragment extends CSHeaderFragmentFragment implements GetCurrentPosition{

    public ArrayList<CSObject> pagerList = new ArrayList<>();
    protected CSHeaderFragmentFragment csFragment;
    public ViewPager viewPager;
    protected int setCurrentItem = -1;
    public TabLayout tabLayout;

    @Override
    public void getArgs() {

    }

    @Override
    public int layoutResource() {
        return R.layout.fragment_view_pager_layout;
    }

    @Override
    public void init() {

        viewPager = fragmentView.findViewById(R.id.viewpager_memories);
        viewPager.setSaveFromParentEnabled(false);
        tabLayout = fragmentView.findViewById(R.id.tabs_memories);
        tabLayout.setVisibility(isTabVisible());
        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentItem  = tab.getPosition();
                if(setCurrentItem == pagerList.size()-1){
                    loadMoreData();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(setCurrentItem != -1)
            viewPager.setCurrentItem(setCurrentItem);
    }

    public int isTabVisible(){
        return View.GONE;
    }

    abstract public void setViewPager(ViewPager viewPager);


    @Override
    public void setClickOnListener() {

    }

    @Override
    public void setOnData() {

    }

    @Override
    public int getCurrentItem(Fragment fragment) {
        return viewPager.getCurrentItem()+1;
    }

    public void loadMoreData(){

    }
}
