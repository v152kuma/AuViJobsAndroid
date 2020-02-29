package com.auvi.fragment;

import android.os.Bundle;
import android.view.View;
import com.auvi.R;
import com.auvi.fragment.base.CSHeaderFragmentFragment;
import com.auvi.util.CSAppUtil;


public class HomeFragment extends CSHeaderFragmentFragment {

    @Override
    public int layoutResource() {
        return R.layout.fragment_search_job_actiivities_selection;
    }

    @Override
    public int headerViewTitle() {
        return R.string.home;
    }

    @Override
    public void init() {

    }

    @Override
    public int backButtonVisibility() {
        return View.GONE;
    }

    @Override
    public void setClickOnListener() {
        setClickListener(fragmentView.findViewById(R.id.search_jobs),fragmentView.findViewById(R.id.activities));
    }

    @Override
    public void setOnData() {
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.search_jobs:
                CSAppUtil.openFragment(new JobListingFragment());
                break;
            case R.id.activities:
                ActivityFragment activityFragment  = new ActivityFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("fromHome",true);
                activityFragment.setArguments(bundle);
                CSAppUtil.openFragment(activityFragment);
                break;
        }
    }

}
