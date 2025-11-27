package com.bca.cooksy.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bca.cooksy.R;
import com.bca.cooksy.utils.Constants;

public class HomeActivity extends AppCompatActivity {

    private TextView tvUsername;
    private ImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.v(TAG, "onCreate called");

        findViews();
        setupProfile();
    }

    private void findViews() {

        tvUsername = findViewById(R.id.tvUsername);
        imgProfile = findViewById(R.id.imgProfile);
    }

    private void setupProfile() {

        /* Receive the email name from login screen. */
        String email = getIntent().getStringExtra("email");

        if (email == null) {
            email = getEmailAddress();
        }

        if (email == null)
            tvUsername.setText("Hi, User");
        else {
            String[] arr = email.split("@");
            tvUsername.setText("Hi, " + arr[0]);
        }

        imgProfile.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ProfileActivity.class)));
    }

    private String getEmailAddress() {

        SharedPreferences pref = getSharedPreferences(Constants.CACHE, MODE_PRIVATE);
        return pref.getString(Constants.EMAIL_ADDRESS, null);
    }

    final String TAG = "HomeActivity";
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "onPause called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.wtf(TAG, "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy called");
    }

}
