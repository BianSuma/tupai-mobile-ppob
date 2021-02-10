package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by dul on 16/07/19.
 */

public class AppButton extends Button {
    public AppButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        setTypeface(null, Typeface.BOLD);
    }
}
