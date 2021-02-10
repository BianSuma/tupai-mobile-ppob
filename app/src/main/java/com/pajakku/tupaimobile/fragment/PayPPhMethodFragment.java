package com.pajakku.tupaimobile.fragment;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.PayTaxActivity;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.NonSwipeableViewPager;
import com.pajakku.tupaimobile.component.TaxWPHeader;
import com.pajakku.tupaimobile.component.PayMethodButton;

/**
 * A simple {@link Fragment} subclass.
 */
@Deprecated
public class PayPPhMethodFragment extends Fragment {
    public static final int VIEW_PAGER_CODE = 6;

    private NonSwipeableViewPager pager;

    private PayTaxActivity fragmentListener;

    public PayPPhMethodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pay_pph_method, container, false);

        pager = getActivity().findViewById(R.id.paytax_pager);

        AppActionBar actionBar = view.findViewById(R.id.paypphmethod_actionbar);
        actionBar.init(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(PayTax5SummaryFragment.VIEW_PAGER_CODE);
            }
        }, null);

        TaxWPHeader twhTop = view.findViewById(R.id.paypphmethod_twh_top);
        twhTop.init(R.drawable.ic_pph, "Angga Budiman", "66438645985995", "PPh 21", "411121", "300", "Agt-Agt 2018");

        RelativeLayout layoutBtnTupai = view.findViewById(R.id.paypphmethod_layoutbtn_saldo);
        layoutBtnTupai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTupaiSaldo();
            }
        });

        TextView tvVa = view.findViewById(R.id.paypphmethod_var_va);
        tvVa.setText( getString(R.string.paypphmethod_label_va, "342749832498324") );
        TextView tvAmount = view.findViewById(R.id.paypphmethod_var_amount);
        tvAmount.setText("Rp 1.000.000");
        TextView tvPayamount = view.findViewById(R.id.paypphmethod_var_payamount);
        tvPayamount.setText(getString(R.string.paypphmethod_label_payamount,"Rp 500.000"));

        PayMethodButton btnPayMethod;

        btnPayMethod = view.findViewById(R.id.paypphmethod_btn_transfermandiri);
        btnPayMethod.init(51000);
        btnPayMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTransferMandiri();
            }
        });
        btnPayMethod = view.findViewById(R.id.paypphmethod_btn_transferbca);
        btnPayMethod.init(51000);
        btnPayMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btnPayMethod = view.findViewById(R.id.paypphmethod_btn_transferbni);
        btnPayMethod.init(51000);
        btnPayMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btnPayMethod = view.findViewById(R.id.paypphmethod_btn_ccvisafriend);
        btnPayMethod.init(51000);
        btnPayMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PayTaxActivity) {
            fragmentListener = (PayTaxActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentListener = null;
    }

    private void clickTupaiSaldo(){
//        pager.setCurrentItem(PayPPhResultFragment.VIEW_PAGER_CODE);
//        PayPPhResultFragment frag = (PayPPhResultFragment) fragmentListener.invokeFragmentInit(PayPPhResultFragment.VIEW_PAGER_CODE);
//        frag.setCallback(R.string.paypphresult_label_msgsuccess);
    }

    private void clickTransferMandiri(){
//        pager.setCurrentItem(PayPPhResultFragment.VIEW_PAGER_CODE);
//        PayPPhResultFragment frag = (PayPPhResultFragment) fragmentListener.invokeFragmentInit(PayPPhResultFragment.VIEW_PAGER_CODE);
//        frag.setCallback(R.string.paypphresult_label_msgfail);
    }

}
