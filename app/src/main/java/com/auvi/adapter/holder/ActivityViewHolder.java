package com.auvi.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import com.auvi.R;

public class ActivityViewHolder extends com.auvi.adapter.holder.base.base.CSViewHolder {
    public ImageView performAction;

    public ActivityViewHolder(View itemView) {
        super(itemView);
        performAction = itemView.findViewById(R.id.perform_action);
    }
}
