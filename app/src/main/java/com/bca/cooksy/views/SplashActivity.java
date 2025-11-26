package com.bca.cooksy.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bca.cooksy.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            showGettingStarted();
        }, 1000);
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
