package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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

public class PayMethodButton extends RelativeLayout {

    private TextView tvVA;
    private TextView tvSaldoAmount;
    private TextView tvSubText;

    public PayMethodButton(Context context, AttributeSet attrs){
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AttrsButton,
                0, 0);
        Drawable leftImg = null;
        String text = "";
        boolean isTupaiSaldo = false;
        try {
            leftImg = a.getDrawable(R.styleable.AttrsButton_left_img);
            text = a.getString(R.styleable.AttrsButton_text);
            isTupaiSaldo = a.getBoolean(R.styleable.AttrsButton_tupai_saldo, false);
        } finally {
            a.recycle();
        }

        setBackgroundResource(R.drawable.bg_helphome_btn);

        LayoutParams relativeParam;

        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(CENTER_VERTICAL);
        relativeParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        ImageView img = new ImageView(context, attrs);
        img.setId(View.generateViewId());
        img.setLayoutParams(relativeParam);
        img.setImageDrawable(leftImg);
        addView(img);

        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(CENTER_VERTICAL);
        relativeParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        ImageView arrow = new ImageView(context, attrs);
        arrow.setId(View.generateViewId());
        arrow.setLayoutParams(relativeParam);
        arrow.setImageResource(R.drawable.ic_rightarrow);
        addView(arrow);

        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RelativeLayout.RIGHT_OF, img.getId());
        relativeParam.leftMargin = (int)getResources().getDimension(R.dimen.layout_content_pad);

        TextView tvText = new TextView(context, attrs);
        tvText.setId(View.generateViewId());
        tvText.setLayoutParams(relativeParam);
        tvText.setTypeface(null, Typeface.BOLD);
        tvText.setText(text);
        addView(tvText);

        // nomor VA
        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RelativeLayout.BELOW, tvText.getId());
        relativeParam.addRule(RelativeLayout.ALIGN_LEFT, tvText.getId());

        tvVA = new TextView(context, attrs);
        tvVA.setId(View.generateViewId());
        tvVA.setLayoutParams(relativeParam);
        tvVA.setVisibility(isTupaiSaldo?VISIBLE:GONE);
        tvVA.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        addView(tvVA);

        // tupai amount
        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RelativeLayout.BELOW, tvVA.getId());
        relativeParam.addRule(RelativeLayout.ALIGN_LEFT, tvVA.getId());

        tvSaldoAmount = new TextView(context, attrs);
        tvSaldoAmount.setId(View.generateViewId());
        tvSaldoAmount.setLayoutParams(relativeParam);
        tvSaldoAmount.setVisibility(isTupaiSaldo?VISIBLE:GONE);
        addView(tvSaldoAmount);

        // total bayar
        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RelativeLayout.BELOW, tvSaldoAmount.getId());
        relativeParam.addRule(RelativeLayout.ALIGN_LEFT, tvSaldoAmount.getId());

        tvSubText = new TextView(context, attrs);
        tvSubText.setLayoutParams(relativeParam);
        addView(tvSubText);

        int pad = (int)getResources().getDimension(R.dimen.layout_content_pad);
        setPadding(pad, pad, pad, pad);

    }

    @Deprecated
    public void init(long amount){
        tvSubText.setText(getResources().getString(R.string.paymentmethod_label_paytotal, Utility.toMoney(true, amount)));
    }

    public void init(String va, long saldo, long payAmount, OnClickListener clickListener){
        if(va != null) tvVA.setText(getResources().getString(R.string.comp_paymethodbutton_label_va, va));
        tvSaldoAmount.setText( Utility.toMoney(true, saldo) );
        tvSubText.setText(getResources().getString(R.string.paymentmethod_label_paytotal, Utility.toMoney(true, payAmount)));
        setOnClickListener(clickListener);
    }
}
