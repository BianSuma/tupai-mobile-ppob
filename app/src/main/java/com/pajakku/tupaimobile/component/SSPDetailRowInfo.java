package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.provider.CalendarContract;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.util.Utility;

/**
 * Created by dul on 18/12/18.
 */

public class SSPDetailRowInfo extends RelativeLayout {

    private TextView tvVal;
    private TextView tvValid;

    public SSPDetailRowInfo(Context context, AttributeSet attrs){
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AttrsInfoRow,
                0, 0);
        String varText = "";
        Drawable rightImg = null;
        try {
             varText = a.getString(R.styleable.AttrsInfoRow_var_text);
            rightImg = a.getDrawable(R.styleable.AttrsInfoRow_right_img);
        } finally {
            a.recycle();
        }

//        setGravity(Gravity.CENTER_VERTICAL);
//        setBackgroundResource(R.drawable.bg_sspdetail_rowinfo);

        RelativeLayout.LayoutParams relativeParam;

        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        TextView tvVar = new TextView(context, attrs);
        tvVar.setId(View.generateViewId());
        tvVar.setLayoutParams(relativeParam);
//        tvVar.setWidth(150);
        tvVar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        tvVar.setTextColor(Color.parseColor("#97948c"));
        tvVar.setText(varText);
        addView(tvVar);

        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(BELOW, tvVar.getId());

        tvVal = new TextView(context, attrs);
        tvVal.setId(View.generateViewId());
        tvVal.setLayoutParams(relativeParam);
        tvVal.setTypeface(null, Typeface.BOLD);
        tvVal.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
//        tvVal.setTextColor(Color.parseColor("#3f3f3f"));
        addView(tvVal);

        if( rightImg != null ) {
            relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            relativeParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

            ImageView img = new ImageView(context, attrs);
            img.setLayoutParams(relativeParam);
            img.setImageDrawable(rightImg);
            addView(img);
        }

        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(BELOW, tvVal.getId());

        tvValid = new TextView(context, attrs);
        tvValid.setLayoutParams(relativeParam);
        tvValid.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        tvValid.setTextColor( Utility.colorResToInt(context,android.R.color.holo_orange_dark) );
        tvValid.setVisibility(GONE);
        addView(tvValid);

        int padSmall = getResources().getDimensionPixelSize(R.dimen.layout_content_pad);
        int padBig = getResources().getDimensionPixelSize(R.dimen.holo_shadow_pad);

        setPadding(padBig, padSmall, padBig, padSmall);
    }

    public void init(String strVal){
        if(strVal == null) return;
        tvVal.setText(strVal);
    }

    public void setValidUntil(String date){
        if(date == null) return;

        date = Utility.strToInformatifStr(date);

        tvValid.setText(getResources().getString(R.string.comp_sspdetailrowinfo_validuntil, date));
        tvValid.setVisibility(VISIBLE);

        int padSmall = getResources().getDimensionPixelSize(R.dimen.layout_content_pad);
        int padBig = getResources().getDimensionPixelSize(R.dimen.holo_shadow_pad);
        setPadding(padBig, padSmall, padBig, padSmall);
    }

    public void hideValidUntil(){
        tvValid.setVisibility(GONE);
    }

    public void setRed(){
        tvVal.setTextColor(Color.parseColor("#990000"));
    }
}
