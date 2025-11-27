package com.bca.cooksy.views;

import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.PermissionChecker;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bca.cooksy.R;
import com.google.android.material.card.MaterialCardView;

public class ProfileActivity extends AppCompatActivity {

    private MaterialCardView cardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();
        registerEvents();
    }

    private void findViews() {

        cardLogout = findViewById(R.id.cardLogout);
    }

    private void registerEvents() {

        cardLogout.setOnClickListener(v -> {

            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("Confirmation")
                    .setMessage("Do you really want to miss out?")
                    .setPositiveButton("Yes", (d, i) -> {
                        // perform logout
                        d.dismiss();
                    })
                    .setCancelable(false)
                    .setNegativeButton("No", (d, i) -> {
                        d.dismiss();
                    });
            dialog.show();
        });
    }

}
