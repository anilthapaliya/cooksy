package com.bca.cooksy.utils;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;

public class App extends Application {

    private DeviceStateReceiver deviceStateReceiver;
    private ConnectivityReceiver connectivityReceiver;
    public static SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();

        createChannels();
        register();
        initDatabase();
    }

    private void createChannels() {

        NotificationChannel channelAppUpdates = new NotificationChannel(Constants.CHANNEL_APP_UPDATE,
                "App Updates", NotificationManager.IMPORTANCE_HIGH);
        channelAppUpdates.setDescription("This channel handles application updates.");
        channelAppUpdates.enableVibration(false);

        NotificationChannel channelRecipes = new NotificationChannel(Constants.CHANNEL_NEW_RECIPE,
                "New Recipes", NotificationManager.IMPORTANCE_LOW);
        channelRecipes.setDescription("This channel shows new recipes from users.");

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channelAppUpdates);
        manager.createNotificationChannel(channelRecipes);
    }

    private void initDatabase() {

        DbHelper helper = new DbHelper(this);
        db = helper.getWritableDatabase();
    }

    private void register() {

        deviceStateReceiver = new DeviceStateReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(deviceStateReceiver, filter);

        connectivityReceiver = new ConnectivityReceiver();
        IntentFilter filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(connectivityReceiver, filter1);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(deviceStateReceiver);
        unregisterReceiver(connectivityReceiver);
    }

}
