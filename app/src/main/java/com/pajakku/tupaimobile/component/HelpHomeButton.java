package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;

/**
 * Created by dul on 18/12/18.
 */

public class HelpHomeButton extends RelativeLayout {

    public HelpHomeButton(Context context, AttributeSet attrs){
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AttrsButton,
                0, 0);
        Drawable leftImg = null;
        String text = "";
        String subText = "";
        try {
            leftImg = a.getDrawable(R.styleable.AttrsButton_left_img);
            text = a.getString(R.styleable.AttrsButton_text);
            subText = a.getString(R.styleable.AttrsButton_sub_text);
        } finally {
            a.recycle();
        }

        setBackgroundResource(R.drawable.bg_helphomebutton);

        RelativeLayout.LayoutParams relativeParam;

        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        ImageView img = new ImageView(context, attrs);
        img.setId(View.generateViewId());
        img.setLayoutParams(relativeParam);
        img.setImageDrawable(leftImg);
        addView(img);

        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        relativeParam.addRule(RelativeLayout.CENTER_VERTICAL);

        ImageView arrow = new ImageView(context, attrs);
        arrow.setId(View.generateViewId());
        arrow.setLayoutParams(relativeParam);
        arrow.setImageResource(R.drawable.ic_rightarrow);
        addView(arrow);

        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RelativeLayout.RIGHT_OF, img.getId());
        relativeParam.addRule(RelativeLayout.LEFT_OF, arrow.getId());
        relativeParam.leftMargin = (int)getResources().getDimension(R.dimen.layout_content_pad);

        LinearLayout ll = new LinearLayout(context, attrs);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(relativeParam);
        ll.setId(View.generateViewId());
        addView(ll);

        TextView tvText = new TextView(context, attrs);
        tvText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tvText.setTypeface(null, Typeface.BOLD);
        tvText.setText(text);
        ll.addView(tvText);

        TextView tvSubText = new TextView(context, attrs);
        tvSubText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tvSubText.setText(subText);
        ll.addView(tvSubText);

        int pad = getResources().getDimensionPixelSize(R.dimen.layout_content_pad);

        setPadding(pad, pad, pad, pad);
    }

//    public void setVer(String strVal){
//        tvVal.setText(strVal);
//    }
}
