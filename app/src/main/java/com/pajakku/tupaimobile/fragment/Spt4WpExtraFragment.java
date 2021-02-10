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
import com.pajakku.tupaimobile.model.SptDraft;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.Utility;
import com.pajakku.tupaimobile.util.Validator;

/**
 * A simple {@link Fragment} subclass.
 */

public class Spt4WpExtraFragment extends Fragment {

    public static final int VIEW_PAGER_CODE = 4;

    private SptActivity sptActivity;

    private AppEditText inpWorkType;
    private AppEditText inpKlu;
    private AppEditText inpPasutriStatus;
    private AppEditText inpPasutriNpwp;

    private Validator validator;

    public Spt4WpExtraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_spt4_wpextra, container, false);

        inpWorkType = v.findViewById(R.id.spt4wpextra_inp_worktype);
        inpWorkType.constraintMandatory = true;
        inpKlu = v.findViewById(R.id.spt4wpextra_inp_klu);
        inpKlu.constraintMandatory = true;
        inpPasutriStatus = v.findViewById(R.id.spt4wpextra_inp_pasutristatus);
        inpPasutriStatus.constraintMandatory = true;
        inpPasutriNpwp = v.findViewById(R.id.spt4wpextra_inp_pasutrinpwp);
        inpPasutriNpwp.constraintMandatory = true;
        inpPasutriNpwp.setConstraintNPWP();

        Button botBtn = v.findViewById(R.id.spt4wpextra_btn_next);
        botBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBotBtn();
            }
        });

        setViewData();

        validator = new Validator();
        validator.addEdit(inpWorkType);
        validator.addEdit(inpKlu);
        validator.addEdit(inpPasutriStatus);
        validator.addEdit(inpPasutriNpwp);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SptActivity) {
            sptActivity = (SptActivity) this.getActivity();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement BaseActivity");
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

        inpWorkType.setText(sd.workType);
        inpKlu.setText(sd.klu);
        inpPasutriStatus.setText( sd.pasutriStatus );
        inpPasutriNpwp.setText( Utility.toPrettyNpwp(sd.pasutriNpwp) );

    }

    public void clickBotBtn(){
        if(!validator.check(getContext())) return;

        SptDraft sd = sptActivity.sptDraft;

        sd.workType = inpWorkType.getTextNotNull();
        sd.klu = inpKlu.getTextNotNull();
        sd.pasutriStatus = inpPasutriStatus.getTextNotNull();
        sd.pasutriNpwp = inpPasutriNpwp.getOnlyNumberNotNull();

        sptActivity.setSpString(AppConstant.SP_AVAL_WORKTYPE, sd.workType);
        sptActivity.setSpString(AppConstant.SP_AVAL_KLU, sd.klu);
        sptActivity.setSpString(AppConstant.SP_AVAL_PASUTRISTATUS, sd.pasutriStatus);
        sptActivity.setSpString(AppConstant.SP_AVAL_PASUTRINPWP, sd.pasutriNpwp);

        sptActivity.viewPager.setCurrentItem(Spt5PeredaranBrutoFragment.VIEW_PAGER_CODE);
//        sptActivity.invokeSetViewData(Spt5PeredaranBrutoFragment.VIEW_PAGER_CODE);
    }

}
