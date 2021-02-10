package com.pajakku.tupaimobile.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.pajakku.tupaimobile.model.dto.ModelMultiSelect;

/**
 * Created by dul on 24/01/19.
 */

public class BaseListAdapter<T> extends ArrayAdapter<T> {

    public boolean isMultiSelect = false;

    public BaseListAdapter(Context context, int res) {
        super(context, res);
    }

    public void checkItem(ModelMultiSelect d, boolean b){
        d.isCheck = b;
        notifyDataSetChanged();
    }
}
