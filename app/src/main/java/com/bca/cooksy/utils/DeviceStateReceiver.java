package com.bca.cooksy.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class DeviceStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {

            NotificationUtils.showAppUpdatesNotification(context, "App Restarted", "Syncing data, please wait ...");
            // Further works
        }

        if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {

            boolean isEnabled = intent.getBooleanExtra("state", false);
            Toast.makeText(context, isEnabled ? "Airplane mode is turned ON." : "Airplane mode is now OFF.", Toast.LENGTH_LONG).show();
        }
    }

}
