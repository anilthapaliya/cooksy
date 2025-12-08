package com.bca.cooksy.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import com.bca.cooksy.R;
import com.bca.cooksy.views.HomeActivity;
import com.bca.cooksy.views.SplashActivity;

import java.util.Random;

public class NotificationUtils {

    public static void showAppUpdatesNotification(Context context, String title, String text) {

        Intent intent = new Intent(context, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                90, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new Notification.Builder(context, Constants.CHANNEL_APP_UPDATE)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.app_logo_only)
                .setContentIntent(pendingIntent)
                .build();
        Random random = new Random();
        NotificationManager manager = context.getSystemService(NotificationManager.class);
        manager.notify(random.nextInt(), notification);
    }

    public static void showBigTextNotification(Context context, String title, String text) {

        Notification.Style bigText = new Notification.BigTextStyle().bigText(text);
        Notification notification = new Notification.Builder(context, Constants.CHANNEL_APP_UPDATE)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.app_logo_only)
                .setStyle(bigText)
                .build();
        NotificationManager manager = context.getSystemService(NotificationManager.class);
        manager.notify(874, notification);
    }

    public static void showBigPictureNotification(Context context, String title, String text) {

        Notification.Style bigPic = new Notification.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.gollum));
        Notification notification = new Notification.Builder(context, Constants.CHANNEL_APP_UPDATE)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.app_logo_only)
                .setStyle(bigPic)
                .setAutoCancel(true)
                .build();
        NotificationManager manager = context.getSystemService(NotificationManager.class);
        manager.notify(6545, notification);
    }

}
