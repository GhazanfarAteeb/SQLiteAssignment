package com.app.sqliteassignment.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.app.sqliteassignment.DatabaseHelper.DatabaseHelper;
import com.app.sqliteassignment.R;


public class HomeScreenActivity extends AppCompatActivity {
    static int userID;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        databaseHelper = new DatabaseHelper(this);
        Bundle bundle = getIntent().getExtras();
        Cursor data = databaseHelper.fetchUser(databaseHelper.getReadableDatabase(), bundle.getString("username"),bundle.getString("password"));

        if(data.moveToFirst()) {
            int colIndex = data.getColumnIndex(DatabaseHelper.COL_USER_ID);
            userID = data.getInt(colIndex);
        }

        findViewById(R.id.fab).setOnClickListener(view-> {
            Intent intent = new Intent(this,AddImageDetailsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}