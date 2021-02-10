package com.pajakku.tupaimobile.fragment;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elconfidencial.bubbleshowcase.BubbleShowCase;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.PayTaxActivity;
import com.pajakku.tupaimobile.api.ApiCallBilling;
import com.pajakku.tupaimobile.api.ApiReqBilling;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.AppRecyclerView;
import com.pajakku.tupaimobile.component.QuickFindPanel;
import com.pajakku.tupaimobile.component.TaxWPHeader;
import com.pajakku.tupaimobile.ifc.SuccessFailCallback;
import com.pajakku.tupaimobile.model.Kjs;
import com.pajakku.tupaimobile.model.Taxtype;
import com.pajakku.tupaimobile.model.dto.ModelMultiSelect;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.TaxtypeAlias;
import com.pajakku.tupaimobile.model.dto.billing.RespKjs;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayTax0KJSFragment extends Fragment {
    public static final int VIEW_PAGER_CODE = 0;

    private PayTaxActivity payTaxActivity;
    private TaxWPHeader taxWPHeader;

    private QuickFindPanel quickFindPanel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AppRecyclerView recyclerView;

    private RelativeLayout recentLayoutWrap;
    private RelativeLayout recentLayout;
//    private TextView tvRecentTitle;

    private View invisView;

    public PayTax0KJSFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_paytax0_kjs, container, false);

        invisView = view.findViewById(R.id.paytaxkjs_qhelp_invis);

        AppActionBar actionBar = view.findViewById(R.id.paytax0kjs_appactionbar);
        actionBar.init(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayTax0KJSFragment.this.getActivity().finish();
            }
        }, new AppActionBar.AppListener() {
            @Override
            public int menuResource() {
                return R.menu.paytaxkjs_rightmenu;
            }

            @Override
            public void onClickRightMenu(int id) {
                clickRightMenu(id);
            }
        } );

        taxWPHeader = view.findViewById(R.id.paytaxkjs_taxwpheader);

        quickFindPanel = view.findViewById(R.id.paytaxkjs_listfindpanel);
        quickFindPanel.init(new CommonCallback<String>() {
            @Override
            public void onSuccess(String data) {
                filterData(data);
            }
        });

        recentLayoutWrap = view.findViewById(R.id.paytax0kjs_recent_layoutwrap);
//        tvRecentTitle = view.findViewById(R.id.paytax0kjs_label_recenttitle);
        recentLayout = view.findViewById(R.id.paytax0kjs_recent_layout);
        recentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recentCode = payTaxActivity.getSpString(AppConstant.SP_RECENT_KJS);
                if(recentCode == null) return;

                List<Kjs> listKjs = ModelMultiSelect.toKjs( recyclerView.getData() );
                Kjs foundKjs = Kjs.findOneByCode(listKjs, recentCode);
                if(foundKjs != null) clickKJSItem(foundKjs);

//                payTaxActivity.kjsFindOneByCode(recentCode, new CommonCallback<Kjs>() {
//                    @Override
//                    public void onSuccess(Kjs data) {
//                        if(data == null) return;
//                        clickKJSItem(data);
//                    }
//                });

            }
        });

        swipeRefreshLayout = view.findViewById(R.id.paytaxkjs_swiperefreshlayout);
        recyclerView = view.findViewById(R.id.paytaxkjs_recyclerview);
        recyclerView.init(false, swipeRefreshLayout, new AppRecyclerView.AppCallback<Kjs>() {
            @Override
            public void onClick(Kjs d) {
                clickKJSItem(d);
            }

            @Override
            public void onRefresh() {
                fetchData(0, 1);
            }

            @Override
            public void onLoadMore(long p) {
                // nothing
            }
        });

        init();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();

        if( payTaxActivity.getSpBool(AppConstant.SP_BACKMARK_PAYTAXBEGIN) ) {
            payTaxActivity.getDataCacheList(AppConstant.SP_CACHEKEY_STREAMKJS, new CommonCallback<List<Kjs>>() {
                @Override
                public void onSuccess(List<Kjs> data) {
                    updateListViewWithFilterUKM(data);
                }
            });
            quickFindPanel.setText("");
        }else{
            payTaxActivity.setSpBool(AppConstant.SP_BACKMARK_PAYTAXBEGIN);

            // TODO: @prod
            fetchData(0, 1);

            // TODO: @test
//            List<Kjs> list = new ArrayList<>();
//            Kjs kjs = new Kjs();
//            kjs.id = 148;
//            kjs.code = "420";
//            kjs.name = "Peredaran Bruto Tertentu";
//            kjs.nopActive = false;
//            kjs.noSkActive = false;
//            kjs.subjekPajakActive = false;
//            kjs.month2Active = false;
//            list.add( kjs );
//            payTaxActivity.setCache(AppConstant.SP_CACHEKEY_STREAMKJS, list);
//            updateListViewWithFilterUKM(list);

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PayTaxActivity) {
            payTaxActivity = (PayTaxActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PayTaxActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        payTaxActivity = null;
    }

    public void init(){

        Taxtype taxType  = payTaxActivity.wrappedSsp.fetchDefaultTaxType();
        taxWPHeader.setIcon(taxType.fetchIcon(), taxType.code);
        taxWPHeader.setHeadName(taxType.name);
        taxWPHeader.setKAP(taxType.code);

    }

    public void updateListViewWithFilterUKM(List<Kjs> data){
        List<Kjs> list = data;

        // filter for UKM
//        List<Kjs> list;
//        if(payTaxActivity.wrappedSsp.wpType == TaxtypeAlias.WPTYPE_UKM) {
//            list = new ArrayList<>();
//            for (Kjs k : data) {
//                if (k.code.equals("420")) {
//                    list.add(k);
//                    break;
//                }
//            }
//        }else{
//            list = data;
//        }

        recyclerView.updateListView(list);

        String recentCode = payTaxActivity.getSpString(AppConstant.SP_RECENT_KJS);
        if(list != null && recentCode != null){
            Kjs recentKjs = null;
            for(Kjs k : list){
                if(k.code.equals(recentCode) ){
                    recentKjs = k;
                    break;
                }
            }
            if(recentKjs != null){
                TextView tv;
                tv = recentLayout.findViewById(R.id.paytax0kjs_recent_code);
                tv.setText(recentKjs.code);
                tv = recentLayout.findViewById(R.id.paytax0kjs_recent_name);
                tv.setText(recentKjs.name);
                recentLayoutWrap.setVisibility(View.VISIBLE);
            }else{
                recentLayoutWrap.setVisibility(View.GONE);
            }
        }else{
            recentLayoutWrap.setVisibility(View.GONE);
        }

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                showQuickHelp();
            }
        });
    }

    public void showProgressBar(){
        recyclerView.isLoading = true;
        swipeRefreshLayout.setRefreshing(true);
    }

    public void hideProgressBar(){
        recyclerView.isLoading = false;
        swipeRefreshLayout.setRefreshing(false);
    }

    private void fetchData(int progressText, long page){
        showProgressBar();
        Taxtype taxType  = payTaxActivity.wrappedSsp.taxType;

//        payTaxActivity.requestTaxSlipTypeList(progressText, taxType.id, page, new SuccessFailCallback<List<Kjs>, ResponseError>() {
//            @Override
//            public void onSuccess(List<Kjs> data) {
//
//                Fragment f = payTaxActivity.getFragment(PayTax0KJSFragment.VIEW_PAGER_CODE);
//                if(f == null) return;
//                ((PayTax0KJSFragment)f).updateListViewWithFilterUKM(data);
//                ((PayTax0KJSFragment)f).hideProgressBar();
//            }
//            @Override
//            public boolean onFail(ResponseError error){
//                Fragment f = payTaxActivity.getFragment(PayTax0KJSFragment.VIEW_PAGER_CODE);
//                if(f != null){
//                    ((PayTax0KJSFragment)f).hideProgressBar();
//                }
//                return true;
//            }
//        });

        ApiReqBilling.findKjsByKjpId(payTaxActivity, new RequestParamConfig(), taxType.idStr, new SuccessFailCallback<List<RespKjs>, ResponseDTO>() {
            @Override
            public void onSuccess(List<RespKjs> data) {
                Fragment f = payTaxActivity.getFragment(PayTax0KJSFragment.VIEW_PAGER_CODE);
                if(f == null) return;
                ((PayTax0KJSFragment)f).updateListViewWithFilterUKM( RespKjs.toKjs(data) );
                ((PayTax0KJSFragment)f).hideProgressBar();
            }

            @Override
            public void onFail(ResponseDTO data) {
                Fragment f = payTaxActivity.getFragment(PayTax0KJSFragment.VIEW_PAGER_CODE);
                if(f != null){
                    ((PayTax0KJSFragment)f).hideProgressBar();
                }
            }
        });

//        ApiReqBilling.getKjs(payTaxActivity, new RequestParamConfig(), taxType.idStr, new CommonCallback<List<RespKjs>>() {
//            @Override
//            public void onSuccess(List<RespKjs> data) {
//                Fragment f = payTaxActivity.getFragment(PayTax0KJSFragment.VIEW_PAGER_CODE);
//                if(f == null) return;
//                ((PayTax0KJSFragment)f).updateListViewWithFilterUKM( RespKjs.toKjs(data) );
//                ((PayTax0KJSFragment)f).hideProgressBar();
//            }
//        });
    }

    private void filterData(final String key){
        payTaxActivity.getDataCacheList(AppConstant.SP_CACHEKEY_STREAMKJS, new CommonCallback<List<Kjs>>() {
            @Override
            public void onSuccess(List<Kjs> data) {
                List<Kjs> d = data;
                if(key != null){
                    d = new ArrayList<>();
                    for(Kjs kjs : data){
                        if( ! kjs.name.toLowerCase().contains(key.toLowerCase()) ) continue;
                        d.add(kjs);
                    }
                }
                updateListViewWithFilterUKM(d);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void clickRightMenu(int id){
        switch (id){
            case R.id.paytaxkjs_btnmenu_quickhelp:
                clickQuickHelp();
                break;
        }
    }

    public void showQuickHelp(){
        if(payTaxActivity.pager.getCurrentItem() != PayTax0KJSFragment.VIEW_PAGER_CODE) return;
        boolean b = payTaxActivity.getSpBool(AppConstant.SP_QH_KJS);
        if(b) return;
        clickQuickHelp();
        payTaxActivity.setSpBool(AppConstant.SP_QH_KJS);
    }

    private void clickQuickHelp(){

        List<BubbleShowCaseBuilder> listSc = new ArrayList<>();
        BubbleShowCaseBuilder sc;

        sc = Utility.createShowCase(getActivity(), getString(R.string.paytaxkjs_qhelptitle_filter), getString(R.string.paytaxkjs_qhelpbody_filter), (View)quickFindPanel.getParent(), quickFindPanel);
        if(sc != null){
            listSc.add(sc);
        }
        sc = Utility.createShowCase(getActivity(), getString(R.string.paytaxkjs_qhelptitle_kjs), getString(R.string.paytaxkjs_qhelpbody_kjs), (View)invisView.getParent(), invisView);
        if(sc != null){
            sc.arrowPosition(BubbleShowCase.ArrowPosition.BOTTOM);
            listSc.add(sc);
        }

        BubbleShowCaseSequence scs = new BubbleShowCaseSequence();
        scs.addShowCases(listSc);
        scs.show();

    }

    private void clickKJSItem(Kjs kjs){

        payTaxActivity.setSpString(AppConstant.SP_RECENT_KJS, kjs.code);

        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(quickFindPanel.inpFind.getWindowToken(), 0);

        payTaxActivity.wrappedSsp.taxSlipType = kjs;

        payTaxActivity.pager.setCurrentItem(PayTax1WPFragment.VIEW_PAGER_CODE);

        payTaxActivity.invokeFragmentInit(PayTax1WPFragment.VIEW_PAGER_CODE);
    }


}
