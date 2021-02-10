package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.icu.text.DisplayContext;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.util.AppConstant;

import org.w3c.dom.Text;

/**
 * Created by dul on 18/12/18.
 */

public class ProfileHomeLabel extends RelativeLayout {

    private TextView tvVal;

    public ProfileHomeLabel(Context context, AttributeSet attrs){
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AttrsInfoRow,
                0, 0);
        Drawable leftIcon = null;
        String varText = "";
        try {
            leftIcon = a.getDrawable(R.styleable.AttrsInfoRow_left_icon);
             varText = a.getString(R.styleable.AttrsInfoRow_var_text);
        } finally {
            a.recycle();
        }

        setBackgroundResource(R.drawable.bg_profhome_quickrow);

        int pad = getResources().getDimensionPixelSize(R.dimen.layout_content_pad);

        LayoutParams relativeParam;

        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(CENTER_VERTICAL);

        ImageView img = new ImageView(context, attrs);
        img.setId(View.generateViewId());
        img.setImageDrawable(leftIcon);
        img.setLayoutParams(relativeParam);
        addView(img);

        relativeParam = new LayoutParams(getResources().getDimensionPixelSize(R.dimen.profhome_label_width), ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RIGHT_OF, img.getId());
        relativeParam.leftMargin = pad;

        TextView tvVar = new TextView(context, attrs);
        tvVar.setId(View.generateViewId());
        tvVar.setLayoutParams(relativeParam);
        tvVar.setText(varText);
        addView(tvVar);

        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(BELOW, tvVar.getId());
        relativeParam.addRule(ALIGN_LEFT, tvVar.getId());

        tvVal = new TextView(context, attrs);
        tvVal.setLayoutParams(relativeParam);
        tvVal.setTypeface(null, Typeface.BOLD);
        addView(tvVal);



        setPadding(pad,pad,0,pad);


    }

    public void init(String strVal){
        tvVal.setText(strVal);
    }
}
