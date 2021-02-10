package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;

/**
 * Created by dul on 20/12/18.
 */

public class DialogTopBar extends RelativeLayout {

    private TextView tvTitle;
    private AttributeSet attrs;

    public DialogTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AttrsActionBar,
                0, 0);
        String title = "";
        Drawable icon = null;
        try {
            title = a.getString(R.styleable.AttrsActionBar_title);
            icon = a.getDrawable(R.styleable.AttrsActionBar_icon);
        } finally {
            a.recycle();
        }

        setBackgroundResource(R.drawable.bg_actionbar_main);

        LayoutParams relativeParam;



        RelativeLayout center = null;
        ImageView imgIcon = null;
        if(icon != null){
            relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            relativeParam.addRule(RelativeLayout.CENTER_IN_PARENT);

            center = new RelativeLayout(context, attrs);
            center.setLayoutParams(relativeParam);
            addView(center);

            relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            relativeParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

            imgIcon = new ImageView(context, attrs);
            imgIcon.setId(generateViewId());
            imgIcon.setImageDrawable(icon);
            imgIcon.setLayoutParams(relativeParam);
            center.addView(imgIcon);
        }

        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        tvTitle = new TextView(context, attrs);
        tvTitle.setTextSize(20);
        tvTitle.setText(title);
        tvTitle.setTextColor(Color.parseColor("#ffffff"));

        if(imgIcon != null){
            relativeParam.addRule(RelativeLayout.RIGHT_OF, imgIcon.getId());
            relativeParam.addRule(RelativeLayout.CENTER_VERTICAL);

            relativeParam.leftMargin = 10;
            tvTitle.setLayoutParams(relativeParam);
            center.addView(tvTitle);
        }else{
            relativeParam.addRule(RelativeLayout.CENTER_IN_PARENT);

            tvTitle.setLayoutParams(relativeParam);
            addView(tvTitle);
        }

    }

    public void init(OnClickListener listener, OnClickListener rightListener){

        LayoutParams relativeParam;

        if(listener != null) {
            relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            relativeParam.addRule(RelativeLayout.CENTER_VERTICAL);
            relativeParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

            ImageButton backBtn = new ImageButton(getContext(), attrs);
            backBtn.setOnClickListener(listener);
            backBtn.setLayoutParams(relativeParam);
            backBtn.setBackgroundResource(R.drawable.ic_actionbar_leftarrow);
            addView(backBtn);
        }

        if(rightListener != null){
            relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            relativeParam.addRule(RelativeLayout.CENTER_VERTICAL);
            relativeParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            relativeParam.rightMargin = 15;

            ImageButton rightBtn = new ImageButton(getContext(), attrs);
            rightBtn.setLayoutParams(relativeParam);
            rightBtn.setBackgroundResource(R.drawable.dialogtopbar_icon_close);
            rightBtn.setOnClickListener(rightListener);
            addView(rightBtn);
        }

    }

    public void setTitle(int title){
        tvTitle.setText(title);
    }

//    public interface ListenerQuerySuccess {
//        int menuResource();
//        void onClickRightMenu(int id);
//    }
}
