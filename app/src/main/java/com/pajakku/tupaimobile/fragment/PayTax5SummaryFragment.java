package com.pajakku.tupaimobile.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.PayTaxActivity;
import com.pajakku.tupaimobile.api.ApiReqBilling;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.SSPDetailRowInfo;
import com.pajakku.tupaimobile.component.TaxWPHeader;
import com.pajakku.tupaimobile.model.Kjs;
import com.pajakku.tupaimobile.model.Taxtype;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.request.RequestSsp;
import com.pajakku.tupaimobile.model.dto.WrappedSsp;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.util.HttpCallbackInterfaceSimple;
import com.pajakku.tupaimobile.util.Utility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayTax5SummaryFragment extends Fragment {
    public static final int VIEW_PAGER_CODE = 5;

//    private NonSwipeableViewPager pager;

    private PayTaxActivity payTaxActivity;

    private TaxWPHeader taxWPHeader;

    private SSPDetailRowInfo rowNpwp;
    private SSPDetailRowInfo rowName;
    private SSPDetailRowInfo rowAddress;
    private SSPDetailRowInfo rowKapkjs;
    private SSPDetailRowInfo rowTaxPeriod;
    private SSPDetailRowInfo rowSetor;
    private SSPDetailRowInfo rowNoSK;
    private SSPDetailRowInfo rowNOP;
    private SSPDetailRowInfo rowNpwpPenyetor;

    private Button btnSave;

    private View invisView;

    public PayTax5SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pay_tax_summary, container, false);

        invisView = view.findViewById(R.id.paytaxsummary_qhelp_invis);

//        pager = getActivity().findViewById(R.id.paytax_pager);

        AppActionBar actionBar = view.findViewById(R.id.paytaxsummary_actionbar);
        actionBar.init(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBack();
            }
        }, new AppActionBar.AppListener() {
            @Override
            public int menuResource() {
                return R.menu.paytaxsummary_rightmenu;
            }

            @Override
            public void onClickRightMenu(int id) {
                clickRightMenu(id);
            }
        });

        if((taxWPHeader = view.findViewById(R.id.paytaxsummary_taxwpheader)) == null) throw new RuntimeException();

        rowNpwp = view.findViewById(R.id.paypphsummary_label_row_npwp);
        rowName = view.findViewById(R.id.paypphsummary_label_row_name);
        rowAddress = view.findViewById(R.id.paypphsummary_label_row_address);
        rowKapkjs = view.findViewById(R.id.paypphsummary_label_row_kapkjs);
        rowTaxPeriod = view.findViewById(R.id.paypphsummary_label_row_taxperiod);
        rowSetor = view.findViewById(R.id.paypphsummary_label_row_setor);
        rowNoSK = view.findViewById(R.id.paypphsummary_label_row_nosk);
        rowNOP = view.findViewById(R.id.paypphsummary_label_row_nop);
        rowNpwpPenyetor = view.findViewById(R.id.paypphsummary_label_row_npwppenyetor);

        btnSave = view.findViewById(R.id.paypphsummary_btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSave();
            }
        });

        init();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PayTaxActivity) {
            payTaxActivity = (PayTaxActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement BaseActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        payTaxActivity = null;
    }

    public void init() {

        WrappedSsp wrappedSsp = payTaxActivity.wrappedSsp;
        Taxtype taxtype = wrappedSsp.fetchDefaultTaxType();
        Kjs kjs = wrappedSsp.fetchDefaultTaxSlipType();

        taxWPHeader.setIcon(wrappedSsp.fetchDefaultTaxType().fetchIcon(), wrappedSsp.fetchDefaultTaxType().code);
        taxWPHeader.setHeadName(wrappedSsp.fetchDefaultName());
        taxWPHeader.setNpwp(wrappedSsp.npwpNotNull());
        taxWPHeader.setTaxAccountPeriod(wrappedSsp.fetchDefaultTaxType(), wrappedSsp.fetchDefaultTaxSlipType(), wrappedSsp.fetchTaxPeriod(getContext()));

        rowNpwp.init(Utility.toPrettyNpwp(wrappedSsp.npwpNotNull()));
        rowName.init(wrappedSsp.fetchDefaultName());
        rowAddress.init(wrappedSsp.fetchDefaultAddress());
        rowKapkjs.init(taxtype.code + " - " + taxtype.name + " | " + kjs.code + " - " + kjs.name);
        rowTaxPeriod.init(wrappedSsp.fetchTaxPeriod(getContext()));
        rowSetor.init(Utility.toMoney(true, wrappedSsp.amount));
        if (wrappedSsp.noSk != null){
            if(wrappedSsp.noSk.length() > 0){
                rowNoSK.init( Utility.toPrettyNoSK(wrappedSsp.noSk) );
                rowNoSK.setVisibility(View.VISIBLE);
            }else{
                rowNoSK.setVisibility(View.GONE);
            }
        }else{
            rowNoSK.setVisibility(View.GONE);
        }
        if(wrappedSsp.nop != null){
            if(wrappedSsp.nop.length() > 0){
                rowNOP.init( Utility.toPrettyNOP(wrappedSsp.nop) );
                rowNOP.setVisibility(View.VISIBLE);
            }else{
                rowNOP.setVisibility(View.GONE);
            }
        }else{
            rowNOP.setVisibility(View.GONE);
        }
        if(wrappedSsp.npwpNotNull().equals(wrappedSsp.npwpPenyetorNotNull())){
            rowNpwpPenyetor.setVisibility(View.GONE);
        }else{
            rowNpwpPenyetor.init( Utility.toPrettyNpwp(wrappedSsp.npwpPenyetorNotNull()) );
            rowNpwpPenyetor.setVisibility(View.VISIBLE);
        }
    }

    private void clickRightMenu(int id){
        switch (id){
            case R.id.paytaxsummary_btnmenu_quickhelp:
                clickQuickHelp();
                break;
        }
    }

    private void clickBack(){
        Kjs kjs = payTaxActivity.wrappedSsp.taxSlipType;
        if(kjs.noSkActive || kjs.nopActive || kjs.subjekPajakActive) {
            payTaxActivity.pager.setCurrentItem(PayTax4SkNopFragment.VIEW_PAGER_CODE);
        }else{
            payTaxActivity.pager.setCurrentItem(PayTax3SetorFragment.VIEW_PAGER_CODE);
        }
    }

    private void clickQuickHelp(){

        List<BubbleShowCaseBuilder> listSc = new ArrayList<>();
        BubbleShowCaseBuilder sc;

        sc = Utility.createShowCase(getActivity(), getString(R.string.paytaxsummary_qhelptitle_list), getString(R.string.paytaxsummary_qhelpbody_list), (View)invisView.getParent(), invisView);
        if(sc != null){
            listSc.add(sc);
        }
        sc = Utility.createShowCase(getActivity(), getString(R.string.paytaxsummary_qhelptitle_btnsave), getString(R.string.paytaxsummary_qhelpbody_btnsave), (View)btnSave.getParent(), btnSave);
        if(sc != null){
            listSc.add(sc);
        }

        BubbleShowCaseSequence scs = new BubbleShowCaseSequence();
        scs.addShowCases(listSc);
        scs.show();

    }

    private void clickSave(){

        // TODO: @prod
        payTaxActivity.saveFCMTokenToServerValidate(new CommonCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                createSsp(new CommonCallback<RequestSsp>() {
                    @Override
                    public void onSuccess(RequestSsp data) {
                        finishCreateSSP();
                    }
                });
            }
        });

        // TODO: @test
//        WrappedSsp ws = payTaxActivity.wrappedSsp;
//        List<ResponseSsp> respSspList = new ArrayList<>();
//        ResponseSsp resSsp = new ResponseSsp();
//        resSsp.id = System.currentTimeMillis();
//        resSsp.name = ws.name;
//        resSsp.npwp = ws.npwp;
//        resSsp.address = ws.address;
//        resSsp.amount = ws.amount;
//        resSsp.referenceNo = Long.toString(resSsp.id);
//
//        BillingDTO billingDTO = new BillingDTO();
//        billingDTO.responseCode = TaxSlipResponse.RESPONSECODE_OK;
//        billingDTO.idBilling = Long.toString(resSsp.id);
//        billingDTO.expiredDate = "2022-12-08T16:21:24.307692";
//        resSsp.billing = billingDTO;
//
//        TaxSlipResponse tsr = new TaxSlipResponse();
//        tsr.idbillingPajakku = billingDTO.idBilling;
//        resSsp.taxSlipResponse = tsr;
//
//        resSsp.month1 = ws.month1;
//        resSsp.month2 = ws.month2;
//        resSsp.year = ws.year;
//
//        Taxtype tt = new Taxtype();
//        tt.code = ws.taxType.code;
//        tt.name = ws.taxType.name;
//        resSsp.taxType = tt;
//
//        Kjs kjs = new Kjs();
//        kjs.code = ws.taxSlipType.code;
//        kjs.name = ws.taxSlipType.name;
//        resSsp.taxSlipType = kjs;
//
//        AuditDTO auditDTO = new AuditDTO();
//        auditDTO.updatedAt = "2019-07-11";
//        resSsp.audit = auditDTO;
//        respSspList.add(resSsp);
//        payTaxActivity.queryDeleteAllAndSaveSSP(AppConstant.SP_CACHEKEY_SSPUNPAID, respSspList, new CommonCallback<Void>() {
//            @Override
//            public void onSuccess(Void data) {
//                finishCreateSSP();
//            }
//        });

    }

    private void finishCreateSSP(){
        payTaxActivity.setResult(Activity.RESULT_OK, new Intent());
        payTaxActivity.finish();
    }

    private void createSsp(final CommonCallback<RequestSsp> listenerHttpResponse){

        payTaxActivity.wrappedSsp.generateIdBilling = true;
        WrappedSsp rs = payTaxActivity.wrappedSsp;
        final RequestSsp requestSsp = rs.toRequestSsp();

        ApiReqBilling.sspAdd(payTaxActivity, new RequestParamConfig(), requestSsp, new CommonCallback<RequestSsp>() {
            @Override
            public void onSuccess(final RequestSsp reqSsp) {
                payTaxActivity.wrappedSsp.id = reqSsp.resultId;
                payTaxActivity.wrappedSsp.isSave = true;

                payTaxActivity.removeCache(AppConstant.SP_CACHEKEY_SSPUNPAID, true, new CommonCallback<Void>() {
                    @Override
                    public void onSuccess(Void data) {
                        listenerHttpResponse.onSuccess(reqSsp);
                    }
                });
            }
        });

//        payTaxActivity.requestHttpSimple(true, R.string.progressdialog_createssp, true,
//                null, true, new HttpCallbackInterfaceSimple<RequestSsp>() {
//            @Override
//            public Call<RequestSsp> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
//                return httpService.createSsp(bearerAuth, companyId, subscriptionId, requestSsp);
//            }
//
//            @Override
//            public void onSuccess(final RequestSsp response) {
//                payTaxActivity.wrappedSsp.id = response.resultId;
//                payTaxActivity.wrappedSsp.isSave = true;
//
//                payTaxActivity.removeCache(AppConstant.SP_CACHEKEY_SSPUNPAID, true, new CommonCallback<Void>() {
//                    @Override
//                    public void onSuccess(Void data) {
//                        listenerHttpResponse.onSuccess(response);
//                    }
//                });
//            }
//
//            @Override
//            public boolean onFail(ResponseError error) {
//                return true;
//            }
//        });
    }


}
