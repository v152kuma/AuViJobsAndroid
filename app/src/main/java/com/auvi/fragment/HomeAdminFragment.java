package com.auvi.fragment;

import android.view.View;

import com.auvi.R;
import com.auvi.adapter.ApprovalJobListAdapter;
import com.auvi.adapter.JobListAdapter;
import com.auvi.adapter.MyActivityAdapter;
import com.auvi.adapter.base.CSTileAdapter;
import com.auvi.entity.Category;
import com.auvi.fragment.base.CSHeaderFragmentFragment;
import com.auvi.fragment.base.CSListFragment;
import com.auvi.listener.OnItemSelection;
import com.auvi.util.CSAppUtil;
import com.auvi.view.CSDynamicView;

import java.util.ArrayList;
import java.util.List;

public class HomeAdminFragment extends CSListFragment  {

    @Override
    public int backButtonVisibility() {
        return View.GONE;
    }

    @Override
    public int layoutResource() {
        return R.layout.fragment_home_admin;
    }

    @Override
    public int getEmptyMessage() {
        return R.string.no_data;
    }

    @Override
    public int headerViewTitle() {
        return R.string.home_admin_title;
    }

    @Override
    public void loadFetch() {

    }

    @Override
    public void setOnData() {
        super.setOnData();
        List<Category> targetSelection = new ArrayList<>();
        targetSelection.add(new Category("HCL",true));
        targetSelection.add(new Category("UHG",false));
        targetSelection.add(new Category("TCS", false));


        CSDynamicView.setUpSpinner(csActivity, fragmentView.findViewById(R.id.filer_drop_down), new OnItemSelection() {
            @Override
            public void onItemSelection(int position) {

            }
        }, targetSelection);
    }

    @Override
    public CSTileAdapter getTileAdaptor() {
        return new ApprovalJobListAdapter(getContext(),new ArrayList<>(),this);
    }

    @Override
    public void onItemClick(View view, int position) {

    }


}
