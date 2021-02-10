package com.pajakku.tupaimobile.api;

import com.pajakku.tupaimobile.model.Wajibpajak;
import com.pajakku.tupaimobile.model.dto.CommonDTO;
import com.pajakku.tupaimobile.model.dto.TaxSlipResponse;
import com.pajakku.tupaimobile.model.dto.billing.BillingRetryDTO;
import com.pajakku.tupaimobile.model.dto.request.FirebaseToken;
import com.pajakku.tupaimobile.model.dto.request.RequestFinnet;
import com.pajakku.tupaimobile.model.dto.request.RequestRegister;
import com.pajakku.tupaimobile.model.dto.request.RequestResetPass;
import com.pajakku.tupaimobile.model.dto.request.RequestSsp;
import com.pajakku.tupaimobile.model.dto.request.UpdateTaxReceiptDTO;
import com.pajakku.tupaimobile.model.dto.response.AppStatusData;
import com.pajakku.tupaimobile.model.dto.response.ResponseAccessToken;
import com.pajakku.tupaimobile.model.dto.response.ResponseAuthorize;
import com.pajakku.tupaimobile.model.dto.response.ResponseFinnet;
import com.pajakku.tupaimobile.model.dto.response.ResponseMe;
import com.pajakku.tupaimobile.model.dto.response.ResponseRegister;
import com.pajakku.tupaimobile.model.dto.response.ResponseSsp;
import com.pajakku.tupaimobile.model.dto.response.TransLog;
import com.pajakku.tupaimobile.model.response.RespListTaxType;
import com.pajakku.tupaimobile.util.AppConstant;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dul on 17/12/18.
 */

// header stream list
//  x-pagination-page: 1
//  x-pagination-count: 33
//  x-pagination-limit: 50

// uri dont start with slash / (prefix)
public interface Http {
        @POST("api/account/register")
        Call<ResponseRegister> register(@Body RequestRegister requestRegister);

        @POST("api/account/forgot_password")
        Call<ResponseBody> resetPass(@Body RequestResetPass param);
        // invalid email: {"errorMessage":{"en":"User account to be activated using the email address is not found. Please provide the correct email address of your account","id":"User yang akan diaktivasi menggunakan email ini tidak ditemukan. Silahkan masukkan alamat email yg benar"},"createdAt":"2019-01-23T16:15:42.375"}
        // {"errorMessage":{"en":"Forgot password feature is disabled","id":"Fitur lupa password nonaktif"},"createdAt":"2019-03-28T18:37:10.699"}

        @POST("api/account/resend_activation")
        Call<ResponseBody> resendActivation(@Body RequestResetPass body);

        @FormUrlEncoded
        @POST("auth/oauth/authorize")
        Call<ResponseAuthorize> authorize(@Field("client_id") String clientId, @Field("redirect_uri") String redirectUri, @Field("state") String state,
                                          @Field("response_type") String responseType, @Field("username") String username, @Field("password") String password, @Field("grant_type") String grantType);
        // {"code":"FkytOuv8sepyXbX298s9kOWCszYpZOaV3p71627n","state":"state"}
        // incorrect username: {"error":"invalid_grant","error_description":"username or password is incorrect"}
        // incorrect password: {"error":"invalid_grant","error_description":"password is incorrect"}

        @FormUrlEncoded
        @POST("auth/oauth/access_token")
        Call<ResponseAccessToken> accessToken(@Field("client_id") String clientId, @Field("client_secret") String clientSecret, @Field("redirect_uri") String redirectUri,
                                              @Field("code") String code, @Field("state") String state, @Field("grant_type") String grantType, @Field("refresh_token") String refreshToken);
        // {"token_type":"Bearer","access_token":"iSKw1P0xO5fy7nK92HLedqe3JcSez6dtnZaEeIBg","expires_in":3599,"refresh_token":"cKgQ9Lt3qxf2b7Chilx7N78S8xK9XTU23GYObAfV"}

        @PUT("api/account/user/{userId}")
        Call<ResponseRegister> editProfile(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Path("userId") long userId, @Body RequestRegister d);

        @GET("auth/oauth/me")
        Call<ResponseMe> me(@Header(AppConstant.HEADER_AUTHORIZATION) String auth);
        // auth/oauth/me {"id":10,"username":"abdul","name":"abdul","email":"dullohmail@yahoo.com","mobilePhone":"08987020869","activationKey":"DNxw5yRnNpWbOArdRI40","expiredKey":"2019-03-30T14:53:25.858","servicePermission":[],"permission":[],"role":{"name":"ROLE_USER","isAdmin":false},"application":{"id":169,"userId":10,"applicationTypeId":6,"name":"Tupai Android","description":"Tupai Android","baseUrl":"/tupai_android","createdBy":"abdul","createdAt":"2020-08-26T16:18:31.226","updatedBy":"abdul","updatedAt":"2020-08-27T11:25:15.893"},"isActive":true,"isDelete":false,"createdBy":"abdul","createdAt":"2019-03-25T11:50:42.784","updatedBy":"SYSTEM","updatedAt":"2019-03-29T14:55:06.052"}
        // auth/oauth/product/me {"id":18,"username":"subhan","name":"subhan","email":"dul.greenlife@gmail.com","mobilePhone":"08987020111","activationKey":"vDXH21Ec3myiRomJoGa3","expiredKey":"2019-01-10T16:56:51.551","permission":[],"application":{"id":17,"userId":2,"applicationTypeId":3,"name":"Ebiling MPK","description":"Pajakku Ebiling App","createdBy":"satriapribadi","createdAt":"2018-12-17T15:16:19.022","updatedBy":"satriapribadi","updatedAt":"2018-12-17T15:16:19.022"},"product":{"id":11,"name":"Ebiling MPK","code":"MPK-001","url":"https://ebiling.pajakku.com","description":"Pajakku Ebiling App","status":true,"baseUrl":"/ebiling_mpk","consumeResources":[{"consumeType":"setCallback","productQuotaCode":"EbilingKuota-001","productUnitCode":"U-0001","resources":[{"id":30,"name":"Generate Id Billing","method":"POST","resourceUri":"/ssp/billingId","baseUrl":"/ebiling_mpk","parameters":[]},{"id":68,"name":"Create SSP with create idbilling","method":"POST","resourceUri":"/ssp/bill","baseUrl":"/ebiling_mpk","parameters":[]}]},{"consumeType":"rollback","productQuotaCode":"EbilingKuota-001","productUnitCode":"U-0001","resources":[{"id":31,"name":"Delete Ssp","method":"DELETE","resourceUri":"/ssp/{id}  ","baseUrl":"/ebiling_mpk","parameters":[{"id":21,"productResourceId":31,"name":"id","description":"id ssp","location":"path","dataType":"string","isMandatory":true}]}]}],"createdBy":"satriapribadi","createdAt":"2018-12-17T15:16:18.986","updatedBy":"satriapribadi","updatedAt":"2019-02-20T13:33:23.984"},"company":[{"id":29,"name":"Perusahaan Ebilling","address":"Jakarta","city":"Bandung","postalCode":"40293","fax":"087822231919","website":"ebilling.com","industry":"Pajak","phone":"087822231919","email":"sinatria25@gmail.com","npwp":"023986557031000","subscriptions":[{"id":323,"productPackageId":25,"companyId":29,"ratePlanId":6,"orderId":327,"status":"Active","effectiveStartDate":"2019-02-20T14:45:36.178","effectiveEndDate":"2020-02-20T14:45:36.178","wajibpajaks":[{"id":25,"companyId":29,"name":"PT Pemula","address":"jl. Anthorium AC no.5B","city":"Bandung","postalCode":"40293","phone":"087822231919","email":"sinatria25@gmail.com","npwp":"023986557031000","picName":"Jordy","picPosition":"Tester","picPhone":"087822231919","permission":[{"id":64,"name":"Finnet Get Balance","method":"GET","resourceUri":"/emon/get_balance","baseUrl":"/ebiling_mpk","parameters":[]},{"id":22,"name":"Update Tax Slip Type","method":"PUT","resourceUri":"/mastertax/taxSlipTypeCode/{id}","baseUrl":"/ebiling_mpk","parameters":[{"id":15,"productResourceId":22,"name":"id","description":"id tax slip code","location":"path","dataType":"string","isMandatory":true}]},{"id":16,"name":"Update Tax Type","method":"PUT","resourceUri":"/mastertax/taxTypeCode/{id}","baseUrl":"/ebiling_mpk","parameters":[{"id":9,"productResourceId":16,"name":"id","description":"id tax code","location":"path","dataType":"string","isMandatory":true}]},{"id":20,"name":"Get All Tax Slip Type by Tax Type","method":"GET","resourceUri":"/mastertax/taxSlipTypeCode/taxTypeCode/{id}","baseUrl":"/ebiling_mpk","parameters":[{"id":12,"productResourceId":20,"name":"id","description":"tax code id","location":"path","dataType":"string","isMandatory":true}]},{"id":59,"name":"Get TaxSlipType By CodeTaxType And Code","method":"GET","resourceUri":"/mastertax/taxSlipTypeCode/{taxTypesCode}/taxTypeCode/{code}","baseUrl":"/ebiling_mpk","parameters":[{"id":72,"productResourceId":59,"name":"code","description":"code","location":"path","dataType":"string","isMandatory":true},{"id":71,"productResourceId":59,"name":"taxTypesCode","description":"taxTypesCode","location":"path","dataType":"string","isMandatory":true}]},{"id":21,"name":"Get Tax Slip Type By Tax Type Id And Code","method":"GET","resourceUri":"/mastertax/taxSlipTypeCode/{id}/{code}","baseUrl":"/ebiling_mpk","parameters":[{"id":14,"productResourceId":21,"name":"code","description":"code tax slip code","location":"path","dataType":"string","isMandatory":true},{"id":13,"productResourceId":21,"name":"id","description":"id taxType","location":"path","dataType":"string","isMandatory":true}]},{"id":19,"name":"Get Tax Slip Type by Id","method":"GET","resourceUri":"/mastertax/taxSlipTypeCode/{id}","baseUrl":"/ebiling_mpk","parameters":[{"id":11,"productResourceId":19,"name":"id","description":"id tax slip code","location":"path","dataType":"string","isMandatory":true}]},{"id":62,"name":"Find All SSP Paid","method":"GET","resourceUri":"/ssp/paid","baseUrl":"/ebiling_mpk","parameters":[]},{"id":13,"name":"Get Tax Type by Id","method":"GET","resourceUri":"/mastertax/taxTypeCode/{id}","baseUrl":"/ebiling_mpk","parameters":[{"id":7,"productResourceId":13,"name":"ID","description":"Id tax code","location":"path","dataType":"string","isMandatory":true}]},{"id":12,"name":"Get All TaxType","method":"GET","resourceUri":"/mastertax/taxTypeCode","baseUrl":"/ebiling_mpk","parameters":[]},{"id":60,"name":"Cek TaxType dan TaxSlipType dengan list","method":"POST","resourceUri":"/mastertax/taxSlipTypeCode/cekKapKjs","baseUrl":"/ebiling_mpk","parameters":[]},{"id":14,"name":"Get Tax Type by Code","method":"GET","resourceUri":"/mastertax/taxTypeCode/{code}/code","baseUrl":"/ebiling_mpk","parameters":[{"id":8,"productResourceId":14,"name":"code","description":"code tax code","location":"path","dataType":"string","isMandatory":true}]},{"id":61,"name":"Find All SSP Unpaid","method":"GET","resourceUri":"/ssp/unpaid","baseUrl":"/ebiling_mpk","parameters":[]},{"id":18,"name":"Get All Tax Slip Type","method":"GET","resourceUri":"/mastertax/taxSlipTypeCode","baseUrl":"/ebiling_mpk","parameters":[]},{"id":69,"name":"Execute Generate Billing","method":"POST","resourceUri":"/ssp/execute","baseUrl":"/ebiling_mpk","parameters":[]},{"id":30,"name":"Generate Id Billing","method":"POST","resourceUri":"/ssp/billingId","baseUrl":"/ebiling_mpk","parameters":[]},{"id":34,"name":"Create Order","method":"POST","resourceUri":"/order","baseUrl":"/ebiling_mpk","parameters":[]},{"id":65,"name":"Cek Status ID Billing","method":"POST","resourceUri":"/ssp/checkStatus","baseUrl":"/ebiling_mpk","parameters":[{"id":73,"productResourceId":65,"name":"fileid","description":"test","location":"body","dataType":"string","isMandatory":true}]},{"id":68,"name":"Create SSP with create idbilling","method":"POST","resourceUri":"/ssp/bill","baseUrl":"/ebiling_mpk","parameters":[]},{"id":29,"name":"Create Ssp","method":"POST","resourceUri":"/ssp","baseUrl":"/ebiling_mpk","parameters":[]},{"id":24,"name":"Create Tax Slip Type","method":"POST","resourceUri":"/mastertax/taxSlipTypeCode","baseUrl":"/ebiling_mpk","parameters":[]},{"id":25,"name":"Get All Ssp","method":"GET","resourceUri":"/ssp","baseUrl":"/ebiling_mpk","parameters":[]},{"id":15,"name":"Create Tax Type","method":"POST","resourceUri":"/mastertax/taxTypeCode","baseUrl":"/ebiling_mpk","parameters":[]},{"id":31,"name":"Delete Ssp","method":"DELETE","resourceUri":"/ssp/{id}  ","baseUrl":"/ebiling_mpk","parameters":[{"id":21,"productResourceId":31,"name":"id","description":"id ssp","location":"path","dataType":"string","isMandatory":true}]},{"id":17,"name":"Delete Tax Type","method":"DELETE","resourceUri":"/mastertax/taxTypeCode/{id}","baseUrl":"/ebiling_mpk","parameters":[{"id":10,"productResourceId":17,"name":"id","description":"id tax code","location":"path","dataType":"string","isMandatory":true}]},{"id":41,"name":"Delete Activity Log","method":"DELETE","resourceUri":"/activityLog/{id}","baseUrl":"/ebiling_mpk","parameters":[{"id":30,"productResourceId":41,"name":"id","description":"id activity log","location":"path","dataType":"string","isMandatory":true}]},{"id":23,"name":"Delete Tax Slip Type","method":"DELETE","resourceUri":"/mastertax/taxSlipTypeCode/{id}","baseUrl":"/ebiling_mpk","parameters":[{"id":16,"productResourceId":23,"name":"id","description":"id tax slip code","location":"path","dataType":"string","isMandatory":true}]},{"id":36,"name":"Delete Order","method":"DELETE","resourceUri":"/order/{id}","baseUrl":"/ebiling_mpk","parameters":[{"id":24,"productResourceId":36,"name":"id","description":"id order","location":"path","dataType":"string","isMandatory":true}]}],"createdBy":"sinatria","createdAt":"2019-02-13T11:45:03.261","updatedBy":"sinatria","updatedAt":"2019-02-13T11:52:23.993"}],"createdAt":"2019-02-20T14:44:22.221","createdBy":"sinatria","updatedAt":"2019-02-20T14:44:22.221","updatedBy":"sinatria"}],"role":{"id":2,"name":"SUB ADMIN COMPANY","isAdmin":false},"isActive":true,"createdBy":"sinatria","createdAt":"2019-02-13T11:41:05.698","updatedBy":"sinatria","updatedAt":"2019-02-13T11:41:05.698"}],"isActive":false,"isDelete":false,"createdBy":"subhan","createdAt":"2019-01-09T16:56:51.551","updatedBy":"subhan","updatedAt":"2019-01-09T16:56:51.551"}

        @PUT(AppConstant.TUPAIBACKEND_PREFIXURL +"fcm")
        Call<ResponseBody> updateFirebaseToken(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Body FirebaseToken d);
        // {"deviceId":"c96s2dOksZM:APA91bH0FPJ0YBBwKV1sxRXBOK_snfh2tyd0Cg010YS2hLxNdXZN7hn2zrpbSDjE6_y9MtH97DdyLoFrqMSTZ83q2dvFgtBVwHnfAqGvNB0zynDGRQn497E5c5sSomE66XTNLAQcBX0J"}

        @POST("api/inquiry_wp")
        Call<Wajibpajak> inquiryWP(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Path("companyId") long companyId, @Body Wajibpajak wajibpajak);

        @Deprecated
        @POST(AppConstant.TUPAIBACKEND_PREFIXURL +"wp")
        Call<Wajibpajak> saveWP(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Body Wajibpajak wajibpajak);
        // {"id":3,"userId":18,"npwp":"666865855555565","name":"gilang","address":"ckl","city":"do"}

        @Deprecated
        @GET(AppConstant.TUPAIBACKEND_PREFIXURL +"wps")
        Call<List<Wajibpajak>> getWPList(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Query("page") long page, @Query("size") int size, @Query("field") String field, @Query("query") String query, @Query("order") String order, @Query("column") String column);
        // [{"id":1,"userId":18,"npwp":"668874407406000","name":"subhan","address":"dago","city":"bandung"}]

        @PUT(AppConstant.TUPAIBACKEND_PREFIXURL +"wp/{wpId}")
        Call<Wajibpajak> editOrganization(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Path("wpId") long wpId, @Body Wajibpajak wajibpajak);

        @Deprecated
        @DELETE(AppConstant.TUPAIBACKEND_PREFIXURL +"wp/{wpId}")
        Call<ResponseBody> deleteOrganization(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Path("wpId") long wpId);

        @POST(AppConstant.EBILLING_PREFIXURL+"updateTaxReceipt")
        Call<ResponseBody> updateTaxReceipt(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Body UpdateTaxReceiptDTO d);

//        @POST(AppConstant.EBILLING_PREFIXURL+"ssp/billingId")
//        Call<ResponseBody> generateIDBill(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Header("X-COMPANY-ID") long xCompanyId, @Body List<Long> d);

        @Deprecated
        @POST(AppConstant.TUPAIBACKEND_PREFIXURL +"ssp")
        Call<RequestSsp> createSsp(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Header("X-COMPANY-ID") long xCompanyId, @Header("X-SUBSCRIPTION-ID") long xSubscriptionId, @Body RequestSsp d);
        // {"errorMessage":{"en":"Product package not exists, please buy product package to continue.","id":"Paket produk tidak ditemukan, silakan Anda membeli paket produk untuk melanjutkan."},"createdAt":"2019-02-18T18:04:40.041"}
        // {"id":23,"userId":18,"taxType":{"id":4,"code":"411121","name":"PPh Pasal 21"},"taxSlipType":{"id":1,"code":"100","name":"Setoran Masa"},"month1":"03","month2":"03","npwp":"668874407406000","name":"subhan","address":"dago","city":"bandung","year":"2019","amount":1111,"npwpPenyetor":"668874407406000","referenceNo":"190322195789801"}
        // header:
        // X-Pajakku-ConsumeID: a3ad3758-c56a-4f2d-8b12-b7b1ad175a40
//        X-Pajakku-Proxy-Latency: 1093
//        X-Pajakku-Upstream-Latency: 2665

        @GET(AppConstant.TUPAIBACKEND_PREFIXURL +"ssps")
        Call<List<ResponseSsp>> getSspList(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Query("order") String order, @Query("column") String column,
                                           @Query("page") long page, @Query("size") int size, @Query("field") String field, @Query("query") String query, @Query("isPaid") boolean isPaid);
        // {"errorMessage":{"en":"Sandbox mode service not found. Please contact your administrator!","id":"Service dengan mode sandbox tidak ditemukan. Silakan hubungi admin Anda!"},"createdAt":"2020-08-02T20:44:00.535"}
        //  [{"id":8,"userId":18,"taxType":{"id":1,"sspId":8,"code":"411121","name":"PPh Pasal 21"},"taxSlipType":{"id":1,"sspId":8,"code":"200","name":"Tahunan"},"month1":"03","month2":"03","npwp":"668874407406000","name":"subhan","address":"cigadung","city":"bandung","year":"2019","amount":5900,"npwpPenyetor":"668874407406000","referenceNo":"190320210409892"}]
        // [{"id":1,"userId":18,"taxType":{"id":1,"sspId":1,"code":"411121","name":"PPh Pasal 21"},"taxSlipType":{"id":1,"sspId":1,"code":"100","name":"Setoran Masa"},"month1":"03","month2":"03","npwp":"668874407406000","name":"subhan","address":"dago","city":"bandung","year":"2019","amount":30000,"npwpPenyetor":"668874407406000","referenceNo":"190320221791571","idBilling":"122605780656088","responseCode":"00","expiredDate":"2019-04-19T22:17:25"}]
        // paid: [{"id":7,"userId":2,"taxType":{"id":3,"sspId":7,"code":"411121","name":"PPh Pasal 21"},"taxSlipType":{"id":3,"sspId":7,"code":"100","name":"Setoran Masa"},"month1":"03","month2":"03","npwp":"706295649063000","name":"Satria Pribadi","address":"Jl. Sukajadi GG. Eme Atas No 39 RT/RW 10/04","city":"Bandung","year":"2019","amount":25000,"npwpPenyetor":"706295649063000","referenceNo":"190325202281201","billing":{"id":3,"sspId":7,"idBilling":"118120000441100","responseCode":"00","expiredDate":"2019-04-24T20:22:51"},"payment":{"id":1,"sspId":7,"ntpn":"01B9D03BDNQALIP1","transactionDateTime":"2019-03-25T20:31:39.236"},"createdAt":"2019-03-25T20:22:44.168","updatedAt":"2019-03-25T20:22:44.168"}]

        @DELETE(AppConstant.TUPAIBACKEND_PREFIXURL +"ssp/{sspId}")
        Call<ResponseBody> deleteSSP(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Header(AppConstant.XPAJAKKU_CONSUME_ID) String consumeId, @Path("sspId") long sspId);
        // {"errorMessage":{"en":"X-Pajakku-ConsumeID not found.","id":"X-Pajakku-ConsumeID tidak ditemukan."},"createdAt":"2019-02-18T17:17:46.658"}

        @GET(AppConstant.TUPAIBACKEND_PREFIXURL +"ssp/{sspId}")
        Call<ResponseSsp> getSingleSSP(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Path("sspId") Long sspId);
        // {"id":9,"userId":10,"taxType":{"id":9,"sspId":9,"code":"411121","name":"PPh Pasal 21"},"taxSlipType":{"id":9,"sspId":9,"code":"100","name":"Setoran Masa"},"month1":"03","month2":"03","npwp":"668874407406000","name":"subhan","address":"dago","city":"bandung","year":"2019","amount":25000,"npwpPenyetor":"668874407406000","referenceNo":"190326023799203","billing":{"id":9,"sspId":9,"idBilling":"122605780980039","responseCode":"00","expiredDate":"2019-04-25T02:37:39"},"createdAt":"2019-03-26T02:37:33.556","updatedAt":"2019-03-26T02:37:33.556"}

        @GET(AppConstant.TUPAIBACKEND_PREFIXURL +"ssp/{sspId}/pay")
        Call<ResponseSsp> paySSP(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Path("sspId") long sspId);
        // {"id":7,"userId":2,"taxType":{"id":3,"sspId":7,"code":"411121","name":"PPh Pasal 21"},"taxSlipType":{"id":3,"sspId":7,"code":"100","name":"Setoran Masa"},"month1":"03","month2":"03","npwp":"706295649063000","name":"Satria Pribadi","address":"Jl. Sukajadi GG. Eme Atas No 39 RT/RW 10/04","city":"Bandung","year":"2019","amount":25000,"npwpPenyetor":"706295649063000","referenceNo":"190325202281201","billing":{"id":3,"sspId":7,"idBilling":"118120000441100","responseCode":"00","expiredDate":"2019-04-24T20:22:51"},"payment":{"id":2,"sspId":7,"ntpn":"01B9D03BDNQALIP1","transactionDateTime":"2019-03-25T20:32:57.795"},"createdAt":"2019-03-25T20:22:44.168","updatedAt":"2019-03-25T20:22:44.168"}

        @POST(AppConstant.TUPAIBACKEND_PREFIXURL +"transaction_log/{sspId}")
        Call<TransLog> startTransaction(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Path("sspId") long sspId);

        @POST(AppConstant.TUPAIBACKEND_PREFIXURL +"common")
        Call<ResponseBody> common(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Body CommonDTO body);

        @Multipart
        @POST(AppConstant.TUPAIBACKEND_PREFIXURL +"common_upload")
        Call<ResponseBody> commonUpload(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Part MultipartBody.Part code,
                                        @Part MultipartBody.Part file,
                                        @Part MultipartBody.Part json);

//        @GET(AppConstant.EBILLING_PREFIXURL+"mastertax/taxType")
        @GET(AppConstant.EBILLING_INTERNAL_PREFIXURL+"kjp/allActive")
//        Call<ResponseBody> getTaxType(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Header("X-COMPANY-ID") long xCompanyId,
//                                      @Header("X-SUBSCRIPTION-ID") long xSubscriptionId, @Query("page") long page, @Query("size") int size);
        Call<List<RespListTaxType>> getTaxType(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Query("page") Long page, @Query("size") Integer size);
        // old {"id":1,"code":"411111","name":"PPh Minyak Bumi","createdBy":"SYSTEM","createdAt":"2019-01-08T16:21:24.307692","updatedBy":"SYSTEM","updatedAt":"2019-01-08T16:21:24.307692"}...
        // new [{"createdBy":"system","createdDate":"2020-07-12 22:15","lastModifiedBy":"system","lastModifiedDate":"2020-07-12 22:15","id":"05137740-fc98-11e9-aad5-362b9e155667","code":"411111","name":"PPh Minyak Bumi","active":true}]

        @GET(AppConstant.EBILLING_PREFIXURL+"mastertax/taxSlipType/taxType/{taxTypeId}")
        Call<ResponseBody> getTaxSlipType(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Path("taxTypeId") long taxTypeId, @Query("page") long page, @Query("size") int size);
        // {"id":1,"taxTypesId":1,"code":"100","name":"Setoran Masa","month1Active":true,"month2Active":false,"nopActive":false,"noSkActive":false,"subjekPajakActive":false,"createdBy":"SYSTEM","createdAt":"2019-01-08T16:21:24.681292","updatedBy":"SYSTEM","updatedAt":"2019-01-08T16:21:24.681292"}{"id":2,"taxTypesId":1,"code":"300","name":"STP","month1Active":true,"month2Active":true,"nopActive":false,"noSkActive":true,"subjekPajakActive":false,"kodeSkAwal":1,"createdBy":"SYSTEM","createdAt":"2019-01-08T16:21:24.681292","updatedBy":"SYSTEM","updatedAt":"2019-01-08T16:21:24.681292"}{"id":3,"taxTypesId":1,"code":"310","name":"SKPKB","month1Active":true,"month2Active":true,"nopActive":false,"noSkActive":true,"subjekPajakActive":false,"kodeSkAwal":2,"createdBy":"SYSTEM","createdAt":"2019-01-08T16:21:24.681292","updatedBy":"SYSTEM","updatedAt":"2019-01-08T16:21:24.681292"}{"id":4,"taxTypesId":1,"code":"320","name":"SKPKBT","month1Active":true,"month2Active":true,"nopActive":false,"noSkActive":true,"subjekPajakActive":false,"kodeSkAwal":3,"createdBy":"SYSTEM","createdAt":"2019-01-08T16:21:24.681292","updatedBy":"SYSTEM","updatedAt":"2019-01-08T16:21:24.681292"}{"id":5,"taxTypesId":1,"code":"390","name":"SK Pembetul/Keberatan/Banding","month1Active":true,"month2Active":true,"nopActive":false,"noSkActive":false,"subjekPajakActive":false,"createdBy":"SYSTEM","createdAt":"2019-01-08T16:21:24.681292","updatedBy":"SYSTEM","updatedAt":"2019-01-08T16:21:24.681292"}

        @GET(AppConstant.TUPAIBACKEND_PREFIXURL+"ssp/{sspId}/get_id_billing/{refNo}")
        Call<BillingRetryDTO> executeBilling(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Header(AppConstant.XPAJAKKU_CONSUME_ID) String consumeId, @Path("sspId") long sspId, @Path("refNo") String refNo);
        // 2020_0805 {"id":4,"userId":10,"taxType":{"id":4,"sspId":4,"code":"411128","name":"420"},"taxSlipType":{"id":4,"sspId":4,"code":"420","name":"420"},"month1":"08","month2":"08","npwp":"668874407406000","name":"subhan","address":"cianjur","city":"cianjur","year":"2020","amount":102,"npwpPenyetor":"668874407406000","referenceNo":"BIV2NU3S3FM36DYD","billing":{"id":4,"sspId":4,"idBilling":"124305248538022","responseCode":"","expiredDate":"2020-09-04T09:48:00"},"status":"READY_PAY","createdAt":"2020-08-05T09:48:50.967","updatedAt":"2020-08-05T10:15:50.138"}
        // {"errorMessage":{"en":"You dont have permission, dont have organization to manage or service not found for URL /ebiling_mpk/checkStatus/69.","id":"Anda tidak memiliki permission, tidak memilik organisasi untuk di kelola atau layanan tidak ditemukan untuk URL /ebiling_mpk/checkStatus/69."},"createdAt":"2019-02-26T14:21:51.57"}
        // {"id":186,"taxType":{"id":1,"code":"411111","name":"PPh Minyak Bumi","createdBy":"SYSTEM","createdAt":"2019-01-17T12:04:16.669422","updatedBy":"SYSTEM","updatedAt":"2019-01-17T12:04:16.669422"},"taxSlipType":{"id":1,"taxTypesId":1,"code":"100","name":"Setoran Masa","month1Active":true,"month2Active":false,"nopActive":false,"noSkActive":false,"subjekPajakActive":false,"createdBy":"SYSTEM","createdAt":"2019-01-17T12:04:16.911307","updatedBy":"SYSTEM","updatedAt":"2019-01-17T12:04:16.911307"},"taxSlipResponse":{"id":1,"taxSlipsId":186,"idBilling":"Setoran Pajak untuk WP OP","expiredDate":"2019-03-15T13:54:02.424","idBillingPajakku":"Setoran Pajak untuk WP OP","expiredDatePajakku":"2019-03-15T13:54:02.424","responseCode":"41","responseDescription":"Setoran Pajak untuk WP OP","ambil":true,"createdBy":"user","createdAt":"2019-03-15T13:54:02.424","updatedBy":"user","updatedAt":"2019-03-15T13:54:02.424"},"month1":1,"month2":1,"code":"oRyw9VENPzqQ37Lk","npwp":"668874407406000","name":"NPWP Asli","address":"Cigadung","city":"Bandung","year":"2019","amount":3000,"npwpPenyetor":"668874407406000","orders":false,"audit":{"status":"IdBilling","createdBy":"user","createdAt":"2019-03-15T13:53:37.117","updatedBy":"user","updatedAt":"2019-03-15T13:53:37.117"}}
        // {"id":187,"taxType":{"id":4,"code":"411121","name":"PPh Pasal 21","createdBy":"SYSTEM","createdAt":"2019-01-17T12:04:16.669422","updatedBy":"SYSTEM","updatedAt":"2019-01-17T12:04:16.669422"},"taxSlipType":{"id":16,"taxTypesId":4,"code":"100","name":"Masa / Angsuran","month1Active":true,"month2Active":false,"nopActive":false,"noSkActive":false,"subjekPajakActive":false,"kodeForm":"F1132010413","createdBy":"SYSTEM","createdAt":"2019-01-17T12:04:16.911307","updatedBy":"SYSTEM","updatedAt":"2019-01-17T12:04:16.911307"},"taxSlipResponse":{"id":2,"taxSlipsId":187,"idBilling":"122605780596028","expiredDate":"2019-04-14T13:58:35","idBillingPajakku":"1HAXiVHoR3IjgdaK","expiredDatePajakku":"2019-04-14T13:58:35","responseCode":"00","responseDescription":"Pembuatan kode billing berhasil","ambil":true,"createdBy":"user","createdAt":"2019-03-15T13:58:40.66","updatedBy":"user","updatedAt":"2019-03-15T13:58:40.66"},"month1":1,"month2":1,"code":"mrxV48ESewl2uyPc","npwp":"668874407406000","name":"NPWP Asli","address":"Cigadung","city":"Bandung","year":"2019","amount":200,"npwpPenyetor":"668874407406000","orders":false,"audit":{"status":"IdBilling","createdBy":"user","createdAt":"2019-03-15T13:58:31.991","updatedBy":"user","updatedAt":"2019-03-15T13:58:31.991"}}
        // {"id":11,"taxSlipsId":25,"idBilling":"122605780655082","expiredDate":"2019-04-19T21:44:39","idBillingPajakku":"XcWMVIzh5iMoKm35","expiredDatePajakku":"2019-04-19T21:44:39","responseCode":"00","responseDescription":"Pembuatan kode billing berhasil","ambil":true,"createdBy":"subhan","createdAt":"2019-03-20T21:44:59.816","updatedBy":"subhan","updatedAt":"2019-03-20T21:44:59.816"}

        @POST(AppConstant.EBILLING_PREFIXURL+"tupai/gateway")
        Call<ResponseBody> callUs(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Body RequestFinnet wp);


        @GET(AppConstant.TUPAIBACKEND_PREFIXURL +"status_data")
        Call<AppStatusData> getStatusData(@Header(AppConstant.HEADER_AUTHORIZATION) String auth);

        @POST(AppConstant.TUPAIBACKEND_PREFIXURL +"spt")
        Call<ResponseBody> reportSPT(@Header(AppConstant.HEADER_AUTHORIZATION) String auth);

        // mobile cash service

        @GET("finnet/mcash/widgetTopUp")
        Call<ResponseFinnet> getTopupLink(@Header(AppConstant.HEADER_AUTHORIZATION) String auth);

        @GET("finnet/mcash/balance")
        Call<ResponseFinnet> mcBalance(@Header(AppConstant.HEADER_AUTHORIZATION) String auth);
        // {"statusCode":"201","statusDesc":"Maaf, Harap aktivasi nomor anda terlebih dahulu sebelum melakukan transaksi ini -7012"}

        @GET("finnet/mcash/confirmation/{"+AppConstant.REQUESTPATH_OTP+"}/{"+AppConstant.REQUESTPATH_CUSTSTATUSCODE+"}")
        Call<ResponseFinnet> sendEmonOTP(@Header(AppConstant.HEADER_AUTHORIZATION) String auth, @Path(AppConstant.REQUESTPATH_OTP) String otp, @Path(AppConstant.REQUESTPATH_CUSTSTATUSCODE) String custStatusCode);
        // {"statusCode":105,"statusDesc":"Access Denied: Invalid Data Type custStatusCode"}

        @GET("finnet/mcash/activation")
        Call<ResponseFinnet> emonRegisterPhone(@Header(AppConstant.HEADER_AUTHORIZATION) String auth);
        // {"statusCode":"000","statusDesc":"Success","custStatusCode":"003","custStatusDesc":"Customer is registered & paired"}


}
