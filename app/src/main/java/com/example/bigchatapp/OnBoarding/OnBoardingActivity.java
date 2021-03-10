package com.example.bigchatapp.OnBoarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import com.example.bigchatapp.Adapters.TopStatusAdapter;
import com.example.bigchatapp.Dashboard.DashboardActivity;
import com.example.bigchatapp.R;
import com.example.bigchatapp.databinding.ActivityOnBoardingBinding;
import com.google.firebase.auth.FirebaseAuth;

public class OnBoardingActivity extends AppCompatActivity {

    ActivityOnBoardingBinding binding;
    boolean isUp = true;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        String Number = binding.ccP.getSelectedCountryCodeWithPlus();

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null) {
            Intent intent = new Intent(OnBoardingActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }


        binding.phone.requestFocus();
/*
        binding.backButton.setOnClickListener(v -> {

            finish();
        });
*/

        binding.btnSendOtp.setOnClickListener(v -> {
            //Toast.makeText(this, Number+""+binding.phone.getText().toString(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OnBoardingActivity.this,OtpVerifyActivity.class);
           intent.putExtra("phoneNumber",Number+""+binding.phone.getText().toString());
            //Toast.makeText(this, "OK"+binding.phone.getText().toString(), Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });

        /*binding.userMobileLayout.setOnClickListener(v -> {
            if (isUp) {
                slideLeft(binding.cardInternal);
            } else {
                slideRight(binding.cardInternal);
            }
            isUp = !isUp;
        });*/
    }

   /* public void slideRight(View view) {
        binding.userMobileLayout.setVisibility(View.VISIBLE);
        binding.userMobile.setVisibility(View.GONE);
        binding.userMobile.setFocusable(false);
        binding.userMobile.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryLight));
        binding.userMobile.setHintTextColor(ContextCompat.getColor(this, R.color.colorPrimaryLight));
        binding.cardBack.setCardBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryLight));
        binding.frameView.setBackground(ContextCompat.getDrawable(this, R.drawable.stroke_light_overlay));

        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        DisplayMetrics metrics = new DisplayMetrics();

        if(density > 2.0){
            TranslateAnimation animateLow = new TranslateAnimation(
                    150,                 // fromXDelta
                    0,                 // toXDelta
                    0,  // fromYDelta
                    0);
            animateLow.setDuration(400);
            animateLow.setFillAfter(true);
            animateLow.setFillEnabled(true);
            view.startAnimation(animateLow);
        }
        else if(density >= 0.75){
            TranslateAnimation animateLow = new TranslateAnimation(
                    100,                 // fromXDelta
                    0,                 // toXDelta
                    0,  // fromYDelta
                    0);
            animateLow.setDuration(400);
            animateLow.setFillAfter(true);
            animateLow.setFillEnabled(true);
            view.startAnimation(animateLow);
        }


    }

    public void slideLeft(View view) {
        binding.userMobileLayout.setVisibility(View.GONE);
        binding.userMobile.setVisibility(View.VISIBLE);
        binding.userMobile.setFocusable(true);
        binding.userMobile.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        binding.userMobile.setHintTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        binding.cardBack.setCardBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        binding.frameView.setBackground(ContextCompat.getDrawable(this, R.drawable.stroke_dark_overlay));
        binding.cardInternal.setRadius(58f);

        DisplayMetrics metrics = new DisplayMetrics();
        float density = getApplicationContext().getResources().getDisplayMetrics().density;

        if(density > 2.0){
            TranslateAnimation animateLow = new TranslateAnimation(
                    0,                 // fromXDelta
                    150,                 // toXDelta
                    0,  // fromYDelta
                    0);
            animateLow.setDuration(400);
            animateLow.setFillAfter(true);
            animateLow.setFillEnabled(true);
            view.startAnimation(animateLow);

        }
        else if(density >= 0.75){
            TranslateAnimation animateLow = new TranslateAnimation(
                    0,                 // fromXDelta
                    100,                 // toXDelta
                    0,  // fromYDelta
                    0);
            animateLow.setDuration(400);
            animateLow.setFillAfter(true);
            animateLow.setFillEnabled(true);
            view.startAnimation(animateLow);
        }
    }*/
}