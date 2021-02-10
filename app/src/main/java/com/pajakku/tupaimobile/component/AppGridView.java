package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.adapter.GridAdapter;
import com.pajakku.tupaimobile.model.GridItem;
import com.pajakku.tupaimobile.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dul on 19/12/18.
 */

public class AppGridView extends GridView {

    public GridAdapter adapter;
    public AppGridView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
//        setColumnWidth(getResources().getDimensionPixelSize(R.dimen.fraghome_griditem_width));
//        setNumColumns(GridView.AUTO_FIT);
        setNumColumns(3);
        setVerticalSpacing(10);
        setHorizontalSpacing(10);
        setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

        adapter = new GridAdapter(context);
        setAdapter(adapter);
    }

    public void setListener(final AppListener appListener){


        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridItem gi = (GridItem)parent.getItemAtPosition(position);
                appListener.onClickItem(gi);
            }
        });

    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


    public void addAll(List<GridItem> list){
        adapter.clear();
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }



    public interface AppListener {
        void onClickItem(GridItem gi);
    }
}
