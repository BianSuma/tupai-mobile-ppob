package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.pajakku.tupaimobile.adapter.list.ListViewAdapter;
import com.pajakku.tupaimobile.model.dto.ListModelBase;

import java.util.List;

/**
 * Created by dul on 05/07/19.
 */

public class AppListView extends ListView {

    public AppListView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public void init(Context ctx, int rowRes){
        setAdapter( new ListViewAdapter(ctx, rowRes) );
    }

    public void add(List<ListModelBase> l){
        ListViewAdapter adapter = (ListViewAdapter)getAdapter();
        adapter.addAll(l);
        adapter.notifyDataSetChanged();
    }
    public void add(ListModelBase m){
        ListViewAdapter adapter = (ListViewAdapter)getAdapter();
        adapter.add(m);
        adapter.notifyDataSetChanged();
    }
}
