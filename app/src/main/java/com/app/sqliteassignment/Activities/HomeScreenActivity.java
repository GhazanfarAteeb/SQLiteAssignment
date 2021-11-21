package com.app.sqliteassignment.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.sqliteassignment.R;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}