package com.auvi.util;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.auvi.R;
import com.auvi.adapter.base.CSTileAdapter;
import com.auvi.application.CSApplicationHelper;

public class CSUIUtil {

    public static void applyKeyBoardEvent(final View clickView, EditText applyOn) {
        applyOn.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    CSAppUtil.closeKeyboard(CSApplicationHelper.application(), v);
                    clickView.performClick();
                }
                return false;
            }
        });
    }

    public static boolean setPasswordVisibility(EditText pass) {
        if (pass.getTransformationMethod() instanceof HideReturnsTransformationMethod) {
            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        pass.setSelection(pass.getText().length());
        return pass.getTransformationMethod() instanceof HideReturnsTransformationMethod;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static Drawable getDrawable(int drawableResId) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return AppCompatResources.getDrawable(CSApplicationHelper.application(), drawableResId);
        } else {
            try {
                return CSApplicationHelper.application().getDrawable(drawableResId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void applyEditTextListener(final EditText... editTextChange) {
        for (EditText editText : editTextChange) {
            applyEditTextListener(editText);
        }
    }

    public static void applyDrawable(EditText editMsg){
        editMsg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                editMsg.requestFocus();
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (editMsg.getRight() - editMsg.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(CSUIUtil.setPasswordVisibility(editMsg)){
                            editMsg.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_baseline_lock, 0, R.drawable.ic_round_visibility_off, 0);
                        }
                        else {
                            editMsg.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_baseline_lock, 0, R.drawable.ic_round_visibility, 0);
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public static void applyEditTextListener(final EditText editTextChange) {
        editTextChange.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (str.length() > 0 && str.contains(" ")) {
//                    editTextChange.setText(editTextChange.getText().toString().trim());
                    editTextChange.setSelection(str.length());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static void applySearchListener(final EditText editTextChange, final CSTileAdapter csTileAdapter, final RecyclerView recyclerView) {
        editTextChange.setImeOptions(EditorInfo.IME_ACTION_NONE);
        editTextChange.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                csTileAdapter.getFilter().filter(s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public static void clearText(EditText view) {
        if (view != null) {
            view.setText("");
        }
    }
}

