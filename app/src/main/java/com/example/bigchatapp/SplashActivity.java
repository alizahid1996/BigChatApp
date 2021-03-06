package com.example.bigchatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.bigchatapp.ChatModule.ChatActivity;
import com.example.bigchatapp.OnBoarding.OnBoardingActivity;
import com.example.bigchatapp.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;
    Context context = SplashActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Animation myanim = AnimationUtils.loadAnimation(context, R.anim.splash_animation);
        binding.animateLayout.setAnimation(myanim);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            startActivity(new Intent(context, ChatActivity.class));
            finish();


        }, 3000);

    }


    }
