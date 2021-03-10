package com.example.bigchatapp.Calling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.bigchatapp.R;
import com.example.bigchatapp.databinding.ActivityIncomingCallBinding;

public class IncomingCallActivity extends AppCompatActivity {
    ActivityIncomingCallBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIncomingCallBinding.inflate(getLayoutInflater());
        binding.getRoot();

        //back button
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding=ActivityIncomingCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        binding.circularStatusView.setBackgroundColor(ContextCompat.getColor(IncomingCallActivity.this,R.color.colorRed));
        rotate.setInterpolator(new LinearInterpolator());
        binding.circularStatusView.startAnimation(rotate);
    }
}