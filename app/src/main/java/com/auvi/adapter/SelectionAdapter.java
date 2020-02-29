package com.auvi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.auvi.R;
import com.auvi.entity.Category;
import com.auvi.listener.OnItemSelection;
import java.util.ArrayList;

public class SelectionAdapter extends RecyclerView.Adapter<SelectionAdapter.SelectionViewHolder> {

    private Context mContext;
    private ArrayList<Category> mCategories;
    private OnItemSelection onItemSelection;


    class SelectionViewHolder extends RecyclerView.ViewHolder {

        TextView selectionView;

        public SelectionViewHolder(@NonNull View itemView) {
            super(itemView);
            selectionView = itemView.findViewById(R.id.selection_view);
        }


    }
    public SelectionAdapter(Context mContext, ArrayList<Category> selectionValue,OnItemSelection onItemSelection) {
        this.mContext = mContext;
        this.mCategories = selectionValue;
        this.onItemSelection = onItemSelection;
    }
    @NonNull
    @Override
    public SelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.selection_view_layout, parent, false);
        return new SelectionViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull final SelectionViewHolder viewHolder, final int i) {

        Category category = mCategories.get(i);
        viewHolder.selectionView.setText(category.getCategoryName());
        if(category.isExpended()){
            viewHolder.selectionView.setTextColor(Color.WHITE);
            viewHolder.selectionView.setBackground(mContext.getResources().getDrawable(R.drawable.filter_back_header_drawable));
        }
        else {
            viewHolder.selectionView.setTextColor(Color.BLACK);
            viewHolder.selectionView.setBackground(mContext.getResources().getDrawable(R.drawable.filter_back_second_header_drawable));
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSelection.onItemSelection(i);
              /*  for(Category cate : mCategories){
                    cate.setExpended(false);
                }*/
                mCategories.get(i).setExpended(true);

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }



}