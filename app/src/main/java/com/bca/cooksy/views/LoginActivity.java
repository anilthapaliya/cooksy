package com.bca.cooksy.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bca.cooksy.R;
import com.bca.cooksy.controllers.LoginController;
import com.bca.cooksy.utils.Constants;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout tilEmail, tilPassword;
    TextInputEditText etEmail, etPassword;
    MaterialButton btnLogin;
    TextView tvSignup;
    LoginController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        controller = new LoginController(this, this);

        findViews();
        registerEvents();
    }

    private void findViews() {

        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignup = findViewById(R.id.tvSignup);
    }

    private void registerEvents() {

        btnLogin.setOnClickListener(view -> {
            controller.performLogin();
        });

        etPassword.setOnEditorActionListener((v, actionId, event) -> {

            if (actionId == EditorInfo.IME_ACTION_DONE) controller.performLogin();

            return false;
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = etEmail.getText().toString();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    tilEmail.setError("Please input valid email address.");
                }
                else {
                    tilEmail.setErrorEnabled(false);
                }
            }
        });

        tvSignup.setOnClickListener(v ->
            startActivity(new Intent(LoginActivity.this, SignupActivity.class)));
    }

    public String getEmail() {
        return etEmail.getText().toString();
    }

    public String getPassword() {
        return etPassword.getText().toString();
    }

    public void showEmailError(String message) {
        tilEmail.setError(message);
    }

    public void showPasswordError(String message) {
        tilPassword.setError(message);
    }

    public void clearErrors() {
        tilEmail.setErrorEnabled(false);
        tilPassword.setErrorEnabled(false);
        etPassword.clearFocus();
        btnLogin.requestFocus();
        hideKeyboard();
    }

    public void hideKeyboard() {

        InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(btnLogin.getWindowToken(), 0);
    }

    public void showToast(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showSnackbar(String message) {

        Snackbar.make(btnLogin, message, Snackbar.LENGTH_LONG).show();
    }

    public void showHome() {

        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("email", etEmail.getText().toString());
        startActivity(intent);
        finish();
        saveLoginData();
    }

    private void saveLoginData() {

        SharedPreferences pref = getSharedPreferences(Constants.CACHE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constants.IS_LOGGED_IN, true);
        editor.putString(Constants.EMAIL_ADDRESS, etEmail.getText().toString().trim());
        editor.commit();
    }

    public void enableItems(boolean value) {

        etEmail.setEnabled(value);
        etPassword.setEnabled(value);
        btnLogin.setEnabled(value);
    }

}
