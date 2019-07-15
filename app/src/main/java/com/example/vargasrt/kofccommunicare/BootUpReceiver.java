package com.example.vargasrt.kofccommunicare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

public class BootUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

//        Intent i=new Intent(context, TestIntentService.class);

        Toast.makeText(context, "Bootup Receiver ... Text Received...", Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            //context.startForegroundService(i);
        }
        else {
            //context.startService(i);
        }

        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
//            Intent serviceIntent = new Intent(context, NotificationActivity.class);
//            context.startService(serviceIntent);
            Toast.makeText(context, "Text Received Bootinggg...", Toast.LENGTH_LONG).show();
        }
    }
}

