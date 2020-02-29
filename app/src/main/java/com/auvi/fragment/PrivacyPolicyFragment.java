package com.auvi.fragment;


import android.webkit.WebView;

import com.auvi.R;
import com.auvi.fragment.base.CSHeaderFragmentFragment;

public class PrivacyPolicyFragment extends CSHeaderFragmentFragment{

    WebView webView;

    @Override
    public int layoutResource() {
        return R.layout.fragment_privacy_policy;
    }

    @Override
    public int headerViewTitle() {
        return R.string.privacy_policy_title;
    }

    @Override
    public void init() {
        webView = fragmentView.findViewById(R.id.privacy_web);
    }

    @Override
    public void setClickOnListener() {

    }

    @Override
    public void setOnData() {
        webView.loadData("","text/html", "UTF-8");
    }

}
