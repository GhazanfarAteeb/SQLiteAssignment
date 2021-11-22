package com.app.sqliteassignment.Activities;

import static com.app.sqliteassignment.DatabaseHelper.DatabaseHelper.COL_IMAGE_ID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import com.app.sqliteassignment.DatabaseHelper.DatabaseHelper;
import com.app.sqliteassignment.Models.ImageModel;
import com.app.sqliteassignment.R;
import com.app.sqliteassignment.adapters.ImageAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class HomeScreenActivity extends AppCompatActivity  {
    static int userID;
    DatabaseHelper databaseHelper;
    RecyclerView recyclerView;
    List<ImageModel> imageList;
    ImageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        recyclerView = findViewById(R.id.recycler_view);
        databaseHelper = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        Cursor data = databaseHelper.fetchUser(databaseHelper.getReadableDatabase(), bundle.getString("username"),bundle.getString("password"));

        if(data.moveToFirst()) {
            int colIndex = data.getColumnIndex(DatabaseHelper.COL_USER_ID);
            userID = data.getInt(colIndex);
        }
        data.close();
        adapter = new ImageAdapter(this);
        imageList = new ArrayList<>();
        setData();

        findViewById(R.id.fab).setOnClickListener(view-> {
            recyclerView.removeAllViews();
            databaseHelper.close();
            Intent intent = new Intent(this,AddImageDetailsActivity.class);
            intent.putExtra("username",bundle.getString("username"));
            intent.putExtra("password" ,bundle.getString("password"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        ImageAdapter.PassData passData = bundle1 -> {
            Intent intent = new Intent(HomeScreenActivity.this, UpdateImageActivity.class);
            intent.putExtra("username", bundle.getString("username"));
            intent.putExtra("password" , bundle.getString("password"));
            intent.putExtra(COL_IMAGE_ID, bundle1.getInt(COL_IMAGE_ID));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        };
        adapter.setPassDataListener(passData);

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void setData() {

        Cursor cursor = databaseHelper.fetchAllData(databaseHelper.getReadableDatabase(),userID);

        while(cursor.moveToNext()) {
            int locationIndex = cursor.getColumnIndex(DatabaseHelper.COL_IMAGE_LOCATION_NAME);
            int locationDescriptionIndex = cursor.getColumnIndex(DatabaseHelper.COL_IMAGE_LOCATION_NAME);
            int userIDIndex = cursor.getColumnIndex(DatabaseHelper.COL_IMAGE_TABLE_USER_ID);
            int imageIDIndex = cursor.getColumnIndex(COL_IMAGE_ID);

            try {
                int pathIndex = cursor.getColumnIndex(DatabaseHelper.COL_IMAGE_PATH);

                imageList.add(new ImageModel(
                        cursor.getInt(imageIDIndex),
                        cursor.getString(locationIndex),
                        cursor.getString(locationDescriptionIndex),
                        cursor.getString(pathIndex),
                        cursor.getInt(userIDIndex)
                ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        adapter.setData(imageList);

    }

}