package com.auvi.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvi.R;
import com.auvi.activity.base.CSActivity;
import com.auvi.entity.CSMenu;

import java.util.List;

/**
 * Created by Administrator on 9/19/2017.
 */
public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MyViewHolder> {

    CSActivity csActivity;
    private List<CSMenu> cropListData;
    Context mContext;

    public MenuListAdapter(CSActivity csActivity, List<CSMenu> mDataSet) {
        this.mContext = csActivity;
        this.csActivity = csActivity;
        this.cropListData = mDataSet;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView menuItem;
        View menuContainer;
        ImageView menuIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            menuContainer =  itemView.findViewById(R.id.menu_container);
            menuItem =  itemView.findViewById(R.id.menu_item);
            menuIcon =  itemView.findViewById(R.id.menu_icon);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_menu_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int postion) {
        holder.menuItem.setText(cropListData.get(postion).itemName);
        holder.menuIcon.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return cropListData.size();
    }

}