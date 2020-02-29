package com.auvi.fragment.base;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

import com.auvi.R;
import com.auvi.adapter.ViewPagerAdaptor.DynamicViewPagerAdapter;
import com.auvi.listener.FragmentInstanceCallBack;
import com.auvi.util.CSAppUtil;

abstract public class CSViewPagerDynamicSlideFragment extends CSViewPageStaticSlideFragment implements FragmentInstanceCallBack {

    public static int countToast = 0 ;
    public DynamicViewPagerAdapter dynamicViewPagerAdapter;
    protected int position;

    @Override
    public void getArgs() {
        if(getArguments()!=null){
            pagerList = getArguments().getParcelableArrayList("pagerList");
            position = getArguments().getInt("page");
        }
    }

    @Override
    public int backButtonVisibility() {
        return View.VISIBLE;
    }

    @Override
    public void setViewPager(ViewPager viewPager) {
        /*viewPager.setOffscreenPageLimit(-1);*/
        dynamicViewPagerAdapter = new DynamicViewPagerAdapter(getChildFragmentManager(),pagerList.size(),this);
        viewPager.setAdapter(dynamicViewPagerAdapter);
        viewPager.setCurrentItem(position);
        if(countToast == 0)
            CSAppUtil.showToast(R.string.left_right);
        countToast++;
    }

    @Override
    public CSHeaderFragmentFragment getInstance(int position) {
        csFragment = getDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("value",pagerList.get(position));
        bundle.putInt("position",position+1);
        bundle.putInt("size",pagerList.size());
        csFragment.setArguments(bundle);
        return csFragment;
    }

    abstract public CSHeaderFragmentFragment getDetailFragment();
}
