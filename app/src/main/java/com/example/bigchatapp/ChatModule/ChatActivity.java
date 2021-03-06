package com.example.bigchatapp.ChatModule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bigchatapp.R;
import com.example.bigchatapp.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}