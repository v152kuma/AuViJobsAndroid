package com.auvi.fragment;


import android.view.View;

import com.auvi.R;
import com.auvi.fragment.base.CSHeaderFragmentFragment;

public class ApplyJobFragment extends CSHeaderFragmentFragment{

    @Override
    public int backButtonVisibility() {
        return View.VISIBLE;
    }

    @Override
    public int layoutResource() {
        return R.layout.fragment_apply_job;
    }

    @Override
    public int headerViewTitle() {
        return R.string.job_description;
    }

    @Override
    public void init() {

    }

    @Override
    public void setClickOnListener() {

    }

    @Override
    public void setOnData() {

    }
}
