package com.auvi.fragment;


import android.os.Bundle;
import android.view.View;

import com.auvi.R;
import com.auvi.adapter.JobListAdapter;
import com.auvi.adapter.base.CSTileAdapter;
import com.auvi.fragment.base.CSHeaderFragmentFragment;
import com.auvi.fragment.base.CSListFragment;
import com.auvi.util.CSAppUtil;

import java.util.ArrayList;

public class JobListingFragment extends CSListFragment {


    @Override
    public int backButtonVisibility() {
        return View.VISIBLE;
    }

    @Override
    public int getEmptyMessage() {
        return R.string.no_data;
    }

    @Override
    public int searchButtonVisibility() {
        return View.GONE;
    }

    @Override
    public int layoutResource() {
        return R.layout.fragment_job_listing;
    }

    @Override
    public int headerViewTitle() {
        return R.string.jobs;
    }

    @Override
    public void loadFetch() {

    }
/*

    @Override
    public boolean onBackPress() {
        searchButton.performClick();
        return true;

    }
*/
/*
    @Override
    public void searchActionPerform() {
        headerSearchView.setVisibility(View.VISIBLE);
        headerSearchView.requestFocusFromTouch();
        headerSearchView.requestFocus();
        headerSearchView.onActionViewExpanded();
        CSAppUtil.openKeyBoard(csActivity);
    }*/

    @Override
    public CSTileAdapter getTileAdaptor() {
        return new JobListAdapter(getContext(),new ArrayList<>(),this);
    }

    @Override
    public void onItemClick(View view, int position) {
        CSAppUtil.openAddFragment(new JobDescriptionFragment(),"HOME");
    }
}
