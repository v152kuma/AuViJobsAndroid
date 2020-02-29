package com.auvi.fragment;

import android.view.View;

import com.auvi.R;
import com.auvi.adapter.MyActivityAdapter;
import com.auvi.adapter.base.CSTileAdapter;
import com.auvi.fragment.base.CSListFragment;

import java.util.ArrayList;

public class ActivityFragment extends CSListFragment {


    @Override
    public int backButtonVisibility() {
        return getArguments() != null ? View.VISIBLE : View.GONE;
    }

    @Override
    public int getEmptyMessage() {
        return R.string.no_data;
    }

    @Override
    public int headerViewTitle() {
        return R.string.my_activity;
    }

    @Override
    public void loadFetch() {

    }

    @Override
    public CSTileAdapter getTileAdaptor() {
        return new MyActivityAdapter(getContext(),new ArrayList<>(),this);
    }
}
