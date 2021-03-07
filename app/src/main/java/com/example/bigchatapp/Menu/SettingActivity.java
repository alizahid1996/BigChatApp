package com.example.bigchatapp.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bigchatapp.R;
import com.example.bigchatapp.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backButton.setOnClickListener(v -> {
            finish();
        });
    }
}