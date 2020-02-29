package com.auvi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.auvi.R;

public class CSImageEditView extends LinearLayout {

    public CSImageEditView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.custom_edit_image_view, this, true);

        String title;
        Drawable subtitle;
        int editTextId;
        int imageViewId;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CSImageEditView, 0, 0);

        try {

            title = a.getString(R.styleable.CSImageEditView_customEditTextHint);

            final int drawableResId = a.getResourceId(R.styleable.CSImageEditView_customImage, -1);
            subtitle = ContextCompat.getDrawable(getContext(), drawableResId);

            editTextId = a.getResourceId(R.styleable.CSImageEditView_editTextid,-1);
            imageViewId = a.getResourceId(R.styleable.CSImageEditView_imageViewId,-1);
        } finally {
            a.recycle();
        }

/*        // Throw an exception if required attributes are not set
        if (title == null) {
            throw new RuntimeException(context.getResources().getString(R.string.no_hind_provided));
        }
        if (subtitle == null) {
            throw new RuntimeException(context.getResources().getString(R.string.no_image_icon_provided));
        }*/

        init(title, subtitle,editTextId,imageViewId);
    }

    // Setup views
    private void init(String title, Drawable ic,int editTextId,int imageViewId) {
        EditText titleView = findViewById(R.id.edit_text_view);
        ImageView imageIcon = findViewById(R.id.image_view);
        if(editTextId!=-1){
            titleView.setId(editTextId);
        }
        if(imageViewId!=-1){
            imageIcon.setId(imageViewId);
        }
        if(title!=null)
            titleView.setHint(title);
        if(ic!=null)
            imageIcon.setImageDrawable(ic);
    }
}