package com.pajakku.tupaimobile.fragment;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.PaymentActivity;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.FoldTextBtn;
import com.pajakku.tupaimobile.component.PayMethodButton;
import com.pajakku.tupaimobile.component.TaxWPHeader;
import com.pajakku.tupaimobile.component.TupaiSaldoBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentMethodFragment extends Fragment {
    public static final int VIEW_PAGER_CODE = 0;

    private AppActionBar actionBar;

    private TupaiSaldoBar tupaiSaldoBar;
    private TaxWPHeader taxWPHeader;

//    private TopupNominal topupNominal;

    private TextView categoryLabelTupaiSaldo;
    private PayMethodButton btnPayWithTupai;

    private FoldTextBtn foldTextBtn;
    private LinearLayout linearLayoutOtherPay;

    private PaymentActivity activityInterface;
    private ViewPager pager;

    public PaymentMethodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_payment_method, container, false);

//        pager = getActivity().findViewById(R.id.payment_pager);

        actionBar = v.findViewById(R.id.paymentmethod_actionbar);
        actionBar.init(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentMethodFragment.this.getActivity().finish();
            }
        }, null);

        if((tupaiSaldoBar = v.findViewById(R.id.paymentmethod_tupaisaldobar)) == null) throw new RuntimeException();

        if((taxWPHeader = v.findViewById(R.id.paymentmethod_taxwpheader)) == null) throw new RuntimeException();

//        if((topupNominal = v.findViewById(R.id.paymentmethod_topupnominal)) == null) throw new RuntimeException();

        if((categoryLabelTupaiSaldo = v.findViewById(R.id.paymentmethod_categorylabel_tupaisaldo)) == null) throw new RuntimeException();
        if((btnPayWithTupai = v.findViewById(R.id.paymentmethod_btn_paywithtupai)) == null) throw new RuntimeException();

        if((foldTextBtn = v.findViewById(R.id.paymentmethod_btn_otherpay)) == null) throw new RuntimeException();

        if((linearLayoutOtherPay = v.findViewById(R.id.paymentmethod_layout_otherpay)) == null) throw new RuntimeException();

        PayMethodButton btnPayMethod;

        btnPayMethod = v.findViewById(R.id.paymentmethod_btn_transfermandiri);
        btnPayMethod.init(null, 0, 51000, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTransfer(R.id.paymentmethod_btn_transfermandiri);
            }
        });

        btnPayMethod = v.findViewById(R.id.paymentmethod_btn_transferbca);
        btnPayMethod.init(null, 0, 51000, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTransfer(R.id.paymentmethod_btn_transferbca);
            }
        });

        btnPayMethod = v.findViewById(R.id.paymentmethod_btn_transferbni);
        btnPayMethod.init(null, 0, 51000, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTransfer(R.id.paymentmethod_btn_transferbni);
            }
        });

        btnPayMethod = v.findViewById(R.id.paymentmethod_btn_ccvisafriend);
        btnPayMethod.init(null, 0, 51000, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCC();
            }
        });

        onCreateViewInit();

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PaymentActivity) {
            activityInterface = (PaymentActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PaymentActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activityInterface = null;
    }

    private void onCreateViewInit(){
//        switch (activityInterface.getMode()){
//            case PaymentActivity.MODE_PAY_TAX:
//                onCreateViewPayTax();
//                break;
//            default:
//                onCreateViewTopUp();
//        }
    }

    private void onCreateViewTopUp(){
        actionBar.setTitle(R.string.paymentmethod_page_titletopup);

        tupaiSaldoBar.init("4329743274834422", 100000);
        tupaiSaldoBar.setVisibility(View.VISIBLE);

        taxWPHeader.setVisibility(View.GONE);

//        topupNominal.setCallback(50000);
//        topupNominal.setVisibility(View.VISIBLE);

        categoryLabelTupaiSaldo.setVisibility(View.GONE);
        btnPayWithTupai.setVisibility(View.GONE);

        foldTextBtn.setVisibility(View.GONE);

        linearLayoutOtherPay.setVisibility(View.VISIBLE);
    }
    private void onCreateViewPayTax(){
        actionBar.setTitle(R.string.paymentmethod_page_titlepaytax);

        tupaiSaldoBar.setVisibility(View.GONE);

        taxWPHeader.init(R.drawable.ic_pph, "Angga Budiman", "66438645985995", "PPh 21", "411121", "300", "Agt-Agt 2018");
        taxWPHeader.setVisibility(View.VISIBLE);

//        topupNominal.setVisibility(View.GONE);

        categoryLabelTupaiSaldo.setVisibility(View.VISIBLE);
        btnPayWithTupai.init("8384329473243", 10000000, 51000, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTupaiSaldo();
            }
        });
        btnPayWithTupai.setVisibility(View.VISIBLE);

        foldTextBtn.init(new FoldTextBtn.AppListener() {
            @Override
            public void onClick(boolean isOpen) {
                clickOtherPay(isOpen);
            }
        });
        foldTextBtn.setVisibility(View.VISIBLE);

        linearLayoutOtherPay.setVisibility(View.GONE);
    }

//    private void invokeFragmentInit(int fragCode){
//        for (Fragment f : getChildFragmentManager().getFragments()) {
//            if (f instanceof SSPUnpaidFragment && fragCode == SSPUnpaidFragment.VIEW_PAGER_CODE) {
//                SSPUnpaidFragment frag = (SSPUnpaidFragment) f;
//                frag.setVer();
//                return;
//            }
//            if (f instanceof SSPDoneFragment && fragCode == SSPDoneFragment.VIEW_PAGER_CODE) {
//                SSPDoneFragment frag = (SSPDoneFragment) f;
//                frag.setVer();
//                return;
//            }
//        }
//    }

    private void clickTupaiSaldo(){
        pager.setCurrentItem(PaymentResultFragment.VIEW_PAGER_CODE);
//        PaymentResultFragment frag = (PaymentResultFragment) activityInterface.getFragment(PaymentResultFragment.VIEW_PAGER_CODE);
//        frag.init(PaymentResultFragment.PAYMENT_SUCCESS);
    }

    private void clickOtherPay(boolean isOpen){
        if(isOpen){
            linearLayoutOtherPay.setVisibility(View.VISIBLE);
        }else{
            linearLayoutOtherPay.setVisibility(View.GONE);
        }
    }

    private void clickTransfer(int id){
//        PaymentPreDto paymentPreDto = activityInterface.getPaymentPreDto();
//
//        if( id == R.id.paymentmethod_btn_transferbni ) {
//            pager.setCurrentItem(PaymentResultFragment.VIEW_PAGER_CODE);
//            PaymentResultFragment frag = (PaymentResultFragment) activityInterface.getFragment(PaymentResultFragment.VIEW_PAGER_CODE);
//            frag.init(PaymentResultFragment.PAYMENT_FAIL);
//        }else{
//            pager.setCurrentItem(PaymentGuideFragment.VIEW_PAGER_CODE);
//            PaymentGuideFragment frag = (PaymentGuideFragment) activityInterface.getFragment(PaymentGuideFragment.VIEW_PAGER_CODE);
//            frag.init(id, paymentPreDto.product, paymentPreDto.price, paymentPreDto.pricePay);
//        }
    }

    private void clickCC(){
//        PaymentPreDto paymentPreDto = activityInterface.getPaymentPreDto();
//
//        pager.setCurrentItem(PaymentCCFragment.VIEW_PAGER_CODE);
//        PaymentCCFragment frag = (PaymentCCFragment) activityInterface.getFragment(PaymentCCFragment.VIEW_PAGER_CODE);
//        frag.init(paymentPreDto.product, paymentPreDto.price, paymentPreDto.pricePay);
    }

}
