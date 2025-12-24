package com.bca.cooksy.views;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bca.cooksy.R;
import com.bca.cooksy.controllers.RandomRecipeController;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.MaterialToolbar;

public class RandomRecipeActivity extends AppCompatActivity {

    private TextView tvTitle, tvOrigin, tvCategory, tvRecipe;
    private ImageView imgFood;
    private ImageView btnReload;
    private MaterialToolbar toolbar;
    private ShimmerFrameLayout frameLayout;
    private LinearLayout contentLayout;
    private RandomRecipeController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_random_recipe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        controller = new RandomRecipeController(this);

        findViews();
        registerEvents();
    }

    private void findViews() {

        tvTitle = findViewById(R.id.tvTitle);
        tvOrigin = findViewById(R.id.tvOrigin);
        tvCategory = findViewById(R.id.tvCategory);
        tvRecipe = findViewById(R.id.tvRecipe);
        btnReload = findViewById(R.id.btnReload);
        imgFood = findViewById(R.id.imgFood);
        toolbar = findViewById(R.id.toolbar);
        frameLayout = findViewById(R.id.shimmerLayout);
        contentLayout = findViewById(R.id.contentLayout);
    }

    private void registerEvents() {

        toolbar.setNavigationOnClickListener(v -> finish());

        btnReload.setOnClickListener(v -> controller.loadNewRecipe());
    }

    public void showProgress(boolean value) {

        contentLayout.setVisibility(value ? View.GONE : View.VISIBLE);
        frameLayout.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    public void showMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setOrigin(String origin) {
        tvOrigin.setText(origin);
    }

    public void setCategory(String category) {
        tvCategory.setText(category);
    }

    public void setRecipe(String recipe) {
        tvRecipe.setText(recipe);
    }

    public void setImage(String url) {

        Glide.with(this)
                .load(url)
                .into(imgFood);
    }

}
