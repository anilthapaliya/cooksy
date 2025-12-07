package com.bca.cooksy.controllers;

import android.content.Context;
import android.util.Patterns;

import com.bca.cooksy.views.LoginActivity;

public class LoginController {

    private Context context;
    private LoginActivity view;

    public LoginController(Context context, LoginActivity view) {
        this.context = context;
        this.view = view;
    }

    public void performLogin() {

        view.enableItems(false);
        String email = view.getEmail();
        String password = view.getPassword();

        if (email.isEmpty()) {
            view.showEmailError("Email can't be empty.");
            view.enableItems(true);
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.showEmailError("Invalid email address.");
            view.enableItems(true);
        }
        else if (password.isEmpty()) {
            view.showPasswordError("Password can't be empty.");
            view.enableItems(true);
        }
        else {
            // perform login on API/backend.
            view.clearErrors();
            //view.showToast("All good! Your form is validated.");
            view.showSnackbar("Login successfully validated.");
            view.showHome();
        }
    }

}
