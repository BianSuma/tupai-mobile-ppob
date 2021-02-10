package com.pajakku.tupaimobile.model.dto;

import android.view.View;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.pajakku.tupaimobile.component.RecyclerViewX;

/**
 * Created by dul on 04/04/19.
 */

public class RequestParamConfig {
    public String requestKey;
    public boolean isShowFailToast = true;
    public String cacheKey = null;
    public boolean isCache = true;
    public boolean isRelogin = false;
    public boolean forceRequest = true;
    public boolean withAuth = true;
    public int progressTextRes = 0;
    public long keepdataDuration;

    public View reloader;

    public RecyclerViewX recyclerViewX;
    public SwipeRefreshLayout swipeRefreshLayout;

//    public Long page;
    public ReqPaging reqPaging;

    public RequestParamConfig(){
        reqPaging = new ReqPaging();
    }

    public void startLoading(){
//        if( ! isContainerExist() ) return;
//        if(appRecyclerView != null && !isLoadMore) appRecyclerView.setRefreshing(true);
//        if(recyclerViewX != null ) recyclerViewX.setRefreshing(true);
        if(swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(true);
    }

    public void stopLoading(){
//        if( ! isContainerExist() ) return;
//        if(appRecyclerView != null){
//            appRecyclerView.setRefreshing(false);
//            if(isLoadMore) appRecyclerView.stopLoadMore();
//        }
        if(recyclerViewX != null){
            recyclerViewX.setRefreshing(false);
//            if(isLoadMore) recyclerViewX.stopLoadMore();
        }
        if(swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(false);
    }

    public void showReloader(){
        if( reloader != null) reloader.setVisibility(View.VISIBLE);
//        if( appActionBar != null) appActionBar.showReloader(requestKey);
    }

    public void hideReloader(){
        if( reloader != null) reloader.setVisibility(View.GONE);
//        if( appActionBar != null) appActionBar.hideReloader();
    }
}
