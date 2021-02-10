package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import com.google.android.material.textfield.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;

import java.lang.reflect.Field;

/**
 * Created by dul on 18/12/18.
 */

public class QuickFindPanel extends RelativeLayout {
    private static final int MODE_WHITE = 1;
    private static final int MODE_BLUE_ACTIONBAR = 2;

    private int mode;

    private boolean isBulk = false;
    public TextInputEditText inpFind;
//    private ImageButton btnFind;
//    private ImageButton btnBulk;

    public QuickFindPanel(Context context, AttributeSet attrs){
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AttrsListfindpanel,
                0, 0);
        String panelMode = null;
        try {
            panelMode = a.getString(R.styleable.AttrsListfindpanel_panel_mode);
        } finally {
            a.recycle();
        }

        mode = MODE_WHITE;
        if(panelMode != null) {
            if (panelMode.equals("MODE_BLUE_ACTIONBAR")) {
                mode = MODE_BLUE_ACTIONBAR;
            }
        }

        switch (mode){
            case MODE_BLUE_ACTIONBAR:
                setBackgroundColor(Color.parseColor("#0d47a1"));
                break;
        }

        int pad = (int)getResources().getDimension(R.dimen.layout_content_pad);

        inpFind = new TextInputEditText(context, attrs);
        inpFind.setId(View.generateViewId());
        inpFind.setHint(R.string.listfindpanel_hint_find);
        switch (mode) {
            case MODE_BLUE_ACTIONBAR:
                inpFind.setBackgroundResource(R.drawable.bg_listfindpanel_edittext);
                inpFind.setTextColor(Color.parseColor("#ffffff"));
                inpFind.setHintTextColor(Color.parseColor("#cccccc"));
                break;
        }
        addView(inpFind);

        switch (mode) {
            case MODE_BLUE_ACTIONBAR:
                try {
                    Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
                    f.setAccessible(true);
                    f.set(inpFind, 0);
                } catch (Exception e) {
                    Log.w(AppConstant.LOG_TAG, "ListFindPanel " + e.getMessage());
                }
                break;
        }

//        btnFind = new ImageButton(context, attrs);
//        btnFind.setId(View.generateViewId());
//        btnFind.setBackgroundResource(R.drawable.bg_btn_find);
//        btnFind.setImageResource(R.drawable.ic_btn_find);
//        addView(btnFind);

//        btnBulk = new ImageButton(context, attrs);
//        btnBulk.setId(View.generateViewId());
//        btnBulk.setBackgroundResource(R.drawable.bg_btn_find);
//        btnBulk.setImageResource(R.drawable.ic_btn_bulk);
//        addView(btnBulk);

        int btnHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        switch (mode){
            case MODE_BLUE_ACTIONBAR:
                btnHeight = getResources().getDimensionPixelSize(R.dimen.button_height);
                break;
        }

        // set layout
        LayoutParams relativeParam;

        relativeParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, btnHeight);
//        relativeParam.addRule(LEFT_OF, btnFind.getId());
        inpFind.setLayoutParams(relativeParam);

//        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        relativeParam.addRule(ALIGN_PARENT_RIGHT);
//        relativeParam.addRule(CENTER_VERTICAL);
//        relativeParam.leftMargin = pad;
//        relativeParam.rightMargin = pad;
//        btnFind.setLayoutParams(relativeParam);

//        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        relativeParam.addRule(ALIGN_PARENT_RIGHT);
//        relativeParam.addRule(CENTER_VERTICAL);
//        btnBulk.setLayoutParams(relativeParam);

        setPadding(pad, pad, pad, pad);

    }

    public void init(final CommonCallback<String> callback){
        inpFind.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString().trim();
                if( str.length() > 0 ) callback.onSuccess(str);
                else callback.onSuccess(null);
            }
        });
    }


    public boolean clickBulk(){
        if(isBulk){
//            btnBulk.setImageResource(R.drawable.ic_btn_bulk);
            isBulk = false;
        }else{
//            btnBulk.setImageResource(R.drawable.ic_bulk_on);
            isBulk = true;
        }
        return isBulk;
    }

    public void setText(String str){
        inpFind.setText(str);
    }

    public String getSearchText(){
        String str = inpFind.getText().toString();
        if(str.length() > 0) return str;
        return null;
    }

    public interface AppListener {
        void onClick(boolean isBulk);
    }
}
