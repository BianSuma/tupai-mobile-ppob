package com.pajakku.tupaimobile.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.PaymentActivity;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.PaymentPrice;
import com.pajakku.tupaimobile.component.TaxWPHeader;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class PaymentCCFragment extends Fragment {
    public static final int VIEW_PAGER_CODE = 2;

//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

//    private String mParam1;
//    private String mParam2;

    private TaxWPHeader taxWPHeader;
    private PaymentPrice paymentPrice;

    private PaymentActivity mListener;

    public PaymentCCFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_payment_cc, container, false);

        final ViewPager pager = null;

        AppActionBar actionBar = v.findViewById(R.id.paymentcc_appactionbar);
        actionBar.init(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(PaymentMethodFragment.VIEW_PAGER_CODE);
            }
        }, null);

        if((taxWPHeader = v.findViewById(R.id.paymentcc_taxwpheader)) == null) throw new RuntimeException();
        if((paymentPrice = v.findViewById(R.id.paymentcc_paymentprice)) == null) throw new RuntimeException();

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PaymentActivity) {
            mListener = (PaymentActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void init(String productName, long price, long pricePay){

//        switch (mListener.getMode()){
//            case PaymentActivity.MODE_PAY_TAX:
//                initPayTax(productName, price, pricePay);
//                break;
//            default:
//                initTopUp(productName, price, pricePay);
//        }
    }

    private void initTopUp(String productName, long price, long pricePay){
        taxWPHeader.setVisibility(View.GONE);

        paymentPrice.initTopupCC(productName, price, pricePay);
    }

    private void initPayTax(String productName, long price, long pricePay){
//        taxWPHeader.setCallback(R.drawable.fraghome_griditem_pph21, "Nanang Ahmad", "3242343243242", "PPh 21", "4211121", "300", "Agt-Agt 2018", 400000, 0);
        taxWPHeader.init(R.drawable.ic_pph, "Nanang Ahmad", "3242343243242", "PPh 21", "4211121", "300", "Agt-Agt 2018");
        taxWPHeader.setVisibility(View.VISIBLE);

        paymentPrice.initPayTaxCC(productName, price, pricePay);
    }
}
