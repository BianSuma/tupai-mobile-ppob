package com.pajakku.tupaimobile.adapter.viewholder;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.model.dto.ClickItemListParam;
import com.pajakku.tupaimobile.model.dto.ModelMultiSelect;
import com.pajakku.tupaimobile.model.holder.HolderCommon2Val;
import com.pajakku.tupaimobile.util.CommonCallback;

public class ViewHolderCommon2Val extends RecyclerView.ViewHolder {
    private View rootView;
    private AppCompatTextView valBold;
    private AppCompatTextView valMini;

    public ViewHolderCommon2Val(@NonNull View itemView) {
        super(itemView);
        rootView = itemView;
        valBold = itemView.findViewById(R.id.rowcommon2val_lbl_bold);
        valMini = itemView.findViewById(R.id.rowcommon2val_val_mini);

    }

    public void populate(ModelMultiSelect d, final CommonCallback<ClickItemListParam> cb){
        final HolderCommon2Val data = (HolderCommon2Val) d;

        valBold.setText( data.boldNotNull() );
        valMini.setText( data.miniNotNull() );

        if(cb != null) {
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClickItemListParam c = new ClickItemListParam();
                    c.object = data;
                    cb.onSuccess(c);
                }
            });
        }
    }

}

