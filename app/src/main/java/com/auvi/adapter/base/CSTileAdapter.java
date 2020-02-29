package com.auvi.adapter.base;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.auvi.adapter.holder.base.base.CSViewHolder;
import com.squareup.picasso.Picasso;

import com.auvi.R;
import com.auvi.entity.base.CSObject;
import com.auvi.fragment.base.CSHeaderFragmentFragment;
import com.auvi.util.CSStringUtil;

import java.util.ArrayList;
import java.util.List;

abstract public class CSTileAdapter extends RecyclerView.Adapter<CSViewHolder> implements Filterable {

    public List<CSObject> homeList;
    public List<CSObject> tempList;
    public Context context;
    public CSHeaderFragmentFragment csFragment;

    public CSTileAdapter(Context context, List<CSObject> homeTileList, CSHeaderFragmentFragment csFragment) {
        this.homeList = homeTileList;
        this.tempList = new ArrayList<>(homeTileList);
        this.context = context;
        this.csFragment = csFragment;
    }

    @NonNull
    @Override
    public CSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutResource(), parent, false);
        return getHolder(view);
    }


    public int layoutResource() {
        return R.layout.recycler_list_item;
    }

    public void notifyDataSetChanged(List<CSObject> homeList){
        this.tempList = new ArrayList<>(homeList);
        super.notifyDataSetChanged();
    }

    abstract public CSViewHolder getHolder(View view);

    @Override
    public void onBindViewHolder(@NonNull CSViewHolder holder, int position) {
        initViewHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    abstract public void initViewHolder(CSViewHolder holder,int position);


    private Filter tempValue;

    @Override
    public Filter getFilter() {
        if(tempValue == null) {
            tempValue=new RecordFilter();
        }
        return tempValue;
    }

    //filter class
    private class RecordFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = tempList;
                results.count = tempList.size();
            } else {
                ArrayList<CSObject> tempValue = new ArrayList<>();
                for (CSObject csObject : tempList) {
                    if (csObject.toString().toLowerCase().contains(constraint.toString().toLowerCase().trim())) {
                        tempValue.add(csObject);
                    }
                }
                results.values = tempValue;
                results.count = tempValue.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,FilterResults results) {
            homeList = (ArrayList<CSObject>) results.values;
            notifyDataSetChanged();
        }
    }

    public void updateImage(ImageView targetImageView, String valueUrl, int placeHolder){

        if(CSStringUtil.isNonEmptyStr(valueUrl)) {
            Picasso.get()
                    .load(valueUrl)
                    .error(placeHolder)
                    .placeholder(placeHolder)
                    .fit()
                    .centerInside()
                    .into(targetImageView);
        }
        else {
            Picasso.get()
                    .load(placeHolder)
                    .fit()
                    .centerInside()
                    .into(targetImageView);
        }
    }
}
