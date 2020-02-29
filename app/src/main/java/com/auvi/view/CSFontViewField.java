package com.auvi.view;

import android.content.Context;
import android.util.AttributeSet;

import com.auvi.application.CSApplication;


//Custom UI Text View for use the Font Awesome ICON.....................
public class CSFontViewField extends androidx.appcompat.widget.AppCompatTextView {

    public CSFontViewField(Context context) {
        this(context, null);
    }

    public CSFontViewField(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CSFontViewField(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    private void init() {
        if (this.isInEditMode()) {
            return;
        }
        this.setTypeface(((CSApplication) getContext().getApplicationContext()).getTypeface());
    }
}
