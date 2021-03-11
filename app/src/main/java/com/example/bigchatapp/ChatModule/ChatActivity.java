package com.example.bigchatapp.ChatModule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bigchatapp.Adapters.MessagesAdapter;
import com.example.bigchatapp.Calling.IncomingCallActivity;
import com.example.bigchatapp.R;
import com.example.bigchatapp.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;
    MessagesAdapter adapter;
    ArrayList<ChatModel> list;

    String senderRoom, receiverRoom;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String senderUid;
    String receiverUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        list = new ArrayList<>();
        binding.chatMessageRecycler.setLayoutManager(new LinearLayoutManager(this));

        String name = getIntent().getStringExtra("name");
        String profile = getIntent().getStringExtra("image");
        binding.nameChatPerson.setText(name);
        Glide.with(ChatActivity.this).load(profile)
                .placeholder(R.drawable.image)
                .into(binding.profile);


        receiverUid = getIntent().getStringExtra("uid");
        senderUid = FirebaseAuth.getInstance().getUid();

        database.getReference().child("presence").child(receiverUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String status = snapshot.getValue(String.class);
                    if(!status.isEmpty()) {
                        if(status.equals("Offline")) {
                            binding.status.setVisibility(View.GONE);
                        } else {
                            binding.status.setText(status);
                            binding.status.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        senderRoom = senderUid + receiverUid;
        receiverRoom = receiverUid + senderUid;

        adapter = new MessagesAdapter(this, list);
        binding.chatMessageRecycler.setAdapter(adapter);


        database.getReference().child("chats")
                .child(senderRoom)
                .child("messages")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren())
                        {
                            ChatModel model = snapshot1.getValue(ChatModel.class);
                            model.setMessageID(snapshot1.getKey());
                            list.add(model);

                        }
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        //Send Chat
        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userMessage = binding.chatMessage.getText().toString();

                Date date = new Date();
                ChatModel message = new ChatModel(userMessage,senderUid,date.getTime());
                binding.chatMessage.setText("");

                String randomKey = database.getReference().push().getKey();

                HashMap<String, Object> lastMsgObj = new HashMap<>();
                lastMsgObj.put("lastMsg", message.getMessage());
                lastMsgObj.put("lastMsgTime", date.getTime());

                database.getReference().child("chats").child(senderRoom).updateChildren(lastMsgObj);
                database.getReference().child("chats").child(receiverRoom).updateChildren(lastMsgObj);


                //ChatModel model = new ChatModel(userMessage, senderUid, date.getTime());
                database.getReference().child("chats")
                        .child(senderRoom)
                        .child("messages")
                        .child(randomKey)
                        .setValue(message)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                database.getReference().child("chats")
                                        .child(receiverRoom)
                                        .child("messages")
                                        .child(randomKey)
                                        .setValue(message)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                            }
                                        });
                            }
                        });
            }
        });
        //Back Button
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.ivVoiceCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(ChatActivity.this, IncomingCallActivity.class);
                startActivity(i);
            }
        });
        binding.iVideoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChatActivity.this, "Video Call Activity", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        String currentId = FirebaseAuth.getInstance().getUid();
        database.getReference().child("presence").child(currentId).setValue("Online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        String currentId = FirebaseAuth.getInstance().getUid();
        database.getReference().child("presence").child(currentId).setValue("Offline");
    }
}