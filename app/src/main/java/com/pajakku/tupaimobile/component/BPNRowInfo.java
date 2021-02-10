package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
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

public class BPNRowInfo extends RelativeLayout {

//    private TextView tvVar;
    private TextView tvVal;

    public BPNRowInfo(Context context, AttributeSet attrs){
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AttrsInfoRow,
                0, 0);
        String varText = "";
//        Drawable rightImg = null;
        try {
             varText = a.getString(R.styleable.AttrsInfoRow_var_text);
//            rightImg = a.getDrawable(R.styleable.AttrsInfoRow_right_img);
        } finally {
            a.recycle();
        }

        setGravity(Gravity.CENTER_VERTICAL);
//        setBackgroundResource(R.drawable.bg_sspdetail_rowinfo);

        LayoutParams relativeParam;

        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        TextView tvVar = new TextView(context, attrs);
//        tvVar.setTypeface(null, Typeface.BOLD);
        tvVar.setId(View.generateViewId());
        tvVar.setLayoutParams(relativeParam);
        tvVar.setWidth(190);
        tvVar.setText(varText);
        addView(tvVar);

        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RelativeLayout.RIGHT_OF, tvVar.getId());

        tvVal = new TextView(context, attrs);
        tvVal.setLayoutParams(relativeParam);
        addView(tvVal);

        setPadding(10, 6, 0, 6);
    }

    public void init(String strVal){
        tvVal.setText((": "+strVal));
    }
}
