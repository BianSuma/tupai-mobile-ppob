package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.model.dto.BrutoTaxItem;

/**
 * Created by dul on 26/07/19.
 */

public class BrutoTaxItemList extends RelativeLayout {

    private TextView tv0;
    private TextView tv1;
    private TextView tv2;

    public BrutoTaxItemList(Context ctx){
        super(ctx);
        setView(ctx);
    }

    public BrutoTaxItemList(Context ctx, AttributeSet attrs){
        super(ctx, attrs);
        setView(ctx);
    }

    private void setView(Context ctx){

        tv0 = new TextView(ctx);
        tv0.setId(View.generateViewId());
        addView(tv0);

        tv1 = new TextView(ctx);
        tv1.setId(View.generateViewId());
        addView(tv1);

        tv2 = new TextView(ctx);
        tv2.setId(View.generateViewId());
        tv2.setTextAlignment(TEXT_ALIGNMENT_VIEW_END);
        addView(tv2);

        LayoutParams lay;

        lay = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tv0.setLayoutParams(lay);

        lay = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lay.addRule(LEFT_OF, tv2.getId());
        tv1.setLayoutParams(lay);

        int w = Math.round(TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 140, getResources().getDisplayMetrics()) );
        lay = new LayoutParams(w, LayoutParams.WRAP_CONTENT);
        lay.addRule(ALIGN_PARENT_RIGHT);
        tv2.setLayoutParams(lay);

        int pad = getResources().getDimensionPixelSize(R.dimen.layout_content_pad);
        setPadding(pad, pad, pad, pad);
    }

    public void init(String str0, String str1, String str2){
        tv0.setText(str0);
        tv1.setText(str1);
        tv2.setText(str2);
    }
    public void setBold(){
        tv0.setTypeface(null, Typeface.BOLD);
        tv1.setTypeface(null, Typeface.BOLD);
        tv2.setTypeface(null, Typeface.BOLD);
    }

}
