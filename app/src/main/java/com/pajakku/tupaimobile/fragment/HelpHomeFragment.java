package com.pajakku.tupaimobile.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.adapter.list.HelpItemAdapter;
import com.pajakku.tupaimobile.model.dto.HelpItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class HelpHomeFragment extends Fragment {
    public static final int VIEW_PAGER_CODE = 0;

//    private BaseActivity mListener;

    private ViewPager viewPager;

    public HelpHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        final NonSwipeableViewPager viewPager = getActivity().findViewById(R.id.fraghelp_pager);
        viewPager = ((HelpFragment)getParentFragment()).viewPager;

        View v = inflater.inflate(R.layout.fragment_help_home, container, false);

        List<HelpItem> listItemHelp = new ArrayList<>();
        HelpItem hi;

        hi = new HelpItem();
        hi.icon = R.drawable.ic_help_ssp;
        hi.name = R.string.helphome_btn_ssp;
        hi.desc = R.string.helphome_btn_ssp_sub;
        listItemHelp.add(hi);

        hi = new HelpItem();
        hi.icon = R.drawable.ic_help_payssp;
        hi.name = R.string.helphome_btn_payssp;
        hi.desc = R.string.helphome_btn_payssp_sub;
        listItemHelp.add(hi);

        HelpItemAdapter helpItemAdapter = new HelpItemAdapter(getActivity());
        helpItemAdapter.addAll(listItemHelp);

        ListView listView = v.findViewById(R.id.helphome_listview);
        listView.setAdapter(helpItemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickItem( (HelpItem)parent.getItemAtPosition(position) );
            }
        });

//        HelpHomeButton helpButton;
//
//        HelpHomeButton btnSSP = v.findViewById(R.id.helphome_btn_ssp);
//        btnSSP.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                viewPager.setCurrentItem(HelpSSPFragment.VIEW_PAGER_CODE);
//            }
//        });

//        helpButton = view.findViewById(R.id.helphome_btn_activatemcash);
//        helpButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                viewPager.setCurrentItem(HelpActivateMCashFragment.VIEW_PAGER_CODE);
//            }
//        });

//        helpButton = v.findViewById(R.id.helphome_btn_payssp);
//        helpButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                viewPager.setCurrentItem(HelpPayTaxFragment.VIEW_PAGER_CODE);
//            }
//        });

//        helpButton = view.findViewById(R.id.helphome_btn_callus);
//        helpButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                viewPager.setCurrentItem(HelpCallUsFragment.VIEW_PAGER_CODE);
//            }
//        });

        return v;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof BaseActivity) {
//            mListener = (BaseActivity) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement BaseActivity");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    private void clickItem(HelpItem hi){
        switch (hi.icon){
            case R.drawable.ic_help_ssp:
                viewPager.setCurrentItem(HelpSSPFragment.VIEW_PAGER_CODE);
                break;
            case R.drawable.ic_help_payssp:
                viewPager.setCurrentItem(HelpPayTaxFragment.VIEW_PAGER_CODE);
                break;
        }
    }

}
