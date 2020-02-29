package com.auvi.fragment.base;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.auvi.R;
import com.auvi.adapter.base.CSTileAdapter;
import com.auvi.entity.base.CSObject;
import com.auvi.listener.RecyclerItemClickListener;
import com.auvi.util.CSAppUtil;

import java.util.ArrayList;

abstract public class CSListFragment extends CSHeaderFragmentFragment{

    RecyclerView myActivityListView;
    protected ArrayList<CSObject> userAgentArrayList = new ArrayList<>();
    protected TextView emptyView;

    abstract public int getEmptyMessage ();

    @Override
    public int layoutResource() {
        return R.layout.fragment_my_activity;
    }

    @Override
    public void init() {
        myActivityListView = fragmentView.findViewById(R.id.my_activity_list_view);
        emptyView = fragmentView.findViewById(R.id.empty_view_title);
    }


    @Override
    public void setClickOnListener() {

    }

    @Override
    public int headerViewTitle() {
        return R.string.my_activity;
    }

    @Override
    public void setOnData() {
        emptyView.setText(getEmptyMessage());
        setUpAdapter();
        loadFetch();
    }

    abstract public void loadFetch();
    abstract public CSTileAdapter getTileAdaptor();

    @Override
    public int backButtonVisibility() {
        return View.VISIBLE;
    }

    public void setUpAdapter() {
        this.csTileAdapter = getTileAdaptor();
        setRecyclerAdaptor(this.myActivityListView,this.csTileAdapter, LinearLayoutManager.VERTICAL);
    }

    @Override
    public void onFail(int requestCode) {
        CSAppUtil.showToast(getEmptyMessage());
    }

    private void setRecyclerAdaptor(RecyclerView recyclerView, RecyclerView.Adapter adapter, int orientation){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), orientation, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),CSListFragment.this));
    }
}