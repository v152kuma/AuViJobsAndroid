package com.auvi.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import com.auvi.R;
import com.auvi.constant.Constant;
import com.auvi.fragment.base.CSHeaderFragmentFragment;
import com.auvi.util.CSAppUtil;
import com.auvi.util.CSStringUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class FeedbackFragment extends CSHeaderFragmentFragment{

    EditText feedback;

    @Override
    public void getArgs() {

    }

    @Override
    public int layoutResource() {
        return R.layout.fragment_feedback;
    }

    @Override
    public int headerViewTitle() {
        return R.string.give_a_feedback;
    }

    @Override
    public void init() {
        feedback = fragmentView.findViewById(R.id.feedback);
    }

    @Override
    public void setClickOnListener() {
        setClickListener(fragmentView.findViewById(R.id.submit));
    }

    @Override
    public void setOnData() {

    }

    @Override
    public void onClick(View v) {
        CSAppUtil.closeKeyboard(getContext(),v);
        switch (v.getId()){
            case R.id.submit:
                if(CSStringUtil.isNonEmptyView(feedback,R.string.error_write_feedback)){
//                    CSNetworkUtil.sendFeedback(getContext(),CSStringUtil.getTextFromView(feedback),this);
                }
        }
    }

    @Override
    public void onGetResponse(JSONObject jsonObject, int requestCode) {
        switch (requestCode){
            case Constant.feedback :
                try {
                    if(jsonObject.has("errCode")) {
                        if(jsonObject.has("message")){
                            CSAppUtil.showToast(jsonObject.getString("message"));
                        }
                        else {
                            CSAppUtil.showToast(R.string.error_unable_to_connect_server);
                        }
                        if(jsonObject.getString("errCode").equalsIgnoreCase("-1")){
                            new Handler().postDelayed(() ->csActivity.onBackPressed() , 100);
                        }
                    }
                }catch (JSONException jsonException){
                    jsonException.printStackTrace();
                }
                break;
        }
    }
}
