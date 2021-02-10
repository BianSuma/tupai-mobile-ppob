package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.TypedValue;
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

public class AppActionBar extends RelativeLayout {

    private ImageButton backBtn;
    private TextView tvTitle;
    private AttributeSet attrs;
    private TextView tvVer;

    public AppActionBar(Context context, AttributeSet attrs) {
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

        RelativeLayout.LayoutParams relativeParam;

        relativeParam = new RelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.actionbar_btn_width), ViewGroup.LayoutParams.MATCH_PARENT);
        relativeParam.addRule(RelativeLayout.CENTER_VERTICAL);
        relativeParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        relativeParam.leftMargin = getResources().getDimensionPixelSize(R.dimen.holo_shadow_pad);

        backBtn = new ImageButton(getContext(), attrs);
        backBtn.setLayoutParams(relativeParam);
        backBtn.setBackgroundResource(R.drawable.ic_actionbar_leftarrow);
        backBtn.setImageResource(R.drawable.ic_actionbar_leftarrow_normal);
        backBtn.setVisibility(View.GONE);
        addView(backBtn);

        RelativeLayout center = null;
        ImageView imgIcon = null;
        if(icon != null){
            relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            relativeParam.addRule(RelativeLayout.CENTER_IN_PARENT);

            center = new RelativeLayout(context, attrs);
            center.setId(View.generateViewId());
            center.setLayoutParams(relativeParam);
            addView(center);

            relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            relativeParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

            imgIcon = new ImageView(context, attrs);
            imgIcon.setId(generateViewId());
            imgIcon.setImageDrawable(icon);
            imgIcon.setLayoutParams(relativeParam);
            center.addView(imgIcon);

        }

        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        tvTitle = new TextView(context, attrs);
        tvTitle.setId(View.generateViewId());
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
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

        int padVer = getResources().getDimensionPixelSize(R.dimen.gap_title_ver);

        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(RIGHT_OF, tvTitle.getId());
        relativeParam.addRule(CENTER_VERTICAL);
        relativeParam.leftMargin = padVer;

        tvVer = new TextView(context, attrs);
        tvVer.setTextColor(Color.parseColor("#eeeeee"));
        tvVer.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
        tvVer.setLayoutParams(relativeParam);

        if (imgIcon != null) {
            center.addView(tvVer);
        }else {
            addView(tvVer);
        }
    }

    public void init(OnClickListener listener, final AppListener rightMenuListener){

        backBtn.setVisibility(listener != null ? VISIBLE : GONE);
        backBtn.setOnClickListener(listener);

        if(rightMenuListener != null){
            setRightMenu(rightMenuListener);
        }

    }

    public void setLeftClick(OnClickListener listener){
        backBtn.setVisibility(VISIBLE);
        backBtn.setOnClickListener(listener);
    }

    public AppActionBar setBackFinish(final AppCompatActivity activity){
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        backBtn.setVisibility(VISIBLE);
        return this;
    }

    public void setPrev(final ViewPager vp, final int pos){
        backBtn.setVisibility(VISIBLE);
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(pos);
            }
        });
    }

    public void setLeftLabel(int title){
        RelativeLayout.LayoutParams relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(CENTER_VERTICAL);
        relativeParam.leftMargin = getResources().getDimensionPixelSize(R.dimen.holo_shadow_pad);

        tvTitle.setLayoutParams(relativeParam);
        tvTitle.setText(title);
    }

    public void init(int title, OnClickListener listener, final AppListener rightMenuListener){
        if(title!=0) tvTitle.setText(title);
        init(listener, rightMenuListener);
    }

    public void setRightMenu(final AppListener appListener){
        int w = getResources().getDimensionPixelSize(R.dimen.actionbar_btn_width);
        RelativeLayout.LayoutParams relativeParam = new RelativeLayout.LayoutParams(w, ViewGroup.LayoutParams.MATCH_PARENT);
        relativeParam.addRule(RelativeLayout.CENTER_VERTICAL);
        relativeParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        relativeParam.rightMargin = 15;

        ImageButton rightBtn = new ImageButton(getContext(), attrs);
        rightBtn.setLayoutParams(relativeParam);
        rightBtn.setBackgroundResource(R.drawable.ic_actionbar_rightmenu);
        rightBtn.setImageResource(R.drawable.ic_actionbar_rightmenu_normal);
        rightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(AppActionBar.this.getContext(), v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        appListener.onClickRightMenu(item.getItemId());
                        return false;
                    }
                });
                popupMenu.inflate(appListener.menuResource());
                popupMenu.show();
            }
        });
        addView(rightBtn);
    }

    public void setRightButton(int iconRes, OnClickListener listener){
        int w = getResources().getDimensionPixelSize(R.dimen.actionbar_btn_width);
        RelativeLayout.LayoutParams relativeParam = new RelativeLayout.LayoutParams(w, ViewGroup.LayoutParams.MATCH_PARENT);
        relativeParam.addRule(RelativeLayout.CENTER_VERTICAL);
        relativeParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        ImageButton rightBtn = new ImageButton(getContext(), attrs);
        rightBtn.setLayoutParams(relativeParam);
        rightBtn.setBackgroundResource(R.drawable.ic_actionbar_rightmenu);
        rightBtn.setImageResource(iconRes);
        rightBtn.setOnClickListener( listener );
        addView(rightBtn);
    }

    public void setVer(String ver){
        tvVer.setText(getResources().getString(R.string.global_version, ver));
    }

    public void setTitle(int title){
        tvTitle.setText(title);
    }

    public interface AppListener {
        int menuResource();
        void onClickRightMenu(int id);
    }
}
