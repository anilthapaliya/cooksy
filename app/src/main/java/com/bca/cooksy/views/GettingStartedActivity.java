package com.bca.cooksy.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.bca.cooksy.R;
import com.bca.cooksy.views.fragments.FoodFragment;
import com.bca.cooksy.views.fragments.MenuFragment;
import com.bca.cooksy.views.fragments.WelcomeFragment;
import com.google.android.material.button.MaterialButton;

public class GettingStartedActivity extends AppCompatActivity {

    private MaterialButton btnNext;
    private TextView tvPrevious;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_getting_started);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();
        registerEvents();
        displayFragment(new WelcomeFragment());
    }

    private void findViews() {

        btnNext = findViewById(R.id.btnNext);
        tvPrevious = findViewById(R.id.tvPrevious);
    }

    private void registerEvents() {

        btnNext.setOnClickListener(v -> {

            if (page == 0) {
                displayFragment(new FoodFragment());
                page++;
            }
            else if (page == 1) {
                displayFragment(new MenuFragment());
                page++;
            }
            else if (page == 2) {
                showLogin();
            }
        });

        tvPrevious.setOnClickListener(v -> {

            if (page == 2) {
                displayFragment(new FoodFragment());
                page--;
            }
            else if (page == 1) {
                displayFragment(new WelcomeFragment());
                page--;
            }
        });
    }

    private void displayFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    private void showLogin() {

        Intent intent = new Intent(GettingStartedActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}