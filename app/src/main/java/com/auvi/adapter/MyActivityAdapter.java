package com.auvi.adapter;

import android.content.Context;
import android.view.View;

import com.auvi.R;
import com.auvi.adapter.base.CSTileAdapter;
import com.auvi.adapter.holder.ActivityViewHolder;
import com.auvi.adapter.holder.base.base.CSViewHolder;
import com.auvi.entity.base.CSObject;
import com.auvi.fragment.base.CSHeaderFragmentFragment;
import com.auvi.view.CSDrawable;

import java.util.List;

public class MyActivityAdapter extends CSTileAdapter {

    private boolean isPendingJobs;

    public MyActivityAdapter(Context context, List<CSObject> homeTileList, CSHeaderFragmentFragment csFragment,boolean isPendingJobs) {
        super(context, homeTileList, csFragment);
        this.isPendingJobs = isPendingJobs;
    }

    public MyActivityAdapter(Context context, List<CSObject> homeTileList, CSHeaderFragmentFragment csFragment) {
        super(context, homeTileList, csFragment);
    }

    @Override
    public CSViewHolder getHolder(View view) {
        return new ActivityViewHolder(view);
    }

    @Override
    public void initViewHolder(CSViewHolder holder, int position) {
        initViewHolder((ActivityViewHolder)holder,position);
    }

    public void initViewHolder(ActivityViewHolder activityViewHolder,int position){
        if(isPendingJobs)
            activityViewHolder.performAction.setImageDrawable(CSDrawable.getDrawable(R.drawable.ic_check));
    }

    @Override
    public int layoutResource() {
        return R.layout.recycler_my_activity_item;
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}