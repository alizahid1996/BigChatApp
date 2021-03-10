package com.example.bigchatapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bigchatapp.ChatModule.ChatActivity;
import com.example.bigchatapp.Models.User;
import com.example.bigchatapp.Models.UserModel;
import com.example.bigchatapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class  UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    ArrayList<UserModel> list;
    Context context;
    //

    public UserAdapter(ArrayList<UserModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_users,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel model = list.get(position);
        Picasso.get().load(model.getUserPic()).placeholder(R.drawable.image).into(holder.imageView);
        holder.userName.setText(model.getUserName());
        holder.userLastMessage.setText(model.getLastMessage());

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView userName, userLastMessage, userMessageTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.profileImageOfChat);
            userName = itemView.findViewById(R.id.userNameOfChat);
            userLastMessage = itemView.findViewById(R.id.userLastMessage);
            userMessageTime = itemView.findViewById(R.id.userMessageTime);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, ChatActivity.class);
                    context.startActivity(i);
                }
            });*/
        }
    }
}