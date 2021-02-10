package com.pajakku.tupaimobile.adapter.list;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.adapter.BaseListAdapter;
import com.pajakku.tupaimobile.model.Transactionlog;
import com.pajakku.tupaimobile.util.Utility;

/**
 * Created by dul on 18/12/18.
 */

public class TransLogAdapter extends BaseListAdapter<Transactionlog> {
    private static int res = R.layout.row_trans_log;

    public TransLogAdapter(Context context) {
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

        Transactionlog ele = getItem(position);

        if (ele != null)
        {
            TextView tvId = v.findViewById(R.id.rowtranslog_label_id);
            tvId.setText( String.valueOf(ele.id) );

            TextView tvTransType = v.findViewById(R.id.rowtranslog_label_transtype);
            tvTransType.setText(ele.fetchLabelTransType());

            TextView tvAmount = v.findViewById(R.id.rowtranslog_label_amount);
            tvAmount.setText(Utility.toMoney(true, Long.parseLong(ele.amount) ) );

            TextView tvUpdatedAt = v.findViewById(R.id.rowtranslog_label_updatedat);
            tvUpdatedAt.setText(ele.updatedAt);

            TextView tvBillId = v.findViewById(R.id.rowtranslog_label_billnumber);
            tvBillId.setText(ele.billNumber);

            TextView tvStatus = v.findViewById(R.id.rowtranslog_label_status);
            tvStatus.setText( ele.fetchStatus() );

            int stat = Utility.DEFACETYPE_ONGOING;
            switch (ele.fetchStatus()){
                case R.string.translog_label_statusfail:
                    stat = Utility.DEFACETYPE_FAIL;
                    break;
                case R.string.translog_label_statussuccess:
                    stat = Utility.DEFACETYPE_SUCCESS;
                    break;
            }
            Utility.defaceTextViewStatus(tvStatus, stat);

        }

        return v;
    }
}
