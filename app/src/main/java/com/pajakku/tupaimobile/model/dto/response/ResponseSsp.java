package com.pajakku.tupaimobile.model.dto.response;

import android.content.Context;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.model.Kjs;
import com.pajakku.tupaimobile.model.Sspdone;
import com.pajakku.tupaimobile.model.Sspunpaid;
import com.pajakku.tupaimobile.model.Taxtype;
import com.pajakku.tupaimobile.model.dto.ModelMultiSelect;
import com.pajakku.tupaimobile.model.dto.TaxReceipt;
import com.pajakku.tupaimobile.model.dto.TaxSlipResponse;
import com.pajakku.tupaimobile.model.dto.TaxtypeAlias;
import com.pajakku.tupaimobile.util.DateFunc;
import com.pajakku.tupaimobile.util.Utility;

/**
 * Created by dul on 18/12/18.
 */

public class ResponseSsp extends ModelMultiSelect {

    public static final String STATUS_NO_ID_BILLING = "NO_ID_BILLING";
    public static final String STATUS_READY_PAY = "READY_PAY";
    public static final String STATUS_ONGOING_PAY = "ONGOING_PAY";  //  sedang dalam proses bayar
    public static final String STATUS_NEED_REFUND = "NEED_REFUND";
    public static final String STATUS_REFUNDED = "REFUNDED";
    public static final String STATUS_LUNAS = "LUNAS";
    public static final String STATUS_MPN_PAY_EXPIRED = "MPN_PAY_EXPIRED";
    public static final String STATUS_MPN_PAY_CANCEL = "MPN_PAY_CANCEL";
    public static final String STATUS_SSP_INVALID = "SSP_INVALID";

    public long id;
    public TaxSlipResponse taxSlipResponse;
    @Deprecated
    public TaxReceipt taxReceipt;
    public Taxtype taxType;
//    public TaxSlipDetail taxSlipDetail;
    public Kjs taxSlipType;
    public String month1;
    public String month2;
    public String npwp;
    public String npwpPenyetor;
    public String name;
    public String address;
    public String nop;
    public String noSk;
    private String city;
    public String year;
    public long amount;
    private boolean orders;
    public String referenceNo;
//    public AuditDTO audit;
//    private boolean selected = false;
    @Deprecated  // id billing bisa didapat dari 'BillingDTO billing'
    public String idBilling;
    public String responseCode;
    public String expiredDate;
    public String updatedAt;
    public String createdAt;
    public String status;
    public BillingDTO billing;
    public MPNPaymentResponse payment;
    public PayInfo payInfo;

    public ResponseSsp() {
        taxSlipResponse = new TaxSlipResponse();
        taxType = new Taxtype();
        taxSlipType = new Kjs();
        billing = new BillingDTO();
        payment = new MPNPaymentResponse();
        payInfo = new PayInfo();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String fetchTaxPeriod(Context context){
        String m = context.getString(Utility.toMonthShort(month1));
        if( ! month1NotNull().equals(month2NotNull()) ){
            m = m + " - " + context.getString(Utility.toMonthShort(month2));
        }
        return m + " " + year;
    }

    public String fetchTaxPeriodLong(){
        String m = DateFunc.toMonthLong(month1);
        if( ! month1NotNull().equals(month2NotNull()) ){
            m = m + " - " + DateFunc.toMonthLong(month2);
        }
        return m + " " + year;
    }

    public String taxPeriodNumber(){
        return (month1!=null?month1:"00") + (month2!=null?month2:"00") + year;
    }

    public String fetchIdBilling(){
        if(taxSlipResponse == null) return null;
        return taxSlipResponse.idBilling;
    }

    public String fetchIdBillingPajakku(){
        if(taxSlipResponse == null) return null;
        return taxSlipResponse.idbillingPajakku;
    }

    public TaxReceipt fetchDefaultTaxReceipt(){
        if(taxReceipt != null) return taxReceipt;
        TaxReceipt tr = new TaxReceipt();
        tr.ntb = "";
        tr.ntpn = "";
        tr.stan = "";
        return tr;
    }

    public String fetchKapKjs(Context context){
        return context.getString(R.string.sspdata_kapkjs, taxType.codeNotNull(), taxType.nameNotNull(), taxSlipType.codeNotNull(), taxSlipType.nameNotNull());
    }

    public String statusShort(){
        if(payment.status != null){
            return payment.status;
        }

        if( ! billing.idBillingNotNull().isEmpty() ){
            if( billing.isExpired() ) return "Kode Billing kadaluarsa";
            else return "Kode Billing siap dibayar";
        }else{
            if(billing.status != null) return billing.status;
        }

        if(statusNotNull().equalsIgnoreCase(ResponseSsp.STATUS_ONGOING_PAY)){
            return "Pembayaran belum selesai";
        }else if(statusNotNull().equalsIgnoreCase(ResponseSsp.STATUS_NEED_REFUND)){
            return "Perlu di-refund";
        }

        if(status != null){
            if(status.equalsIgnoreCase(STATUS_NO_ID_BILLING)) return "Dlm Permintaan Kode Billing";

            return status;
        }

        return "";
    }

    // -------------- NOT NULL

    public String referenceNoNotNull(){
        if(referenceNo == null) return "";
        return referenceNo;
    }

    public String month1NotNull(){
        if(month1 == null) return "";
        return month1;
    }

    public String month2NotNull(){
        if(month2 == null) return "";
        return month2;
    }

    public String nopZero(){
        if(nop == null) return "000000000000000000";
        return nop;
    }

    public String noSkZero(){
        if(noSk == null) return "000000000000000";
        return noSk;
    }

    public String statusNotNull(){
        if(status == null) return "";
        return status;
    }

    public String npwpPenyetorNotNull(){
        if(npwpPenyetor == null) return "";
        return npwpPenyetor;
    }

    public String updatedAtNotNull(){
        if(updatedAt != null) return updatedAt;
        return "";
    }

    // -------------

    public boolean isCanPay(){
        return ! billing.idBillingNotNull().isEmpty() && payment.statusNotNull().isEmpty() &&
                ! billing.isExpired();
    }

    public boolean isPayProgress(){
        return ! payment.statusNotNull().isEmpty() && ! payment.statusNotNull().equalsIgnoreCase(MPNPaymentResponse.BIL_WAITING_REFUND) &&
                ! isRefunded();
    }

    public boolean isRefunded(){
        return payment.statusNotNull().equalsIgnoreCase(MPNPaymentResponse.BIL_REFUNDED);
    }

    public boolean isShowRefundBtn(){
        return payment.statusNotNull().equalsIgnoreCase(MPNPaymentResponse.BIL_WAITING_REFUND);
    }

    public int fetchIcon(){
        for(TaxtypeAlias tta : Taxtype.taxtypeAliases){
            if(tta.code.equalsIgnoreCase( taxType.code )){
                if(tta.icon != 0) return tta.icon;
            }
        }
        return R.drawable.ic_taxmisc;
    }

    // ------------- CONVERT

    public boolean createdAtAgo(long minute){
//        2020-08-11T14:29:26.883
        long createLong = DateFunc.dateToLongNotNull(createdAt);
        Utility.log("createdAtAgo "+DateFunc.getAgo(createLong)+" - "+createdAt);
        return ( System.currentTimeMillis() - createLong > (minute*60*1000) );
    }

    public Sspunpaid toSspunpaid(){
        Sspunpaid s = new Sspunpaid();
        s.id = id;
        s.name = name;
        s.npwp = npwp;
        s.address = address;
        s.amount = amount;
        s.idBilling = billing.idBilling;
        s.billNumExpired = billing.expiredDate;
        s.idBillingPajakku = fetchIdBillingPajakku();
        s.taxslipresponseCode = billing.responseCode;
        s.taxslipresponseDesc = idBilling;
        s.month1 = Integer.valueOf(month1);
        s.month2 = Integer.valueOf(month2);
        s.year = Integer.parseInt(year);
        s.taxTypeCode = taxType.code;
        s.taxTypeName = taxType.name;
        s.kjsCode = taxSlipType.code;
        s.kjsName = taxSlipType.name;
        s.createdAt = createdAt;
        s.updatedAt = updatedAt;
        s.transRefId = payment.transRefId;
        s.ntb = payment.ntb;
        s.ntpn = payment.ntpn;
        s.receiptPaydate = fetchDefaultTaxReceipt().paymentDateTime;
        s.receiptBookdate = fetchDefaultTaxReceipt().bookDate;
        s.receiptProvider = payment.mcashProvider;
        s.refNo = referenceNo;
        s.npwpPenyetor = npwpPenyetor;
        s.noSk = null;
        s.nop = null;
        s.status = statusShort();
        return s;
    }

    public Sspdone toSspdone(){
        Sspdone s = new Sspdone();
        s.id = id;
        s.name = name;
        s.npwp = npwp;
        s.address = address;
        s.amount = amount;
        s.idBilling = billing.idBilling;
        s.billNumExpired = billing.expiredDate;
        s.idBillingPajakku = fetchIdBillingPajakku();
        s.taxslipresponseCode = billing.responseCode;
        s.taxslipresponseDesc = idBilling;
        s.month1 = Integer.valueOf( month1 );
        s.month2 = Integer.valueOf( month2 );
        s.year = Integer.parseInt(year);
        s.taxTypeCode = taxType.code;
        s.taxTypeName = taxType.name;
        s.kjsCode = taxSlipType.code;
        s.kjsName = taxSlipType.name;
        s.transRefId = payment.transRefId;
        s.ntb = payment.ntb;
        s.ntpn = payment.ntpn;
        s.createdAt = createdAt;
        s.updatedAt = updatedAt;
        s.receiptPaydate = payment.transactionDateTime;
        s.receiptBookdate = fetchDefaultTaxReceipt().bookDate;
        s.receiptProvider = payment.mcashProvider;
        s.refNo = referenceNo;
        s.npwpPenyetor = npwpPenyetor;
        s.noSk = null;
        s.nop = null;
        s.status = statusShort();
        return s;
    }
}
