package com.pajakku.tupaimobile.adapter.list;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.model.WpNtpn;

import java.util.List;

/**
 * Created by dul on 18/12/18.
 */

public class WpNtpnAdapter extends ArrayAdapter<WpNtpn> {
    private int res;

    public WpNtpnAdapter(Context context, int resource, List<WpNtpn> items) {
        super(context, resource, items);
        res = resource;
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

        WpNtpn ele = getItem(position);

        if (ele != null)
        {
            TextView tvKap = v.findViewById(R.id.rowwpntpn_label_kap);
            tvKap.setText(ele.getKap());
            TextView tvKjs = v.findViewById(R.id.rowwpntpn_label_kjs);
            tvKjs.setText(ele.getKjs());
            TextView tvMonthyear = v.findViewById(R.id.rowwpntpn_label_monthyear);
            tvMonthyear.setText(ele.getMonthYear());
            TextView tvSetor = v.findViewById(R.id.rowwpntpn_label_setor);
            tvSetor.setText(ele.getSetor());
            TextView tvNtpn = v.findViewById(R.id.rowwpntpn_label_ntpn);
            tvNtpn.setText(ele.getNtpn());
        }

        return v;
    }
}
