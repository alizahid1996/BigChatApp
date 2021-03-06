package com.example.bigchatapp.OnBoarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.bigchatapp.R;
import com.example.bigchatapp.databinding.ActivityOnBoardingBinding;

public class OnBoardingActivity extends AppCompatActivity {

    ActivityOnBoardingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backButton.setOnClickListener(v -> {

            finish();
        });

        binding.sendOTP.setOnClickListener(v -> {
            Intent intent = new Intent(OnBoardingActivity.this,OtpVerifyActivity.class);
            startActivity(intent);
        });
    }
}