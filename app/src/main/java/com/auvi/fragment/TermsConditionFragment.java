package com.auvi.fragment;


import android.view.View;
import android.webkit.WebView;

import com.auvi.R;
import com.auvi.constant.TermConditionContent;
import com.auvi.fragment.base.CSHeaderFragmentFragment;

public class TermsConditionFragment extends CSHeaderFragmentFragment{

    WebView webView;

    @Override
    public int backButtonVisibility() {
        return View.VISIBLE;
    }

    @Override
    public int layoutResource() {
        return R.layout.fragment_privacy_policy;
    }

    @Override
    public int headerViewTitle() {
        return R.string.term_condition;
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
        webView.loadData(new TermConditionContent().termsCondition,"text/html", "UTF-8");
    }
}
