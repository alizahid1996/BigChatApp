package com.example.bigchatapp.Calling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

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
    }
}