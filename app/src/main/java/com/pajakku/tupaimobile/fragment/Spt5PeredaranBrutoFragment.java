package com.pajakku.tupaimobile.fragment;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.SptActivity;
import com.pajakku.tupaimobile.component.AppEditText;
import com.pajakku.tupaimobile.component.AppListView;
import com.pajakku.tupaimobile.model.SptDraft;
import com.pajakku.tupaimobile.model.dto.BrutoTax;
import com.pajakku.tupaimobile.model.dto.BrutoTaxItem;
import com.pajakku.tupaimobile.model.dto.ListModelBase;
import com.pajakku.tupaimobile.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class Spt5PeredaranBrutoFragment extends Fragment {

    public static final int VIEW_PAGER_CODE = 5;

    private SptActivity sptActivity;

    private AppListView appListView;
//    private ListViewAdapter listViewAdapter;
    private AppEditText inpPasutriStatus;
    private AppEditText inpPasutriNpwp;

    private Validator validator;

    public Spt5PeredaranBrutoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_spt5_peredaranbruto, container, false);

        appListView = v.findViewById(R.id.spt5bruto_applistview);
        appListView.init(getActivity(), R.layout.row_brutotax);

        Button botBtn = v.findViewById(R.id.spt5bruto_btn_next);
        botBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBotBtn();
            }
        });

        setViewData();

        validator = new Validator();
//        validator.addEdit(inpWorkType);
//        validator.addEdit(inpKlu);
//        validator.addEdit(inpPasutriStatus);
//        validator.addEdit(inpPasutriNpwp);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SptActivity) {
            sptActivity = (SptActivity) this.getActivity();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SptActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        sptActivity = null;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void setViewData(){

        SptDraft sd = sptActivity.sptDraft;

        // TODO: @test
        List<ListModelBase> listBt = new ArrayList<>();
        BrutoTax bt = new BrutoTax();
        bt.name = "Bandung";
        BrutoTaxItem bti;
        int i = 1;
        for(; i<13; i++) {
            bti = new BrutoTaxItem();
            bti.m = i;
            bti.a = "6000";
            bt.l.add(bti);
        }
        listBt.add(bt);
        bt = new BrutoTax();
        bt.name = "Cimahi";
        i = 1;
        for(; i<13; i++) {
            bti = new BrutoTaxItem();
            bti.m = i;
            bti.a = "5000";
            bt.l.add(bti);
        }
        listBt.add(bt);
        appListView.add(listBt);

    }

    public void clickBotBtn(){
        if(!validator.check(getContext())) return;

        SptDraft sd = sptActivity.sptDraft;



//        sd.workType = inpWorkType.getTextNotNull();
//        sd.klu = inpKlu.getTextNotNull();
//        sd.pasutriStatus = inpPasutriStatus.getTextNotNull();
//        sd.pasutriNpwp = inpPasutriNpwp.getOnlyNumberNotNull();
//
//        sptActivity.setSpString(AppConstant.SP_AVAL_WORKTYPE, sd.workType);
//        sptActivity.setSpString(AppConstant.SP_AVAL_KLU, sd.klu);
//        sptActivity.setSpString(AppConstant.SP_AVAL_PASUTRISTATUS, sd.pasutriStatus);
//        sptActivity.setSpString(AppConstant.SP_AVAL_PASUTRINPWP, sd.pasutriNpwp);


    }

}
