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
import com.pajakku.tupaimobile.model.dto.PulsaProduct;
import com.pajakku.tupaimobile.util.Utility;

import java.util.List;

/**
 * Created by dul on 18/12/18.
 */

public class PulsaProductAdapter extends ArrayAdapter<PulsaProduct> {
    private static int res = R.layout.row_pulsa_product;

    public PulsaProductAdapter(Context context, List<PulsaProduct> items) {
        super(context, res, items);
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

        PulsaProduct ele = getItem(position);

        if (ele != null)
        {
            ImageView icon = v.findViewById(R.id.rowpulsaproduct_icon);
            icon.setImageResource(R.drawable.ic_simpati);
            TextView tvOperator = v.findViewById(R.id.rowpulsaproduct_operator);
            tvOperator.setText("SimPATI");
            TextView tvName = v.findViewById(R.id.rowpulsaproduct_name);
            tvName.setText(ele.getName());
            TextView tvPrice = v.findViewById(R.id.rowpulsaproduct_price);
            tvPrice.setText(Utility.toMoney(true, 40000));
        }

        return v;
    }
}
