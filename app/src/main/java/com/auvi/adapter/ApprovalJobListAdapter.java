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

public class ApprovalJobListAdapter extends CSTileAdapter {


    public ApprovalJobListAdapter(Context context, List<CSObject> homeTileList, CSHeaderFragmentFragment csFragment) {
        super(context, homeTileList, csFragment);
    }

    @Override
    public CSViewHolder getHolder(View view) {
        return new CSViewHolder(view);
    }

    @Override
    public void initViewHolder(CSViewHolder holder, int position) {

    }

    @Override
    public int layoutResource() {
        return R.layout.recycler_my_activity_approval_item;
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}