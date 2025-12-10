package com.bca.cooksy.views;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bca.cooksy.R;
import com.bca.cooksy.utils.Constants;
import com.bca.cooksy.utils.NotificationUtils;
import com.facebook.shimmer.ShimmerFrameLayout;

public class HomeActivity extends AppCompatActivity {

    private TextView tvUsername;
    private ImageView imgProfile;
    private HorizontalScrollView scrollDishes;
    private ShimmerFrameLayout shimmerDishes;

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
        handlePermission();

        showProgress(true);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            showProgress(false);
            NotificationUtils.showNewRecipe(this, "New Recipe", "Make a delicious dish today!",
                    "Omelette Recipe", "Eggs, Salt, Pepper, Oil",
                    "- Put oil on pan.\n- Crack open the eggs.- Sprinkle salt and pepper.\n- Cook for 2 minutes on both sides.\n- Savour your tasty omelette.");
        }, 5000);
    }

    private void findViews() {

        tvUsername = findViewById(R.id.tvUsername);
        imgProfile = findViewById(R.id.imgProfile);
        scrollDishes = findViewById(R.id.scrollDishes);
        shimmerDishes = findViewById(R.id.shimmerDishes);
    }

    private void showProgress(boolean value) {

        if (value) {
            shimmerDishes.setVisibility(View.VISIBLE);
            shimmerDishes.startShimmer();
            scrollDishes.setVisibility(View.GONE);
        }
        else {
            shimmerDishes.setVisibility(View.GONE);
            shimmerDishes.stopShimmer();
            scrollDishes.setVisibility(View.VISIBLE);
        }
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

    private final int NOTIFICATION_CODE = 876876;
    private void handlePermission() {

        //1. Check if the permission is already granted.
        //2. If not granted, ask for permission.
        //3. Handle user's response.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
         != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == NOTIFICATION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "User explicitly granted notification permission", Toast.LENGTH_LONG).show();
            }
            else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                        .setTitle("Notification Permission")
                        .setMessage("This app requires notification permission to notify about updates.");
                dialog.show();
            }
        }
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
