package com.example.bigchatapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bigchatapp.Dashboard.DashboardActivity;
import com.example.bigchatapp.Models.User;
import com.example.bigchatapp.Models.UserModel;
import com.example.bigchatapp.databinding.ActivityUserProfileDialogBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.example.bigchatapp.Constants.CAMERA_IMAGE_CODE;
import static com.example.bigchatapp.Constants.CAMERA_PERMISSION_CODE;
import static com.example.bigchatapp.Constants.GALLERY_IMAGE_CODE;

public class UserProfileDialogActivity extends AppCompatActivity {

    ActivityUserProfileDialogBinding binding;
    Bitmap personImageBitmap = null;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri uriImage;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(this);
        dialog.setTitle("Updating Profile");
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        textWatcher(binding.inputUserName, binding.inputUserNameLayout, "Please enter name");

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserProfileDialogActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
        });


        binding.peronImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImageDialog();
            }
        });

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.inputUserName.getText().toString();
                if (name.isEmpty())
                {
                    binding.inputUserNameLayout.setError("Pleas Enter name");
                    return;
                }
                dialog.show();
                if (uriImage != null)
                {
                    StorageReference ref = storage.getReference().child("Profiles").child("myfile");
                    ref.putFile(uriImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful())
                            {
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageUri = uri.toString();
                                       // String userId = auth.getUid();
                                        String phone = auth.getCurrentUser().getPhoneNumber();
                                        String name = binding.inputUserName.getText().toString();
                                        User user = new User("userId", name, phone, imageUri);
                                        database.getReference().child("users").setValue(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        dialog.dismiss();
                                                        Intent i = new Intent(UserProfileDialogActivity.this, DashboardActivity.class);
                                                        startActivity(i);
                                                        finish();

                                                    }
                                                });
                                    }
                                });
                            }
                            else
                            {
                                dialog.dismiss();
                                Toast.makeText(UserProfileDialogActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    StorageReference ref = storage.getReference().child("Profiles").child("myfile");
                    ref.putFile(uriImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful())
                            {
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageUri = uri.toString();
                                        // String userId = auth.getUid();
                                        String phone = auth.getCurrentUser().getPhoneNumber();
                                        String name = binding.inputUserName.getText().toString();
                                        User user = new User("userId", name, phone, "No Image");
                                        database.getReference().child("users").setValue(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        dialog.dismiss();
                                                        Intent i = new Intent(UserProfileDialogActivity.this, DashboardActivity.class);
                                                        startActivity(i);
                                                        finish();

                                                    }
                                                });
                                    }
                                });
                            }
                            else
                            {
                                dialog.dismiss();
                                Toast.makeText(UserProfileDialogActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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
        if (requestCode == 45 && resultCode == RESULT_OK)
        {
            if (imageReturnedIntent != null)
            {
                binding.peronImage.setImageURI(imageReturnedIntent.getData());
                uriImage = imageReturnedIntent.getData();
            }

            if(uriImage != null) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uriImage);
                    personImageBitmap = BitmapFactory.decodeStream(inputStream);
                    binding.peronImage.setImageBitmap(personImageBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
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

    private void chooseImageDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileDialogActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(UserProfileDialogActivity.this).inflate(R.layout.choose_image_dialog, viewGroup, false);

        LinearLayout fromCamera = dialogView.findViewById(R.id.fromCamera);
        LinearLayout fromGallery = dialogView.findViewById(R.id.fromGallery);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);

        fromCamera.setOnClickListener(v->{
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                alertDialog.dismiss();
            }
            else {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_IMAGE_CODE);
                alertDialog.dismiss();
            }
        });

        fromGallery.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, GALLERY_IMAGE_CODE);
            alertDialog.dismiss();
        });

        alertDialog.show();
    }

    private void textWatcher(TextInputEditText inputUsername, TextInputLayout inputUserLayout, String enter_email) {
        inputUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0)
                    inputUserLayout.setError(enter_email);
                else
                    inputUserLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
