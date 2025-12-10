package com.bca.cooksy.views;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bca.cooksy.R;
import com.google.android.material.appbar.MaterialToolbar;

public class RecipeActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private TextView tvTitle, tvIngredients, tvRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();
        populateViews();
    }

    private void findViews() {

        toolbar = findViewById(R.id.toolbar);
        tvTitle = findViewById(R.id.tvTitle);
        tvIngredients = findViewById(R.id.tvIngredients);
        tvRecipe = findViewById(R.id.tvRecipe);
    }

    private void populateViews() {

        String title = getIntent().getStringExtra("title");
        String ingredients = getIntent().getStringExtra("ingredients");
        String recipe = getIntent().getStringExtra("recipe");

        tvTitle.setText(title);
        tvIngredients.setText(ingredients);
        tvRecipe.setText(recipe);
    }

}
