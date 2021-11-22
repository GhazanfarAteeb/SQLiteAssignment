package com.app.sqliteassignment.Activities;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.sqliteassignment.DatabaseHelper.DatabaseHelper;
import com.app.sqliteassignment.R;
import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UpdateImageActivity extends AppCompatActivity {
   private ImageView ivImage;
    private Bitmap bitmap;
    RelativeLayout rl;
    DatabaseHelper databaseHelper;
    Button okButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_image);

        EditText etLocation = findViewById(R.id.et_location);
        EditText etDescription = findViewById(R.id.et_description);

        databaseHelper = new DatabaseHelper(this);

        ivImage = findViewById(R.id.iv_image);

        rl = findViewById(R.id.rl_pic_container);
        Bundle bundle = getIntent().getExtras();
        int pathIndex = 0;
        final File[] f = {null};
        Cursor data = databaseHelper.getImage(databaseHelper.getReadableDatabase(),bundle.getInt(DatabaseHelper.COL_IMAGE_ID));
        if (data.moveToFirst()) {
            int locationIndex = data.getColumnIndex(DatabaseHelper.COL_IMAGE_LOCATION_NAME);
            int locationDescriptionIndex = data.getColumnIndex(DatabaseHelper.COL_IMAGE_LOCATION_NAME);
            pathIndex = data.getColumnIndex(DatabaseHelper.COL_IMAGE_PATH);

            etLocation.setText(data.getString(locationIndex));
            etDescription.setText(data.getString(locationDescriptionIndex));
            Glide.with(this).load(data.getString(pathIndex)).asBitmap().into(ivImage);

        }
        okButton = findViewById(R.id.button_ok);
        int finalPathIndex = pathIndex;
        okButton.setOnClickListener(view -> {
            try {
                FileOutputStream fos;

                if (bitmap != null) {
                /* path to /data/data/your app/app_data/imageDir */
                    // Create imageDir
                    String FILENAME = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + ".jpeg";
                    f[0] = new File(new ContextWrapper(getApplicationContext()).getDir("images", Context.MODE_PRIVATE),FILENAME);
                    fos = new FileOutputStream(f[0]);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();
                }


                boolean etLocationValidation = validateText(etLocation);
                boolean etDescriptionValidation = validateText(etDescription);

                if (etLocationValidation && etDescriptionValidation) {
                    String location = etLocation.getText().toString().trim();
                    String description = etDescription.getText().toString().trim();

                    if (bitmap!=null) {
                        databaseHelper.updateImage(databaseHelper.getReadableDatabase(), location, description, f[0].getPath(), bundle.getInt(DatabaseHelper.COL_IMAGE_ID));
                    } else {
                        databaseHelper.updateImage(databaseHelper.getReadableDatabase(), location, description, data.getString(finalPathIndex), bundle.getInt(DatabaseHelper.COL_IMAGE_ID));

                    }
                    databaseHelper.close();
                    Toast.makeText(this,"Data added successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(this, HomeScreenActivity.class);
                    intent.putExtra("username",bundle.getString("username"));
                    intent.putExtra("password" ,bundle.getString("password"));
                    startActivity(intent);
                }
            } catch (Exception e ) {
                e.printStackTrace();
            }
        });
        rl.setOnClickListener(v -> CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(UpdateImageActivity.this)
        );

        findViewById(R.id.iv_back).setOnClickListener(view ->{
            Intent intent = new Intent(this, HomeScreenActivity.class);
            intent.putExtra("username",bundle.getString("username"));
            intent.putExtra("password" ,bundle.getString("password"));
            startActivity(intent);
        });
        findViewById(R.id.button_cancel).setOnClickListener(view ->{
            Intent intent = new Intent(this, HomeScreenActivity.class);
            intent.putExtra("username",bundle.getString("username"));
            intent.putExtra("password" ,bundle.getString("password"));
            startActivity(intent);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    Glide.with(this).load(resultUri).asBitmap().into(ivImage);
                    rl.setBackground(getResources().getDrawable(R.drawable.doted_border));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private boolean validateText(@NonNull EditText field) {
        String text = field.getText().toString().trim();
        boolean noError = true;

        if(text.isEmpty()) {
            field.setError("Field cannot be empty");
            noError = false;
        }

        return noError;
    }
}