package com.pajakku.tupaimobile.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.MainActivity;
import com.pajakku.tupaimobile.model.Sspdone;
import com.pajakku.tupaimobile.model.Sspunpaid;

import java.util.Map;

/**
 * Created by dul on 21/03/19.
 */

public class AppFirebaseService extends FirebaseMessagingService {

    public static int NOTIF_TYPE_FCM_NEWTOKEN = 1;
    public static int NOTIF_TYPE_JUSTGENERATEBILLNUM = 2;
    public static int NOTIF_TYPE_PAIDSSP = 3;

    private LocalBroadcastManager broadcastManager;

    public static String MAPCOLUMN_ID = "id";
    public static String MAPCOLUMN_REFNUM = "refNo";
    public static String MAPCOLUMN_CODE = "code";
    public static String MAPCOLUMN_STATUS = "status";
    public static String MAPCOLUMN_TITLE = "title";
    public static String MAPCOLUMN_BODY = "body";
    public static String MAPCOLUMN_IDBILLING = "idBilling";
    public static String MAPCOLUMN_EXPIREDDATE = "expiredAt";
    public static String MAPCOLUMN_RESPONSECODE = "responseCode";

    public static String MAPCOLUMNTYPE_AFTERCREATEBILLINGID = "AFTER_CREATE_BILLINGID";
    public static String MAPCOLUMNTYPE_AFTERPAY = "AFTER_PAY";

    @Override
    public void onCreate(){
        broadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onNewToken(String token){
        Log.d(AppConstant.LOG_TAG, "onNewToken "+token);

//        Intent intent = new Intent(AppConstant.ITNFILTER_TUPAINOTIF);
//        intent.putExtra(AppConstant.ITN_NOTIFX_NOTIFTYPE, NOTIF_TYPE_FCM_NEWTOKEN);
//        intent.putExtra(AppConstant.ITN_NOTIFX_FCMTOKEN, token);
//        broadcastManager.sendBroadcast(intent);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(AppConstant.LOG_TAG, "From: " + remoteMessage.getFrom());

        Map<String,String> data = remoteMessage.getData();

        // TODO: @test
//        data.put(MAPCOLUMN_ID, "11");
//        data.put(MAPCOLUMN_IDBILLING, "BILL_NUM_123");
//        data.put(MAPCOLUMN_EXPIREDDATE, "2019-04-20T16:37:11");

        if (data.size() > 0) {
            Log.d(AppConstant.LOG_TAG, "Message data payload: " + data);
            // {idBilling=, id=38, body=Ada kesalahan data untuk SSP dengan No. Ref. BIV2ESM1PQQMLNPP, code=AFTER_CREATE_BILLINGID, title=Kode Billing, expiredAt=2020-08-13T10:50:57.698, responseCode=}

            String notifType = data.get(MAPCOLUMN_CODE);
            if(notifType == null) return;

            if(notifType.equals(MAPCOLUMNTYPE_AFTERCREATEBILLINGID)) {

                Sspunpaid sspunpaid = new Sspdone();
                sspunpaid.id = Long.parseLong(data.get(MAPCOLUMN_ID));
                sspunpaid.idBilling = data.get(MAPCOLUMN_IDBILLING);
                sspunpaid.billNumExpired = data.get(MAPCOLUMN_EXPIREDDATE);
                sspunpaid.taxslipresponseCode = data.get(MAPCOLUMN_RESPONSECODE);

                Intent intent = new Intent(AppConstant.ITNFILTER_TUPAINOTIF);
                intent.putExtra(AppConstant.ITN_NOTIFX_NOTIFTYPE, NOTIF_TYPE_JUSTGENERATEBILLNUM);
                intent.putExtra(AppConstant.ITN_NOTIFSSP_SSPUNPAID, sspunpaid);
                intent.putExtra(AppConstant.ITN_NOTIFSSP_TITLE, data.get(MAPCOLUMN_TITLE));
                intent.putExtra(AppConstant.ITN_NOTIFSSP_BODY, data.get(MAPCOLUMN_BODY));
                broadcastManager.sendBroadcast(intent);
            }else if(notifType.equals(MAPCOLUMNTYPE_AFTERPAY)){
                Intent intent = new Intent(AppConstant.ITNFILTER_TUPAINOTIF);
                intent.putExtra(AppConstant.ITN_NOTIFX_NOTIFTYPE, NOTIF_TYPE_PAIDSSP);
                intent.putExtra(AppConstant.ITN_NOTIFPAY_SSPID, data.get(MAPCOLUMN_ID));
                intent.putExtra(AppConstant.ITN_NOTIFPAY_REFNUM, data.get(MAPCOLUMN_REFNUM));
                intent.putExtra(AppConstant.ITN_NOTIFPAY_STATUS, data.get(MAPCOLUMN_STATUS));
                broadcastManager.sendBroadcast(intent);
            }

        }

    }

    private void popNotifAfterPay(String title, String body){
//        Sspunpaid sspunpaid = new Sspunpaid();
//        sspunpaid.id = Long.parseLong( data.get(MAPCOLUMN_ID) );
//        sspunpaid.idBilling = data.get(MAPCOLUMN_IDBILLING);
//        sspunpaid.billNumExpired = data.get(MAPCOLUMN_EXPIREDDATE);
//        sspunpaid.taxslipresponseCode = data.get(MAPCOLUMN_RESPONSECODE);

        Intent intent = new Intent(this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.putExtra(AppConstant.ITN_NOTIFSSP_NOTIFDATE, System.currentTimeMillis());
//        intent.putExtra(AppConstant.ITN_NOTIFSSP_SSPUNPAID, sspunpaid);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, AppConstant.NOTIF_CHANNEL_ID )
                .setSmallIcon(R.drawable.main_actionbar_logo)
                .setContentTitle( title )
                .setContentText( body )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setDefaults(NotificationCompat.DEFAULT_SOUND|NotificationCompat.DEFAULT_LIGHTS|NotificationCompat.DEFAULT_VIBRATE)
                ;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(AppConstant.NTF_NORMAL, builder.build());

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notChannel = new NotificationChannel(AppConstant.NOTIF_CHANNEL_ID, "Tupai Notif", NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(notChannel);
        }
    }

    @Override
    public void onDeletedMessages(){

    }
}
