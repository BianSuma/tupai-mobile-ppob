package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.model.Kjs;
import com.pajakku.tupaimobile.model.Sspunpaid;
import com.pajakku.tupaimobile.model.Taxtype;
import com.pajakku.tupaimobile.model.dto.response.ResponseSsp;
import com.pajakku.tupaimobile.util.Utility;

/**
 * Created by dul on 18/12/18.
 */

public class TaxWPHeader extends RelativeLayout {

    public static final int STATUS_BELUM_BAYAR = 1;
    public static final int STATUS_DONE = 2;

    private ImageView icon;
    private TextView headName;
    private TextView tvDetail0;
    private TextView tvDetail1;
    private TextView tvJumlahBayar;
    private TextView tvAmount;
    private TextView tvStatus;

    public TaxWPHeader(Context context, AttributeSet attrs){
        super(context, attrs);
//        this.attrs = attrs;

//        TypedArray a = context.getTheme().obtainStyledAttributes(
//                attrs,
//                R.styleable.AttrsInfoRow,
//                0, 0);
////        boolean taxAmount = false;
////        boolean taxStatus = false;
//        try {
////             taxAmount = a.getBoolean(R.styleable.AttrsInfoRow_tax_amount, false);
////             taxStatus = a.getBoolean(R.styleable.AttrsInfoRow_status, false);
//        } finally {
//            a.recycle();
//        }

//        setBackgroundColor(Color.parseColor("#f2f2f2"));
        setBackgroundResource(R.drawable.bg_taxwpheader);

        RelativeLayout.LayoutParams relativeParam;

        // icon
        int iconSize = Math.round(TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()) );
        relativeParam = new RelativeLayout.LayoutParams(iconSize, iconSize);

        icon = new ImageView(context, attrs);
        icon.setId(View.generateViewId());
        icon.setLayoutParams(relativeParam);
        addView(icon);

        // person name
        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RelativeLayout.RIGHT_OF, icon.getId());
        relativeParam.leftMargin = (int)getResources().getDimension(R.dimen.layout_content_pad);

        headName = new TextView(context, attrs);
        headName.setId(View.generateViewId());
        headName.setLayoutParams(relativeParam);
        headName.setTypeface(null, Typeface.BOLD);
        addView(headName);

        // npwp
        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RelativeLayout.BELOW, headName.getId());
        relativeParam.addRule(RelativeLayout.ALIGN_LEFT, headName.getId());

        tvDetail0 = new TextView(context, attrs);
        tvDetail0.setId(View.generateViewId());
        tvDetail0.setLayoutParams(relativeParam);
        tvDetail0.setVisibility(GONE);
        addView(tvDetail0);

        // taxDetail
        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RelativeLayout.BELOW, tvDetail0.getId());
        relativeParam.addRule(RelativeLayout.ALIGN_LEFT, tvDetail0.getId());

        tvDetail1 = new TextView(context, attrs);
        tvDetail1.setId(View.generateViewId());
        tvDetail1.setLayoutParams(relativeParam);
        tvDetail1.setTextSize( TypedValue.COMPLEX_UNIT_SP, 10 );
        addView(tvDetail1);

        // jumlah bayar
        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RelativeLayout.BELOW, tvDetail1.getId());
        relativeParam.addRule(RelativeLayout.ALIGN_LEFT, tvDetail1.getId());

        tvJumlahBayar = new TextView(context, attrs);
        tvJumlahBayar.setId(View.generateViewId());
        tvJumlahBayar.setLayoutParams(relativeParam);
        tvJumlahBayar.setVisibility(GONE);
        tvJumlahBayar.setText(getResources().getString(R.string.comp_taxwpheader_label_totalpay));
        addView(tvJumlahBayar);

        // amount
        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RelativeLayout.RIGHT_OF, tvJumlahBayar.getId());
        relativeParam.addRule(RelativeLayout.ALIGN_TOP, tvJumlahBayar.getId());
        relativeParam.leftMargin = getResources().getDimensionPixelSize(R.dimen.layout_content_pad);

        tvAmount = new TextView(context, attrs);
        tvAmount.setLayoutParams(relativeParam);
        tvAmount.setTypeface(null, Typeface.BOLD);
        tvAmount.setTextColor(Color.parseColor("#157215"));
        tvAmount.setVisibility(GONE);
        addView(tvAmount);

        // status
        relativeParam = new RelativeLayout.LayoutParams((int) getResources().getDimension(R.dimen.ssp_status_width), ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        tvStatus = new TextView(context, attrs);
        tvStatus.setLayoutParams(relativeParam);
        tvStatus.setGravity(Gravity.CENTER);
        tvStatus.setTextColor(Color.parseColor("#ffffff"));
        tvStatus.setVisibility(GONE);
        addView(tvStatus);

        int pad = (int)getResources().getDimension(R.dimen.layout_content_pad);
        setPadding(pad,pad,pad,pad);
    }

    public void init(int leftIcon, String personName, String npwp, String pasal, String kap, String kjs, String taxPeriod){

        icon.setImageResource(leftIcon);
        headName.setText(personName);
        if(npwp!=null) tvDetail0.setText(npwp); else tvDetail0.setVisibility(GONE);
        tvDetail1.setText(getResources().getString(R.string.paypphmethod_label_taxkap, pasal, kap, kjs, taxPeriod));

    }
    public void setIcon(int i, String taxTypeCode){
        int category = Taxtype.fetchCategoryStatic(taxTypeCode);
        Drawable d = Taxtype.fetchColoredItem(getContext(), i, category);
        int backCircle = Taxtype.fetchBackCircle(category);
        int pad = getResources().getDimensionPixelSize(R.dimen.layout_content_pad);

        icon.setImageDrawable(d);
        icon.setBackgroundResource(backCircle);
        icon.setPadding(pad, pad, pad, pad);
    }
    public void setHeadName(String name){
        headName.setText(name);
    }
    public void setNpwp(String n){
        tvDetail0.setText( Utility.toPrettyNpwp(n) );
        tvDetail0.setVisibility(VISIBLE);
    }
    public void setKAP(String d){
        tvDetail0.setText( getResources().getString(R.string.comp_taxwpheader_val_kap, d));
        tvDetail0.setVisibility(VISIBLE);
    }
    public void setKapKjs(Taxtype taxtype, Kjs kjs){
        tvDetail0.setText( kjs.name );
        tvDetail0.setVisibility(VISIBLE);
        tvDetail1.setText( getResources().getString(R.string.comp_taxwpheader_val_kapkjs, taxtype.code, kjs.code));
    }
    public void setTaxAccount(Taxtype taxtype, Kjs kjs){
        tvDetail1.setText(getResources().getString(R.string.comp_taxwpheader_val_taxaccount, taxtype.name, taxtype.code, kjs.code));
    }
    public void setTaxAccountPeriod(Taxtype taxtype, Kjs kjs, String period){
        tvDetail1.setText(getResources().getString(R.string.comp_taxwpheader_val_taxaccountperiod, taxtype.name, taxtype.code, kjs.code, period));
    }
    public void setTaxAccountPeriod(Sspunpaid sspunpaid){
        tvDetail1.setText(getResources().getString(R.string.comp_taxwpheader_val_taxaccountperiod, sspunpaid.taxTypeName, sspunpaid.taxTypeCode,
                sspunpaid.kjsCode, sspunpaid.fetchTaxPeriod(getContext())));
    }
    public void setTaxAccountPeriod(ResponseSsp respSsp){
        tvDetail1.setText(getResources().getString(R.string.comp_taxwpheader_val_taxaccountperiod, respSsp.taxType.nameNotNull(), respSsp.taxType.codeNotNull(),
                respSsp.taxSlipType.codeNotNull(), respSsp.fetchTaxPeriod(getContext())));
    }

    public void setStatusCode(int sc){

        int statusBg = R.drawable.bg_label_sspstatus_pink;
        int statusText = R.string.sspstatus_belumbayar;
        switch (sc){
            case STATUS_DONE:
                statusBg = R.drawable.bg_label_sspstatus_green;
                statusText = R.string.sspstatus_done;
                break;
        }

        tvStatus.setBackgroundResource(statusBg);
        tvStatus.setText(statusText);
        tvStatus.setVisibility(VISIBLE);
    }

    public void setAmount(long amount){
        tvJumlahBayar.setVisibility(VISIBLE);

        tvAmount.setText(Utility.toMoney(true, amount));
        tvAmount.setVisibility(VISIBLE);
    }
}
