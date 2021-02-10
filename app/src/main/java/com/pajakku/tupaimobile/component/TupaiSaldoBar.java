package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
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

public class TupaiSaldoBar extends RelativeLayout {

    private TextView tvNova;
    private TextView tvSaldo;

    public TupaiSaldoBar(Context context, AttributeSet attrs){
        super(context, attrs);

//        TypedArray a = context.getTheme().obtainStyledAttributes(
//                attrs,
//                R.styleable.AttrsInfoRow,
//                0, 0);
//        Drawable leftIcon = null;
//        String varText = "";
//        try {
//            leftIcon = a.getDrawable(R.styleable.AttrsInfoRow_left_icon);
//             varText = a.getString(R.styleable.AttrsInfoRow_var_text);
//        } finally {
//            a.recycle();
//        }

        setBackgroundColor(Color.parseColor("#cee2e3"));

        int pad = getResources().getDimensionPixelSize(R.dimen.layout_content_pad);

        LayoutParams relativeParam;

        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(CENTER_VERTICAL);

        ImageView img = new ImageView(context, attrs);
        img.setId(View.generateViewId());
        img.setImageResource(R.drawable.wallet);
        img.setLayoutParams(relativeParam);
        addView(img);

        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RIGHT_OF, img.getId());
        relativeParam.leftMargin = pad;

        TextView tvTitle = new TextView(context, attrs);
        tvTitle.setId(View.generateViewId());
        tvTitle.setLayoutParams(relativeParam);
        tvTitle.setText(R.string.comp_tupaisaldobar_label_yoursaldo);
        addView(tvTitle);

        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(BELOW, tvTitle.getId());
        relativeParam.addRule(ALIGN_LEFT, tvTitle.getId());

        tvNova = new TextView(context, attrs);
        tvNova.setId(View.generateViewId());
        tvNova.setLayoutParams(relativeParam);
//        tvNova.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
        addView(tvNova);

        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(BELOW, tvNova.getId());
        relativeParam.addRule(ALIGN_LEFT, tvNova.getId());

        tvSaldo = new TextView(context, attrs);
        tvSaldo.setLayoutParams(relativeParam);
        tvSaldo.setTypeface(null, Typeface.BOLD);
        addView(tvSaldo);


        setPadding(pad,pad,pad,pad);

    }

    public void init(String nova, long saldo){
        tvNova.setText( getResources().getString(R.string.comp_tupaisaldobar_val_nova, nova) );
        tvSaldo.setText(Utility.toMoney(true, saldo));
    }
}
