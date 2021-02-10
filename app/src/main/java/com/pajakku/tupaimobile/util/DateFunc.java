package com.pajakku.tupaimobile.util;



import com.pajakku.tupaimobile.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public final class DateFunc {

    public static String trimRight(String s){
        if(s == null) return "";
        if(s.length() < 10) return s;
        return s.substring(0, 10);
    }

    public static String getAgo(Long d){
        if(d == null) return "";
        long ms = System.currentTimeMillis() - d;
        if(ms < 1000) return "new";
        else if(ms < 1000*60 ) return (ms/1000)+" sec";
        else if(ms < 1000*60*60 ) return (ms/(1000*60))+" min";
        else if(ms < 1000*60*60*24 ) return (ms/(1000*60*60))+" hour";
        else if(ms < 1000*60*60*24*7 ) return (ms/(1000*60*60*24))+" day";
        return "week+";
    }

    public static String nowTimeFull(){
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(new Date());
    }

    public static String msToDuration(Long ms){
        if(ms == null) ms = 0L;
        long sec = (ms/1000);
        String strSec = (sec < 10 ? "0" : "") + sec;
        long min = (ms/(1000*60));
        String strMin = (min < 10 ? "0" : "") + min;
        long hour = (ms/(1000*60*60));
        String strHour = (hour < 10 ? "0" : "") + hour;
        return strHour+":"+strMin+":"+strSec;
    }

    public static String monStrShort(Integer m){
        if(m != null){
            switch (m){
                case 1: return "Jan";
                case 2: return "Feb";
                case 3: return "Mar";
                case 4: return "Apr";
                case 5: return "Mei";
                case 6: return "Jun";
                case 7: return "Jul";
                case 8: return "Agu";
                case 9: return "Sep";
                case 10: return "Okt";
                case 11: return "Nov";
                case 12: return "Des";
            }
        }
        return "";
    }

    public static String monStrLong(Integer m){
        if(m != null){
            switch (m){
                case 1: return "Januari";
                case 2: return "Februari";
                case 3: return "Maret";
                case 4: return "April";
                case 5: return "Mei";
                case 6: return "Juni";
                case 7: return "Juli";
                case 8: return "Agustus";
                case 9: return "September";
                case 10: return "Oktober";
                case 11: return "November";
                case 12: return "Desember";
            }
        }
        return "";
    }

    public static String toMonthLong(String m) {
        if(m == null) m = "";
        if( m.equals("01") ){
            return "Januari";
        }
        if( m.equals("02") ){
            return "Februari";
        }
        if( m.equals("03") ){
            return "Maret";
        }
        if( m.equals("04") ){
            return "April";
        }
        if( m.equals("05") ){
            return "Mei";
        }
        if( m.equals("06") ){
            return "Juni";
        }
        if( m.equals("07") ){
            return "Juli";
        }
        if( m.equals("08") ){
            return "Agustus";
        }
        if( m.equals("09") ){
            return "September";
        }
        if( m.equals("10") ){
            return "Oktober";
        }
        if( m.equals("11") ){
            return "November";
        }
        if( m.equals("12") ){
            return "Desember";
        }

        return "";
    }

    // ---------------------- CURRENT DATE

    public static int currentDay(){
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    // 0 = jan, 1 = feb
    public static int currMonth(){
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    public static int currentYear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static String nowTime(){
        DateFormat df = new SimpleDateFormat("h:m", new Locale("in"));
        return df.format(new Date());
    }

//    public static LongMinMax minMaxMonth(int month, int year){
//        Calendar cal = new GregorianCalendar(year, month-1, 1);
//        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//        LongMinMax d = new LongMinMax();
//        d.min = cal.getTimeInMillis();
//        d.max = d.min + (daysInMonth * (1000 * 60 * 60 * 24));
//        return d;
//    }

    // m: 1=jan, 2=feb
    public static int monthDayCount(int m, int y){
        Calendar cal = new GregorianCalendar(y, m-1, 1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    // m: 1=jan, 2=feb
//    public static List<SpinItem> dateInMonth(int m, int year){
//        Calendar cal = new GregorianCalendar(year, m-1, 1);
//        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//        SpinItem d;
//        List<SpinItem> l = new ArrayList<>();
//        for(int i = 1; i<=daysInMonth; i++){
//            d = new SpinItem();
//            d.id = cal.getTimeInMillis();
//            d.nameStr = i+"-"+m+"-"+year;
//            l.add(d);
//
//            cal.add(Calendar.DAY_OF_MONTH, 1);
//        }
//        return l;
//    }

    // --------------- long to str

    public static String msToListDate(Long ms){
        if(ms == null) return "";
        Date d = new Date(ms);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy", new Locale("in"));
        return df.format(d);
    }

    public static String msToSimpleDate(Long ms){
        if(ms == null) return "";
        Date d = new Date(ms);
        DateFormat df = new SimpleDateFormat("d MMMM yyyy", new Locale("in"));
        return df.format(d);
    }

    public static String msToFullDate(Long ms){
        if(ms == null) return "";
        Date d = new Date(ms);
        DateFormat df = new SimpleDateFormat("d MMMM yyyy, hh:mm", new Locale("in"));
        return df.format(d);
    }

    public static String msToSimpleDate(Long ms, TimeZone tz){
        if(ms == null) return "";
        Date d = new Date(ms);
        DateFormat df = new SimpleDateFormat("d MMMM yyyy", new Locale("in"));
        df.setTimeZone(tz);
        return df.format(d);
    }

    public static String longToSimpleStr(Long ms){
        if(ms == null) return "";
        Date d = new Date(ms);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(d);
    }

    public static String longToSimpleStrFull(Long ms){
        if(ms == null) return "";
        Date d = new Date(ms);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return df.format(d);
    }

    public static String yyyyMMddhhmm(Long ms){
        if(ms == null) return "";
        Date d = new Date(ms);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd, hh:mm", new Locale("in"));
        return df.format(d);
    }

    public static String yyyyMMddhhmm(Long ms, TimeZone tz){
        if(ms == null) return "";
        Date d = new Date(ms);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm", new Locale("in"));
        df.setTimeZone(tz);
        return df.format(d);
    }


    public static String longToIndoDate(Long ms, boolean nullable) {
        if(ms == null) return nullable?null:"";
        Date d = new Date(ms);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(d);
    }

    /// -------------- str to long

    public static Long dateIndoToLong(String date, boolean nullable) {
        if (date != null) {
            SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
            try {
                Date d = f.parse(date);
                return d.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return nullable?null:0L;
    }

    public static Long stringtoDateMillis(String date) {
        if (date != null) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = f.parse(date.substring(0, 10));
                return d.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static Long longDateStrToLong(String date){
        if(date == null) return null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date d = df.parse(date);
            return d.getTime();
        }catch (Exception e){
            return null;
        }
    }

    public static long dateToLongNotNull(String date){
        if(date == null) return 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date d = df.parse(date);
            return d.getTime();
        }catch (Exception e){
            return 0;
        }
    }

    /// -------- str to str --------------

    public static String strToInformatifStr(String date, TimeZone tz){
        if(date == null) return "";
        if(date.length() < 10) return date;
        date = date.substring(0, 10);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            df.setTimeZone(tz);
            Date d = df.parse(date);
            df = new SimpleDateFormat("d MMMM yyyy", new Locale("in"));
            return df.format(d);
        }catch (Exception e){
            return date;
        }
    }

    public static String longDateStrToInformatif(String date){
        if(date == null) return "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date d = df.parse(date);
            df = new SimpleDateFormat("d MMMM yyyy HH:mm", new Locale("in"));
            return df.format(d);
        }catch (Exception e){
            return date;
        }
    }

    public static String longDateStrToShortInformatif(String date){
        if(date == null) return "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date d = df.parse(date);
            df = new SimpleDateFormat("d MMMM yyyy", new Locale("in"));
            return df.format(d);
        }catch (Exception e){
            return date;
        }
    }

//    public static String longToShort(String date, TimeZone tz){
//        if(date == null) return "";
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        try {
//            df.setTimeZone(tz);
//            Date d = df.parse(date);
//            df = new SimpleDateFormat("dd-MM-yyyy", Utility.getIndoLocale());
//            return df.format(d);
//        }catch (Exception e){
//            return date;
//        }
//    }

    public static int strDateToDay(String date, TimeZone tz){
        if(date == null) return 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            df.setTimeZone(tz);
            Date d = df.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            return cal.get(Calendar.DAY_OF_MONTH);
        }catch (Exception e){
            return 0;
        }
    }
    
//    public static String yyyyMMdd_dMMMyyyy(String date, TimeZone tz){
//        if(date == null) return "";
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        try {
//            df.setTimeZone(tz);
//            Date d = df.parse(date);
//            df = new SimpleDateFormat("d MMM yyyy", Utility.getIndoLocale());
//            return df.format(d);
//        }catch (Exception e){
//            return date;
//        }
//    }
//
//    public static String yyyyMMdd_dMMMyyyy_HHmm(String date, TimeZone tz){
//        if(date == null) return "";
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        try {
//            df.setTimeZone(tz);
//            Date d = df.parse(date);
//            df = new SimpleDateFormat("d MMM yyyy HH:mm", Utility.getIndoLocale());
//            return df.format(d);
//        }catch (Exception e){
//            return date;
//        }
//    }

//    public static String yyyyMMdd_yyyyMMdd(String date, TimeZone tz){
//        if(date == null) return "";
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        try {
//            df.setTimeZone(tz);
//            Date d = df.parse(date);
//            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Utility.getIndoLocale());
//            return df.format(d);
//        }catch (Exception e){
//            return date;
//        }
//    }

    // ----------- DATE TO STR

    public static String dateToString(Date d){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return df.format(d);
    }

    public static String dateToStringNoT(Date d){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(d);
    }

    public static String getCurrentLogDate(){
        DateFormat df = new SimpleDateFormat("yyyy_MMdd_HHmm_s");
        return df.format(new Date());
    }

    public static String toPrettyDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        return formatter.format(date);
    }

    private DateFunc(){}
}
