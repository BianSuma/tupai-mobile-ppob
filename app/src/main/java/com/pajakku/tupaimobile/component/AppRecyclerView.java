package com.pajakku.tupaimobile.component;

import android.content.Context;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

import com.pajakku.tupaimobile.adapter.list.AppRecyclerAdapter;
import com.pajakku.tupaimobile.model.dto.ModelMultiSelect;
import com.pajakku.tupaimobile.util.CommonCallback;

import java.util.List;

/**
 * Created by dul on 29/03/19.
 */

public class AppRecyclerView extends RecyclerView {

    public long page = 1;
    public boolean isLoading = false;

    public AppRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public <T> void init(final boolean isLoadMore, SwipeRefreshLayout swipeRefreshLayout, final AppCallback callback){
        setAdapter(new AppRecyclerAdapter(getContext(), new CommonCallback<T>() {
            @Override
            public void onSuccess(T data) {
                callback.onClick(data);
            }
        }));
        if(swipeRefreshLayout != null) swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callback.onRefresh();
            }
        });
        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);

                if( ! isLoadMore ) return;

                if( ! isLoading ) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rv.getLayoutManager();
                    final AppRecyclerAdapter adapter = (AppRecyclerAdapter) rv.getAdapter();
                    final List<ModelMultiSelect> list = adapter.list;

                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == list.size() - 1) {
                        list.add(null);
                        adapter.notifyItemInserted(list.size() - 1);
                        isLoading = true;

                        callback.onLoadMore(page+1);

//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                list.remove(list.size() - 1);
//                                int scrollPosition = list.size();
//                                adapter.notifyItemRemoved(scrollPosition);
//
//                                Kjs kjs = new Kjs();
//                                kjs.code = "00"+list.size();
//                                kjs.name = "The KJS";
//                                list.add(kjs);
//
//                                adapter.notifyDataSetChanged();
//                                isLoading = false;
//                            }
//                        }, 2000);

                    }
                }

            }
        });
    }

    public List<ModelMultiSelect> getData(){
        return ((AppRecyclerAdapter)getAdapter()).list;
    }

    public <T> void updateListView(List<T> list){
        isLoading = false;
        page = 1;
        AppRecyclerAdapter adapter = (AppRecyclerAdapter) getAdapter();
        adapter.list.clear();
        adapter.list.addAll((List<ModelMultiSelect>)list);
        adapter.notifyDataSetChanged();
    }

    public void stopLoadMore(){
        isLoading = false;
        AppRecyclerAdapter adapter = (AppRecyclerAdapter) getAdapter();
        List<ModelMultiSelect> l = adapter.list;
        if(!l.isEmpty()) {
            l.remove(l.size() - 1);
            adapter.notifyItemRemoved(l.size());
        }
    }

    public <T> void loadMoreAddItem(List<T> list){
        if(list.size() > 0) page++;

        stopLoadMore();

        AppRecyclerAdapter adapter = (AppRecyclerAdapter) getAdapter();
        List<ModelMultiSelect> l = adapter.list;

        l.addAll((List<ModelMultiSelect>)list);
        adapter.notifyDataSetChanged();
    }

    public interface AppCallback <T> {
        void onClick(T d);
        void onRefresh();
        void onLoadMore(long nextPage);
    }
}
