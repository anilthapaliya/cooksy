package com.bca.cooksy.services;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bca.cooksy.models.Meals;
import com.bca.cooksy.models.Recipe;
import com.bca.cooksy.utils.App;
import com.bca.cooksy.utils.Constants;
import com.bca.cooksy.utils.DbHelper;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RecipeService extends Service {

    private Handler handler;
    private HandlerThread handlerThread;
    private static boolean isRunning = false;
    private String api;

    @Override
    public void onCreate() {
        super.onCreate();

        handlerThread = new HandlerThread("ServiceThread");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!isRunning) {
            api = intent.getStringExtra("api");
            isRunning = true;
            handler.post(taskRunnable);
        }

        return START_STICKY;
    }

    private final Runnable taskRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isRunning) return;

            fetchData();
            handler.postDelayed(this, 60 * 1000); // Repeat after .. seconds.
        }
    };

    private void fetchData() {

        try {
            // Call the api to fetch a random recipe.
            URL url = new URL(api);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            conn.disconnect();

            // Instantiate string response to model class: Recipe
            Gson gson = new Gson();
            Meals m = gson.fromJson(sb.toString(), Meals.class);
            Recipe recipe = m.getMeals().get(0);

            // Store the data in the database.
            ContentValues values = new ContentValues();
            values.put("title", recipe.getTitle());
            values.put("category", recipe.getCategory());
            values.put("origin", recipe.getOrigin());

            App.db.insert(DbHelper.RECIPE_TABLE, null, values);

            Thread.sleep(30 * 1000); // sleep for 1 minute.

        } catch (MalformedURLException e) {
            Log.d("Service Exception", "Invalid URL supplied.");
        } catch (IOException e) {
            Log.d("Service Exception", "Unable to open connection.");
        } catch (InterruptedException e) {
            Log.d("Service Exception", "Error while sleeping.");
        }
    }

    @Override
    public void onDestroy() {

        isRunning = false;
        handler.removeCallbacksAndMessages(null);
        handlerThread.quitSafely();
        handler.getLooper().quit();
        super.onDestroy();
    }

    public static boolean isRunning() {
        return isRunning;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
