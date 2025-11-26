package com.bca.cooksy.views;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.bca.cooksy.R;
import com.bca.cooksy.controllers.SignupController;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignupActivity extends AppCompatActivity {

    private TextInputLayout tilName, tilEmail, tilPassword;
    private TextInputEditText etName, etEmail, etPassword;
    RadioGroup radioGroup;
    RadioButton radioMale, radioFemale;
    CheckBox chkTerms;
    MaterialButton btnSignup;
    TextView tvLogin;
    Spinner spnCountry;
    SignupController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        controller = new SignupController(this);

        findViews();
        eventRegister();
        makeSpinnerReady();
    }

    private void findViews() {

        tilName = findViewById(R.id.tilName);
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        radioGroup = findViewById(R.id.radioGroup);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        chkTerms = findViewById(R.id.chkTerms);
        spnCountry = findViewById(R.id.spnCountry);
        btnSignup = findViewById(R.id.btnSignup);
    }

    private void eventRegister() {

        btnSignup.setOnClickListener(view -> {
            int sth = radioGroup.getCheckedRadioButtonId();
            String gender;
            if(sth == R.id.radioMale)
                gender = "MALE";
            else gender = "FEMALE";
            boolean isChecked = false;
            if (chkTerms.isChecked()) isChecked = true;

            controller.performSignup(etEmail.getText().toString(),
                    etName.getText().toString(),etPassword.getText().toString(), gender, isChecked);
        });

        spnCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showMessage("Selected " + controller.getCountries().get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        tvLogin.setOnClickListener(v -> finish());
    }

    private void makeSpinnerReady() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, controller.getCountries());
        spnCountry.setAdapter(adapter);
    }

    public void showNameError(String message) {
        tilName.setError(message);
    }

    public void showEmailError(String message) {
        tilEmail.setError(message);
    }

    public void showPasswordError(String message) {
        tilPassword.setError(message);
    }
    public void showMessage(String message){
        Snackbar.make(btnSignup, message, Snackbar.LENGTH_LONG).show();
    }

}
