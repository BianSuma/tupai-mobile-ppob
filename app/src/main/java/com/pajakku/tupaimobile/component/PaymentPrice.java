package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.util.Utility;

import static android.widget.RelativeLayout.ALIGN_PARENT_LEFT;
import static android.widget.RelativeLayout.ALIGN_PARENT_RIGHT;

/**
 * Created by dul on 18/12/18.
 */

public class PaymentPrice extends LinearLayout {

    private RelativeLayout layoutProduct;
    private TextView tvProduct;
    private TextView tvProductValue;
    private RelativeLayout layoutPrice;
    private TextView tvPrice;
    private TextView tvPriceValue;
    private RelativeLayout layoutTotalTransfer;
    private TextView tvTotalTransfer;
    private TextView tvTotalTransferValue;
    private RelativeLayout layoutTotalPay;
    private TextView tvTotalPay;
    private TextView tvTotalPayValue;

    public PaymentPrice(Context context, AttributeSet attrs){
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

        setOrientation(VERTICAL);
        setBackgroundResource(android.R.drawable.dialog_holo_light_frame);

        int padSmall = getResources().getDimensionPixelSize(R.dimen.layout_content_pad);

        RelativeLayout.LayoutParams relativeParam;

        // layoutProduct -------------
        layoutProduct = new RelativeLayout(context, attrs);
        layoutProduct.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(layoutProduct);

        // tvProduct
        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(ALIGN_PARENT_LEFT);

        tvProduct = new TextView(context, attrs);
        tvProduct.setLayoutParams(relativeParam);
        layoutProduct.addView(tvProduct);

        // tvProductValue
        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(ALIGN_PARENT_RIGHT);

        tvProductValue = new TextView(context, attrs);
        tvProductValue.setLayoutParams(relativeParam);
        layoutProduct.addView(tvProductValue);

        // layoutPrice ------------
        layoutPrice = new RelativeLayout(context, attrs);
        layoutPrice.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(layoutPrice);

        // tvPrice
        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(ALIGN_PARENT_LEFT);

        tvPrice = new TextView(context, attrs);
        tvPrice.setLayoutParams(relativeParam);
        layoutPrice.addView(tvPrice);

        // tvPriceValue
        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(ALIGN_PARENT_RIGHT);

        tvPriceValue = new TextView(context, attrs);
        tvPriceValue.setLayoutParams(relativeParam);
        layoutPrice.addView(tvPriceValue);

        // layoutTotalTransfer ------------
        layoutTotalTransfer = new RelativeLayout(context, attrs);
        layoutTotalTransfer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutTotalTransfer.setBackgroundResource(R.drawable.bg_paymentprice_totalbar);
        addView(layoutTotalTransfer);

        // tvTotalTransfer
        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(ALIGN_PARENT_LEFT);

        tvTotalTransfer = new TextView(context, attrs);
        tvTotalTransfer.setLayoutParams(relativeParam);
        tvTotalTransfer.setText(R.string.comp_paymentprice_label_totaltransfer);
        layoutTotalTransfer.addView(tvTotalTransfer);

        // tvTotalTransferValue
        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(ALIGN_PARENT_RIGHT);

        tvTotalTransferValue = new TextView(context, attrs);
        tvTotalTransferValue.setLayoutParams(relativeParam);
        tvTotalTransferValue.setTypeface(null, Typeface.BOLD);
        layoutTotalTransfer.addView(tvTotalTransferValue);

        // layoutTotalPay ------------
        layoutTotalPay = new RelativeLayout(context, attrs);
        layoutTotalPay.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutTotalPay.setBackgroundResource(R.drawable.bg_paymentprice_totalbar);
        addView(layoutTotalPay);

        // tvTotalPay
        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(ALIGN_PARENT_LEFT);

        tvTotalPay = new TextView(context, attrs);
        tvTotalPay.setLayoutParams(relativeParam);
        tvTotalPay.setText(R.string.comp_paymentprice_label_totalpay);
        layoutTotalPay.addView(tvTotalPay);

        // tvTotalPayValue
        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(ALIGN_PARENT_RIGHT);

        tvTotalPayValue = new TextView(context, attrs);
        tvTotalPayValue.setLayoutParams(relativeParam);
        tvTotalPayValue.setTypeface(null, Typeface.BOLD);
        layoutTotalPay.addView(tvTotalPayValue);

        // layout padding
        layoutProduct.setPadding(padSmall, padSmall, padSmall, padSmall);
        layoutPrice.setPadding(padSmall, padSmall, padSmall, padSmall);
        layoutTotalTransfer.setPadding(padSmall, padSmall, padSmall, padSmall);
        layoutTotalPay.setPadding(padSmall, padSmall, padSmall, padSmall);

        int pad = getResources().getDimensionPixelSize(R.dimen.holo_shadow_pad);
        setPadding(pad,pad,pad,pad);

    }

    private void hideLayout(){
//        layoutProduct.setVisibility(GONE);
//        layoutPrice.setVisibility(GONE);
        layoutTotalTransfer.setVisibility(GONE);
        layoutTotalPay.setVisibility(GONE);
    }

    private void initTopupField(){
        tvProduct.setText(R.string.comp_paymentprice_label_product);
        tvPrice.setText(R.string.comp_paymentprice_label_price);
    }

    public void initTopupTransfer(String product, long price, long totalTransfer){
        hideLayout();
        initTopupField();

        tvProductValue.setText(product);
        tvPriceValue.setText(Utility.toMoney(true, price));
        tvTotalTransferValue.setText(Utility.toMoney(true, totalTransfer));

        layoutProduct.setVisibility(VISIBLE);
        layoutPrice.setVisibility(VISIBLE);
        layoutTotalTransfer.setVisibility(VISIBLE);
    }

    public void initTopupCC(String product, long price, long totalPay){
        hideLayout();
        initTopupField();

        tvProductValue.setText(product);
        tvPriceValue.setText(Utility.toMoney(true, price));
        tvTotalPayValue.setText(Utility.toMoney(true, totalPay));

        layoutProduct.setVisibility(VISIBLE);
        layoutPrice.setVisibility(VISIBLE);
        layoutTotalPay.setVisibility(VISIBLE);
    }

    private void initPayTaxField(){
        tvProduct.setText(R.string.comp_paymentprice_label_paytax);
        tvPrice.setText(R.string.comp_paymentprice_label_channelcost);
    }

    public void initPayTaxTransfer(String product, long price, long totalTransfer){
        hideLayout();
        initPayTaxField();

        tvProductValue.setText(product);
        tvPriceValue.setText(Utility.toMoney(true, price));
        tvTotalTransferValue.setText(Utility.toMoney(true, totalTransfer));

        layoutProduct.setVisibility(VISIBLE);
        layoutPrice.setVisibility(VISIBLE);
        layoutTotalTransfer.setVisibility(VISIBLE);
    }

    public void initPayTaxCC(String product, long price, long totalPay){
        hideLayout();
        initPayTaxField();

        tvProductValue.setText(product);
        tvPriceValue.setText(Utility.toMoney(true, price));
        tvTotalPayValue.setText(Utility.toMoney(true, totalPay));

        layoutProduct.setVisibility(VISIBLE);
        layoutPrice.setVisibility(VISIBLE);
        layoutTotalPay.setVisibility(VISIBLE);
    }
}
