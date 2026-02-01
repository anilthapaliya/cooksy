package com.bca.cooksy.controllers;

import android.content.ContentValues;
import android.database.Cursor;

import com.bca.cooksy.adapter.RecipeAdapter;
import com.bca.cooksy.models.Recipe;
import com.bca.cooksy.utils.App;
import com.bca.cooksy.utils.DbHelper;
import com.bca.cooksy.views.DishesActivity;

import java.util.ArrayList;
import java.util.List;

public class DishesController {

    DishesActivity view;
    RecipeAdapter adapter;
    public List<Recipe> recipes;

    public DishesController(DishesActivity view) {
        this.view = view;
        this.recipes = new ArrayList<>();
    }

    public void setAdapter(RecipeAdapter adapter) {
        this.adapter = adapter;
        getRecipes();
    }

    public void getRecipes() {

        String query = "SELECT title, category, origin FROM " + DbHelper.RECIPE_TABLE;
        Cursor reader = App.db.rawQuery(query, null);
        while(reader.moveToNext()) {
            String title = reader.getString(0);
            String category = reader.getString(1);
            String origin = reader.getString(2);

            Recipe recipe = new Recipe(title, category, origin);
            recipes.add(recipe);
        }
        reader.close();
        adapter.notifyDataSetChanged();
    }

    public void addRecipe(String title, String category, String origin) {

        Recipe recipe = new Recipe(title, origin, category);
        recipes.add(recipe);
        adapter.notifyItemInserted(recipes.size() - 1);

        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("category", category);
        values.put("origin", origin);

        App.db.insert(DbHelper.RECIPE_TABLE, null, values);
    }

    public void deleteRecipe(int position) {

        recipes.remove(position);
        adapter.notifyItemRemoved(position);
    }

    public void editRecipe(Recipe recipe) {

        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).getTitle().equals(recipe.getTitle())) {
                recipes.set(i, recipe);
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }

}
