package com.bca.cooksy.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bca.cooksy.R;
import com.bca.cooksy.utils.Constants;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            decideScreen();
        }, 1000);
    }

    private void decideScreen() {

        SharedPreferences prefs = getSharedPreferences(Constants.CACHE, MODE_PRIVATE);
        boolean isGettingStartedShown = prefs.getBoolean(Constants.IS_GETTING_STARTED_SHOWN, false);
        boolean isLoggedIn = prefs.getBoolean(Constants.IS_LOGGED_IN, false);

        if (isLoggedIn)
            showHome();
        else {
            if (isGettingStartedShown) showLogin();
            else showGettingStarted();
        }
    }

    private void showHome() {

        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void showLogin() {

        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showGettingStarted() {

        Intent intent = new Intent(SplashActivity.this, GettingStartedActivity.class);
        startActivity(intent);
        finish();
    }

}
