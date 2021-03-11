package com.example.bigchatapp.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.bigchatapp.ChatModule.ChatActivity;
import com.example.bigchatapp.R;
import com.example.bigchatapp.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding binding;

    String user_name = "";
    String Image = "";
    String mid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            mid = bundle.getString("uid");
            user_name = bundle.getString("name");
            Image = bundle.getString("image");
        }

        binding.userName.setText(user_name);
        Log.d("Hy",user_name);
        Glide.with(this).load(Image).centerInside().into(binding.settingImage);

       /* String name = getIntent().getStringExtra("name");
        String profile = getIntent().getStringExtra("image");
        binding.userName.setText(name);
        Glide.with(SettingActivity.this).load(profile).into(binding.settingImage);*/

        binding.backButton.setOnClickListener(v -> {
            finish();
        });
    }
}