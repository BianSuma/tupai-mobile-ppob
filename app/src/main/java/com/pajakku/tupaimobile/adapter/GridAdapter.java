package com.pajakku.tupaimobile.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.model.GridItem;
import com.pajakku.tupaimobile.model.Taxtype;

/**
 * Created by dul on 18/12/18.
 */

public class GridAdapter extends ArrayAdapter<GridItem> {
    private static int res = R.layout.row_grid_item;

    public GridAdapter(Context context) {
        super(context, res);
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(res, null);
        }

        GridItem data = getItem(position);

        if (data != null)
        {

            Drawable d = Taxtype.fetchColoredItem(getContext(), data.icon, data.type);
            int pad = getContext().getResources().getDimensionPixelSize(R.dimen.taxicon_circle_pad);
            int backCircle = Taxtype.fetchBackCircle(data.type);

            ImageView icon = v.findViewById(R.id.rowgriditem_icon);
            if(backCircle != 0) {
                icon.setBackgroundResource(backCircle);
                icon.setPadding(pad, pad, pad, pad);
            }
            icon.setImageDrawable(d);

            TextView tvName = v.findViewById(R.id.rowgriditem_name);
            tvName.setText(data.name);

            TextView tvPasalCount = v.findViewById(R.id.rowgriditem_pasalcount);

            RelativeLayout rl = v.findViewById(R.id.rowgriditem_layout_row);
            if(data.type == GridItem.TYPE_WP){

                if(data.isSelected()){
                    rl.setBackgroundResource(R.drawable.bg_btn_gridselect);
                } else {
                    rl.setBackgroundResource(R.drawable.bg_btn_grid);
                }

                int padLayout = getContext().getResources().getDimensionPixelSize(R.dimen.layout_content_pad);
                int padLayoutBig = getContext().getResources().getDimensionPixelSize(R.dimen.griditem_layout_padtopbot);
                rl.setPadding(padLayout, padLayoutBig, padLayout, padLayoutBig);

                tvPasalCount.setText( getContext().getString(R.string.comp_gridadapter_label_pasal,data.getItemList().size()) );
                tvPasalCount.setVisibility(View.VISIBLE);
            }else{
                tvPasalCount.setVisibility(View.GONE);
            }

            int face = Typeface.NORMAL;

            if(data.isSelected()){
                face = Typeface.BOLD;
            }
            tvName.setTypeface(null, face);

        }

        return v;
    }
}
