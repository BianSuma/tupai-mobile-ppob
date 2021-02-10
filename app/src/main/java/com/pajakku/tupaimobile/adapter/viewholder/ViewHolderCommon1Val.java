package com.pajakku.tupaimobile.adapter.viewholder;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.model.dto.ClickItemListParam;
import com.pajakku.tupaimobile.model.dto.ModelMultiSelect;
import com.pajakku.tupaimobile.model.holder.HolderCommon1Val;
import com.pajakku.tupaimobile.util.CommonCallback;

public class ViewHolderCommon1Val extends RecyclerView.ViewHolder {
    private View rootView;
    private AppCompatTextView lblName;

    public ViewHolderCommon1Val(@NonNull View itemView) {
        super(itemView);
        rootView = itemView;
        lblName = itemView.findViewById(R.id.rowcommon1val_lbl_name);

    }

    public void populate(ModelMultiSelect d, final CommonCallback<ClickItemListParam> cb){
        final HolderCommon1Val data = (HolderCommon1Val) d;

        lblName.setText( data.strNotNull() );

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

