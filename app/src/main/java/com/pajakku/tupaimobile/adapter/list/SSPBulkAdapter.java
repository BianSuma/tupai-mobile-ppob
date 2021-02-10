package com.pajakku.tupaimobile.adapter.list;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.model.Sspunpaid;
import com.pajakku.tupaimobile.util.Utility;

/**
 * Created by dul on 18/12/18.
 */

public class SSPBulkAdapter extends ArrayAdapter<Sspunpaid> {
    private static int res = R.layout.row_ssp_bulk;

    public SSPBulkAdapter(Context context) {
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

        Sspunpaid data = getItem(position);

        if (data != null)
        {
            ImageView icon = v.findViewById(R.id.rowunfinishssp_icon);
            icon.setImageResource(data.fetchIcon());
            TextView tvName = v.findViewById(R.id.rowunfinishssp_name);
            tvName.setText( data.name );
            TextView tvNpwp = v.findViewById(R.id.rowunfinishssp_npwp);
            tvNpwp.setText( data.npwp );
            TextView tvTaxDate = v.findViewById(R.id.rowunfinishssp_taxdetail);
            tvTaxDate.setText( data.fetchTaxPeriod(getContext()) );
            TextView tvAmount = v.findViewById(R.id.rowunfinishssp_amount);
            tvAmount.setText(Utility.toMoney(true, data.amount));

//            int statusText = data.fetchStatusIcon();
//            TextView tvStatus = v.findViewById(R.id.rowunfinishssp_status);
//            tvStatus.setBackgroundResource(statusText==R.string.sspstatus_done?R.drawable.bg_label_sspstatus_green:R.drawable.bg_label_sspstatus_pink);
//            tvStatus.setText(statusText);

            TextView billCode = v.findViewById(R.id.rowunfinishssp_bilcode);
            billCode.setVisibility(View.GONE);
            TextView billCodePajakku = v.findViewById(R.id.rowunfinishssp_bilcodepajakku);
            billCodePajakku.setVisibility(View.GONE);
        }

        return v;
    }
}
