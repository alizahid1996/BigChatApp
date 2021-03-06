package com.example.bigchatapp.OnBoarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.bigchatapp.Dashboard.DashboardActivity;
import com.example.bigchatapp.R;
import com.example.bigchatapp.databinding.ActivityOtpVerifyBinding;

public class OtpVerifyActivity extends AppCompatActivity {

    ActivityOtpVerifyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);

        binding = ActivityOtpVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backButton.setOnClickListener(v -> {

            finish();
        });

        binding.submitOTP.setOnClickListener(v -> {
            Intent intent = new Intent(OtpVerifyActivity.this, DashboardActivity.class);
            startActivity(intent);
        });
    }
}