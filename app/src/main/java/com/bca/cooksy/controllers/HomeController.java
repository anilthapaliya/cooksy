package com.bca.cooksy.controllers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.bca.cooksy.utils.Constants;
import com.bca.cooksy.utils.NotificationUtils;
import com.bca.cooksy.utils.ReminderReceiver;
import com.bca.cooksy.views.HomeActivity;

public class HomeController {

    HomeActivity view;

    public HomeController(HomeActivity view) {

        this.view = view;
        //scheduleNotification();
    }

    public void setReminder(String message, String minutes) {

        int min;
        try {
            min = Integer.parseInt(minutes);
        } catch (NumberFormatException e) {
            min = 1;
        }

        Intent intent = new Intent(view, ReminderReceiver.class);
        intent.putExtra(Constants.REMINDER_TITLE, message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(view, 376,
                intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        long timeWhen = System.currentTimeMillis() + (long) min * 60 * 1000;

        try {
            AlarmManager alarmManager = (AlarmManager) view.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeWhen, pendingIntent);
        } catch (SecurityException e) { Log.d("ALARM Exception", "Unable to schedule exact alarm."); return; }

        view.showMessage("Reminder successfully set after " + min + " minutes.");
    }

    public void addRecipe() {

        // 1. update UI so that it shows some progress
        // 2. perform background task
        // 3. after completion, again notify UI.

        Thread thread = new Thread(() -> {

            view.runOnUiThread(() -> {
                view.showInfiniteProgress(true); // UI task
            });

            // Perform background task, it takes 4-8 seconds, background safe
            try {
                Thread.sleep(5000); // Background task

                view.runOnUiThread(() -> {
                    view.showInfiniteProgress(false); // UI task
                    Toast.makeText(view, "New recipe added.", Toast.LENGTH_LONG).show(); // UI task
                });
            } catch (InterruptedException e) {
                System.out.println("Exception!");
            }
        });
        thread.start();
    }

    public void scheduleNotification() {

        /*Upload upload1 = new Upload(21, "profile.jpg");
        upload1.execute();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        Upload upload2 = new Upload(34, "hero.png");
        upload2.execute();*/

        Handler uiHandler = new Handler(Looper.getMainLooper());
        HandlerThread hThread = new HandlerThread("background_thread");
        hThread.start();
        Handler bgHandler = new Handler(hThread.getLooper());
        bgHandler.post(() -> {
           // show notification.
            uiHandler.post(() -> { NotificationUtils.showProgressNotification(view, "File Upload", "File is uploading..", 66); });
           // perform actual task.
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                System.out.println("Problem Aayo!!");
            }
           // again show update.
            NotificationUtils.updateNotification(view, "Completeed", "Now you can rest in peace.", 66);
        });
    }

    class Upload extends AsyncTask<Void, Void, Void> {
        int id;
        String fileName;

        public Upload(int id, String fileName) { this.id = id; this.fileName = fileName; }

        @Override
        protected void onPreExecute() {
            super.onPreExecute(); // UI thread
            NotificationUtils.showProgressNotification(view, "File: " + fileName,
                    "File is uploading, please wait..", id);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // background thread
            try {
                Thread.sleep(8000);
                // actual file upload code goes here
            } catch (InterruptedException e) {
                System.out.println("Exception!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused); // UI thread
            //NotificationUtils.updateNotification(view, "File: " + fileName, "Upload completed.", id);
            NotificationUtils.cancelNotification(view, id);
        }
    }

}
