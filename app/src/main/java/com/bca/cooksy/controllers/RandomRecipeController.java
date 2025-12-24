package com.bca.cooksy.controllers;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import com.bca.cooksy.models.Meals;
import com.bca.cooksy.models.Recipe;
import com.bca.cooksy.utils.Constants;
import com.bca.cooksy.views.RandomRecipeActivity;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RandomRecipeController {

    private RandomRecipeActivity view;

    public RandomRecipeController(RandomRecipeActivity view) {
        this.view = view;
        loadNewRecipe();
    }

    public void loadNewRecipe() {

        // Call an API here.
        HandlerThread hThread = new HandlerThread("background");
        hThread.start();
        Handler bgHandler = new Handler(hThread.getLooper());
        Handler mainHandler = new Handler(Looper.getMainLooper());

        // Actual API calling.
        bgHandler.post(() -> {
            //1. Show loading progress
            mainHandler.post(() -> view.showProgress(true));

            //2. Get data from API
            try {
                URL url = new URL(Constants.API_MEAL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                conn.disconnect();

                // Instantiate string response to model class: Recipe
                Gson gson = new Gson();
                Meals m = gson.fromJson(sb.toString(), Meals.class);
                Recipe recipe = m.getMeals().get(0);

                //3. Turn off loading and populate the API response.
                mainHandler.post(() -> {
                    view.showProgress(false);
                    view.setTitle(recipe.getTitle());
                    view.setCategory(recipe.getCategory());
                    view.setOrigin(recipe.getOrigin());
                    view.setRecipe(recipe.getInstructions());
                    view.setImage(recipe.getImageLink());
                });
            } catch (MalformedURLException e) {
                Log.d("Exception", "Invalid URL supplied.");
                mainHandler.post(() -> {
                    view.showMessage("Unable to load new recipe.");
                });
            } catch (IOException e) {
                Log.d("Exception", "Unable to open connection.");
                mainHandler.post(() -> {
                    view.showMessage("Connection to server not established.");
                });
            }
        });
    }

}
