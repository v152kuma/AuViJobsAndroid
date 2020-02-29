package com.auvi.fragment;


import android.view.View;

import com.auvi.R;
import com.auvi.fragment.base.CSHeaderFragmentFragment;
import com.auvi.util.CSAppUtil;

public class JobDescriptionFragment extends CSHeaderFragmentFragment{

    @Override
    public int backButtonVisibility() {
        return View.VISIBLE;
    }

    @Override
    public int layoutResource() {
        return R.layout.fragment_job_description;
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
        setClickListener(fragmentView.findViewById(R.id.apply_button));
    }

    @Override
    public void setOnData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.apply_button:
                CSAppUtil.openAddFragment(new ApplyJobFragment(),"JOB");
                break;
        }
    }
}
