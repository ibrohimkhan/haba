package com.udacity.haba.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import androidx.appcompat.widget.AppCompatEditText;

public class IngredientEditText extends AppCompatEditText {

    public interface KeyImeChange {
        void onKeyIme(int keyCode, KeyEvent event);
    }

    public IngredientEditText(Context context) {
        super(context);
    }

    public IngredientEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IngredientEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private KeyImeChange keyImeChangeListener;

    public void setKeyImeChangeListener(KeyImeChange listener) {
        keyImeChangeListener = listener;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyImeChangeListener != null) keyImeChangeListener.onKeyIme(keyCode, event);
        return false;
    }
}
