package com.bca.cooksy.utils;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        createChannels();
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

}
