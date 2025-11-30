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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bca.cooksy.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

public class ProfileActivity extends AppCompatActivity {

    private MaterialCardView cardLogout;
    private ImageView imgCamera;

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
        imgCamera = findViewById(R.id.imgUpload);
    }

    private final int CAMERA_CODE = 123345;
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

        imgCamera.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                /*
                    * If rationale = true → user has denied before (so you may explain then request)
                    If rationale = false → first time → request permission
                * */
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_CODE);
                }
            }
            else {
                openCamera();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
            else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                        .setTitle("Camera Permission")
                        .setMessage("This app requires camera to capture images.")
                                .setPositiveButton("GOTO SETTINGS", (d, i) -> {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.setData(Uri.fromParts("package", getPackageName(), null));
                                    startActivity(intent);
                                })
                                .setNegativeButton("OK", (d, i) -> {
                                    d.dismiss();
                                });
                dialog.show();
            }
        }
    }

    private void openCamera() {

        Intent intent = new Intent(ProfileActivity.this, CameraActivity.class);
        startActivity(intent);
    }

}
