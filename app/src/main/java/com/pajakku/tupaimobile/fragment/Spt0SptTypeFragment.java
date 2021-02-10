package com.pajakku.tupaimobile.fragment;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.SptActivity;
import com.pajakku.tupaimobile.adapter.list.ListViewAdapter;
import com.pajakku.tupaimobile.component.AppRecyclerView;
import com.pajakku.tupaimobile.model.SptType;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Spt0SptTypeFragment extends Fragment {

    public static final int VIEW_PAGER_CODE = 0;

    private SptActivity sptActivity;

    private SwipeRefreshLayout swipeRefreshLayout;
    private AppRecyclerView appRecyclerView;

    public Spt0SptTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_spt0_spttype, container, false);

//        AppActionBar appActionBar = v.findViewById(R.id.spttype_appactionbar);
//        appActionBar.setLeftClick(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sptActivity.finish();
//            }
//        });

        List<SptType> list = fetchSpttypeList();

        ListViewAdapter listViewAdapter = new ListViewAdapter(getActivity(), R.layout.row_spttype);
        listViewAdapter.addAll(list);
        ListView listView = v.findViewById(R.id.spttype_applistview);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickItem( (SptType)parent.getItemAtPosition(position) );
            }
        });


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

    public static List<SptType> fetchSpttypeList(){
        List<SptType> l = new ArrayList<>();
        SptType st;

        st = new SptType();
        st.code = SptType.TYPE_1770;
        st.alias = "1770";
        st.aliasList = R.string.spt0spttype_labelaliaslist_1770;
        st.name = "SPT Tahunan PPh Orang Pribadi 1770 UMKM";
        st.info = R.string.spt0spttype_labelinfo_1770;
        l.add( st );
//        st = new SptType();
//        st.code = SptType.TYPE_23_26;
//        st.alias = "23/26";
//        st.aliasList = 0;
//        st.name = "SPT Masa PPh Pasal 23/26";
//        st.info = 0;
//        list.add( st );
//        st = new SptType();
//        st.code = SptType.TYPE_4_2;
//        st.alias = "4 (2)";
//        st.aliasList = 0;
//        st.name = "SPT Masa PPh Pasal 4 Ayat 2";
//        st.info = 0;
//        list.add( st );

        return l;
    }

    public static SptType fetchSingleSpttype(String code){
        for(SptType l : fetchSpttypeList()){
            if( l.code.equals(code) ) return l;
        }
        return null;
    }

    public void setViewData(){

    }

    public void clickItem(SptType sptType){
        sptActivity.sptDraft.sptTypeCode = sptType.code;
        sptActivity.viewPager.setCurrentItem(Spt1IntroFragment.VIEW_PAGER_CODE);
        sptActivity.invokeSetViewData(Spt1IntroFragment.VIEW_PAGER_CODE);
    }

}
