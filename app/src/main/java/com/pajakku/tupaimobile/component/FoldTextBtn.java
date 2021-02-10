package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;

/**
 * Created by dul on 18/12/18.
 */

public class FoldTextBtn extends RelativeLayout {

    private boolean isOpen = false;
    private ImageView arrow;

    public FoldTextBtn(Context context, AttributeSet attrs){
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AttrsButton,
                0, 0);
        String text = "";
        try {
            text = a.getString(R.styleable.AttrsButton_text);
        } finally {
            a.recycle();
        }

        setBackgroundResource(R.drawable.bg_foldtextbtn);

        LayoutParams relativeParam;

        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(ALIGN_PARENT_LEFT);
        relativeParam.addRule(CENTER_VERTICAL);

        TextView tvText = new TextView(context, attrs);
        tvText.setLayoutParams(relativeParam);
        tvText.setTextColor(Color.parseColor("#314ea6"));
        tvText.setText(text);
        addView(tvText);

        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(CENTER_VERTICAL);
        relativeParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        arrow = new ImageView(context, attrs);
        arrow.setLayoutParams(relativeParam);
        arrow.setImageResource(R.drawable.ic_arrow_bot);
        addView(arrow);

        int pad = (int)getResources().getDimension(R.dimen.layout_content_pad);
        setPadding(pad, 0, pad, 0);

    }

    public void init(final AppListener clickListener){
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickView(clickListener);
            }
        });
    }

    private void clickView(AppListener clickListener){
        if(isOpen){
            isOpen = false;
            arrow.setImageResource(R.drawable.ic_arrow_bot);
        }else{
            isOpen = true;
            arrow.setImageResource(R.drawable.ic_arrow_top);
        }
        clickListener.onClick(isOpen);
    }

    public interface AppListener {
        void onClick(boolean isOpen);
    }
}
