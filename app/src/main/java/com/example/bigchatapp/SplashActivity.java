package com.example.bigchatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.bigchatapp.ChatModule.ChatActivity;
import com.example.bigchatapp.Dashboard.DashboardActivity;
import com.example.bigchatapp.Models.User;
import com.example.bigchatapp.OnBoarding.OnBoardingActivity;
import com.example.bigchatapp.databinding.ActivitySplashBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;
    Context context = SplashActivity.this;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();

        Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.splash_animation);
        binding.animateLayout.setAnimation(myAnim);



        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            if(auth.getCurrentUser() != null) {
                Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
            else {

                startActivity(new Intent(context, OnBoardingActivity.class));
                finish();
            }

        }, 3000);

    }


}
