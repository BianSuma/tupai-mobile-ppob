package com.pajakku.tupaimobile.activity;

import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.reflect.TypeToken;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.adapter.BaseListAdapter;
import com.pajakku.tupaimobile.adapter.list.TransLogAdapter;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.ListMultiSelect;
import com.pajakku.tupaimobile.model.Transactionlog;
import com.pajakku.tupaimobile.model.dto.request.RequestFinnet;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.util.HttpCallbackInterface;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class TransactionLogActivity extends RepositoryActivity {

    private ListMultiSelect listMultiSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_log);

        AppActionBar appActionBar = findViewById(R.id.translog_appactionbar);
        appActionBar.setBackFinish(this);
        appActionBar.setRightMenu(new AppActionBar.AppListener() {
            @Override
            public int menuResource() {
                return R.menu.translog_rightmenu;
            }

            @Override
            public void onClickRightMenu(int id) {
                fetchData(1);
            }
        });

        BaseListAdapter adapter = new TransLogAdapter(this);

        SwipeRefreshLayout swipyRefreshLayout = findViewById(R.id.translog_swipyrefreshlayout);
        listMultiSelect = findViewById(R.id.translog_listmultiselect);
        listMultiSelect.init(swipyRefreshLayout, adapter, new ListMultiSelect.AppListener<Transactionlog>() {
            @Override
            public void onClick(Transactionlog d) {
                clickItem(d);
            }

            @Override
            public void onSwipeLoadData(long page) {
                fetchData(page);
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();

        // TODO: @prod
        getDataCacheList(AppConstant.SP_CACHEKEY_TRANSLOG, new CommonCallback<List<Transactionlog>>() {
            @Override
            public void onSuccess(List<Transactionlog> data) {
                listMultiSelect.updateListView(data);
            }
        });

        // TODO: @test
//        List<Transactionlog> list = new ArrayList<>();
//        Transactionlog tl;
//        for(int i=0; i<10; i++){
//            int stat = Transactionlog.TRANSSTATUS_START;
//            if(i % 2 == 0) stat = Transactionlog.TRANSSTATUS_SUCCESS_FRAME_PAYMENT;
//            if(i % 3 == 0) stat = Transactionlog.TRANSSTATUS_FAIL_FRAMEPAYMENT;
//
//            tl = new Transactionlog();
//            tl.id = (i + 1);
//            tl.transType = Transactionlog.TRANSTYPE_PAYSSP;
//            tl.billNumber = "34279847829494723";
//            tl.amount = "6500";
//            tl.updatedAt = "2019-02-28 17:19:00";
//            tl.transStatus = stat;
//            list.add(tl);
//        }
//        listMultiSelect.updateListView(list);

    }

    private void fetchData(final long page){

//        final RequestFinnet rf = new RequestFinnet();
//        rf.requestType = RequestFinnet.REQUESTTYPE_GETTRANSACTIONLOG;
//
//        requestHttpStream(R.string.progressdialog_translog, true, AppConstant.SP_CACHEKEY_TRANSLOG, new TypeToken<List<Transactionlog>>() {
//        }.getType(), true, new HttpCallbackInterface<Transactionlog,Transactionlog>() {
//            @Override
//            public Call<Transactionlog> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
//                return null;
//            }
//
//            @Override
//            public Call<ResponseBody> requestMethodStream(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
//                return httpService.getTransactionLog(bearerAuth, subscriptionId, page, AppConstant.LIST_SIZE, rf);
//            }
//
//            @Override
//            public void onSuccess(Transactionlog response) {
//                // nothing
//            }
//
//            @Override
//            public void onSuccessStream(List<Transactionlog> response) {
//                listMultiSelect.updateListView( response );
//            }
//
//            @Override
//            public boolean onFail(ResponseError error) {
//                return true;
//            }
//        });
    }


    private void clickItem(Transactionlog d){

    }
}
