package com.auvi.adapter.ViewPagerAdaptor;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.auvi.listener.FragmentInstanceCallBack;

public class DynamicViewPagerAdapter extends FragmentStatePagerAdapter {

    private int count;
    FragmentInstanceCallBack fragmentInstanceCallBack;

    public DynamicViewPagerAdapter(FragmentManager manager, int count, FragmentInstanceCallBack fragmentIntanceCallBack) {
        super(manager);
        if (manager.getFragments() != null) {
            manager.getFragments().clear();
        }
        this.fragmentInstanceCallBack = fragmentIntanceCallBack;
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentInstanceCallBack.getInstance(position);
    }

    @Override
    public int getCount() {
        return count;
    }

    public void notifyDataSetChanged(int count) {
        this.count = count;
        super.notifyDataSetChanged();
    }
}