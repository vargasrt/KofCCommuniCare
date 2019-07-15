package com.example.vargasrt.kofccommunicare;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.widget.Filter;
import android.widget.Toast;

import com.example.vargasrt.kofccommunicare.adapter.MessagesAdapter;
import com.example.vargasrt.kofccommunicare.model.Messagebox;
import com.example.vargasrt.kofccommunicare.realm.RealmControllerMessagebox;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;

/*
public class MessageReceiver {
}
package com.example.administrator.rtvtext_kcfapi;

        import android.app.Notification;
        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Build;
        import android.os.Bundle;
        import android.support.v4.app.NotificationCompat;
        import android.telephony.SmsMessage;
        import android.util.Log;
        import android.widget.TextView;
        import android.widget.Toast;

        import static android.content.Context.NOTIFICATION_SERVICE;
*/

public class MessageReceiver extends BroadcastReceiver {

    private Realm realm;
    private MessagesAdapter adapter;

    @Override
    public void onReceive(Context context, Intent intent) {


        //get message passed in
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages;
        String str = "";
        String phoneNum = "";

//        Toast.makeText(context, "MessageReceiver ... Text Received...", Toast.LENGTH_SHORT).show();

        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            messages = new SmsMessage[pdus != null ? pdus.length : 0];
            for(int i=0; i<messages.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) (pdus != null ? pdus[i] : null));
                phoneNum = messages[i].getOriginatingAddress();
//                str += messages[i].getOriginatingAddress();
//                str += ": ";
                str += messages[i].getMessageBody();
                str += "\n";
            }

            if (phoneNum.equals("09178255632") || phoneNum.equals("+639178255632")) {
                //display the message
                //String msgcode = (str.length() > 7)? str.substring(str.length() - 7): str;
/*
                Intent serviceIntent = new Intent(context, FilterSMSReceived.class);
                serviceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                serviceIntent.putExtra("MSGRECEIVE",str);
                context.startActivity(serviceIntent);
*/
// use this to start and trigger a service
                Intent serviceIntent= new Intent(context, SMSServices.class);
// potentially add data to the intent
                serviceIntent.putExtra("MSGRECEIVE",str);
//                serviceIntent.putExtra("KEY1", "Value to be used by the service");
                context.startService(serviceIntent);

//                TextConnectChildFragment.getInstance().setReceiveSMSData(str);



/*
                String imageSenderKCUrl = getURLForResource(R.mipmap.ic_launcher_kcshield);

                ArrayList<Messagebox> messageboxes = new ArrayList<>();

                Messagebox messagebox = new Messagebox();
//                            messagebox.setMsgid((results.size() + 1) + System.currentTimeMillis());
                messagebox.setMsgid(Prefs.with(context).getTexconnectCounter() + 1);
                messagebox.setImageSenderUrl(imageSenderKCUrl);
                messagebox.setMsgtimestamp(DateFormat.getDateTimeInstance().format(new Date()).toString());
                messagebox.setMsgbound("INBOX");
                messagebox.setMsgparam1("BCINQ");
                messagebox.setMsgparam3(str);
                messageboxes.add(messagebox);

                for (Messagebox m : messageboxes) {
                    Prefs.with(context).setTextconnectCounter((Prefs.with(context).getTexconnectCounter() + 1));
                    // Persist your data easily
                    realm.beginTransaction();
                    realm.copyToRealm(m);
                    realm.commitTransaction();
                    adapter.notifyDataSetChanged();
                }
*/

                //send a broadcast intent to update the SMS received in a TextView

                /*
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction("SMS_RECEIVED_ACTION");
                broadcastIntent.putExtra("message", str);
                context.sendBroadcast(broadcastIntent);
                */

/*

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.mipmap.ic_launcher_kcshield)
                                .setContentTitle("From:  K of C CommuniCare Server")
                                .setContentText("In response to your inquiry");

                Intent msgIntent = new Intent(context, NotificationActivity.class);
                msgIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                msgIntent.putExtra("NOTIFICATION_TITLE","From:  K of C CommuniCare Server");
                msgIntent.putExtra("NOTIFICATION_BODY",str);

                PendingIntent contentIntent = PendingIntent.getActivity(context, 0, msgIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(contentIntent);

                if (MyApplication.isActivityVisible()!=true) {
                    NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.cancelAll();
                    mBuilder.setAutoCancel(true);
                    mNotificationManager.notify(1, mBuilder.build());
                } else {
                    context.startActivity(msgIntent);
                }
                //Intent msgIntent = new Intent(context, NotificationActivity.class);
                //msgIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                //msgIntent.putExtra("NOTIFICATION_TITLE","From:  KofC CommuniCare");
                //msgIntent.putExtra("NOTIFICATION_BODY",str);
*/
            }
        }
    }

    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }
}
