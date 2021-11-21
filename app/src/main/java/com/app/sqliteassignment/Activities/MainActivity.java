package com.app.sqliteassignment.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;

import com.app.sqliteassignment.DatabaseHelper.DatabaseHelper;
import com.app.sqliteassignment.R;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    EditText username;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);

        databaseHelper = new DatabaseHelper(this);

        ((android.widget.TextView) findViewById(R.id.tv_sign_up)).setOnClickListener(view -> startActivity(new Intent(this, SignUpActivity.class)));

        ((android.widget.Button) findViewById(R.id.button_Sign_in)).setOnClickListener(view -> {
            String usernameText = username.getText().toString().trim();
            String passwordText = password.getText().toString().trim();

            boolean validateUsername = validateField(username);
            boolean validatePassword = validateField(password);

            if (validateUsername && validatePassword) {
                Cursor data = databaseHelper.fetchUser(databaseHelper.getReadableDatabase(), usernameText, passwordText);
                if(data.moveToFirst()) {
                    startActivity(new Intent(this, HomeScreenActivity.class));
                }
            }
        });
    }

    private boolean validateField(EditText field) {
        String text = field.getText().toString().trim();
        boolean noError = true;
        if(text.isEmpty()) {
            field.setError("Field cannot be empty");
            noError = false;
        }
        return noError;
    }
}