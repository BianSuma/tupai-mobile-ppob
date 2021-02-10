package com.pajakku.tupaimobile.model;

//import com.orm.SugarRecord;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import android.content.Context;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.model.dto.ModelMultiSelect;
import com.pajakku.tupaimobile.model.dto.TaxReceipt;
import com.pajakku.tupaimobile.model.dto.TaxSlipResponse;
import com.pajakku.tupaimobile.model.dto.TaxtypeAlias;
import com.pajakku.tupaimobile.model.dto.response.BillingDTO;
import com.pajakku.tupaimobile.model.dto.response.MPNPaymentResponse;
import com.pajakku.tupaimobile.model.dto.response.ResponseSsp;
import com.pajakku.tupaimobile.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dul on 17/01/19.
 */

@Entity(tableName = Sspunpaid.TABLE_NAME)
public class Sspunpaid extends ModelMultiSelect {
    public static final String TABLE_NAME = "sspunpaid";

    public static final String COLUMN_ID = "id";

//    public static final String STATUS_NO_ID_BILLING = "NO_ID_BILLING";
//    public static final String STATUS_READY_PAY = "READY_PAY";
//    public static final String STATUS_ONGOING_PAY = "ONGOING_PAY";  //  sedang dalam proses bayar
//    public static final String STATUS_NEED_REFUND = "NEED_REFUND";
//    public static final String STATUS_REFUNDED = "REFUNDED";
//    public static final String STATUS_LUNAS = "LUNAS";
//    public static final String STATUS_MPN_PAY_EXPIRED = "MPN_PAY_EXPIRED";
//    public static final String STATUS_MPN_PAY_CANCEL = "MPN_PAY_CANCEL";
//    public static final String STATUS_SSP_INVALID = "SSP_INVALID";

    @PrimaryKey
    public long id;
    @ColumnInfo(name="name")
    public String name;
    @ColumnInfo(name="npwp")
    public String npwp;
    @ColumnInfo(name="address")
    public String address;
    @ColumnInfo(name="amount")
    public long amount;
    @ColumnInfo(name="id_billing")
    public String idBilling;
    @ColumnInfo(name="bill_num_expired")
    public String billNumExpired;
    @ColumnInfo(name="taxslipresponse_code")
    public String taxslipresponseCode;
    @ColumnInfo(name="taxslipresponse_desc")
    public String taxslipresponseDesc;
    @ColumnInfo(name="id_billing_pajakku")
    public String idBillingPajakku;
    @ColumnInfo(name="status")
    public String status;
    @ColumnInfo(name="month1")
    public int month1;
    @ColumnInfo(name="month2")
    public int month2;
    @ColumnInfo(name="year")
    public int year;
    @ColumnInfo(name="tax_type_code")
    public String taxTypeCode;
    @ColumnInfo(name="tax_type_name")
    public String taxTypeName;
    @ColumnInfo(name="kjs_code")
    public String kjsCode;
    @ColumnInfo(name="kjs_name")
    public String kjsName;
    @ColumnInfo(name="trans_ref_id")
    public String transRefId;
    @ColumnInfo(name="ntb")
    public String ntb;
    @ColumnInfo(name="ntpn")
    public String ntpn;
    @ColumnInfo(name="created_at")
    public String createdAt;
    @ColumnInfo(name="updated_at")
    public String updatedAt;
    @ColumnInfo(name="receipt_paydate")
    public String receiptPaydate;
    @ColumnInfo(name="receipt_bookdate")
    public String receiptBookdate;
    @ColumnInfo(name="receipt_provider")
    public String receiptProvider;
    @ColumnInfo(name="ref_no")
    public String refNo;
    @ColumnInfo(name="npwp_penyetor")
    public String npwpPenyetor;
    @ColumnInfo(name="no_sk")
    public String noSk;
    @ColumnInfo(name="nop")
    public String nop;

    public Sspunpaid() {
    }

    // ----------- NOT NULL

    @Ignore
    public String idBillingNotNull(){
        if(idBilling == null) return "";
        return idBilling;
    }
    @Ignore
    public String ntpnNotNull(){
        if(ntpn == null) return "";
        return ntpn;
    }

    @Ignore
    public String statusNotNull(){
        if(status == null) return "";
        return status;
    }

    public String ntbNotNull(){
        if(ntb == null) return "";
        return ntb;
    }

    // ------------

    @Ignore
    public static List<Sspunpaid> getInstanceList(List<ResponseSsp> sspdtos){
        List<Sspunpaid> list = new ArrayList<>();
        for(ResponseSsp dto : sspdtos){
            list.add( dto.toSspunpaid() );
        }
        return list;
    }

    @Ignore
    public String fetchTaxPeriod(Context context){
        if(month1 <= 0) return "";
        String m = context.getString(Utility.toMonthShort(month1));
        if(month1 != month2){
            m = m + " - " + context.getString(Utility.toMonthShort(month2));
        }
        return m + " " + year;
    }

    @Ignore
    public String fetchTaxPeriodLong(Context context){
        if(month1 <= 0) return "";
        String m = context.getString(Utility.toMonthLong(month1));
        if(month1 != month2){
            m = m + " - " + context.getString(Utility.toMonthLong(month2));
        }
        return m + " " + year;
    }

    @Ignore
    public int fetchIcon(){
        for(TaxtypeAlias tta : Taxtype.taxtypeAliases){
            if(tta.code.equals(taxTypeCodeNotNull())){
                if(tta.icon != 0) return tta.icon;
            }
        }
        return R.drawable.ic_taxmisc;
    }

    @Ignore
    public Taxtype fetchTaxtype(){
        Taxtype tt = new Taxtype();
        tt.code = taxTypeCode;
        tt.name = taxTypeName;
        return tt;
    }

    @Ignore
    public Kjs fetchKjs(){
        Kjs kjs = new Kjs();
        kjs.code = kjsCode;
        kjs.name = kjsName;
        return kjs;
    }

    @Ignore
    public String taxTypeCodeNotNull(){
        return (taxTypeCode != null ? taxTypeCode : "");
    }

    @Ignore
    public String fetchKapKjs(Context context){
        return context.getString(R.string.sspdata_kapkjs, taxTypeCode, taxTypeName, kjsCode, kjsName);
    }

//    @Ignore
//    public String fetchDefaultConsumeId(){
//        if(consumeId != null) return consumeId;
//        return "";
//    }

    @Ignore
    public String fetchDefaultTaxTypeCode(){
        if(taxTypeCode != null) return taxTypeCode;
        return "";
    }

    @Ignore
    public String fetchIdBillingNotNull(){
        if(idBilling != null) return idBilling;
        return "";
    }

    @Ignore
    public String fetchBillNumExpiredNotNull(){
        if(billNumExpired != null) return billNumExpired;
        return "";
    }

    @Ignore
    public String taxslipresponseCodeNotNull(){
        if( taxslipresponseCode != null ) return taxslipresponseCode;
        return "";
    }

    @Ignore
    public ResponseSsp toResponseSsp(){
        ResponseSsp rs = new ResponseSsp();
        rs.id = id;

        TaxSlipResponse tsr = new TaxSlipResponse();
        tsr.idBilling = idBilling;
        tsr.expiredDate = billNumExpired;
        tsr.idbillingPajakku = idBillingPajakku;
        tsr.responseCode = taxslipresponseCode;
        tsr.responseDescription = taxslipresponseDesc;
        rs.taxSlipResponse = tsr;

        TaxReceipt tr = new TaxReceipt();
        tr.ntb = ntb;
        tr.ntpn = ntpn;
        tr.stan = "";
        tr.paymentDateTime = receiptPaydate;
        tr.bookDate = receiptBookdate;
        tr.providerBranchCode = receiptProvider;
        rs.taxReceipt = tr;

        Taxtype tt = new Taxtype();
        tt.code = taxTypeCode;
        tt.name = taxTypeName;
        rs.taxType = tt;

        Kjs kjs = new Kjs();
        kjs.code = kjsCode;
        kjs.name = kjsName;
        rs.taxSlipType = kjs;

        rs.month1 = Utility.padZeroMonth(month1);
        rs.month2 = Utility.padZeroMonth(month2);
        rs.npwp = npwp;
        rs.npwpPenyetor = npwpPenyetor;
        rs.name = name;
        rs.address = address;
        rs.year = Integer.toString(year);
        rs.amount = amount;
        rs.referenceNo = refNo;

        rs.idBilling = idBilling;
        rs.responseCode = taxslipresponseCode;
        rs.expiredDate = billNumExpired;
        rs.createdAt = createdAt;
        rs.updatedAt = updatedAt;
        rs.status = status;

        BillingDTO bill = new BillingDTO();
        bill.idBilling = idBilling;
        bill.expiredDate = billNumExpired;
        bill.responseCode = taxslipresponseCode;
        rs.billing = bill;

        MPNPaymentResponse pay = new MPNPaymentResponse();
        pay.transRefId = transRefId;
        pay.ntpn = ntpn;
        pay.ntb = ntb;
        pay.transactionDateTime = receiptPaydate;
        pay.mcashProvider = receiptProvider;
        rs.payment = pay;

        return rs;
    }

    @Override
    @Ignore
    public String toString() {
        return "Sspunpaid{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", npwp='" + npwp + '\'' +
                ", amount=" + amount +
                '}';
    }
}
