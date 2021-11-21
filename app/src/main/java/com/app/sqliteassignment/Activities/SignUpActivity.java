package com.app.sqliteassignment.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.sqliteassignment.DatabaseHelper.DatabaseHelper;
import com.app.sqliteassignment.R;

public class SignUpActivity extends AppCompatActivity {
    Button signUpButton;
    EditText firstName, lastName, email, password, confirmPassword;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpButton = findViewById(R.id.button_sign_up);
        firstName = findViewById(R.id.et_first_name);
        lastName = findViewById(R.id.et_last_name);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        confirmPassword = findViewById(R.id.et_confirm_password);

        databaseHelper = new DatabaseHelper(this);

        signUpButton.setOnClickListener(view -> {
            boolean validateFirstName = validateText(firstName);
            boolean validateLastName = validateText(lastName);
            boolean validateEmail = validateEmail(email);
            boolean validatePassword = validatePassword(password);
            boolean validateConfirmPassword = validatePassword(confirmPassword);

            if (validateFirstName && validateLastName && validateEmail && validatePassword && validateConfirmPassword) {
                String passwordText = password.getText().toString().trim();
                String confirmPasswordText = confirmPassword.getText().toString().trim();

                if (passwordText.equals(confirmPasswordText)) {
                    databaseHelper.addUser(
                            databaseHelper.getWritableDatabase(),
                            firstName.getText().toString().trim(),
                            lastName.getText().toString().trim(),
                            email.getText().toString().trim(),
                            password.getText().toString().trim()
                    );
                    Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, HomeScreenActivity.class));
                } else {
                    password.setError("Password must be same");
                    confirmPassword.setError("Password must be same");
                }
            }
        });

        ((android.widget.TextView) findViewById(R.id.tv_sign_in)).setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));
    }

    private boolean validateEmail(EditText field) {
        String text = field.getText().toString().trim();
        boolean noError = true;

        if (text.length() < 5) {
            field.setError("Enter a valid email address");
            noError = false;
        }

        return noError;
    }

    private boolean validateText(EditText field) {
        String text = field.getText().toString().trim();
        boolean noError = true;

        if(text.isEmpty()) {
            field.setError("Field cannot be empty");
            noError = false;
        } else if (!text.matches("[a-zA-Z]+")) {
            field.setError("Field should have alphabets only");
            noError = false;
        }

        return noError;
    }

    private boolean validatePassword(EditText field) {
        String text = field.getText().toString().trim();
        boolean noError = true;

        if(text.length() < 7) {
            field.setError("There must be at least 8 characters");
            noError = false;
        }

        return noError;
    }
}