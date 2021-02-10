package com.pajakku.tupaimobile.adapter.list;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.adapter.BaseListAdapter;
import com.pajakku.tupaimobile.model.Sspunpaid;
import com.pajakku.tupaimobile.model.Taxtype;
import com.pajakku.tupaimobile.model.dto.TaxSlipResponse;
import com.pajakku.tupaimobile.util.Utility;

/**
 * Created by dul on 18/12/18.
 */

@Deprecated
public class SSPAdapterUnpaid extends BaseListAdapter<Sspunpaid> {
    private static int res = R.layout.row_unfinish_ssp;

    public SSPAdapterUnpaid(Context context) {
        super(context, res);

    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(res, null);
        }

        final Sspunpaid data = getItem(position);

        if (data != null)
        {
            int category = Taxtype.fetchCategoryStatic(data.taxTypeCode);
            int backCircle = Taxtype.fetchBackCircle(category);
            int iconPad = getContext().getResources().getDimensionPixelSize(R.dimen.layout_content_pad);
            Drawable d = Taxtype.fetchColoredItem(getContext(), data.fetchIcon(), Taxtype.fetchCategoryStatic(data.fetchDefaultTaxTypeCode()));
            ImageView icon = v.findViewById(R.id.rowunfinishssp_icon);
            icon.setBackgroundResource(backCircle);
            icon.setImageDrawable( d );
            icon.setPadding(iconPad, iconPad, iconPad, iconPad);

            TextView tvName = v.findViewById(R.id.rowunfinishssp_name);
            tvName.setText(String.valueOf(data.name));
            TextView tvNpwp = v.findViewById(R.id.rowunfinishssp_npwp);
            tvNpwp.setText(data.npwp);
            TextView tvTaxDate = v.findViewById(R.id.rowunfinishssp_taxdetail);
            tvTaxDate.setText( getContext().getString(R.string.ssplist_label_taxdetail, data.taxTypeName, data.fetchTaxPeriod(getContext()) ) );
            TextView tvAmount = v.findViewById(R.id.rowunfinishssp_amount);
            tvAmount.setText( Utility.toMoney(true, data.amount) );

            TextView tvBillCode = v.findViewById(R.id.rowunfinishssp_bilcode);
            if(data.taxslipresponseCodeNotNull().equals(TaxSlipResponse.RESPONSECODE_OK)){
                tvBillCode.setTextColor(Color.parseColor("#52c05a"));
            }else{
                tvBillCode.setTextColor(Color.parseColor("#990000"));
            }
            TextView tvBillCodePajakku = v.findViewById(R.id.rowunfinishssp_bilcodepajakku);

            if(data.idBilling != null) {
                if (data.taxslipresponseCodeNotNull().equals(TaxSlipResponse.RESPONSECODE_OK)) {
                    tvBillCode.setText(Utility.billNumberX(data.idBilling));
                } else {
                    tvBillCode.setText(data.idBilling);
                }
                tvBillCode.setVisibility(View.VISIBLE);
            }else{
                tvBillCode.setVisibility(View.GONE);
            }
            tvBillCodePajakku.setVisibility(View.GONE);

            ImageView rightArrow = v.findViewById(R.id.rowunfinishssp_rightarrow);
            CheckBox checkBox = v.findViewById(R.id.rowunfinishssp_selected);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                    data.isCheck = b;
                    SSPAdapterUnpaid.this.checkItem(data, b);
                }
            });
            checkBox.setChecked( data.isCheck );
            if(isMultiSelect){
                rightArrow.setVisibility(View.GONE);
                checkBox.setVisibility(View.VISIBLE);
            }else{
                rightArrow.setVisibility(View.VISIBLE);
                checkBox.setVisibility(View.GONE);
            }
        }

        return v;
    }

}
