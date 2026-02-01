package com.bca.cooksy.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bca.cooksy.R;
import com.bca.cooksy.adapter.RecipeAdapter;
import com.bca.cooksy.controllers.DishesController;
import com.bca.cooksy.models.Recipe;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DishesActivity extends AppCompatActivity {

    private RecyclerView recyclerDishes;
    private FloatingActionButton fabAdd;
    DishesController controller;
    RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dishes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerDishes = findViewById(R.id.recyclerDishes);
        fabAdd = findViewById(R.id.fabAdd);

        controller = new DishesController(this);
        this.adapter = new RecipeAdapter(controller.recipes, new RecipeAdapter.ItemClickListener() {
            @Override
            public void onEdit(int position) {
                Recipe recipe = controller.recipes.get(position);
                showAddRecipeSheet(recipe.getTitle(), recipe.getCategory(), recipe.getOrigin());
            }

            @Override
            public void onDelete(int position) {
                controller.deleteRecipe(position);
            }
        });
        controller.setAdapter(adapter);
        recyclerDishes.setAdapter(adapter);
        fabAdd.setOnClickListener(v -> showAddRecipeSheet(null, null, null));
    }

    private void showAddRecipeSheet(String title, String category, String origin) {

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater()
                .inflate(R.layout.bottomsheet_add_recipe, null);

        EditText etTitle = view.findViewById(R.id.etTitle);
        EditText etCategory = view.findViewById(R.id.etCategory);
        EditText etOrigin = view.findViewById(R.id.etOrigin);
        Button btnAdd = view.findViewById(R.id.btnAdd);

        if (title != null) {
            etTitle.setText(title);
            etTitle.setEnabled(false);
        }
        if (category != null) etCategory.setText(category);
        if (origin != null) etOrigin.setText(origin);

        btnAdd.setOnClickListener(v -> {
            String title1 = etTitle.getText().toString().trim();
            String category1 = etCategory.getText().toString().trim();
            String origin1 = etOrigin.getText().toString().trim();

            if (title1.isEmpty() || category1.isEmpty() || origin1.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (title == null)
                controller.addRecipe(title1, category1, origin1);
            else
                controller.editRecipe(new Recipe(title, category1, origin1));

            dialog.dismiss();
        });

        dialog.setContentView(view);
        dialog.show();
    }

}
