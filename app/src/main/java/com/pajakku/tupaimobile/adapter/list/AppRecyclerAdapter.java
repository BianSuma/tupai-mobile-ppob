package com.pajakku.tupaimobile.adapter.list;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.adapter.viewholder.ViewHolderCommon1Val;
import com.pajakku.tupaimobile.adapter.viewholder.ViewHolderCommon2Val;
import com.pajakku.tupaimobile.fragment.Spt0SptTypeFragment;
import com.pajakku.tupaimobile.model.Kjs;
import com.pajakku.tupaimobile.model.Spt;
import com.pajakku.tupaimobile.model.SptType;
import com.pajakku.tupaimobile.model.Sspdone;
import com.pajakku.tupaimobile.model.Sspunpaid;
import com.pajakku.tupaimobile.model.Taxtype;
import com.pajakku.tupaimobile.model.Wajibpajak;
import com.pajakku.tupaimobile.model.dto.ClickItemListParam;
import com.pajakku.tupaimobile.model.dto.ModelMultiSelect;
import com.pajakku.tupaimobile.model.dto.NtpnBundle;
import com.pajakku.tupaimobile.model.dto.TaxSlipResponse;
import com.pajakku.tupaimobile.model.dto.YearInt;
import com.pajakku.tupaimobile.model.holder.HolderCommon1Val;
import com.pajakku.tupaimobile.model.holder.HolderCommon2Val;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dul on 29/03/19.
 */

public class AppRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int MODE_LOADING = 1;
    public static final int MODE_LIST_KJS = 2;
    public static final int MODE_LIST_SSPUNPAID = 3;
    public static final int MODE_LIST_SSPDONE = 4;
    public static final int MODE_LIST_WP = 5;
    public static final int MODE_LIST_TAXTYPE = 6;
    public static final int MODE_LIST_YEAR = 7;
    public static final int MODE_LIST_NTPNBUNDLE = 8;
    public static final int MODE_LIST_SPT = 9;
    public static final int MODE_COMMON_1_VAL = 10;
    public static final int MODE_COMMON_2_VAL = 11;

    private boolean isMultiSelect = false;

    public List<ModelMultiSelect> list = new ArrayList<>();
    private Context context;
    private CommonCallback commonCallback;
    public CommonCallback<ClickItemListParam> cbExtra;

    public AppRecyclerAdapter(){

    }

    public <T> AppRecyclerAdapter(Context ctx, CommonCallback<T> commonCallback) {
        this.context = ctx;
        this.commonCallback = commonCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;

        switch (viewType){
            case MODE_COMMON_1_VAL:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_common1val, parent, false);
                return new ViewHolderCommon1Val(v);
            case MODE_COMMON_2_VAL:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_common2val, parent, false);
                return new ViewHolderCommon2Val(v);
        }

        if (viewType == MODE_LIST_KJS) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_kjs, parent, false);
            return new KJSViewHolder(view);
        } else if (viewType == MODE_LIST_SSPUNPAID){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_unfinish_ssp, parent, false);
            return new SspunpaidViewHolder(view);
        } else if (viewType == MODE_LIST_SSPDONE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_unfinish_ssp, parent, false);
            return new SspdoneViewHolder(view);
        } else if (viewType == MODE_LIST_WP){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wajib_pajak, parent, false);
            return new WajibpajakViewHolder(view);
        } else if (viewType == MODE_LIST_TAXTYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_taxtype, parent, false);
            return new TaxtypeViewHolder(view);
        } else if (viewType == MODE_LIST_YEAR){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_year, parent, false);
            return new YearViewHolder(view);
        } else if (viewType == MODE_LIST_NTPNBUNDLE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ntpnbundle, parent, false);
            return new NtpnBundleViewHolder(view);
        } else if (viewType == MODE_LIST_SPT){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_spt, parent, false);
            return new SptViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ModelMultiSelect d = list.get(position);

        if (viewHolder instanceof KJSViewHolder) {
            populateKJSItem((KJSViewHolder) viewHolder, position);
        } else if (viewHolder instanceof SspunpaidViewHolder) {
            populateSspunpaidItem((SspunpaidViewHolder)viewHolder, position);
        } else if (viewHolder instanceof SspdoneViewHolder) {
            populateSspdoneItem((SspdoneViewHolder)viewHolder, position);
        } else if (viewHolder instanceof WajibpajakViewHolder) {
            populateWajibpajakItem((WajibpajakViewHolder)viewHolder, position);
        } else if (viewHolder instanceof TaxtypeViewHolder) {
            populateTaxtypeItem((TaxtypeViewHolder)viewHolder, position);
        } else if (viewHolder instanceof YearViewHolder) {
            populateYearItem((YearViewHolder)viewHolder, position);
        } else if (viewHolder instanceof NtpnBundleViewHolder) {
            populateNtpnBundleItem((NtpnBundleViewHolder)viewHolder, position);
        } else if (viewHolder instanceof SptViewHolder) {
            populateSptItem((SptViewHolder)viewHolder, position);

        } else if (viewHolder instanceof ViewHolderCommon1Val) {
            ((ViewHolderCommon1Val)viewHolder).populate(d, cbExtra);
        } else if (viewHolder instanceof ViewHolderCommon2Val) {
            ((ViewHolderCommon2Val)viewHolder).populate(d, cbExtra);

        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    @Override
    public int getItemViewType(int position) {
        ModelMultiSelect m = list.get(position);
        if(m == null) return MODE_LOADING;

        if(m instanceof Kjs){
            return MODE_LIST_KJS;
        }else if(m instanceof Sspdone){
            return MODE_LIST_SSPDONE;
        }else if(m instanceof Wajibpajak){
            return MODE_LIST_WP;
        }else if(m instanceof Taxtype){
            return MODE_LIST_TAXTYPE;
        }else if(m instanceof YearInt){
            return MODE_LIST_YEAR;
        }else if(m instanceof NtpnBundle){
            return MODE_LIST_NTPNBUNDLE;
        }else if(m instanceof Spt){
            return MODE_LIST_SPT;
        }else if(m instanceof HolderCommon1Val){
            return MODE_COMMON_1_VAL;
        }else if(m instanceof HolderCommon2Val){
            return MODE_COMMON_2_VAL;
        }

        return MODE_LIST_SSPUNPAID;
    }
    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.recyclerlistloading_progressbar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
    }

    // populate kjs
    private class KJSViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tvCode;
        public TextView tvName;

        public KJSViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            tvCode = itemView.findViewById(R.id.rowkjs_code);
            tvName = itemView.findViewById(R.id.rowkjs_name);
        }
    }
    private void populateKJSItem(KJSViewHolder viewHolder, int position) {
        final Kjs item = (Kjs)list.get(position);
        viewHolder.tvCode.setText(item.code);
        viewHolder.tvName.setText(item.name);

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonCallback.onSuccess(item);
            }
        });
    }

    // populate sspunpaid
    private class SspunpaidViewHolder extends RecyclerView.ViewHolder {
        public View rootView;

        public ImageView icon;
        public TextView tvName;
        public TextView tvNpwp;
        public TextView tvTaxDate;
        public TextView tvAmount;
        public TextView tvBillCode;
//        public ImageView rightArrow;
//        public CheckBox checkBox;
        public AppCompatTextView lblStatus;

        public SspunpaidViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;

            icon = itemView.findViewById(R.id.rowunfinishssp_icon);
            tvName = itemView.findViewById(R.id.rowunfinishssp_name);
            tvNpwp = itemView.findViewById(R.id.rowunfinishssp_npwp);
            tvTaxDate = itemView.findViewById(R.id.rowunfinishssp_taxdetail);
            tvAmount = itemView.findViewById(R.id.rowunfinishssp_amount);
            tvBillCode = itemView.findViewById(R.id.rowunfinishssp_bilcode);
//            rightArrow = itemView.findViewById(R.id.rowunfinishssp_rightarrow);
//            checkBox = itemView.findViewById(R.id.rowunfinishssp_selected);
            lblStatus = itemView.findViewById(R.id.rowunfinishssp_lbl_status);
        }
    }
    private void populateSspunpaidItem(SspunpaidViewHolder viewHolder, int position) {
        final Sspunpaid item = (Sspunpaid)list.get(position);

        int category = Taxtype.fetchCategoryStatic(item.taxTypeCode);
        int backCircle = Taxtype.fetchBackCircle(category);
        Drawable d = Taxtype.fetchColoredItem(context, item.fetchIcon(), Taxtype.fetchCategoryStatic(item.fetchDefaultTaxTypeCode()));
        viewHolder.icon.setBackgroundResource(backCircle);
        viewHolder.icon.setImageDrawable( d );

        viewHolder.tvName.setText(String.valueOf(item.name));
        viewHolder.tvNpwp.setText(item.npwp);
        viewHolder.tvTaxDate.setText( context.getString(R.string.ssplist_label_taxdetail, item.taxTypeName, item.fetchTaxPeriod(context) ) );
        viewHolder.tvAmount.setText( Utility.toMoney(true, item.amount) );

//        if(item.taxslipresponseCodeNotNull().equals(TaxSlipResponse.RESPONSECODE_OK)){
//            viewHolder.tvBillCode.setTextColor(Color.parseColor("#52c05a"));
//        }else{
//            viewHolder.tvBillCode.setTextColor(Color.parseColor("#990000"));
//        }

        if(item.idBilling != null) {
            if (item.taxslipresponseCodeNotNull().equals(TaxSlipResponse.RESPONSECODE_OK)) {
                viewHolder.tvBillCode.setText(Utility.billNumberX(item.idBilling));
            } else {
                viewHolder.tvBillCode.setText(item.idBilling);
            }
            viewHolder.tvBillCode.setVisibility(View.VISIBLE);
        }else{
            viewHolder.tvBillCode.setVisibility(View.GONE);
        }

        viewHolder.lblStatus.setText( item.statusNotNull() );

//        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                item.isCheck = b;
//                notifyDataSetChanged();
//            }
//        });
//        viewHolder.checkBox.setChecked( item.isCheck );
//        if(isMultiSelect){
//            viewHolder.rightArrow.setVisibility(View.GONE);
//            viewHolder.checkBox.setVisibility(View.VISIBLE);
//        }else{
//            viewHolder.rightArrow.setVisibility(View.VISIBLE);
//            viewHolder.checkBox.setVisibility(View.GONE);
//        }

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonCallback.onSuccess(item);
            }
        });
    }

    // populate sspdone
    private class SspdoneViewHolder extends RecyclerView.ViewHolder {
        public View rootView;

        public ImageView icon;
        public TextView tvName;
        public TextView tvNpwp;
        public TextView tvTaxDate;
        public TextView tvAmount;
        public TextView tvBillCode;
//        public ImageView rightArrow;
//        public CheckBox checkBox;
        public AppCompatTextView lblStatus;

        public SspdoneViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;

            icon = itemView.findViewById(R.id.rowunfinishssp_icon);
            tvName = itemView.findViewById(R.id.rowunfinishssp_name);
            tvNpwp = itemView.findViewById(R.id.rowunfinishssp_npwp);
            tvTaxDate = itemView.findViewById(R.id.rowunfinishssp_taxdetail);
            tvAmount = itemView.findViewById(R.id.rowunfinishssp_amount);
            tvBillCode = itemView.findViewById(R.id.rowunfinishssp_bilcode);
//            rightArrow = itemView.findViewById(R.id.rowunfinishssp_rightarrow);
//            checkBox = itemView.findViewById(R.id.rowunfinishssp_selected);
            lblStatus = itemView.findViewById(R.id.rowunfinishssp_lbl_status);
        }
    }
    private void populateSspdoneItem(SspdoneViewHolder viewHolder, int position) {
        final Sspdone item = (Sspdone)list.get(position);

        int category = Taxtype.fetchCategoryStatic(item.taxTypeCode);
        int backCircle = Taxtype.fetchBackCircle(category);
        Drawable d = Taxtype.fetchColoredItem(context, item.fetchIcon(), Taxtype.fetchCategoryStatic(item.fetchDefaultTaxTypeCode()));
        viewHolder.icon.setBackgroundResource(backCircle);
        viewHolder.icon.setImageDrawable( d );

        viewHolder.tvName.setText(String.valueOf(item.name));
        viewHolder.tvNpwp.setText(item.npwp);
        viewHolder.tvTaxDate.setText( context.getString(R.string.ssplist_label_taxdetail, item.taxTypeName, item.fetchTaxPeriod(context) ) );
        viewHolder.tvAmount.setText( Utility.toMoney(true, item.amount) );

        viewHolder.lblStatus.setVisibility(View.GONE);

//        if(item.taxslipresponseCodeNotNull().equals(TaxSlipResponse.RESPONSECODE_OK)){
//            viewHolder.tvBillCode.setTextColor(Color.parseColor("#52c05a"));
//        }else{
//            viewHolder.tvBillCode.setTextColor(Color.parseColor("#990000"));
//        }

        if(item.idBilling != null) {
            if (item.taxslipresponseCodeNotNull().equals(TaxSlipResponse.RESPONSECODE_OK)) {
                viewHolder.tvBillCode.setText(Utility.billNumberX(item.idBilling));
            } else {
                viewHolder.tvBillCode.setText(item.idBilling);
            }
            viewHolder.tvBillCode.setVisibility(View.VISIBLE);
        }else{
            viewHolder.tvBillCode.setVisibility(View.GONE);
        }

//        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                item.isCheck = b;
//                notifyDataSetChanged();
//            }
//        });
//        viewHolder.checkBox.setChecked( item.isCheck );
//        if(isMultiSelect){
//            viewHolder.rightArrow.setVisibility(View.GONE);
//            viewHolder.checkBox.setVisibility(View.VISIBLE);
//        }else{
//            viewHolder.rightArrow.setVisibility(View.VISIBLE);
//            viewHolder.checkBox.setVisibility(View.GONE);
//        }

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonCallback.onSuccess(item);
            }
        });
    }

    // populate wajibpajak
    private class WajibpajakViewHolder extends RecyclerView.ViewHolder {
        public View rootView;

        public TextView tvInitName;
        public TextView tvName;
        public TextView tvNpwp;
        public TextView tvAddress;

        public WajibpajakViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;

            tvInitName = itemView.findViewById(R.id.rowwp_iconlabel_initname);
            tvName = itemView.findViewById(R.id.rowwp_name);
            tvNpwp = itemView.findViewById(R.id.rowwp_npwp);
            tvAddress = itemView.findViewById(R.id.rowwp_address);
        }
    }
    private void populateWajibpajakItem(WajibpajakViewHolder viewHolder, int position) {
        final Wajibpajak item = (Wajibpajak) list.get(position);

        viewHolder.tvInitName.setText(item.name.substring(0,1));
        viewHolder.tvName.setText(item.name);
        viewHolder.tvNpwp.setText( Utility.toPrettyNpwp(item.npwp) );
        viewHolder.tvAddress.setText(item.address);

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonCallback.onSuccess(item);
            }
        });
    }

    // populate taxtype
    private class TaxtypeViewHolder extends RecyclerView.ViewHolder {
        public View rootView;

        public ImageView icon;
        public TextView tvName;
        public TextView tvCode;

        public TaxtypeViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;

            icon = itemView.findViewById(R.id.rowtaxtype_icon);
            tvName = itemView.findViewById(R.id.rowtaxtype_name);
            tvCode = itemView.findViewById(R.id.rowtaxtype_code);
        }
    }
    private void populateTaxtypeItem(TaxtypeViewHolder viewHolder, int position) {
        final Taxtype item = (Taxtype) list.get(position);

        int category = Taxtype.fetchCategoryStatic(item.code);
        int backCircle = Taxtype.fetchBackCircle(category);
//        Drawable d = Taxtype.fetchColoredItem(context, item.fetchIcon(), Taxtype.fetchCategoryStatic(item.code));
        viewHolder.icon.setBackgroundResource(backCircle);
        viewHolder.icon.setImageResource(item.fetchIcon());

        viewHolder.tvName.setText(item.name);
        viewHolder.tvCode.setText( item.code );

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonCallback.onSuccess(item);
            }
        });
    }

    // populate YearInt
    private class YearViewHolder extends RecyclerView.ViewHolder {
        public View rootView;

        public TextView tvYear;

        public YearViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;

            tvYear = itemView.findViewById(R.id.rowyear_year);
        }
    }
    private void populateYearItem(YearViewHolder viewHolder, int position) {
        final YearInt item = (YearInt) list.get(position);

        viewHolder.tvYear.setText( Integer.toString(item.year) );

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonCallback.onSuccess(item);
            }
        });
    }

    // populate NtpnBundle
    private class NtpnBundleViewHolder extends RecyclerView.ViewHolder {
        public View rootView;

        public TextView tvNtpn;
        public TextView tvPeriod;
        public TextView tvSetor;

        public NtpnBundleViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;

            tvNtpn = itemView.findViewById(R.id.rowntpnbundle_ntpn);
            tvPeriod = itemView.findViewById(R.id.rowntpnbundle_period);
            tvSetor = itemView.findViewById(R.id.rowntpnbundle_setor);
        }
    }
    private void populateNtpnBundleItem(NtpnBundleViewHolder viewHolder, int position) {
        final NtpnBundle item = (NtpnBundle) list.get(position);

        viewHolder.tvNtpn.setText( item.ntpn );
        switch (item.month0){
            case 1:
                viewHolder.tvPeriod.setText( ("Januari "+item.year) );
                break;
            case 2:
                viewHolder.tvPeriod.setText( ("Februari "+item.year) );
                break;
            default:
                viewHolder.tvPeriod.setText( ("Maret "+item.year) );
        }
        viewHolder.tvSetor.setText( Utility.toMoney(true, item.setor) );

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonCallback.onSuccess(item);
            }
        });
    }

    // populate SPT
    private class SptViewHolder extends RecyclerView.ViewHolder {
        public View rootView;

        public ImageView icon;
        public TextView tvWpName;
        public TextView tvNpwp;
        public TextView tvInfo;
        public TextView tvAmount;
        public TextView tvPembetulan;
        public TextView tvStatus;

        public SptViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;

            icon = itemView.findViewById(R.id.rowspt_icon);
            tvWpName = itemView.findViewById(R.id.rowspt_name);
            tvNpwp = itemView.findViewById(R.id.rowspt_npwp);
            tvInfo = itemView.findViewById(R.id.rowspt_sptinfo);
            tvAmount = itemView.findViewById(R.id.rowspt_amount);
            tvPembetulan = itemView.findViewById(R.id.rowspt_pembetulan);
            tvStatus = itemView.findViewById(R.id.rowspt_status);
        }
    }
    private void populateSptItem(SptViewHolder viewHolder, int position) {
        final Spt item = (Spt) list.get(position);

        SptType st = Spt0SptTypeFragment.fetchSingleSpttype(item.sptTypeCode);

        if(item.fetchStatusIcon() != 0) viewHolder.icon.setImageResource( item.fetchStatusIcon() );
        viewHolder.tvWpName.setText( item.wpName );
        viewHolder.tvNpwp.setText( Utility.toPrettyNpwp(item.npwp) );
        if(st != null && st.aliasList != 0) viewHolder.tvInfo.setText( (context.getString(st.aliasList) + " " +item.year) );
        viewHolder.tvAmount.setText( Utility.toMoney(true, item.amount) );
        viewHolder.tvPembetulan.setText( item.fetchPembetulan() );
        viewHolder.tvStatus.setText( item.status );

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonCallback.onSuccess(item);
            }
        });
    }
}
