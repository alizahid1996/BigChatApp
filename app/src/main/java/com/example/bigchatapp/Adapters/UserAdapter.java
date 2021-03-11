package com.example.bigchatapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bigchatapp.ChatModule.ChatActivity;
import com.example.bigchatapp.Models.User;
import com.example.bigchatapp.Models.UserModel;
import com.example.bigchatapp.R;
import com.example.bigchatapp.databinding.ConverstionChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class  UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    ArrayList<User> list;
    Context context;

    public UserAdapter(ArrayList<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.converstion_chat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User model = list.get(position);

        String senderId = FirebaseAuth.getInstance().getUid();

        String senderRoom = senderId + model.getUid();

        FirebaseDatabase.getInstance().getReference()
                .child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            String lastMsg = snapshot.child("lastMsg").getValue(String.class);
                            long time = snapshot.child("lastMsgTime").getValue(Long.class);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                            holder.binding.userMessageTime.setText(dateFormat.format(new Date(time)));
                            holder.binding.userLastMessage.setText(lastMsg);
                        } else {
                            holder.binding.userLastMessage.setText("Tap to chat");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        Glide.with(context).load(model.getProfileImage()).into(holder.binding.profileImage);
        holder.binding.username.setText(model.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                intent.putExtra("name", model.getName());
                intent.putExtra("image", model.getProfileImage());
                intent.putExtra("uid", model.getUid());
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConverstionChatBinding binding;
/*

        ImageView imageView;
        TextView userName, userLastMessage, userMessageTime;
*/

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ConverstionChatBinding.bind(itemView);

           /* imageView = itemView.findViewById(R.id.profileImage);
            userName = itemView.findViewById(R.id.username);
            userLastMessage = itemView.findViewById(R.id.userLastMessage);
            userMessageTime = itemView.findViewById(R.id.userMessageTime);*/
        }
    }
}