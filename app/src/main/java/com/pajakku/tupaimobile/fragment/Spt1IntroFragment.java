package com.pajakku.tupaimobile.fragment;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.SptActivity;
import com.pajakku.tupaimobile.component.AppButton;
import com.pajakku.tupaimobile.model.SptType;
import com.pajakku.tupaimobile.model.SptDraft;

/**
 * A simple {@link Fragment} subclass.
 */
public class Spt1IntroFragment extends Fragment {

    public static final int VIEW_PAGER_CODE = 1;

    private SptActivity sptActivity;

    private TextView tvSptAlias;
    private TextView tvSptName;
    private TextView tvInfo;

    public Spt1IntroFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_spt0_intro, container, false);

        tvSptAlias = v.findViewById(R.id.spt0intro_label_sptalias);
        tvSptName = v.findViewById(R.id.spt0intro_label_sptname);
        tvInfo = v.findViewById(R.id.spt0intro_label_info);

        AppButton btnNext = v.findViewById(R.id.spt0intro_btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickNext();
            }
        });

        setViewData();

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
        SptDraft sptDraft = sptActivity.sptDraft;

        SptType st = Spt0SptTypeFragment.fetchSingleSpttype(sptDraft.sptTypeCode);
        if(st == null) return;

        tvSptAlias.setText(st.alias);
        tvSptName.setText(st.name);

        if( st.info != 0 )
            tvInfo.setText( st.info );
    }

    public void clickNext(){
        sptActivity.viewPager.setCurrentItem(Spt2WpFragment.VIEW_PAGER_CODE);
    }

}
