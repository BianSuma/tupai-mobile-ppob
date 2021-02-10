package com.pajakku.tupaimobile.api;

import com.pajakku.tupaimobile.model.dto.billing.RespKjs;
import com.pajakku.tupaimobile.model.dto.mpnpajakku.ReqMpnRefund;
import com.pajakku.tupaimobile.model.dto.mpnpajakku.RespMpnBillingStatus;
import com.pajakku.tupaimobile.util.AppConstant;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by dul on 17/12/18.
 */

// header stream list
//  x-pagination-page: 1
//  x-pagination-count: 33
//  x-pagination-limit: 50

// uri dont start with slash / (prefix)
public interface ApiCallMpnPajakku {
        @GET  // url ada ApiReqMpnPajakku.mpnGetStatus
        Call<RespMpnBillingStatus> mpnGetStatus(@Url String url, @Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Header("X-Client") String xClient);
        // {"_id":{"$oid":"5f449103e6c82bbd673d54f4"},"partnerCode":"900000000003","branchCode":"000001","currency":"IDR","gmt":"2020-08-25 13:30:25","localDatetime":"2020-08-25 13:30:25","settlementDate":"0825","transactionId":"000003","paymentCode":"124305809503026","billingInfo":{"billingInfo1":"999999999999999","billingInfo2":"100101","billingInfo3":"100","billingInfo4":"02022020","billingInfo5":"123456789012345","billingInfo6":"Jl. 2641 Kulas Knoll 3, Bandung","billingInfo7":"000000000000000000","billingInfo8":"","billingInfo9":"","billingInfo10":""},"amount":"11142","customerName":"Arden Weissnat","response":{"code":"02","messageEn":"Payment code expired.","messageId":"Kode billing kadaluarsa."},"ntb":"200825257023","status":"Waiting Refund","orderNo":"200825111829049","createdAt":{"$date":1598329091136},"createdBy":"abdul","updatedAt":{"$date":1598337031944},"updatedBy":"abdul"}

        @POST  // url ada ApiReqMpnPajakku.refund
        Call<ResponseBody> refund(@Url String url, @Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Header("X-Client") String xClient, @Body ReqMpnRefund body);
        //  {"_id":{"$oid":"5f44bed72a000039be0830be"},"paymentId":{"$oid":"5f449103e6c82bbd673d54f4"},"bankName":"BCA","bankCode":"014","accountNumber":"25809877","accountHolderName":"subhan","amount":11142,"description":"Kode billing kadaluarsa.","status":"Waiting Refund","createdBy":"abdul","createdAt":{"$date":1598340823076},"updatedBy":"abdul","updatedAt":{"$date":1598340823076}}
        // {"errorMessage":{"en":"Refund already exists.","id":"Data pengembalian sudah ada."},"createdAt":"2020-08-25T14:36:28.594"}

}
