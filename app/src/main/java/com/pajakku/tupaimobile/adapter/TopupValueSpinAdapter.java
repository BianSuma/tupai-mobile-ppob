package com.pajakku.tupaimobile.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;

import java.util.List;

/**
 * Created by dul on 18/12/18.
 */

public class TopupValueSpinAdapter extends ArrayAdapter<String> {

    private int res;
    private int title;

    public TopupValueSpinAdapter(Context context, int title, int resource, List<String> items) {
        super(context, resource, items);
        this.title = title;
        res = resource;
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View v = getViewData(position, convertView, res);
        TextView tvTitle = v.findViewById(R.id.rowspinitem_label_title);
        tvTitle.setText(title);
        return v;
    }

    @Override
    @NonNull
    public View getDropDownView(int position, View v, @NonNull ViewGroup parent) {
        return getViewData(position, v, R.layout.row_spin_item_dropdown);
    }

    private View getViewData(int pos, View v, int resource)
    {
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(resource, null);
        }

        String ele = getItem(pos);

        if (ele != null)
        {
            TextView tvName = v.findViewById(R.id.rowspinitem_label_name);
            tvName.setText(ele);
        }

        return v;
    }
}
