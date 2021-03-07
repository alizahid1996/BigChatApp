package com.example.bigchatapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bigchatapp.Dashboard.DashboardActivity;
import com.example.bigchatapp.databinding.ActivityUserProfileDialogBinding;

import static com.example.bigchatapp.Constants.CAMERA_IMAGE_CODE;
import static com.example.bigchatapp.Constants.CAMERA_PERMISSION_CODE;

public class UserProfileDialogActivity extends AppCompatActivity {

    ActivityUserProfileDialogBinding binding;
    Bitmap personImageBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserProfileDialogActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
        });

        binding.peronImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (UserProfileDialogActivity.this.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_IMAGE_CODE);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode == CAMERA_IMAGE_CODE && resultCode == Activity.RESULT_OK) {
            personImageBitmap = (Bitmap) imageReturnedIntent.getExtras().get("data");
            binding.peronImage.setImageBitmap(personImageBitmap);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(UserProfileDialogActivity.this, "Camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_IMAGE_CODE);
            } else {
                Toast.makeText(UserProfileDialogActivity.this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}
