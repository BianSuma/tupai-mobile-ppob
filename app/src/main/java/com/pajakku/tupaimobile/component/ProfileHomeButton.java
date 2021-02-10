package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;

/**
 * Created by dul on 18/12/18.
 */

public class ProfileHomeButton extends RelativeLayout {

//    private TextView tvVar;
//    private TextView tvVal;

    public ProfileHomeButton(Context context, AttributeSet attrs){
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AttrsButton,
                0, 0);
        Drawable leftImg = null;
        String text = "";
//        String subText = "";
        boolean rightArrow = true;
        try {
            leftImg = a.getDrawable(R.styleable.AttrsButton_left_img);
            text = a.getString(R.styleable.AttrsButton_text);
//            subText = a.getString(R.styleable.AttrsButton_sub_text);
            rightArrow = a.getBoolean(R.styleable.AttrsButton_right_arrow, true);
        } finally {
            a.recycle();
        }

//        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        setBackgroundResource(R.drawable.bg_helphome_btn);

        LayoutParams relativeParam;

        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        relativeParam.addRule(RelativeLayout.CENTER_VERTICAL);

        ImageView img = new ImageView(context, attrs);
        img.setId(View.generateViewId());
        img.setLayoutParams(relativeParam);
        img.setImageDrawable(leftImg);
        addView(img);

        if( rightArrow ) {
            relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            relativeParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            relativeParam.addRule(RelativeLayout.CENTER_VERTICAL);
//            relativeParam.leftMargin = (int)getResources().getDimension(R.dimen.layout_content_padding);

            ImageView arrow = new ImageView(context, attrs);
            arrow.setId(View.generateViewId());
            arrow.setLayoutParams(relativeParam);
            arrow.setImageResource(R.drawable.ic_rightarrow);
            addView(arrow);
        }

        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RelativeLayout.RIGHT_OF, img.getId());
        relativeParam.addRule(RelativeLayout.CENTER_VERTICAL);
        relativeParam.leftMargin = 10;

        TextView tvText = new TextView(context, attrs);
        tvText.setTextColor( Color.parseColor("#2a55ae") );
        tvText.setLayoutParams(relativeParam);
        tvText.setText(text);
        addView(tvText);

        int pad = getResources().getDimensionPixelSize(R.dimen.layout_content_pad);
        setPadding(pad,pad,pad,pad);

    }

//    public void setVer(String strVal){
//        tvVal.setText(strVal);
//    }
}
