package com.pajakku.tupaimobile.adapter.list;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.adapter.BaseListAdapter;
import com.pajakku.tupaimobile.model.Kjs;

/**
 * Created by dul on 18/12/18.
 */

public class KJSAdapter extends BaseListAdapter<Kjs> {
    private static int res = R.layout.row_kjs;

    public KJSAdapter(Context context) {
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

        Kjs ele = getItem(position);

        if (ele != null)
        {
            TextView tvCode = v.findViewById(R.id.rowkjs_code);
            tvCode.setText(ele.code);
            TextView tvName = v.findViewById(R.id.rowkjs_name);
            tvName.setText(ele.name);
        }

        return v;
    }
}
