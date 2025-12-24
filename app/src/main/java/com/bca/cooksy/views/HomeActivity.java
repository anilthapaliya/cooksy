package com.bca.cooksy.views;

import android.Manifest;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.bca.cooksy.controllers.HomeController;
import com.bca.cooksy.utils.Constants;
import com.bca.cooksy.utils.NotificationUtils;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class HomeActivity extends AppCompatActivity {

    private TextView tvUsername;
    private ImageView imgProfile;
    private FloatingActionButton btnAdd;
    private MaterialCardView cardReminder, cardRandomRecipe;
    private ProgressBar progressBar;
    private HorizontalScrollView scrollDishes;
    private ShimmerFrameLayout shimmerDishes;
    private HomeController controller;

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
        controller = new HomeController(this);

        findViews();
        registerEvents();
        setupProfile();
        handlePermission();

        showProgress(true);
        /*new Handler(Looper.getMainLooper()).postDelayed(() -> {
            showProgress(false);
            NotificationUtils.showNewRecipe(this, "New Recipe", "Make a delicious dish today!",
                    "Omelette Recipe", "Eggs, Salt, Pepper, Oil",
                    "- Put oil on pan.\n- Crack open the eggs.- Sprinkle salt and pepper.\n- Cook for 2 minutes on both sides.\n- Savour your tasty omelette.");
        }, 5000);*/
    }

    private void findViews() {

        tvUsername = findViewById(R.id.tvUsername);
        imgProfile = findViewById(R.id.imgProfile);
        btnAdd = findViewById(R.id.btnAdd);
        cardReminder = findViewById(R.id.cardReminder);
        cardRandomRecipe = findViewById(R.id.cardRandomRecipe);
        progressBar = findViewById(R.id.progressBar);
        scrollDishes = findViewById(R.id.scrollDishes);
        shimmerDishes = findViewById(R.id.shimmerDishes);
    }

    private void registerEvents() {

        btnAdd.setOnClickListener(v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View view = getLayoutInflater().inflate(R.layout.add_new_recipe, null);
            dialog.setContentView(view);

            MaterialButton btnAddRecipe = view.findViewById(R.id.btnAddRecipe);
            btnAddRecipe.setOnClickListener(b -> {
                controller.addRecipe();
                dialog.dismiss();
            });
            dialog.show();
        });

        cardReminder.setOnClickListener(v -> {

            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (manager.canScheduleExactAlarms()) {
                showReminderDialog();
            }
            else {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
            }
        });

        cardRandomRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(this, RandomRecipeActivity.class);
            startActivity(intent);
        });
    }

    private void showReminderDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Set a reminder")
                .setMessage("Please enter a title and minutes to schedule a reminder.");
        EditText etTitle = new EditText(this);
        etTitle.setHint("Title");
        etTitle.setInputType(InputType.TYPE_CLASS_TEXT);

        EditText etMinute = new EditText(this);
        etMinute.setHint("After minutes");
        etMinute.setInputType(InputType.TYPE_CLASS_NUMBER);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(12, 15, 12, 15);
        layout.setLayoutParams(params);
        layout.addView(etTitle);
        layout.addView(etMinute);

        dialog.setCancelable(false);
        dialog.setView(layout);
        dialog.setNegativeButton("Cancel", (d, i) -> d.dismiss());
        dialog.setPositiveButton("SET", (d, i) -> {
            // code to schedule a reminder.
            String msg = etTitle.getText().toString();
            String minutes = etMinute.getText().toString();
            if (msg.isEmpty() || minutes.isEmpty())
                Toast.makeText(this, "Please enter all fields.", Toast.LENGTH_LONG).show();
            else {
                controller.setReminder(msg, minutes);
                d.dismiss();
            }
        });

        dialog.show();
    }

    public void showMessage(String message) {

        Snackbar.make(btnAdd, message, Snackbar.LENGTH_LONG).show();
    }

    public void showInfiniteProgress(boolean value) {

        progressBar.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
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
