package com.auvi.fragment.base;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import com.auvi.R;
import com.auvi.activity.base.CSActivity;
import com.auvi.adapter.base.CSTileAdapter;
import com.auvi.listener.OnBackPress;
import com.auvi.listener.CSResponseListener;
import com.auvi.util.CSAppUtil;

abstract public class CSFragment extends Fragment implements View.OnClickListener,
        CSResponseListener,
        OnBackPress{

    public View fragmentView;
    final public String TAG = "CSFRAGMET"+this.getClass();
    public CSActivity csActivity;
    public CSTileAdapter csTileAdapter;


    abstract public void getArgs();

    abstract public int layoutResource();

    abstract public void init();


    abstract protected void setUpMainHeaderView();

    abstract public void setClickOnListener();

    abstract public void setOnData();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            getArgs();
            fragmentView = inflater.inflate(layoutResource(), container, false);
            csActivity = ((CSActivity) getActivity());
            init(savedInstanceState);
            if (csActivity != null)
                setUpMainHeaderView();
        }catch (Exception ex){
            Log.e(TAG, "onCreateView: "+ex);
            ex.printStackTrace();
        }
        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        setClickOnListener();
        setOnData();
    }

    public void init(Bundle saveInstance){
        init();
    }

    public void setClickListener(View... params) {
        for (View view : params) {
            if(view!=null)
                view.setOnClickListener(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onBackPress() {
        return false;
    }

    @Override
    public void onClick(View v) {
        CSAppUtil.closeKeyboard(csActivity,v);
    }

    @Override
    public void onGetResponse(JSONObject jsonObject, int requestCode) {

    }

    @Override
    public void onFail(int requestCode) {
        CSAppUtil.showToast(R.string.no_data);
    }

}
