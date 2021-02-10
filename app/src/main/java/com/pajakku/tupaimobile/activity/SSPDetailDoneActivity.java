package com.pajakku.tupaimobile.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.api.ApiMain;
import com.pajakku.tupaimobile.api.ApiReqWrapMpnPajakku;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.AppButton;
import com.pajakku.tupaimobile.component.AppProgressDialog;
import com.pajakku.tupaimobile.component.SSPDetailRowInfo;
import com.pajakku.tupaimobile.model.Sspdone;
import com.pajakku.tupaimobile.model.actdata.ActDataSspDetailDone;
import com.pajakku.tupaimobile.model.actdata.ActDataSspDetailUnpaid;
import com.pajakku.tupaimobile.model.actparam.ActParamSspDetailDone;
import com.pajakku.tupaimobile.model.actparam.ActParamSspDetailUnpaid;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.ResultCreatePdf;
import com.pajakku.tupaimobile.model.dto.response.MPNPaymentResponse;
import com.pajakku.tupaimobile.model.dto.response.ResponseSsp;
import com.pajakku.tupaimobile.tester.AppTester;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.DateFunc;
import com.pajakku.tupaimobile.util.Utility;
import com.pajakku.tupaimobile.util.pdfwriter.PDFWriter;
import com.pajakku.tupaimobile.util.pdfwriter.PaperSize;
import com.pajakku.tupaimobile.util.pdfwriter.StandardFonts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class SSPDetailDoneActivity extends BaseActivity {
    private static final String ACTDATA_KEY = AppConstant.SP_DETAILACTIVIY_SSPDONE;

    private ActDataSspDetailDone actData;
//    private Sspdone sspdone;

    private SwipeRefreshLayout swipeRefreshLayout;

    private AppCompatTextView lblStatus;

    private SSPDetailRowInfo rowInfoNtpn;
    private SSPDetailRowInfo rowInfoNpwp;
    private SSPDetailRowInfo rowInfoPenyetor;
    private SSPDetailRowInfo rowInfoRefNo;
    private SSPDetailRowInfo rowInfoWpname;
    private SSPDetailRowInfo rowInfoAddress;
    private SSPDetailRowInfo rowInfoKapkjs;
    private SSPDetailRowInfo rowInfoTaxPeriod;
    private SSPDetailRowInfo rowInfoSetor;
    private SSPDetailRowInfo rowInfoBillCode;
    private SSPDetailRowInfo rowInfoNtb;
    private SSPDetailRowInfo rowInfoPaydate;
    private SSPDetailRowInfo rowInfoBookdate;
    private SSPDetailRowInfo rowInfoPayProvider;

    private AppCompatButton btnCekPayStatus;
    private AppButton btnGeneratePdf;
    private AppCompatButton btnReloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity context = this;
        setContentView(R.layout.activity_sspdetail_done);

        initData(savedInstanceState);

//        sspdone = (sspdone != null ? sspdone : new Sspdone());

        final AppActionBar actionBar = findViewById(R.id.sspdetaildone_actionbar);
        actionBar.setBackFinish(this);

        swipeRefreshLayout = findViewById(R.id.fraghome_swipyrefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cekPayStatus();
            }
        });

        lblStatus = findViewById(R.id.sspdetailfinish_lbl_status);

        rowInfoNtb = findViewById(R.id.sspdetailfinish_label_ntb);
        rowInfoNtpn = findViewById(R.id.sspdetailfinish_label_ntpn);
        rowInfoNpwp = findViewById(R.id.sspdetailfinish_label_npwp);
        rowInfoPenyetor = findViewById(R.id.sspdetailfinish_label_npwppenyetor);
        rowInfoRefNo = findViewById(R.id.sspdetailfinish_label_refno);
        rowInfoWpname = findViewById(R.id.sspdetailfinish_label_wpname);
        rowInfoAddress = findViewById(R.id.sspdetailfinish_label_address);
        rowInfoKapkjs = findViewById(R.id.sspdetailfinish_label_kapkjs);
        rowInfoTaxPeriod = findViewById(R.id.sspdetailfinish_label_taxperiod);
        rowInfoSetor = findViewById(R.id.sspdetailfinish_label_setor);
        rowInfoBillCode = findViewById(R.id.sspdetailfinish_label_billcode);
        rowInfoPaydate = findViewById(R.id.sspdetailfinish_label_paydate);
        rowInfoBookdate = findViewById(R.id.sspdetailfinish_label_bookdate);
        rowInfoPayProvider = findViewById(R.id.sspdetailfinish_label_payprovider);

        btnCekPayStatus = findViewById(R.id.sspdetaildone_btn_cek_pay_status);
        btnCekPayStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekPayStatus();
            }
        });

        btnGeneratePdf = findViewById(R.id.sspdetaildone_btn_createpdf);
        btnGeneratePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPdfPermission();
            }
        });

        btnReloader = findViewById(R.id.sspdetaildone_btn_reloader);
        btnReloader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewData();
            }
        });

    }

    private void initData(Bundle savedInstanceState){
        if (savedInstanceState == null) {
            Intent itn = getIntent();
//            sspdone = (Sspdone) itn.getSerializableExtra(ACTDATA_KEY);
            ActParamSspDetailDone ap = (ActParamSspDetailDone) itn.getSerializableExtra(ACTDATA_KEY);

            actData = new ActDataSspDetailDone();
            actData.ap = ap;
        } else {
//            sspdone = (Sspdone) savedInstanceState.getSerializable(ACTDATA_KEY);
            actData = (ActDataSspDetailDone) savedInstanceState.getSerializable(ACTDATA_KEY);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

//        outState.putSerializable(ACTDATA_KEY, sspdone);
        outState.putSerializable(ACTDATA_KEY, actData);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case AppConstant.RP_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createPdf();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    protected void setViewData() {
        RequestParamConfig rpc = new RequestParamConfig();
        rpc.isRelogin = true;
        rpc.progressTextRes = R.string.progressdialog_common;
        rpc.reloader = btnReloader;
        ApiMain.getSingleSSPX(this, rpc, actData.ap.sspId,
                new com.pajakku.tupaimobile.ifc.SuccessFailCallback<ResponseSsp, ResponseDTO>() {
                    @Override
                    public void onSuccess(ResponseSsp data) {
                        actData.responseSsp = data;
                        setViewData(actData.responseSsp);
                    }

                    @Override
                    public void onFail(ResponseDTO data) {
                        btnCekPayStatus.setVisibility(View.GONE);
                        btnGeneratePdf.setVisibility(View.GONE);
                    }
                });
    }

    protected void setViewData(ResponseSsp responseSsp) {

        lblStatus.setVisibility( responseSsp.payment.ntpnNotNull().isEmpty() ? View.VISIBLE : View.GONE );

        rowInfoNtpn.init( responseSsp.payment.ntpnNotNull().isEmpty() ? "----" : responseSsp.payment.ntpnNotNull() );
        rowInfoNpwp.init(Utility.toPrettyNpwp(responseSsp.npwp));
        if (responseSsp.npwpPenyetorNotNull().isEmpty()) {
            rowInfoPenyetor.setVisibility(View.GONE);
        } else {
            rowInfoPenyetor.init(Utility.toPrettyNpwp(responseSsp.npwpPenyetorNotNull()));
            rowInfoPenyetor.setVisibility(View.VISIBLE);
        }
        rowInfoRefNo.init(responseSsp.referenceNo);
        rowInfoWpname.init(responseSsp.name);
        rowInfoAddress.init(responseSsp.address);
        rowInfoKapkjs.init(responseSsp.fetchKapKjs(this));
        rowInfoTaxPeriod.init(responseSsp.fetchTaxPeriod(this));
        rowInfoSetor.init(Utility.toMoney(true, responseSsp.amount));
        rowInfoBillCode.init(responseSsp.billing.idBillingNotNull());
        rowInfoNtb.init(responseSsp.payment.ntbNotNull().isEmpty() ? "----":responseSsp.payment.ntbNotNull());
        rowInfoPaydate.init(DateFunc.yyyyMMddhhmm(responseSsp.payInfo.paymentUpdatedAt));
//        rowInfoBookdate.init(sspdone.receiptPaydate);
//        rowInfoPayProvider.init(sspdone.receiptProvider);

        btnCekPayStatus.setVisibility( responseSsp.payment.ntpnNotNull().isEmpty() ? View.VISIBLE : View.GONE);

        btnGeneratePdf.setVisibility(View.VISIBLE);
    }

    private void cekPayStatus(){
        final Activity context = this;
        RequestParamConfig rpc = new RequestParamConfig();
        rpc.swipeRefreshLayout = swipeRefreshLayout;
        ApiReqWrapMpnPajakku.cekPayStatus(this, rpc, actData.responseSsp.id, actData.responseSsp.billing.idBillingNotNull(),
                null, null, new CommonCallback<ResponseSsp>() {
                    @Override
                    public void onSuccess(ResponseSsp data) {
                        if( data.payment.statusNotNull().equalsIgnoreCase(MPNPaymentResponse.BIL_PAID) )
                            setViewData();
                        else
                            context.finish();
                    }
                });
    }

    private String generatePDF() {
        ResponseSsp responseSsp = actData.responseSsp;

//        if(!AppTester.NO_TEST_PIECE) sspdone.ntpn = "";

        PDFWriter mPDFWriter = new PDFWriter(PaperSize.FOLIO_WIDTH, PaperSize.FOLIO_HEIGHT);

        int fontSize = 15;
        int marginPage = 50;
        int lineH = 30;
        int top;

        int tab0 = 120;
        int imgSize = 50;
        Bitmap bitmap;

        try {
            AssetManager mngr = getAssets();

//            bitmap = BitmapFactory.decodeStream(mngr.open("logo_backwhite.png"));
            bitmap = BitmapFactory.decodeStream(mngr.open("label_pajakku.png"));
            mPDFWriter.addImage(marginPage, PaperSize.FOLIO_HEIGHT - marginPage - imgSize +10, imgSize+40, imgSize, bitmap);
//            bitmap = BitmapFactory.decodeStream(mngr.open("djp_backwhite.png"));
            bitmap = BitmapFactory.decodeStream(mngr.open("logo_kemenkeu.png"));
            mPDFWriter.addImage(PaperSize.FOLIO_WIDTH - marginPage - imgSize -20, PaperSize.FOLIO_HEIGHT - marginPage - imgSize +10, imgSize, imgSize, bitmap);
        }catch (IOException e){
            return null;
        }

        top = PaperSize.FOLIO_HEIGHT-marginPage;
        mPDFWriter.setFont(StandardFonts.TIMES_BOLD, StandardFonts.TIMES_BOLD);
        mPDFWriter.addText(tab0+60, top, fontSize+3, "BUKTI PENERIMAAN NEGARA");

        top -= lineH;
        mPDFWriter.addText(tab0+100, top, fontSize+3, "PENERIMAAN PAJAK");

        mPDFWriter.setFont(StandardFonts.TIMES_ROMAN, StandardFonts.TIMES_ROMAN);

        top -= lineH;
//        mPDFWriter.addText(tab0, top, fontSize-2, "Jl. Kemanggisan Utama Raya No J-4 Jakarta Barat - 11480 Indonesia");

        top -= lineH -10;
        mPDFWriter.addLine(marginPage,top,PaperSize.FOLIO_WIDTH-marginPage,top);

        int tabColon0 = marginPage+150;
        int tabVal0 = tabColon0 + 10;

//        int tab1 = 340;
//        int tabColon1 = tab1 + 50;
//        int tabVal1 = tabColon1 + 10;

        top -= lineH;
        top -= lineH;
        mPDFWriter.setFont(StandardFonts.TIMES_BOLD, StandardFonts.TIMES_BOLD);
        mPDFWriter.addText(marginPage, top, fontSize, "Data Pembayaran");

        mPDFWriter.setFont(StandardFonts.TIMES_ROMAN, StandardFonts.TIMES_ROMAN);

        top -= lineH;
        mPDFWriter.addText(marginPage, top, fontSize, "Tanggal Pembayaran");
        mPDFWriter.addText(tabColon0, top, fontSize, ":");
        mPDFWriter.addText(tabVal0, top, fontSize, DateFunc.longToSimpleStrFull(responseSsp.payInfo.paymentUpdatedAt));

        top -= lineH;
        mPDFWriter.addText(marginPage, top, fontSize, "Tanggal Buku");
        mPDFWriter.addText(tabColon0, top, fontSize, ":");
        mPDFWriter.addText(tabVal0, top, fontSize, DateFunc.longToSimpleStr(responseSsp.payInfo.paymentCreatedAt));

        String lpl = responseSsp.payment.mcashProviderNotNull();
        if(lpl.equalsIgnoreCase("MPN_PAJAKKU")) lpl = "Pajakku";

        top -= lineH;
        mPDFWriter.addText(marginPage, top, fontSize, "Nama Bank/LPL");
        mPDFWriter.addText(tabColon0, top, fontSize, ":");
        mPDFWriter.addText(tabVal0, top, fontSize, lpl);

        top -= lineH;
        mPDFWriter.addText(marginPage, top, fontSize, "Kode Cabang Bank");
        mPDFWriter.addText(tabColon0, top, fontSize, ":");
        mPDFWriter.addText(tabVal0, top, fontSize, responseSsp.payInfo.branchCodeNotNull());

        top -= lineH;
        mPDFWriter.addText(marginPage, top, fontSize, "NTB");
        mPDFWriter.addText(tabColon0, top, fontSize, ":");
        mPDFWriter.addText(tabVal0, top, fontSize, responseSsp.payment.ntbNotNull());

        top -= lineH;
        mPDFWriter.addText(marginPage, top, fontSize, "NTPN");
        mPDFWriter.addText(tabColon0, top, fontSize, ":");
        mPDFWriter.addText(tabVal0, top, fontSize, responseSsp.payment.ntpnNotNull().isEmpty() ? "_______________" : responseSsp.payment.ntpnNotNull());

//        if(sspdone.transRefId != null){
//            top -= lineH;
//            mPDFWriter.addText(marginPage, top, fontSize, "No. Transaksi");
//            mPDFWriter.addText(tabColon0, top, fontSize, ":");
//            mPDFWriter.addText(tabVal0, top, fontSize, sspdone.transRefId);
//        }

//        top -= lineH;
//        mPDFWriter.addText(marginPage, top, fontSize, "No. Ref. SSP");
//        mPDFWriter.addText(tabColon0, top, fontSize, ":");
//        mPDFWriter.addText(tabVal0, top, fontSize, sspdone.refNo);

        top -= lineH;
        top -= lineH;
        mPDFWriter.setFont(StandardFonts.TIMES_BOLD, StandardFonts.TIMES_BOLD);
        mPDFWriter.addText(marginPage, top, fontSize, "Data Setoran");

        mPDFWriter.setFont(StandardFonts.TIMES_ROMAN, StandardFonts.TIMES_ROMAN);

        top -= lineH;
        mPDFWriter.addText(marginPage, top, fontSize, "Kode Billing");
        mPDFWriter.addText(tabColon0, top, fontSize, ":");
        mPDFWriter.addText(tabVal0, top, fontSize, responseSsp.billing.idBillingNotNull());

        top -= lineH;
        mPDFWriter.addText(marginPage, top, fontSize, "NPWP");
        mPDFWriter.addText(tabColon0, top, fontSize, ":");
        mPDFWriter.addText(tabVal0, top, fontSize, Utility.toPrettyNpwp(responseSsp.npwp) );

        top -= lineH;
        mPDFWriter.addText(marginPage, top, fontSize, "Nama Wajib Pajak");
        mPDFWriter.addText(tabColon0, top, fontSize, ":");
        mPDFWriter.addText(tabVal0, top, fontSize, responseSsp.name);

        top -= lineH;
        mPDFWriter.addText(marginPage, top, fontSize, "Alamat");
        mPDFWriter.addText(tabColon0, top, fontSize, ":");
        mPDFWriter.addText(tabVal0, top, fontSize, responseSsp.address);

        top -= lineH;
        mPDFWriter.addText(marginPage, top, fontSize, "Nomor Objek Pajak");
        mPDFWriter.addText(tabColon0, top, fontSize, ":");
        mPDFWriter.addText(tabVal0, top, fontSize, responseSsp.nopZero());

        top -= lineH;
        mPDFWriter.addText(marginPage, top, fontSize, "Jenis Pajak");
        mPDFWriter.addText(tabColon0, top, fontSize, ":");
        mPDFWriter.addText(tabVal0, top, fontSize, responseSsp.taxType.codeNotNull() );

        top -= lineH;
        mPDFWriter.addText(marginPage, top, fontSize, "Jenis Setoran");
        mPDFWriter.addText(tabColon0, top, fontSize, ":");
        mPDFWriter.addText(tabVal0, top, fontSize, responseSsp.taxSlipType.codeNotNull() );

        top -= lineH;
        mPDFWriter.addText(marginPage, top, fontSize, "Masa dan Tahun Pajak");
        mPDFWriter.addText(tabColon0, top, fontSize, ":");
        mPDFWriter.addText(tabVal0, top, fontSize, responseSsp.taxPeriodNumber());

        top -= lineH;
        mPDFWriter.addText(marginPage, top, fontSize, "Nomor Ketetapan");
        mPDFWriter.addText(tabColon0, top, fontSize, ":");
        mPDFWriter.addText(tabVal0, top, fontSize, responseSsp.noSkZero() );

        top -= lineH;
        mPDFWriter.addText(marginPage, top, fontSize, "Jumlah Setoran");
        mPDFWriter.addText(tabColon0, top, fontSize, ":");
        mPDFWriter.addText(tabVal0, top, fontSize, Utility.toMoney(false, responseSsp.amount) );

        top -= lineH;
        mPDFWriter.addText(marginPage, top, fontSize, "Terbilang");
        mPDFWriter.addText(tabColon0, top, fontSize, ":");
        mPDFWriter.addText(tabVal0, top, fontSize, Utility.terbilang(responseSsp.amount) + "Rupiah" );

        top -= lineH;
        mPDFWriter.addText(marginPage, top, fontSize, "Mata Uang");
        mPDFWriter.addText(tabColon0, top, fontSize, ":");
        mPDFWriter.addText(tabVal0, top, fontSize, responseSsp.payInfo.mataUangNotNull() );

        top -= lineH * 2;
        mPDFWriter.setFont(StandardFonts.TIMES_ITALIC, StandardFonts.TIMES_ITALIC);

        String note = "";
        int noteLeft = 0;
        boolean isNtpn = ! responseSsp.payment.ntpnNotNull().isEmpty();
        if( isNtpn ){
            note = "Informasi ini adalah hasil cetakan komputer dan tidak memerlukan tanda tangan";
            noteLeft = marginPage + 50;
        }else{
            note = "Transaksi sedang dalam Proses";
            noteLeft = marginPage + 170;
        }

        mPDFWriter.addText(noteLeft, top, fontSize-3, note);

        return mPDFWriter.asString();
    }

    private ResultCreatePdf outputToFile(String fileName, String pdfContent, String encoding) {
        ResultCreatePdf rcp = new ResultCreatePdf();

        File downloads = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS);
        if ( ! downloads.exists() ) {
            rcp.msg = "Folder " + downloads.getPath()+" tidak tersedia.";
            return rcp;
        }

        File newFile = new File(downloads, fileName);

        try {
            newFile.createNewFile();
            try {
                FileOutputStream pdfFile = new FileOutputStream(newFile);
                pdfFile.write(pdfContent.getBytes(encoding));
                pdfFile.close();

                rcp.msg = "Berhasil membuat PDF "+newFile.getPath();
                rcp.file = newFile;
            } catch (FileNotFoundException e) {
                rcp.msg = "Gagal membuat PDF. "+e.getMessage();
            }
        } catch (IOException e) {
            rcp.msg = "Gagal membuat PDF. "+e.getMessage();
        }

        return rcp;
    }

    private void createPdfPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    AppConstant.RP_WRITE_EXTERNAL_STORAGE);
        }else{
            createPdf();
        }
    }

    private void createPdf(){
        executeQueryProcessSuccess(new ListenerQuerySuccess<ResultCreatePdf>() {
            @Override
            public ResultCreatePdf onProcess() {
                String pdfcontent = generatePDF();
                if(pdfcontent == null) {
                    ResultCreatePdf rcp = new ResultCreatePdf();
                    rcp.msg = "Gagal membuat PDF. File gambar tidak bisa diakses.";
                    return rcp;
                }
                return outputToFile("BPN_"+Utility.getCurrentLogDate()+".pdf", pdfcontent, "ISO-8859-1");
            }

            @Override
            public void onSuccess(ResultCreatePdf result) {
                AppProgressDialog.hide();
                Utility.toast(SSPDetailDoneActivity.this, result.msg);
                if(result.file != null) Utility.openPdf(SSPDetailDoneActivity.this, result.file);
            }
        });

        AppProgressDialog.show(this, getString(R.string.progressdialog_createpdf), null);
    }

    public static void startAct(Activity act, long sspId){
        ActParamSspDetailDone ap = new ActParamSspDetailDone();
        ap.sspId = sspId;

        Intent intent = new Intent(act, SSPDetailDoneActivity.class);
        intent.putExtra(ACTDATA_KEY, ap);
        act.startActivity(intent);
    }
}