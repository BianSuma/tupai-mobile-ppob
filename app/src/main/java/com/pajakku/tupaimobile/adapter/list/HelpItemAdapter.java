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
import com.pajakku.tupaimobile.model.dto.HelpItem;

/**
 * Created by dul on 18/12/18.
 */

public class HelpItemAdapter extends ArrayAdapter<HelpItem> {
    private static int res = R.layout.row_helpitem;

    public HelpItemAdapter(Context context) {
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

        HelpItem data = getItem(position);

        if (data != null)
        {
            ImageView tvCode = v.findViewById(R.id.rowhelpitem_icon);
            tvCode.setImageResource(data.icon);
            TextView tvName = v.findViewById(R.id.rowhelpitem_name);
            tvName.setText(data.name);
            TextView tvDesc = v.findViewById(R.id.rowhelpitem_desc);
            tvDesc.setText(data.desc);
        }

        return v;
    }
}
