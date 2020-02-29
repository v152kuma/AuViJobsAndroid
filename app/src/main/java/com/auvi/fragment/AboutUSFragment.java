package com.auvi.fragment;


import android.view.View;
import android.webkit.WebView;

import com.auvi.R;
import com.auvi.fragment.base.CSHeaderFragmentFragment;
import com.auvi.util.CSStringUtil;

public class AboutUSFragment extends CSHeaderFragmentFragment{

    WebView webView;

    @Override
    public int backButtonVisibility() {
        return getArguments() == null ? View.GONE : View.VISIBLE;
    }

    @Override
    public int layoutResource() {
        return R.layout.fragment_privacy_policy;
    }

    @Override
    public int headerViewTitle() {
        return R.string.about_us;
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
        webView.loadData(CSStringUtil.getString(R.string.about_us),"text/html", "UTF-8");
    }
}
