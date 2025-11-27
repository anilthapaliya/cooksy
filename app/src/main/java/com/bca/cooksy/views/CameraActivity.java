package com.bca.cooksy.views;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bca.cooksy.R;
import com.google.common.util.concurrent.ListenableFuture;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.Executor;

public class CameraActivity extends AppCompatActivity {

    private PreviewView cameraPreview;
    private ImageView cameraClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_camera);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();
        prepareCamera();
    }

    private void findViews() {

        cameraPreview = findViewById(R.id.cameraPreview);
        cameraClick = findViewById(R.id.cameraClick);
    }

    private ImageCapture imageCapture;
    private Executor executor;
    private void prepareCamera() {

        ListenableFuture<ProcessCameraProvider> provider = ProcessCameraProvider.getInstance(this);
        executor = getMainExecutor();

        provider.addListener(() -> {
            try {
                ProcessCameraProvider p = provider.get();
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(cameraPreview.getSurfaceProvider());
                imageCapture = new ImageCapture.Builder().build();

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                p.unbindAll();
                p.bindToLifecycle(this, cameraSelector, preview, imageCapture);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }, executor);

        cameraClick.setOnClickListener(v -> click());
    }

    private void click() {

        if (imageCapture == null) return;
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(System.currentTimeMillis());
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_" + name + ".jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Cooksy");

        ImageCapture.OutputFileOptions options = new ImageCapture.OutputFileOptions
                .Builder(getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                .build();

        imageCapture.takePicture(options, executor, new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults output) {
                Uri savedUri = output.getSavedUri();
                runOnUiThread(() ->
                        Toast.makeText(CameraActivity.this, "Saved: " + savedUri, Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onError(@NonNull ImageCaptureException e) {
                runOnUiThread(() ->
                        Toast.makeText(CameraActivity.this, "Capture failed", Toast.LENGTH_SHORT).show());
            }
        });
    }

}
