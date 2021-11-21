package com.app.sqliteassignment.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sqliteassignment.DatabaseHelper.DatabaseHelper;
import com.app.sqliteassignment.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddImageDetailsActivity extends AppCompatActivity {
    private TextView tvAddPicture;
    private ImageView ivImage;
    private Bitmap bitmap;
    static File directory;
    Button okButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image_details);

        ivImage = findViewById(R.id.iv_image);
        tvAddPicture = findViewById(R.id.tv_add_picture);
        RelativeLayout rl = findViewById(R.id.rl_pic_container);

        Bundle bundle = getIntent().getExtras();

        okButton = findViewById(R.id.button_ok);
        okButton.setOnClickListener(view -> {
            try {
                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                /* path to /data/data/your app/app_data/imageDir */
                FileOutputStream fos;
                directory = cw.getDir("images", Context.MODE_PRIVATE);
                // Create imageDir
                String FILENAME = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                FILENAME += ".jpg";
                fos = new FileOutputStream(new File(directory,FILENAME));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();

                DatabaseHelper databaseHelper = new DatabaseHelper(this);
                String location = ((EditText) findViewById(R.id.et_location)).getText().toString().trim();
                String description = ((EditText) findViewById(R.id.et_description)).getText().toString().trim();
                databaseHelper.addImage(databaseHelper.getReadableDatabase(), location, description, FILENAME, HomeScreenActivity.userID);
                Toast.makeText(this,"Data added successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, HomeScreenActivity.class);
                startActivity(intent);
            } catch (Exception e ) {
                e.printStackTrace();
            }
        });
        rl.setOnClickListener(v -> CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(AddImageDetailsActivity.this)
        );

        ((ImageView) findViewById(R.id.iv_back)).setOnClickListener(view ->startActivity(new Intent(this, HomeScreenActivity.class)));
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
                    ivImage.setImageBitmap(bitmap);
                    tvAddPicture.setVisibility(View.GONE);

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}