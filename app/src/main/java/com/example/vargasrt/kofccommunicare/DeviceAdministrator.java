package com.example.vargasrt.kofccommunicare;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

public class DeviceAdministrator extends DeviceAdminReceiver {
    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
       // Toast.makeText(context, "Sample Device Admin has been disabled.", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
     //   Toast.makeText(context, "Sample Device Admin has been enabled.", Toast.LENGTH_SHORT).show();
    }
}
