package com.auvi.fragment;


import android.view.View;

import com.auvi.R;
import com.auvi.entity.Category;
import com.auvi.fragment.base.CSHeaderFragmentFragment;
import com.auvi.listener.OnItemSelection;
import com.auvi.util.CSAppUtil;
import com.auvi.view.CSDynamicView;

import java.util.ArrayList;

public class PostCreationFragment extends CSHeaderFragmentFragment{


    @Override
    public int layoutResource() {
        return R.layout.fragment_post_job;
    }

    @Override
    public int headerViewTitle() {
        return R.string.post_job;
    }

    @Override
    public void init() {

    }

    @Override
    public void setClickOnListener() {

    }

    @Override
    public void setOnData() {
        ArrayList<Category> targetSelection = new ArrayList<>();
        targetSelection.add(new Category("Office Boy",true));
        targetSelection.add(new Category("Cleaner",false));
        targetSelection.add(new Category("Guard", false));



        CSDynamicView.setUpRecyclerViewHorizontal(csActivity, fragmentView.findViewById(R.id.job_type_selection), new OnItemSelection() {
            @Override
            public void onItemSelection(int position) {

            }
        },targetSelection);
    }
}
