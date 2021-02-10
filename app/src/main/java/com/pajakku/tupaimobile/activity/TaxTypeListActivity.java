package com.pajakku.tupaimobile.activity;

import android.content.Intent;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.api.ApiReqBilling;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.AppRecyclerView;
import com.pajakku.tupaimobile.component.QuickFindPanel;
import com.pajakku.tupaimobile.model.Taxtype;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.response.RespListTaxType;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;

import java.util.ArrayList;
import java.util.List;

public class TaxTypeListActivity extends BaseActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private AppRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_type_list);

        AppActionBar appActionBar = findViewById(R.id.taxtypelist_appactionbar);
        appActionBar.setBackFinish(this);

        final QuickFindPanel quickFindPanel = findViewById(R.id.taxtypelist_listfindpanel);
        quickFindPanel.init(new CommonCallback<String>() {
            @Override
            public void onSuccess(String data) {
                fetchData(data);
            }
        });

        swipeRefreshLayout = findViewById(R.id.taxtypelist_swiperefreshlayout);
        recyclerView = findViewById(R.id.taxtypelist_apprecyclerview);
        recyclerView.init(false, swipeRefreshLayout, new AppRecyclerView.AppCallback<Taxtype>() {
            @Override
            public void onClick(Taxtype d) {
                clickItem(d);
            }

            @Override
            public void onRefresh() {
                quickFindPanel.setText("");
            }

            @Override
            public void onLoadMore(long p) {
                // nothing
            }
        });

        fetchData(null);
    }


    private void fetchData(final String key){

        RequestParamConfig rpc = new RequestParamConfig();
        rpc.forceRequest = false;
        ApiReqBilling.findKjp(this, rpc, new CommonCallback<List<RespListTaxType>>() {
            @Override
            public void onSuccess(List<RespListTaxType> data) {
                List<Taxtype> list = RespListTaxType.toTaxType(data);
                List<Taxtype> d = list;
                if(key != null){
                    d = new ArrayList<>();
                    for(Taxtype tt : list){
                        if( ! tt.name.toLowerCase().contains(key.toLowerCase()) ) continue;
                        d.add(tt);
                    }
                }
                recyclerView.updateListView(d);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

//        getDataCacheList(AppConstant.SP_CACHEKEY_TAXTYPE, new CommonCallback<List<Taxtype>>() {
//            @Override
//            public void onSuccess(List<Taxtype> data) {
//                List<Taxtype> d = data;
//                if(key != null){
//                    d = new ArrayList<>();
//                    for(Taxtype tt : data){
//                        if( ! tt.name.toLowerCase().contains(key.toLowerCase()) ) continue;
//                        d.add(tt);
//                    }
//                }
//                recyclerView.updateListView(d);
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });

    }

    private void clickItem(Taxtype taxtype){
        Intent itn = new Intent();
        itn.putExtra(AppConstant.ITN_TAXTYPELISTHOME_TAXTYPE, taxtype);
        setResult(RESULT_OK, itn);
        finish();
    }
}
