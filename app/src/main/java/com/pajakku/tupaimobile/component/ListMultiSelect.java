package com.pajakku.tupaimobile.component;

import android.content.Context;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pajakku.tupaimobile.adapter.BaseListAdapter;
import com.pajakku.tupaimobile.model.dto.ModelMultiSelect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dul on 24/01/19.
 */

public class ListMultiSelect extends ListView {
    private long page = 1;

//    private ProgressBar progressBar = null;
    private SwipeRefreshLayout swipyRefreshLayout;
    private BaseListAdapter adapter;
    public ListMultiSelect(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public <T> void init(final SwipeRefreshLayout swipyRefreshLayout, final BaseListAdapter adapter, final AppListener<T> appListener){
        this.swipyRefreshLayout = swipyRefreshLayout;

        setAdapter(this.adapter = adapter);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if( adapter.isMultiSelect ) checkItem((ModelMultiSelect) parent.getItemAtPosition(position));
                else appListener.onClick((T)parent.getItemAtPosition(position));
            }
        });

        if(this.swipyRefreshLayout != null) {
            this.swipyRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    appListener.onSwipeLoadData(1);
                }
            });
//            this.swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
//                @Override
//                public void onRefresh(SwipyRefreshLayoutDirection direction) {
//                    if (direction == SwipyRefreshLayoutDirection.TOP) {
//                        if (--page < 1) page = 1;
//                    } else if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
//                        page++;
//                    }
//                    appListener.onSwipeLoadData(page);
//                }
//            });
        }
    }

    public void setMultiSelect(boolean isMultiSelect){

        adapter.isMultiSelect = isMultiSelect;
        adapter.notifyDataSetChanged();
    }

//    public void setProgressBar(ProgressBar pb){
//        progressBar = pb;
//    }

    private void checkItem(ModelMultiSelect d){
        d.isCheck = ! d.isCheck;
        adapter.notifyDataSetChanged();
    }

    public <T> void updateListView(List<T> d){
//        if(progressBar != null) progressBar.setVisibility(GONE);
        adapter.clear();
        adapter.addAll(d);
    }

    public interface AppListener <T> {
        void onClick(T d);
        void onSwipeLoadData(long page);
    }

    public <T> ArrayList<T> getMultiSelected(){
        ArrayList<T> list = new ArrayList<>();
        for(int i = 0; i<adapter.getCount(); i++){
            ModelMultiSelect d = (ModelMultiSelect) adapter.getItem(i);
            if(d.isCheck) list.add((T)d);
        }
        return list;
    }

}
