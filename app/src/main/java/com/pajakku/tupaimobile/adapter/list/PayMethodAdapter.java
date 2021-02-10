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
import com.pajakku.tupaimobile.model.dto.PayMethod;

/**
 * Created by dul on 18/12/18.
 */

public class PayMethodAdapter extends ArrayAdapter<PayMethod> {
    private static int res = R.layout.row_paymethod;

    public PayMethodAdapter(Context context) {
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

        PayMethod data = getItem(position);

        if (data != null)
        {
            ImageView tvCode = v.findViewById(R.id.rowpaymethod_icon);
            tvCode.setImageResource(data.icon);
            TextView tvName = v.findViewById(R.id.rowpaymethod_name);
            tvName.setText(data.name);
        }

        return v;
    }
}
