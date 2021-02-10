package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;

/**
 * Created by dul on 16/04/19.
 */

public class AppCircleButton extends RelativeLayout {

    private RelativeLayout leftLay;
    private RelativeLayout rightLay;

    public AppCircleButton(Context ctx, AttributeSet attrs){
        super(ctx, attrs);

        TypedArray a = ctx.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AttrsActionBar,
                0, 0);
        Drawable icon = null;
        try {
            icon = a.getDrawable(R.styleable.AttrsActionBar_icon);
        } finally {
            a.recycle();
        }

        setBackgroundResource(R.drawable.bg_appcirclebtn);

        int btnSize = getResources().getDimensionPixelSize(R.dimen.appcirclebutton_size);
        int diffSize = getResources().getDimensionPixelSize(R.dimen.appcirclebutton_difficonsize);
        int iconSize = btnSize - diffSize;
        int btnWidth = getResources().getDimensionPixelSize(R.dimen.findsortbutton_single_width);
        int btnH = getResources().getDimensionPixelSize(R.dimen.button_height);
        LayoutParams lay;
        TextView textView;

        lay = new LayoutParams(iconSize, iconSize);
        ImageView imageView = new ImageView(ctx, attrs);
        imageView.setLayoutParams(lay);
        if(icon != null) imageView.setImageDrawable(icon);
        addView(imageView);

//        lay = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lay.addRule(CENTER_IN_PARENT);
//        textView = new TextView(ctx, attrs);
//        textView.setLayoutParams(lay);
//        textView.setText(R.string.comp_findsortbutton_tv_find);
//
//        lay = new LayoutParams(btnWidth, btnH);
//        leftLay = new RelativeLayout(ctx, attrs);
//        leftLay.setId(View.generateViewId());
//        leftLay.setLayoutParams(lay);
//        leftLay.addView(textView);
//        addView(leftLay);

        // right side

//        lay = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lay.addRule(CENTER_IN_PARENT);
//        textView = new TextView(ctx, attrs);
//        textView.setLayoutParams(lay);
//        textView.setText(R.string.comp_findsortbutton_tv_sort);
//
//        lay = new LayoutParams(btnWidth, btnH);
//        lay.addRule(RIGHT_OF, leftLay.getId());
//        rightLay = new RelativeLayout(ctx, attrs);
//        rightLay.setLayoutParams(lay);
//        rightLay.addView(textView);
//        addView(rightLay);

        setPadding(diffSize, diffSize, diffSize, diffSize);

    }

//    public void init(OnClickListener leftLis, OnClickListener rightLis){
//        leftLay.setOnClickListener( leftLis );
//        rightLay.setOnClickListener( rightLis );
//    }
}
