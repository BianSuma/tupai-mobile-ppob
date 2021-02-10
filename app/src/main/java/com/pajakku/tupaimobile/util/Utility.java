package com.pajakku.tupaimobile.util;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.elconfidencial.bubbleshowcase.BubbleShowCase;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseListener;
import com.google.gson.Gson;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.AboutActivity;
import com.pajakku.tupaimobile.activity.BaseActivity;
import com.pajakku.tupaimobile.component.BaseInput;
import com.pajakku.tupaimobile.component.InpFile;
import com.pajakku.tupaimobile.component.InpPicker;
import com.pajakku.tupaimobile.fragment.BaseFragment;
import com.pajakku.tupaimobile.model.dto.FileBrowserItem;
import com.pajakku.tupaimobile.model.dto.PickedDTO;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by dul on 31/12/18.
 */

public final class Utility {

    public static final int DEFACETYPE_ONGOING = 1;
    public static final int DEFACETYPE_FAIL = 2;
    public static final int DEFACETYPE_SUCCESS = 3;

    private static List<String> extDirPaths;
    private static Gson gson;

    public static Gson gson(){
        if(gson == null){
            gson = new Gson();
        }
        return gson;
    }

    public static void proceedActivityResult(List<BaseInput> inputs, int reqCode, Serializable seri, CommonCallback<BaseInput> cb){
//        Serializable seri = data.getSerializableExtra(AppConstant.SP_ACTIVITYRESULT);
        InpFile inpFile;
        InpPicker inpPicker;
        for(BaseInput i : inputs){
            if(i instanceof InpFile){
                inpFile = ((InpFile)i);
                if( inpFile.actRes == reqCode) {
                    inpFile.onPick.onSuccess((FileBrowserItem) seri);
                    inpFile.setValueUnchange((FileBrowserItem) seri);
                    cb.onSuccess(inpFile);
                    break;
                }
            }
            if(i instanceof InpPicker){
                inpPicker = ((InpPicker)i);
                if( inpPicker.actRes == reqCode) {
                    inpPicker.onPick.onSuccess((PickedDTO) seri);
                    inpPicker.setValueUnchange((PickedDTO) seri);
                    cb.onSuccess(inpPicker);
                    break;
                }
            }
        }
    }

    public static void verifyStoragePermissions(Activity activity, int actRes) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            String []permissions = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions( activity, permissions, actRes );
        }
    }

    public static String getPathFileName(String path){
//        File.separator /             slash
//        File.separatorChar /         slash
//        File.pathSeparator :         period
//        File.pathSeparatorChar :     period
        if(path != null) {
            int idx = path.lastIndexOf(File.separatorChar);
            if (idx != -1) {
                return path.substring(idx + 1);
            }
        }
        return "";
    }

    public static PackageInfo getPInfo(Context ctx){
        PackageInfo pInfo;
        try {
            pInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            pInfo = new PackageInfo();
            pInfo.versionCode = 0;
            pInfo.versionName = "???";
        }
        return pInfo;
    }

    public static void defaceTextViewStatus(TextView tv, int type){
        int bg = R.drawable.bgtextviewstatus_ongoing;
        String tc = "#ccba4c";
        switch (type){
            case DEFACETYPE_FAIL:
                bg = R.drawable.bgtextviewstatus_fail;
                tc = "#ffffff";
                break;
            case DEFACETYPE_SUCCESS:
                bg = R.drawable.bgtextviewstatus_success;
                tc = "#43a6f5";
                break;
        }
        tv.setBackgroundResource(bg);
        tv.setTextColor(Color.parseColor(tc));
    }

    public static <T> List<T> streamParse(Gson gson, ResponseBody body, Type type){
        if(body == null) return new ArrayList<>();
        String bodyStr = null;
        try {
            bodyStr = body.string();
        }catch (Exception e){
            return new ArrayList<>();
        }
        if(bodyStr == null) return new ArrayList<>();
        if (bodyStr.length() == 0) return new ArrayList<>();

        bodyStr = "[" + bodyStr.replace("}{","},{") + "]";

        return gson.fromJson(bodyStr, type);
    }

    public static BubbleShowCaseBuilder createShowCase(Activity activity, final String title, String body, View bigView, View target){

        Rect scrollBounds = new Rect(), viewRect = new Rect();
        bigView.getGlobalVisibleRect(scrollBounds);
        target.getGlobalVisibleRect(viewRect);
        if ( ! scrollBounds.contains(viewRect) || target.getWidth() != (viewRect.right-viewRect.left) || target.getHeight() != (viewRect.bottom-viewRect.top) ) {
            return null;
        }

        return new BubbleShowCaseBuilder(activity) //Activity instance
                .title( title ) //Any title for the bubble view
                .description( body ) //More detailed description
//                .arrowPosition(BubbleShowCase.ArrowPosition.RIGHT) //You can force the position of the arrow to change the location of the bubble.
//                .backgroundColor(Color.GREEN) //Bubble background color
//                .textColor(Color.BLACK) //Bubble Text color
//                .titleTextSize(17) //Title text size in SP (default value 16sp)
//                .descriptionTextSize(15) //Subtitle text size in SP (default value 14sp)
//                .image(imageDrawable) //Bubble main image
//                .closeActionImage(CloseImageDrawable) //Custom close action image
                .listener(new BubbleShowCaseListener() {
                    @Override
                    public void onTargetClick(@NonNull BubbleShowCase bubbleShowCase) {

                    }

                    @Override
                    public void onCloseActionImageClick(@NonNull BubbleShowCase bubbleShowCase) {

                    }

                    @Override
                    public void onBackgroundDimClick(@NonNull BubbleShowCase bubbleShowCase) {
                        bubbleShowCase.finishSequence();
                    }

                    @Override
                    public void onBubbleClick(@NonNull BubbleShowCase bubbleShowCase) {
                        bubbleShowCase.dismiss();
                    }
                })
                .targetView( target );
    }

    public static String getBearerSpaceAuth(String authToken){
        return AppConstant.HEADER_BEARER_SPACE+authToken;
    }

    public static void showConfirmationDialog(Context ctx, int msg, final CommonCallback<Void> callback){
        showConfirmationDialog(ctx, ctx.getString(msg), callback);
    }

    public static void showConfirmationDialog(Context ctx, String msg, final CommonCallback<Void> callback){
        new AlertDialog.Builder(ctx, R.style.ConfirmDialogTheme)
                .setTitle(ctx.getString(R.string.global_confirm))
                .setMessage(msg)
                .setIcon(R.drawable.ic_confirmdialog)
                .setPositiveButton(R.string.global_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        callback.onSuccess(null);
                    }})
                .setNegativeButton(R.string.global_no, null).show();
    }

    public static String toMoney(boolean currency, long money){
        Locale indoLocale = new Locale("in");
        DecimalFormat df = (DecimalFormat) NumberFormat.getCurrencyInstance(indoLocale);
        df.setMaximumFractionDigits(0);

        DecimalFormatSymbols symbol = df.getDecimalFormatSymbols();
        symbol.setCurrencySymbol( (currency?"Rp ":"") );
        df.setDecimalFormatSymbols(symbol);

        return df.format(money);
    }

    public static String padZeroMonth(int m){
        if(m < 10) return ("0"+m);
        return Integer.toString(m);
    }

    public static String dateToString(Date d){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return df.format(d);
    }

    public static String dateToStringNoT(Date d){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(d);
    }

    public static String strToInformatifStr(String date){
        if(date == null) return "";
        if(date.length() < 10) return date;
        date = date.substring(0, 10);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = df.parse(date);
            df = new SimpleDateFormat("d MMMM yyyy", new Locale("in"));
            return df.format(d);
        }catch (Exception e){
            return date;
        }
    }

    public static String longDateStrToInformatif(String date){
        if(date == null) return "";
//        if(date.length() < 10) return date;
//        date = date.substring(0, 10);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date d = df.parse(date);
            df = new SimpleDateFormat("d MMMM yyyy HH:mm:ss", new Locale("in"));
            return df.format(d);
        }catch (Exception e){
            return date;
        }
    }

    public static String longDateStrToInformatifSimple(String date){
        if(date == null) return "";
//        if(date.length() < 10) return date;
//        date = date.substring(0, 10);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date d = df.parse(date);
            df = new SimpleDateFormat("d MMMM yyyy", new Locale("in"));
            return df.format(d);
        }catch (Exception e){
            return date;
        }
    }

    public static int colorResToInt(Context ctx, int r){
        return ContextCompat.getColor(ctx, r);
    }

    public static MultipartBody.Part pathToPartFile(String key, String filePath){
        if( filePath == null ) return null;
        File file = new File(filePath);

        String fileType = "application/octet-stream";
//        String fileName = file.getName().toUpperCase();
//        if( fileName.endsWith(".PDF") ){
//            fileType = "application/pdf";
//        }else if( fileName.endsWith(".JPG") || fileName.endsWith(".JPEG") || fileName.endsWith(".PNG") ){
//            fileType = "image/*";
//        }else{
//            return null;
//        }

        RequestBody requestFile = RequestBody.create( MediaType.get(fileType), file);
        return MultipartBody.Part.createFormData(key, file.getName(), requestFile);
    }

    public static List<String> getExtDirPaths(){
        if(extDirPaths == null) {
            extDirPaths = new ArrayList<>();

            List<String> sdCardPossiblePath = Arrays.asList("/storage", "/mnt", "/removable", "/data");
            File file;
            File[] files;
            for (String sdPath : sdCardPossiblePath) {
                file = new File(sdPath);
                if (!file.exists()) continue;
                if(!file.isDirectory()) continue;
                files = file.listFiles();
                if (files == null) continue;
                extDirPaths.add(file.getAbsolutePath());
            }
        }

        return extDirPaths;
    }

    public static Map<String,String> urltoMapQueryParam(String url){
        Map<String,String> map = new HashMap<>();
        if(url == null) return map;
        String[] arr = url.split("\\?");
        if(arr.length == 1) return map;
        arr = arr[1].split("&");
        String[] que;
        for(String p : arr){
            que = p.split("=");
            map.put(que[0], que[1]);
        }
        return map;
    }

    public static boolean isStillValid(String date){
        if(date == null) return false;
        if(date.length() >= 10) date = date.substring(0, 10);
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = df.parse(date);
            return ( d.getTime() > System.currentTimeMillis() );
        }catch (Exception e){
            return false;
        }
    }

    public static String getCurrentLogDate(){
        DateFormat df = new SimpleDateFormat("yyyy_MMdd_HHmm");
        return df.format(new Date());
    }

    public static String toPrettyNpwp(String str){
        try {
            String s;
            String ss = "";

            if(str.length() < 2) return str;
            s = str.substring(0, 2);
            ss += s;
            ss += ".";

            if(str.length() < 5){
                s = str.substring(2, str.length());
                if(s.length() > 0) return ss + s;
                return ss.substring(0, ss.length()-1);
            }
            s = str.substring(2, 5);
            ss += s;
            ss += ".";

            if(str.length() < 8) {
                s = str.substring(5, str.length());
                if(s.length() > 0) return ss + s;
                return ss.substring(0, ss.length()-1);
            }
            s = str.substring(5, 8);
            ss += s;
            ss += ".";

            if(str.length() < 9) {
                s = str.substring(8, str.length());
                if(s.length() > 0) return ss + s;
                return ss.substring(0, ss.length()-1);
            }
            s = str.substring(8, 9);
            ss += s;
            ss += "-";

            if(str.length() < 12) {
                s = str.substring(9, str.length());
                if(s.length() > 0) return ss + s;
                return ss.substring(0, ss.length()-1);
            }
            s = str.substring(9, 12);
            ss += s;
            ss += ".";

            if(str.length() < 15) {
                s = str.substring(12, str.length());
                if(s.length() > 0) return ss + s;
                return ss.substring(0, ss.length()-1);
            }
            s = str.substring(12, 15);
            ss += s;
            return ss;
        }catch (Exception e){
            return str;
        }
    }

    public static String toPrettyNoSK(String str){
        try {
            String s;
            String ss = "";

            if(str.length() < 5) return str;
            s = str.substring(0, 5);
            ss += s;
            ss += "/";

            if(str.length() < 8){
                s = str.substring(5, str.length());
                if(s.length() > 0) return ss + s;
                return ss.substring(0, ss.length()-1);
            }
            s = str.substring(5, 8);
            ss += s;
            ss += "/";

            if(str.length() < 10) {
                s = str.substring(8, str.length());
                if(s.length() > 0) return ss + s;
                return ss.substring(0, ss.length()-1);
            }
            s = str.substring(8, 10);
            ss += s;
            ss += "/";

            if(str.length() < 13) {
                s = str.substring(10, str.length());
                if(s.length() > 0) return ss + s;
                return ss.substring(0, ss.length()-1);
            }
            s = str.substring(10, 13);
            ss += s;
            ss += "/";

            if(str.length() < 15) {
                s = str.substring(13, str.length());
                if(s.length() > 0) return ss + s;
                return ss.substring(0, ss.length()-1);
            }
            s = str.substring(13, 15);
            ss += s;

            return ss;
        }catch (Exception e){
            return str;
        }
    }

    public static String toPrettyNOP(String str){
        try {
            String s;
            String ss = "";

            if(str.length() < 2) return str;
            s = str.substring(0, 2);
            ss += s;
            ss += ".";

            if(str.length() < 4){
                s = str.substring(2, str.length());
                if(s.length() > 0) return ss + s;
                return ss.substring(0, ss.length()-1);
            }
            s = str.substring(2, 4);
            ss += s;
            ss += ".";

            if(str.length() < 7) {
                s = str.substring(4, str.length());
                if(s.length() > 0) return ss + s;
                return ss.substring(0, ss.length()-1);
            }
            s = str.substring(4, 7);
            ss += s;
            ss += ".";

            if(str.length() < 10) {
                s = str.substring(7, str.length());
                if(s.length() > 0) return ss + s;
                return ss.substring(0, ss.length()-1);
            }
            s = str.substring(7, 10);
            ss += s;
            ss += ".";

            if(str.length() < 13) {
                s = str.substring(10, str.length());
                if(s.length() > 0) return ss + s;
                return ss.substring(0, ss.length()-1);
            }
            s = str.substring(10, 13);
            ss += s;
            ss += ".";

            if(str.length() < 17) {
                s = str.substring(13, str.length());
                if(s.length() > 0) return ss + s;
                return ss.substring(0, ss.length()-1);
            }
            s = str.substring(13, 17);
            ss += s;
            ss += ".";

            if(str.length() < 18) {
                s = str.substring(17, str.length());
                if(s.length() > 0) return ss + s;
                return ss.substring(0, ss.length()-1);
            }
            s = str.substring(17, 18);
            ss += s;

            return ss;
        }catch (Exception e){
            return str;
        }
    }

    public static int currentYear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }
    public static int currentMonth(){
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    public static String billNumberX(String bn){
        if(bn == null) return "";
        if(bn.length() < 3) return bn;
        return bn.substring(0, bn.length()-3) + "XXX";
    }

    private static String[] nomina = {"","Satu ","Dua ","Tiga ","Empat ","Lima ","Enam ",
            "Tujuh ","Delapan ","Sembilan ","Sepuluh ","Sebelas "};
    public static String terbilang(long angka)
    {
        if(angka<12)
        {
            return nomina[(int)angka];
        }

        if(angka>=12 && angka <=19)
        {
            return nomina[(int)angka%10] +"Belas ";
        }

        if(angka>=20 && angka <=99)
        {
            return nomina[(int)angka/10] +"Puluh "+nomina[(int)angka%10];
        }

        if(angka>=100 && angka <=199)
        {
            return "Seratus "+ terbilang(angka%100);
        }

        if(angka>=200 && angka <=999)
        {
            return nomina[(int)angka/100]+"Ratus "+terbilang(angka%100);
        }

        if(angka>=1000 && angka <=1999)
        {
            return "Seribu "+ terbilang(angka%1000);
        }

        if(angka >= 2000 && angka <=999999)
        {
            return terbilang((int)angka/1000)+"Ribu "+ terbilang(angka%1000);
        }

        if(angka >= 1000000 && angka <=999999999)
        {
            return terbilang((int)angka/1000000)+"Juta "+ terbilang(angka%1000000);
        }

        return "";
    }

    public static void showAbout(BaseActivity ctx){
//        AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
//        alertDialog.setTitle("Tupai Mobile");
//        alertDialog.setMessage("Versi "+ctx.getPInfo().versionName);
//        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//        alertDialog.show();

        Intent i = new Intent(ctx, AboutActivity.class);
        ctx.startActivity(i);
    }

    public static String toPrettyNumber(String str){
        if(str == null) return "";
        String pretty = "";
        for(int i=0; i<str.length(); i++){
            pretty = pretty + str.charAt(i);
        }
        return pretty;
    }

    public static int toMonthShort(int m) {
        switch (m){
            case 1: return R.string.monthnameshort_0;
            case 2: return R.string.monthnameshort_1;
            case 3: return R.string.monthnameshort_2;
            case 4: return R.string.monthnameshort_3;
            case 5: return R.string.monthnameshort_4;
            case 6: return R.string.monthnameshort_5;
            case 7: return R.string.monthnameshort_6;
            case 8: return R.string.monthnameshort_7;
            case 9: return R.string.monthnameshort_8;
            case 10: return R.string.monthnameshort_9;
            case 11: return R.string.monthnameshort_10;
            case 12: return R.string.monthnameshort_11;
        }
        return 0;
    }
    public static int toMonthShort(String m) {
        if( m.equals("01") ){
            return R.string.monthnameshort_0;
        }
        if( m.equals("02") ){
            return R.string.monthnameshort_1;
        }
        if( m.equals("03") ){
            return R.string.monthnameshort_2;
        }
        if( m.equals("04") ){
            return R.string.monthnameshort_3;
        }
        if( m.equals("05") ){
            return R.string.monthnameshort_4;
        }
        if( m.equals("06") ){
            return R.string.monthnameshort_5;
        }
        if( m.equals("07") ){
            return R.string.monthnameshort_6;
        }
        if( m.equals("08") ){
            return R.string.monthnameshort_7;
        }
        if( m.equals("09") ){
            return R.string.monthnameshort_8;
        }
        if( m.equals("10") ){
            return R.string.monthnameshort_9;
        }
        if( m.equals("11") ){
            return R.string.monthnameshort_10;
        }
        if( m.equals("12") ){
            return R.string.monthnameshort_11;
        }

        return 0;
    }

    public static int toMonthLong(int m) {
        switch (m){
            case 1: return R.string.monthname_0;
            case 2: return R.string.monthname_1;
            case 3: return R.string.monthname_2;
            case 4: return R.string.monthname_3;
            case 5: return R.string.monthname_4;
            case 6: return R.string.monthname_5;
            case 7: return R.string.monthname_6;
            case 8: return R.string.monthname_7;
            case 9: return R.string.monthname_8;
            case 10: return R.string.monthname_9;
            case 11: return R.string.monthname_10;
            case 12: return R.string.monthname_11;
        }
        return 0;
    }

    public static void openPdf(Context ctx, File file){
        Uri uri = FileProvider.getUriForFile(ctx, ctx.getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            ctx.startActivity(intent);
        }catch (Exception e){
            // no PDF reader
        }
    }

    public static void gotoWebsite(Context ctx, String url){
        gotoWebsite(ctx, Uri.parse(url));
    }

    public static void gotoWebsite(Context ctx, Uri uri){
        Utility.log("gotoWebsite "+uri.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            ctx.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            // Chrome browser presumably not installed so allow user to choose instead
            intent.setPackage(null);
            ctx.startActivity(intent);
        }
    }

//    public static void gotoWebsiteHeader(Context ctx, Uri uri, String authToken){
//        Utility.log("gotoWebsite "+uri.toString());
//
//        Bundle bundle = new Bundle();
//        bundle.putString("")
//        if(mExtraHeader!=null){
//            for(String key: mExtraHeader.keySet()){
//                bundle.putString(key, mExtraHeader.get(key));
//            }
//        }
//
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        intent.putExtra(Browser.EXTRA_HEADERS, bundle);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setPackage("com.android.chrome");
//        try {
//            ctx.startActivity(intent);
//        } catch (ActivityNotFoundException ex) {
//            // Chrome browser presumably not installed so allow user to choose instead
//            intent.setPackage(null);
//            ctx.startActivity(intent);
//        }
//    }

    public static boolean checkInput(BaseActivity act, BaseFragment frag, BaseInput sender, List<BaseInput> inputs, View btnBot){

        boolean valid = true;

//        Utility.log("log count "+inputs.size());
        for (BaseInput bi : inputs) {
            if (!bi.checkInputWarn()) {
                valid = false;
            }
        }

        boolean validateExtra = true;
        if(frag != null){
            validateExtra = frag.onSubmitValidate(act, sender);
        }else {
            act.onSubmitValidate(sender);
        }
//        if( this instanceof FragmentOnSubmitValidate){
//            validateExtra = ((FragmentOnSubmitValidate)this).onSubmitValidate();
//        }

        boolean rtn = valid && validateExtra;

        if( ! AppConf.VALIDATE ){
            rtn = true;
        }

        if(btnBot != null) btnBot.setEnabled(rtn);

        if( frag != null){
            frag.onChangeInputComponent(act, sender, rtn);
        }else {

        }

        return rtn;
    }

    public static void log(String str){
        Log.d(AppConstant.LOG_TAG, str);
    }

    public static void logErr(String str){
        Log.e(AppConstant.LOG_TAG, str);
    }

    public static void logWarn(String str){
        Log.w(AppConstant.LOG_TAG, str);
    }

    public static void toast(Context ctx, int msg){
        Utility.toast(ctx, ctx.getString(msg));
    }

    public static void toast(Context ctx, String msg){
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

    private Utility(){}
}
