package com.bca.cooksy.controllers;

import com.bca.cooksy.views.SignupActivity;

import java.util.ArrayList;

public class SignupController {

    private SignupActivity view;
    private ArrayList<String> countries;

    public SignupController(SignupActivity view) {
        this.view = view;
        countries = new ArrayList<>();
        loadCountries();
    }

    public void performSignup(String email,String name,String password, String gender, boolean isChecked) {

        if(name.isEmpty()) {
            view.showNameError("name cannot be empty.");
        }
        if(email.isEmpty()) {
            view.showEmailError("Email cannot be empty.");
        }
        if(password.isEmpty()) {
            view.showPasswordError("Password Cannot be empty.");
        }
        if (!isChecked) {
            view.showMessage("Please read Terms and Conditions.");
        }
        else {
            view.showMessage("user account created");
            // Call API to create an account on backend.
        }
    }

    public void loadCountries() {

        countries.add("Nepal");
        countries.add("USA");
        countries.add("Australia");
        countries.add("India");
        countries.add("Germany");
    }

    public ArrayList<String> getCountries() {
        return countries;
    }

}
