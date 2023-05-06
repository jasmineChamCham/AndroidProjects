package com.example.contactapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class NewContactActivity extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 200;
    private ImageView ivCamera;
    private String imagePath = "";

    ImageView ivAvatar;
    EditText etFirstName;
    EditText etLastName;
    EditText etPhone;
    EditText etEmail;
    EditText etHome;
    int index=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        etFirstName = findViewById(R.id.tv_firstname);
        etLastName = findViewById(R.id.tv_lastname);
        etPhone = findViewById(R.id.tv_phone);
        etEmail = findViewById(R.id.tv_email);
        etHome = findViewById(R.id.tv_home);
        ivAvatar = findViewById(R.id.ivAvatar);

        Intent receivedIntent = getIntent();
        if (receivedIntent != null){
            String firstName = receivedIntent.getStringExtra("firstName");
            String lastName = receivedIntent.getStringExtra("lastName");
            String phone = receivedIntent.getStringExtra("phone");
            String email = receivedIntent.getStringExtra("email");
            String home = receivedIntent.getStringExtra("home");
            String avatar = receivedIntent.getStringExtra("avatar");
            index = receivedIntent.getIntExtra("index", 0);
            etFirstName.setText(firstName);
            etLastName.setText(lastName);
            etPhone.setText(phone);
            etEmail.setText(email);
            etHome.setText(home);

            if (avatar != null) {
                imagePath = avatar;
                Log.d("avatar in update",avatar);
                File imgFile = new File(avatar);
                if (imgFile != null){
                    Log.d("imgFile in update",imgFile.getAbsolutePath());
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    if (myBitmap != null) {
                        Log.d("myBitmap in update", myBitmap.toString());
                        ivAvatar.setImageBitmap(myBitmap);
                    }
                }
            }
        }

        ImageView ivCamera = findViewById(R.id.camera);
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(NewContactActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(NewContactActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                } else {
                    openCamera();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagePath = saveImage(imageBitmap);
            ivAvatar.setImageBitmap(imageBitmap);
        }
    }


    private String saveImage(Bitmap bitmap) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "IMG_" + timeStamp + ".JPEG";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(storageDir, fileName);

        try (OutputStream out = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageFile.getAbsolutePath();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_new_contact, menu);

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MenuItem saveItem = menu.findItem(R.id.item_save);
        saveItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent();
                Log.d("firstName here", etFirstName.getText().toString());
                intent.putExtra("firstName", etFirstName.getText().toString());
                intent.putExtra("lastName", etLastName.getText().toString());
                intent.putExtra("phone", etPhone.getText().toString());
                intent.putExtra("email", etEmail.getText().toString());
                intent.putExtra("home", etHome.getText().toString());
                intent.putExtra("avatar", imagePath);
                intent.putExtra("index", index);
                setResult(RESULT_OK, intent);
                finish();
                return true;
            }
        });
        return true;
    }
}
