package com.auvi.view;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.auvi.adapter.SelectionAdapter;
import com.auvi.entity.Category;
import com.auvi.listener.OnItemSelection;

import java.util.ArrayList;
import java.util.List;

public class CSDynamicView {

    public static void setUpRecyclerViewHorizontal(Context context,
                                                   RecyclerView recyclerView,
                                                   OnItemSelection onItemSelection,
                                                   ArrayList<Category> targetSelection){
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(context, RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        SelectionAdapter selectionAdapter = new SelectionAdapter(context, targetSelection,onItemSelection);
        recyclerView.setAdapter(selectionAdapter);
    }

    public static void setUpSpinner(Context context,
                                                   Spinner spinner,
                                                   OnItemSelection onItemSelection,
                                                   List<Category> targetSelection){
        ArrayAdapter selectionAdapter = new ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item);
        selectionAdapter.addAll(targetSelection);
        spinner.setAdapter(selectionAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onItemSelection.onItemSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
