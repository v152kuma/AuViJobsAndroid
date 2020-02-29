package com.auvi.fragment;


import android.os.Bundle;
import android.view.View;

import com.auvi.R;
import com.auvi.fragment.base.CSHeaderFragmentFragment;
import com.auvi.util.CSAppUtil;

public class AccountTypeSelectionFragment extends CSHeaderFragmentFragment{


    @Override
    public int layoutResource() {
        return R.layout.fragment_account_type_selection;
    }

    @Override
    public void init() {

    }

    @Override
    public int headerViewVisibility() {
        return View.GONE;
    }

    @Override
    public void setClickOnListener() {
        setClickListener(fragmentView.findViewById(R.id.become_job_seeker),fragmentView.findViewById(R.id.become_employer));
    }

    @Override
    public void setOnData() {
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        LoginFragment loginFragment = new LoginFragment();
        Bundle bundle = new Bundle();
        switch (v.getId()){
            case R.id.become_job_seeker:
                bundle.putBoolean("partner",false);
                loginFragment.setArguments(bundle);
                CSAppUtil.openFragment(loginFragment);
                break;
            case R.id.become_employer:
                bundle.putBoolean("partner",true);
                loginFragment.setArguments(bundle);
                CSAppUtil.openFragment(loginFragment);
                break;
        }
    }
}
