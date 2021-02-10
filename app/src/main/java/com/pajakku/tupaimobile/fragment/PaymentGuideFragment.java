package com.pajakku.tupaimobile.fragment;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.PaymentActivity;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.NonSwipeableViewPager;
import com.pajakku.tupaimobile.component.PaymentPrice;
import com.pajakku.tupaimobile.component.TaxWPHeader;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentGuideFragment extends Fragment {
    public static final int VIEW_PAGER_CODE = 1;

    private PaymentActivity activityInterface;

    private PaymentPrice paymentPrice;
    private TaxWPHeader taxWPHeader;

    public PaymentGuideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_payment_guide, container, false);

        if((paymentPrice = v.findViewById(R.id.paymentguide_paymentprice)) == null) throw new RuntimeException();
        if((taxWPHeader = v.findViewById(R.id.paymentguide_taxwpheader)) == null) throw new RuntimeException();

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

    public void init(int transferId, String productName, long price, long priceTransfer){

        int pageTitle = 0;
        int resImg = 0;
        switch (transferId){
            case R.id.paymentmethod_btn_transfermandiri:
                pageTitle = R.string.paymentguide_page_titlemandiri;
                resImg = R.drawable.payguide_mandiri;
                break;
            case R.id.paymentmethod_btn_transferbca:
                pageTitle = R.string.paymentguide_page_titlebca;
                resImg = R.drawable.payguide_bca;
                break;
            case R.id.paymentmethod_btn_transferbni:
                pageTitle = R.string.paymentguide_page_titlebni;
                resImg = R.drawable.payguide_bni;
                break;
        }

        final NonSwipeableViewPager pager = null;

        AppActionBar actionBar = getView().findViewById(R.id.paymentguide_appactionbar);
        actionBar.init(pageTitle, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(PaymentMethodFragment.VIEW_PAGER_CODE);
            }
        }, null);

//        switch (activityInterface.getMode()){
//            case PaymentActivity.MODE_PAY_TAX:
//                initPayTax(productName, price, priceTransfer);
//                break;
//            default:
//                initTopUp(productName, price, priceTransfer);
//        }

        ImageView imgGuide = getView().findViewById(R.id.paymentguide_img_guide);
        imgGuide.setImageResource(resImg);
    }

    private void initTopUp(String productName, long price, long priceTransfer){
        taxWPHeader.setVisibility(View.GONE);

        paymentPrice.initTopupTransfer(productName, price, priceTransfer);
    }

    private void initPayTax(String productName, long price, long priceTransfer){
//        taxWPHeader.setCallback(R.drawable.fraghome_griditem_pph21, "Nanang Ahmad", "3242343243242", "PPh 21", "4211121", "300", "Agt-Agt 2018", 400000, 0);
        taxWPHeader.init(R.drawable.ic_pph, "Nanang Ahmad", "3242343243242", "PPh 21", "4211121", "300", "Agt-Agt 2018");
        taxWPHeader.setVisibility(View.VISIBLE);

        paymentPrice.initPayTaxTransfer(productName, price, priceTransfer);
    }

}
