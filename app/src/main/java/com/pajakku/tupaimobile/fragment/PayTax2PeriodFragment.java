package com.pajakku.tupaimobile.fragment;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.PayTaxActivity;
import com.pajakku.tupaimobile.adapter.TopupValueSpinAdapter;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.AppEditText;
import com.pajakku.tupaimobile.component.NonSwipeableViewPager;
import com.pajakku.tupaimobile.component.TaxWPHeader;
import com.pajakku.tupaimobile.model.dto.WrappedSsp;
import com.pajakku.tupaimobile.util.Utility;
import com.pajakku.tupaimobile.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayTax2PeriodFragment extends Fragment {
    public static final int VIEW_PAGER_CODE = 2;

    private static final String month2char[] = {"01","02","03","04","05","06","07","08","09","10","11","12"};

    private PayTaxActivity payTaxActivity;
    private TaxWPHeader taxWPHeader;
    private Spinner spinMonth0;
    private Spinner spinMonth1;
    private AppEditText inpYear;
    private Button btnNext;

    private Validator validator;

    public PayTax2PeriodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pay_tax_period, container, false);

        AppActionBar actionBar = view.findViewById(R.id.paypphperiod_actionbar);
        actionBar.init(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payTaxActivity.pager.setCurrentItem(PayTax1WPFragment.VIEW_PAGER_CODE);
            }
        }, new AppActionBar.AppListener() {
            @Override
            public int menuResource() {
                return R.menu.paytaxperiod_rightmenu;
            }

            @Override
            public void onClickRightMenu(int id) {
                clickRightMenu(id);
            }
        });

         if((taxWPHeader = view.findViewById(R.id.paytaxperiod_taxwpheader)) == null ) throw new RuntimeException();

        List<String> spinArr = new ArrayList<>();
        spinArr.add(getString(R.string.monthname_0));
        spinArr.add(getString(R.string.monthname_1));
        spinArr.add(getString(R.string.monthname_2));
        spinArr.add(getString(R.string.monthname_3));
        spinArr.add(getString(R.string.monthname_4));
        spinArr.add(getString(R.string.monthname_5));
        spinArr.add(getString(R.string.monthname_6));
        spinArr.add(getString(R.string.monthname_7));
        spinArr.add(getString(R.string.monthname_8));
        spinArr.add(getString(R.string.monthname_9));
        spinArr.add(getString(R.string.monthname_10));
        spinArr.add(getString(R.string.monthname_11));

        TopupValueSpinAdapter adapterMonth0 = new TopupValueSpinAdapter(getContext(), R.string.paypphperiod_hint_month0, R.layout.row_spin_item, spinArr);
        spinMonth0 = view.findViewById(R.id.paypphperiod_spin_month0);
        spinMonth0.setAdapter(adapterMonth0);

        TopupValueSpinAdapter adapterMonth1 = new TopupValueSpinAdapter(getContext(), R.string.paypphperiod_hint_month1, R.layout.row_spin_item, spinArr);
        spinMonth1 = view.findViewById(R.id.paypphperiod_spin_month1);
        spinMonth1.setAdapter(adapterMonth1);

        validateMonth(spinMonth0, spinMonth1);

        inpYear = view.findViewById(R.id.paypphperiod_inp_year);
        inpYear.constraintMandatory = true;
        inpYear.setConstraintNumber();

        btnNext = view.findViewById(R.id.paypphperiod_btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickNext();
            }
        });

        validator = new Validator();
        validator.addEdit(inpYear);

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
                    + " must implement PayTaxActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        payTaxActivity = null;
    }

    public void init(){

        WrappedSsp wrappedSsp = payTaxActivity.wrappedSsp;

        taxWPHeader.setIcon(wrappedSsp.fetchDefaultTaxType().fetchIcon(), wrappedSsp.fetchDefaultTaxType().code);
        taxWPHeader.setHeadName(wrappedSsp.fetchDefaultName());
        taxWPHeader.setNpwp(wrappedSsp.npwpNotNull());
        taxWPHeader.setTaxAccount(wrappedSsp.fetchDefaultTaxType(), wrappedSsp.fetchDefaultTaxSlipType());

        if(wrappedSsp.yearNotNull().length() != 0) inpYear.setText( wrappedSsp.yearNotNull() );
        else inpYear.setText( String.valueOf(Utility.currentYear()) );

        int intMonth0 = Utility.currentMonth();
        if( ! wrappedSsp.taxSlipType.fetchMonth1Active() ){
            spinMonth0.setEnabled(false);
            if( wrappedSsp.taxSlipType.month1Id != null ){
                intMonth0 = wrappedSsp.taxSlipType.month1Id-1;
            }
        }
        spinMonth0.setSelection(intMonth0, true);

        if( wrappedSsp.taxSlipType.fetchMonth2Active() ) {
            spinMonth1.setSelection(Utility.currentMonth(), true);
        }else{
            spinMonth1.setEnabled(false);
            if( wrappedSsp.taxSlipType.month2Id != null ) {
                spinMonth1.setSelection(wrappedSsp.taxSlipType.month2Id-1, true);
            }else{
                spinMonth0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        spinMonth1.setSelection(position, true);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spinMonth1.setSelection(intMonth0, true);
            }
        }



    }

    private void validateMonth(final Spinner spin0, final Spinner spin1){
        spin0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if( position > spin1.getSelectedItemPosition() ) spin1.setSelection(position, true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if( position < spin0.getSelectedItemPosition() ) spin0.setSelection(position, true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void clickRightMenu(int id){
        switch (id){
            case R.id.paytaxperiod_btnmenu_quickhelp:
                clickQuickHelp();
                break;
        }
    }

    private void clickQuickHelp(){

        List<BubbleShowCaseBuilder> listSc = new ArrayList<>();
        BubbleShowCaseBuilder sc;

        sc = Utility.createShowCase(getActivity(), getString(R.string.paytaxperiod_qhelptitle_month0), getString(R.string.paytaxperiod_qhelpbody_month0), (View)spinMonth0.getParent(), spinMonth0);
        if(sc != null){
            listSc.add(sc);
        }
        sc = Utility.createShowCase(getActivity(), getString(R.string.paytaxperiod_qhelptitle_month1), getString(R.string.paytaxperiod_qhelpbody_month1), (View)spinMonth1.getParent(), spinMonth1);
        if(sc != null){
            listSc.add(sc);
        }
        sc = Utility.createShowCase(getActivity(), getString(R.string.paytaxperiod_qhelptitle_year), getString(R.string.paytaxperiod_qhelpbody_year), (View)inpYear.getParent(), inpYear);
        if(sc != null){
            listSc.add(sc);
        }
        sc = Utility.createShowCase(getActivity(), getString(R.string.paytaxperiod_qhelptitle_btnnext), getString(R.string.paytaxperiod_qhelpbody_btnnext), (View)btnNext.getParent(), btnNext);
        if(sc != null){
            listSc.add(sc);
        }

        BubbleShowCaseSequence scs = new BubbleShowCaseSequence();
        scs.addShowCases(listSc);
        scs.show();

    }

    private void clickNext(){
        if(!validator.check(getContext())) return;

        NonSwipeableViewPager pager = getActivity().findViewById(R.id.paytax_pager);
        pager.setCurrentItem(PayTax3SetorFragment.VIEW_PAGER_CODE);

        payTaxActivity.wrappedSsp.month1 = month2char[spinMonth0.getSelectedItemPosition()];
        payTaxActivity.wrappedSsp.month2 = month2char[spinMonth1.getSelectedItemPosition()];
        payTaxActivity.wrappedSsp.year = inpYear.getText();
        payTaxActivity.invokeFragmentInit(PayTax3SetorFragment.VIEW_PAGER_CODE);
    }

}
