package com.auvi.adapter;

import android.content.Context;
import android.view.View;
import com.auvi.adapter.base.CSTileAdapter;
import com.auvi.adapter.holder.base.base.CSViewHolder;
import com.auvi.entity.base.CSObject;
import com.auvi.fragment.base.CSHeaderFragmentFragment;
import java.util.List;

public class JobListAdapter extends CSTileAdapter {

    public JobListAdapter(Context context, List<CSObject> homeTileList, CSHeaderFragmentFragment csFragment) {
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
    public int getItemCount() {
        return 20;
    }
}