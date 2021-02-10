package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by dul on 01/04/19.
 */

public class AppProgressBar extends LinearLayout {

    private View viewLeft;
    private View viewRight;

    public AppProgressBar(Context context, AttributeSet attrs){
        super(context, attrs);

        setOrientation(HORIZONTAL);

        LinearLayout.LayoutParams lay = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        lay.weight = .0F;

        viewLeft = new View(context, attrs);
        viewLeft.setLayoutParams(lay);
        viewLeft.setBackgroundColor(Color.parseColor("#ff0000"));
        addView(viewLeft);

        lay = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        lay.weight = 1F;

        viewRight = new View(context, attrs);
        viewRight.setLayoutParams(lay);
        viewRight.setBackgroundColor(Color.parseColor("#ffffff"));
        addView(viewRight);
    }

    public void setPercen(float f){
        LinearLayout.LayoutParams lay = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        lay.weight = f;
        viewLeft.setLayoutParams(lay);

        lay = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        lay.weight = 10 - f;
        viewRight.setLayoutParams(lay);
    }
}
