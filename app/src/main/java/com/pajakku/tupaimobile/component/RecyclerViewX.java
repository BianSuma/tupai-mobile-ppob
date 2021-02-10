package com.pajakku.tupaimobile.component;

import android.app.Activity;
import android.content.ContextWrapper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.pajakku.tupaimobile.adapter.list.AppRecyclerAdapter;
import com.pajakku.tupaimobile.model.dto.ClickItemListParam;
import com.pajakku.tupaimobile.model.dto.ModelMultiSelect;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.Utility;

import java.util.List;

public class RecyclerViewX extends ContextWrapper {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private AppRecyclerAdapter adapter;
    private boolean isLoading;
    private long page;

    // viewId = SwipeRefreshLayout / RecyclerView, if RecyclerView then no SwipeRefreshLayout
    public RecyclerViewX(Activity act, View v, int viewId){
        super(act);
        View lay;
        if(v != null) lay = v.findViewById(viewId);
        else lay = act.findViewById(viewId);

        adapter = new AppRecyclerAdapter();

        if(lay instanceof SwipeRefreshLayout) {
            swipeRefreshLayout = (SwipeRefreshLayout) lay;
            View view;
            int childCount = swipeRefreshLayout.getChildCount();
            for(int i = 0; i<childCount; i++){
                view = swipeRefreshLayout.getChildAt(i);  // may return CircleImageView (not RecyclerView)
                if(view instanceof RecyclerView){
                    recyclerView = (RecyclerView)view;
                    break;
                }
            }
        }else{
            if(v != null) recyclerView = v.findViewById(viewId);
            else recyclerView = act.findViewById(viewId);
        }
        recyclerView.setAdapter( adapter );

        page = 1;
    }

    public void setOnClick(final CommonCallback<ClickItemListParam> commonCallback, final int... menus){
        if(commonCallback == null) return;
        adapter.cbExtra = new CommonCallback<ClickItemListParam>() {
            @Override
            public void onSuccess(ClickItemListParam data) {
                if(data.view == null){
                    commonCallback.onSuccess(data);
                    return;
                }
                showPopupMenu(data, menus, commonCallback);
            }
        };
    }

    private void showPopupMenu(final ClickItemListParam data, int[] arr, final CommonCallback<ClickItemListParam> cb){
        if(arr == null) return;
        if(arr.length <= 0) return;
        PopupMenu popupMenu = new PopupMenu(this, data.view);
        int i = 0;
        for(int j : arr) {
            popupMenu.getMenu().add( 1, j, i++, j);
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(android.view.MenuItem item) {
                data.id = item.getItemId();
                if(cb != null) cb.onSuccess(data);
                return false;
            }
        });
        popupMenu.show();
    }

    public void setRefreshing(boolean b){
        isLoading = b;
        if(swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(b);
    }

    public void setOnRefresh(final CommonCallback<RecyclerViewX> callback){
        final RecyclerViewX context = this;
        if(swipeRefreshLayout != null) swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isLoading) return;
                if(callback != null) {
                    callback.onSuccess(context);
                }else{
                    Utility.log("no refresh listener");
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    public void reload(){
        adapter.notifyDataSetChanged();
    }

    public void setOnScroll(final CommonCallback<Long> callback){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);

                // @test
//                if(!AppTester.LIST_TEST) return;

                if(isLoading) return;

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rv.getLayoutManager();
                final List<ModelMultiSelect> list = adapter.list;

                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == list.size() - 1) {
                    list.add(null);
                    adapter.notifyItemInserted(list.size() - 1);
                    isLoading = true;

                    callback.onSuccess(page+1);

                }

            }
        });
    }

    public <T> void setList(T l){
        List<ModelMultiSelect> list = (List<ModelMultiSelect>)l;
//        tvEmptyData.setVisibility( list.size() > 0 ? GONE : VISIBLE );
        page = 1;
        adapter.list.clear();
        adapter.list.addAll( list );
        adapter.notifyDataSetChanged();
    }

    public List<ModelMultiSelect> getData(){
        return adapter.list;
    }

    public <T> void loadMoreAddItem(T l){
        List<ModelMultiSelect> list = (List<ModelMultiSelect>)l;
        if(list.size() > 0) {
            page++;
            adapter.list.addAll(list);
            adapter.notifyDataSetChanged();
        }

//        tvEmptyData.setVisibility( appRecyclerAdapter.list.size() > 0 ? GONE : VISIBLE );
    }

    public void stopLoadMore(){
        List<ModelMultiSelect> l = adapter.list;
        if(l.size() > 0) l.remove(l.size() - 1);
        adapter.notifyItemRemoved(l.size());
    }

}
