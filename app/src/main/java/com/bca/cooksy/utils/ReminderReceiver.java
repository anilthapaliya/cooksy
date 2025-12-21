package com.bca.cooksy.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String msg = intent.getStringExtra(Constants.REMINDER_TITLE);
        NotificationUtils.showAppUpdatesNotification(context,
                "Cooksy Reminder", msg);
    }

}
