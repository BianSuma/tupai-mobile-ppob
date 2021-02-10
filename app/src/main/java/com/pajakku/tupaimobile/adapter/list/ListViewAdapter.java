package com.pajakku.tupaimobile.adapter.list;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.component.AppEditText;
import com.pajakku.tupaimobile.component.AppImageView;
import com.pajakku.tupaimobile.component.BrutoTaxItemList;
import com.pajakku.tupaimobile.model.SptType;
import com.pajakku.tupaimobile.model.dto.BrutoTax;
import com.pajakku.tupaimobile.model.dto.BrutoTaxItem;
import com.pajakku.tupaimobile.model.dto.ListModelBase;
import com.pajakku.tupaimobile.util.Utility;

/**
 * Created by dul on 05/07/19.
 */

public class ListViewAdapter extends ArrayAdapter<ListModelBase>{

    private Context context;
    private int res;

    public ListViewAdapter(Context ctx, int r){
        super(ctx, r);
        context = ctx;
        res = r;

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

        ListModelBase data = getItem(position);

        if (data != null)
        {
            if( data instanceof SptType){
                SptType st = (SptType) data;
                TextView tv;

                tv = v.findViewById(R.id.rowspttype_alias);
                tv.setText(st.alias);
                tv = v.findViewById(R.id.rowspttype_name);
                tv.setText(data.name);
            }else if( data instanceof BrutoTax){
                BrutoTax bt = (BrutoTax)data;

                AppEditText inpWorkAddress = v.findViewById(R.id.rowbrutotax_inp_workaddress);
                inpWorkAddress.setText(bt.name);

                BrutoTaxItemList childView;

                childView = v.findViewById(R.id.rowbrutotax_child_head);
                childView.setBold();
                childView.init("Masa Pajak", "Peredaran Bruto", "PPh Final 0,5%");

                LinearLayout ll = v.findViewById(R.id.rowbrutotax_layout_list);
                ll.removeAllViews();
                long totalBruto = 0, totalTax = 0;
                long amountBruto, amountTax;
                for(BrutoTaxItem bti : bt.l){
                    childView = new BrutoTaxItemList(getContext());
                    amountTax = Long.parseLong(bti.a);
                    amountBruto = Math.round((double)(amountTax * 1000) / 5);
                    childView.init(  context.getString(Utility.toMonthShort(bti.m)), Utility.toMoney(false, amountBruto), Utility.toMoney(false, amountTax));
                    ll.addView(childView);

                    totalBruto += amountBruto;
                    totalTax += amountTax;
                }

                childView  = v.findViewById(R.id.rowbrutotax_child_sum);
                childView.setBold();
                childView.init("Jumlah", Utility.toMoney(false, totalBruto), Utility.toMoney(false, totalTax));

                final RelativeLayout layoutChild = v.findViewById(R.id.rowbrutotax_layout_child);

                AppImageView imgFold = v.findViewById(R.id.rowbrutotax_btn_fold);
                imgFold.setBackgroundResource(R.drawable.ic_actionbar_rightmenu);
                imgFold.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if( layoutChild.getVisibility() == View.VISIBLE ) layoutChild.setVisibility(View.GONE);
                        else layoutChild.setVisibility(View.VISIBLE);
                    }
                });
            }
        }

        return v;
    }
}
