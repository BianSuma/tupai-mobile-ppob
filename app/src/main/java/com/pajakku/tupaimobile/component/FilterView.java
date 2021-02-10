package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.model.dto.FilterParam;
import com.pajakku.tupaimobile.util.CommonCallback;

/**
 * Created by dul on 04/04/19.
 */

public class FilterView extends RelativeLayout {
    public static int MODE_FIND = 1;
    public static int MODE_SORT = 2;
    public static int MODE_FIND_WP = 3;
    public static int MODE_SORT_WP = 4;

    public static final int LISTTYPE_SSPUNPAID = 1;
    public static final int LISTTYPE_SSPDONE = 2;
    public static final int LISTTYPE_WP = 3;

    public int mode = MODE_FIND;
//    public int listType = LISTTYPE_SSPUNPAID;

//    public boolean isShow = false;
    private RadioButton radioBtnName;
    private RadioButton radioBtnNpwp;
    private RadioButton radioBtnYear;
    private RadioButton radioBtnAddress;

    private RadioGroup radioGroup;
    private AppEditText appEditText;
    private Button btnApply;

    private RadioButton radioBtnSortName;
    private RadioButton radioBtnSortNpwp;
    private RadioButton radioBtnSortYear;
    private RadioButton radioBtnSortAddress;
    private RadioGroup radioGroupSortColumn;

    private RadioButton radioBtnDesc;
    private RadioButton radioBtnAsc;
    private RadioGroup radioGroupAscDesc;

    private ScrollView scrollViewFind;
    private ScrollView scrollViewSort;
    private ScrollView scrollViewFindWp;
    private ScrollView scrollViewSortWp;

    public FilterParam paramFindSspUnpaid = new FilterParam();
    public FilterParam paramFindSspDone = new FilterParam();
    public FilterParam paramFindWp = new FilterParam();

    public FilterView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setVisibility(GONE);
        setBackgroundColor(Color.parseColor("#fdfdfd"));
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        int btnH = getResources().getDimensionPixelSize(R.dimen.button_height);
        int pad = getResources().getDimensionPixelSize(R.dimen.layout_content_pad);
        int bigPad = getResources().getDimensionPixelSize(R.dimen.holo_shadow_pad);

        LayoutParams lay;

        lay = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, btnH);
        lay.addRule(ALIGN_PARENT_BOTTOM);
        lay.addRule(CENTER_HORIZONTAL);
        lay.setMargins(pad, pad, pad, pad);
        btnApply = new Button(context, attrs);
        btnApply.setId(View.generateViewId());
        btnApply.setLayoutParams(lay);
        btnApply.setGravity(Gravity.CENTER);
        btnApply.setBackgroundResource(R.drawable.bg_btn_common);
        btnApply.setTypeface(null, Typeface.BOLD);
        btnApply.setText(R.string.comp_filterview_btn_apply);
        addView(btnApply);

        scrollViewFind = createScrollViewFind(context, attrs);
        addView(scrollViewFind);

        scrollViewSort = createScrollViewSort(context, attrs);
        addView(scrollViewSort);

        scrollViewFindWp = createScrollViewFindWp(context, attrs);
        addView(scrollViewFindWp);

        scrollViewSortWp = createScrollViewSortWP(context, attrs);
        addView(scrollViewSortWp);

    }

    public void setModeFind(){
        mode = MODE_FIND;
        scrollViewFind.setVisibility(VISIBLE);
        scrollViewSort.setVisibility(GONE);
        scrollViewFindWp.setVisibility(GONE);
        scrollViewSortWp.setVisibility(GONE);
    }

    public void setModeSort(){
        mode = MODE_SORT;
        scrollViewFind.setVisibility(GONE);
        scrollViewSort.setVisibility(VISIBLE);
        scrollViewFindWp.setVisibility(GONE);
        scrollViewSortWp.setVisibility(GONE);
    }

    public void setModeFindWp(){
        mode = MODE_FIND_WP;
        scrollViewFind.setVisibility(GONE);
        scrollViewSort.setVisibility(GONE);
        scrollViewFindWp.setVisibility(VISIBLE);
        scrollViewSortWp.setVisibility(GONE);
    }

    public void setModeSortWp(){
        mode = MODE_SORT_WP;
        scrollViewFind.setVisibility(GONE);
        scrollViewSort.setVisibility(GONE);
        scrollViewFindWp.setVisibility(GONE);
        scrollViewSortWp.setVisibility(VISIBLE);
    }

    private ScrollView createScrollViewFind(Context context, AttributeSet attrs) {
        LayoutParams lay;
        int pad = getResources().getDimensionPixelSize(R.dimen.layout_content_pad);
        int bigPad = getResources().getDimensionPixelSize(R.dimen.holo_shadow_pad);

        RelativeLayout topBar = createTopBar(context, attrs, R.string.comp_filterview_label_title);

        // radio title
        lay = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lay.addRule(BELOW, topBar.getId());
        lay.setMargins(pad, pad, pad, 0);
        TextView radioTitle = new TextView(context, attrs);
        radioTitle.setId(View.generateViewId());
        radioTitle.setLayoutParams(lay);
        radioTitle.setText(R.string.comp_filterview_label_findbytitle);

        // radio button element
        lay = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        radioBtnName = new RadioButton(context, attrs);
        radioBtnName.setId(View.generateViewId());
        radioBtnName.setText(R.string.comp_filterview_radio_findbyname);
        radioBtnName.setLayoutParams(lay);
        radioBtnName.setPadding(0, 0, bigPad, 0);
        radioBtnName.setChecked(true);

        radioBtnNpwp = new RadioButton(context, attrs);
        radioBtnNpwp.setId(View.generateViewId());
        radioBtnNpwp.setText(R.string.comp_filterview_radio_findbynpwp);
        radioBtnNpwp.setLayoutParams(lay);
        radioBtnNpwp.setPadding(0, 0, bigPad, 0);

        radioBtnYear = new RadioButton(context, attrs);
        radioBtnYear.setId(View.generateViewId());
        radioBtnYear.setText(R.string.comp_filterview_radio_findbyyear);
        radioBtnYear.setLayoutParams(lay);
        radioBtnYear.setPadding(0, 0, bigPad, 0);

        // radio group
        lay = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lay.addRule(BELOW, radioTitle.getId());
        lay.setMargins(pad, 0, pad, pad);
        radioGroup = new RadioGroup(context, attrs);
        radioGroup.setId(View.generateViewId());
        radioGroup.setLayoutParams(lay);
        radioGroup.setOrientation(RadioGroup.HORIZONTAL);
        radioGroup.addView(radioBtnName);
        radioGroup.addView(radioBtnNpwp);
        radioGroup.addView(radioBtnYear);

        // appEditText
        lay = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lay.addRule(BELOW, radioGroup.getId());
        lay.setMargins(pad, pad, pad, pad);
        appEditText = new AppEditText(context, attrs);
        appEditText.setId(View.generateViewId());
        appEditText.setHint(R.string.comp_filterview_hint_findkey);
        appEditText.setLayoutParams(lay);

        lay = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout scrollContentFind = new RelativeLayout(context, attrs);
        scrollContentFind.setLayoutParams(lay);
        scrollContentFind.addView(topBar);
        scrollContentFind.addView(radioTitle);
        scrollContentFind.addView(radioGroup);
        scrollContentFind.addView(appEditText);

        lay = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lay.addRule(ABOVE, btnApply.getId());
        ScrollView scroll = new ScrollView(context, attrs);
        scroll.addView(scrollContentFind);
        scroll.setLayoutParams(lay);
        return scroll;
    }

    private ScrollView createScrollViewSort(Context context, AttributeSet attrs){
        LayoutParams lay;
        int pad = getResources().getDimensionPixelSize(R.dimen.layout_content_pad);
        int bigPad = getResources().getDimensionPixelSize(R.dimen.holo_shadow_pad);

        RelativeLayout topBar = createTopBar(context, attrs, R.string.comp_filterview_label_titlesort);

        // radio button element
        lay = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        radioBtnSortName = new RadioButton(context, attrs);
        radioBtnSortName.setId(View.generateViewId());
        radioBtnSortName.setText(R.string.comp_filterview_radio_sortbyname);
        radioBtnSortName.setLayoutParams(lay);
        radioBtnSortName.setPadding(0, 0, bigPad, 0);
        radioBtnSortName.setChecked(true);

        radioBtnSortNpwp = new RadioButton(context, attrs);
        radioBtnSortNpwp.setId(View.generateViewId());
        radioBtnSortNpwp.setText(R.string.comp_filterview_radio_sortbynpwp);
        radioBtnSortNpwp.setLayoutParams(lay);
        radioBtnSortNpwp.setPadding(0, 0, bigPad, 0);

        radioBtnSortYear = new RadioButton(context, attrs);
        radioBtnSortYear.setId(View.generateViewId());
        radioBtnSortYear.setText(R.string.comp_filterview_radio_sortbyyear);
        radioBtnSortYear.setLayoutParams(lay);
        radioBtnSortYear.setPadding(0, 0, bigPad, 0);

        // radio title
        lay = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lay.addRule(BELOW, topBar.getId());
        lay.setMargins(pad, pad, pad, 0);
        TextView radioTitleColumn = new TextView(context, attrs);
        radioTitleColumn.setId(View.generateViewId());
        radioTitleColumn.setLayoutParams(lay);
        radioTitleColumn.setText(R.string.comp_filterview_label_sortbytitle);

        lay = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lay.addRule(BELOW, radioTitleColumn.getId());
        lay.setMargins(pad, 0, pad, pad);
        radioGroupSortColumn = new RadioGroup(context, attrs);
        radioGroupSortColumn.setId(View.generateViewId());
        radioGroupSortColumn.setLayoutParams(lay);
        radioGroupSortColumn.setOrientation(RadioGroup.HORIZONTAL);
        radioGroupSortColumn.addView(radioBtnSortName);
        radioGroupSortColumn.addView(radioBtnSortNpwp);
        radioGroupSortColumn.addView(radioBtnSortYear);

        // radio button element
        lay = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        radioBtnDesc = new RadioButton(context, attrs);
        radioBtnDesc.setId(View.generateViewId());
        radioBtnDesc.setText(R.string.comp_filterview_radio_sortdesc);
        radioBtnDesc.setLayoutParams(lay);
        radioBtnDesc.setPadding(0, 0, bigPad, 0);
        radioBtnDesc.setChecked(true);

        radioBtnAsc = new RadioButton(context, attrs);
        radioBtnAsc.setId(View.generateViewId());
        radioBtnAsc.setText(R.string.comp_filterview_radio_sortasc);
        radioBtnAsc.setLayoutParams(lay);
        radioBtnAsc.setPadding(0, 0, bigPad, 0);

        // radio title
        lay = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lay.addRule(BELOW, radioGroupSortColumn.getId());
        lay.setMargins(pad, pad, pad, 0);
        TextView radioTitleAscDesc = new TextView(context, attrs);
        radioTitleAscDesc.setId(View.generateViewId());
        radioTitleAscDesc.setLayoutParams(lay);
        radioTitleAscDesc.setText(R.string.comp_filterview_label_sortmethodtitle);

        lay = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lay.addRule(BELOW, radioTitleAscDesc.getId());
        lay.setMargins(pad, 0, pad, pad);
        radioGroupAscDesc = new RadioGroup(context, attrs);
        radioGroupAscDesc.setId(View.generateViewId());
        radioGroupAscDesc.setLayoutParams(lay);
        radioGroupAscDesc.setOrientation(RadioGroup.HORIZONTAL);
        radioGroupAscDesc.addView(radioBtnDesc);
        radioGroupAscDesc.addView(radioBtnAsc);

        lay = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout scrollContentSort = new RelativeLayout(context, attrs);
        scrollContentSort.setLayoutParams(lay);
        scrollContentSort.addView( topBar );
        scrollContentSort.addView( radioTitleColumn );
        scrollContentSort.addView(radioGroupSortColumn);
        scrollContentSort.addView( radioTitleAscDesc );
        scrollContentSort.addView(radioGroupAscDesc);

        lay = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lay.addRule(ABOVE, btnApply.getId());
        ScrollView scroll = new ScrollView(context, attrs);
        scroll.addView(scrollContentSort);
        scroll.setLayoutParams(lay);
        scroll.setVisibility(GONE);

        return scroll;
    }

    private ScrollView createScrollViewFindWp(Context context, AttributeSet attrs) {
        LayoutParams lay;
        int pad = getResources().getDimensionPixelSize(R.dimen.layout_content_pad);
        int bigPad = getResources().getDimensionPixelSize(R.dimen.holo_shadow_pad);

        RelativeLayout topBar = createTopBar(context, attrs, R.string.comp_filterview_label_title);

        // radio title
        lay = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lay.addRule(BELOW, topBar.getId());
        lay.setMargins(pad, pad, pad, 0);
        TextView radioTitle = new TextView(context, attrs);
        radioTitle.setId(View.generateViewId());
        radioTitle.setLayoutParams(lay);
        radioTitle.setText(R.string.comp_filterview_label_findbytitle);

        // radio button element
        lay = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        radioBtnName = new RadioButton(context, attrs);
        radioBtnName.setId(View.generateViewId());
        radioBtnName.setText(R.string.comp_filterview_radio_findbyname);
        radioBtnName.setLayoutParams(lay);
        radioBtnName.setPadding(0, 0, bigPad, 0);
        radioBtnName.setChecked(true);

        radioBtnNpwp = new RadioButton(context, attrs);
        radioBtnNpwp.setId(View.generateViewId());
        radioBtnNpwp.setText(R.string.comp_filterview_radio_findbynpwp);
        radioBtnNpwp.setLayoutParams(lay);
        radioBtnNpwp.setPadding(0, 0, bigPad, 0);

        radioBtnAddress = new RadioButton(context, attrs);
        radioBtnAddress.setId(View.generateViewId());
        radioBtnAddress.setText(R.string.comp_filterview_radio_findbyaddress);
        radioBtnAddress.setLayoutParams(lay);
        radioBtnAddress.setPadding(0, 0, bigPad, 0);

        // radio group
        lay = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lay.addRule(BELOW, radioTitle.getId());
        lay.setMargins(pad, 0, pad, pad);
        radioGroup = new RadioGroup(context, attrs);
        radioGroup.setId(View.generateViewId());
        radioGroup.setLayoutParams(lay);
        radioGroup.setOrientation(RadioGroup.HORIZONTAL);
        radioGroup.addView(radioBtnName);
        radioGroup.addView(radioBtnNpwp);
        radioGroup.addView(radioBtnAddress);

        // appEditText
        lay = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lay.addRule(BELOW, radioGroup.getId());
        lay.setMargins(pad, pad, pad, pad);
        appEditText = new AppEditText(context, attrs);
        appEditText.setId(View.generateViewId());
        appEditText.setHint(R.string.comp_filterview_hint_findkey);
        appEditText.setLayoutParams(lay);

        lay = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout scrollContentFind = new RelativeLayout(context, attrs);
        scrollContentFind.setLayoutParams(lay);
        scrollContentFind.addView(topBar);
        scrollContentFind.addView(radioTitle);
        scrollContentFind.addView(radioGroup);
        scrollContentFind.addView(appEditText);

        lay = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lay.addRule(ABOVE, btnApply.getId());
        ScrollView scroll = new ScrollView(context, attrs);
        scroll.addView(scrollContentFind);
        scroll.setLayoutParams(lay);
        return scroll;
    }

    private ScrollView createScrollViewSortWP(Context context, AttributeSet attrs){
        LayoutParams lay;
        int pad = getResources().getDimensionPixelSize(R.dimen.layout_content_pad);
        int bigPad = getResources().getDimensionPixelSize(R.dimen.holo_shadow_pad);

        RelativeLayout topBar = createTopBar(context, attrs, R.string.comp_filterview_label_titlesort);

        // radio button element
        lay = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        radioBtnSortName = new RadioButton(context, attrs);
        radioBtnSortName.setId(View.generateViewId());
        radioBtnSortName.setText(R.string.comp_filterview_radio_sortbyname);
        radioBtnSortName.setLayoutParams(lay);
        radioBtnSortName.setPadding(0, 0, bigPad, 0);
        radioBtnSortName.setChecked(true);

        radioBtnSortNpwp = new RadioButton(context, attrs);
        radioBtnSortNpwp.setId(View.generateViewId());
        radioBtnSortNpwp.setText(R.string.comp_filterview_radio_sortbynpwp);
        radioBtnSortNpwp.setLayoutParams(lay);
        radioBtnSortNpwp.setPadding(0, 0, bigPad, 0);

        radioBtnSortAddress = new RadioButton(context, attrs);
        radioBtnSortAddress.setId(View.generateViewId());
        radioBtnSortAddress.setText(R.string.comp_filterview_radio_sortbyaddress);
        radioBtnSortAddress.setLayoutParams(lay);
        radioBtnSortAddress.setPadding(0, 0, bigPad, 0);

        // radio title
        lay = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lay.addRule(BELOW, topBar.getId());
        lay.setMargins(pad, pad, pad, 0);
        TextView radioTitleColumn = new TextView(context, attrs);
        radioTitleColumn.setId(View.generateViewId());
        radioTitleColumn.setLayoutParams(lay);
        radioTitleColumn.setText(R.string.comp_filterview_label_sortbytitle);

        lay = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lay.addRule(BELOW, radioTitleColumn.getId());
        lay.setMargins(pad, 0, pad, pad);
        radioGroupSortColumn = new RadioGroup(context, attrs);
        radioGroupSortColumn.setId(View.generateViewId());
        radioGroupSortColumn.setLayoutParams(lay);
        radioGroupSortColumn.setOrientation(RadioGroup.HORIZONTAL);
        radioGroupSortColumn.addView(radioBtnSortName);
        radioGroupSortColumn.addView(radioBtnSortNpwp);
        radioGroupSortColumn.addView(radioBtnSortAddress);

        // radio button element
        lay = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        radioBtnDesc = new RadioButton(context, attrs);
        radioBtnDesc.setId(View.generateViewId());
        radioBtnDesc.setText(R.string.comp_filterview_radio_sortdesc);
        radioBtnDesc.setLayoutParams(lay);
        radioBtnDesc.setPadding(0, 0, bigPad, 0);
        radioBtnDesc.setChecked(true);

        radioBtnAsc = new RadioButton(context, attrs);
        radioBtnAsc.setId(View.generateViewId());
        radioBtnAsc.setText(R.string.comp_filterview_radio_sortasc);
        radioBtnAsc.setLayoutParams(lay);
        radioBtnAsc.setPadding(0, 0, bigPad, 0);

        // radio title
        lay = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lay.addRule(BELOW, radioGroupSortColumn.getId());
        lay.setMargins(pad, pad, pad, 0);
        TextView radioTitleAscDesc = new TextView(context, attrs);
        radioTitleAscDesc.setId(View.generateViewId());
        radioTitleAscDesc.setLayoutParams(lay);
        radioTitleAscDesc.setText(R.string.comp_filterview_label_sortmethodtitle);

        lay = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lay.addRule(BELOW, radioTitleAscDesc.getId());
        lay.setMargins(pad, 0, pad, pad);
        radioGroupAscDesc = new RadioGroup(context, attrs);
        radioGroupAscDesc.setId(View.generateViewId());
        radioGroupAscDesc.setLayoutParams(lay);
        radioGroupAscDesc.setOrientation(RadioGroup.HORIZONTAL);
        radioGroupAscDesc.addView(radioBtnDesc);
        radioGroupAscDesc.addView(radioBtnAsc);

        lay = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout scrollContentSort = new RelativeLayout(context, attrs);
        scrollContentSort.setLayoutParams(lay);
        scrollContentSort.addView( topBar );
        scrollContentSort.addView( radioTitleColumn );
        scrollContentSort.addView(radioGroupSortColumn);
        scrollContentSort.addView( radioTitleAscDesc );
        scrollContentSort.addView(radioGroupAscDesc);

        lay = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lay.addRule(ABOVE, btnApply.getId());
        ScrollView scroll = new ScrollView(context, attrs);
        scroll.addView(scrollContentSort);
        scroll.setLayoutParams(lay);
        scroll.setVisibility(GONE);

        return scroll;
    }

    private RelativeLayout createTopBar(Context context, AttributeSet attrs, int resTitle){
        LayoutParams lay;
        int bigPad = getResources().getDimensionPixelSize(R.dimen.holo_shadow_pad);

        lay = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lay.addRule(CENTER_VERTICAL);
        TextView tvTitle = new TextView(context, attrs);
        tvTitle.setLayoutParams(lay);
        tvTitle.setTypeface(null, Typeface.BOLD);
        tvTitle.setText(resTitle);

        lay = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lay.addRule(ALIGN_PARENT_RIGHT);
        lay.addRule(CENTER_VERTICAL);
        ImageButton btnDismiss = new ImageButton(context, attrs);
        btnDismiss.setLayoutParams(lay);
        btnDismiss.setBackgroundResource(R.drawable.bg_btn_dismiss);
        btnDismiss.setImageResource(R.drawable.ic_circle_dismiss);
        btnDismiss.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterView.this.hide();
            }
        });
        btnDismiss.setPadding(bigPad, bigPad, bigPad, bigPad);

        // top bar
        lay = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout topBar = new RelativeLayout(context, attrs);
        topBar.setId(View.generateViewId());
        topBar.setLayoutParams(lay);
        topBar.addView(tvTitle);
        topBar.addView(btnDismiss);
        topBar.setBackgroundResource(R.drawable.bg_filterview_topbar);
        topBar.setPadding(bigPad, 0, 0, 0);

        return topBar;
    }

    public void setCallback(final int listType, final CommonCallback<FilterParam> callback){
        btnApply.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                FilterParam param = paramFindSspUnpaid;

                switch (listType){
                    case FilterView.LISTTYPE_SSPDONE:
                        param = paramFindSspDone;
                        break;
                    case FilterView.LISTTYPE_WP:
                        param = paramFindWp;
                        break;
                }

                int id = radioGroup.getCheckedRadioButtonId();
                String f = null;
                if(id == radioBtnName.getId()){
                    f = "name";
                }else if(id == radioBtnNpwp.getId()){
                    f = "npwp";
                }else if(id == radioBtnYear.getId()){
                    f = "year";
                }else if(id == radioBtnAddress.getId()){
                    f = "address";
                }
                param.field = f;

                param.query = appEditText.getText();

                callback.onSuccess(param);
                FilterView.this.hide();
            }
        });
    }

    public void setCallbackSort(final int listType, final CommonCallback<FilterParam> callback){
        btnApply.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                FilterParam param = paramFindSspUnpaid;

                switch (listType){
                    case FilterView.LISTTYPE_SSPDONE:
                        param = paramFindSspDone;
                        break;
                    case FilterView.LISTTYPE_WP:
                        param = paramFindWp;
                        break;
                }

                int id = radioGroupSortColumn.getCheckedRadioButtonId();
                String c = null;
                if(id == radioBtnSortName.getId()) {
                    c = "name";
                }else if(id == radioBtnSortNpwp.getId()){
                    c = "npwp";
                }else if(id == radioBtnSortYear.getId()){
                    c = "year";
                }else if(id == radioBtnSortAddress.getId()){
                    c = "address";
                }
                param.column = c;

                id = radioGroupAscDesc.getCheckedRadioButtonId();
                String f = null;
                if(id == radioBtnAsc.getId()){
                    f = "ASC";
                }else if(id == radioBtnDesc.getId()){
                    f = "DESC";
                }
                param.order = f;

                callback.onSuccess(param);
                FilterView.this.hide();
            }
        });
    }

    public void show(){
//        isShow = true;
        setVisibility(VISIBLE);

//        int h = getHeight();
//        int w = getWidth();
//
//        LayoutParams lay = (RelativeLayout.LayoutParams)getLayoutParams();
//        lay.width = 0;  lay.height = 0;
//        setLayoutParams(lay);
//
//        this.animate()
////                .translationY(this.getHeight())
////                .alpha(1)
//                .setDuration(300)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
////                        FilterView.this.setVisibility(View.VISIBLE);
//                    }
//                });
    }

    public void hide(){
//        isShow = false;
        setVisibility(GONE);

        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(appEditText.getWindowToken(), 0);
    }
}
