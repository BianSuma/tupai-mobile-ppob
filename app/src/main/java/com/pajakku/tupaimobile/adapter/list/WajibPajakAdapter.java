package com.pajakku.tupaimobile.adapter.list;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.adapter.BaseListAdapter;
import com.pajakku.tupaimobile.model.Wajibpajak;
import com.pajakku.tupaimobile.util.Utility;

/**
 * Created by dul on 18/12/18.
 */

public class WajibPajakAdapter extends BaseListAdapter<Wajibpajak> {
    private static int res = R.layout.row_wajib_pajak;

    public WajibPajakAdapter(Context context) {
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

        Wajibpajak ele = getItem(position);

        if (ele != null)
        {
//            ImageView icon = v.findViewById(R.id.rowwp_icon);
//            icon.setImageResource(R.drawable.ic_person_none);
            TextView tvInitName = v.findViewById(R.id.rowwp_iconlabel_initname);
            tvInitName.setText(ele.name.substring(0,1));

            TextView tvName = v.findViewById(R.id.rowwp_name);
            tvName.setText(ele.name);
            TextView tvNpwp = v.findViewById(R.id.rowwp_npwp);
            tvNpwp.setText(Utility.toPrettyNpwp(ele.npwp) );
            TextView tvAddress = v.findViewById(R.id.rowwp_address);
            tvAddress.setText(ele.address);
        }

        return v;
    }
}
