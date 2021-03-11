package com.example.bigchatapp.Adapters;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.bigchatapp.ChatModule.ChatModel;
import com.example.bigchatapp.R;
import com.example.bigchatapp.databinding.ItemReceiverBinding;
import com.example.bigchatapp.databinding.ItemSenderBinding;
import com.example.bigchatapp.databinding.TryItemRecieveBinding;
import com.example.bigchatapp.databinding.TryItemSentBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessagesAdapter extends RecyclerView.Adapter{

    Context context;
    ArrayList<ChatModel> messages;

    final int ITEM_SENT = 1;
    final int ITEM_RECEIVED = 2;

    public MessagesAdapter(Context context, ArrayList<ChatModel> messages)
    {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_SENT)
        {
            View v = LayoutInflater.from(context).inflate(R.layout.item_sender, parent, false);
            return new sentViewholder(v);
        }
        else
        {
            View v = LayoutInflater.from(context).inflate(R.layout.item_receiver, parent, false);
            return new receiverViewHolder(v);
        }

    }

    @Override
    public int getItemViewType(int position) {
        ChatModel model = messages.get(position);
        if (FirebaseAuth.getInstance().getUid().equals(model.getSenderID()))
        {
            return ITEM_SENT;
        }
        else {
            return ITEM_RECEIVED;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatModel model = messages.get(position);
        if (holder.getClass() == sentViewholder.class)
        {
            sentViewholder sentViewholder = (MessagesAdapter.sentViewholder) holder;
            sentViewholder.binding.messageText.setText(model.getMessage());
        }
        else
        {
            receiverViewHolder receiverViewHolder = (MessagesAdapter.receiverViewHolder) holder;
            receiverViewHolder.binding.messageText.setText(model.getMessage());
        }
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class sentViewholder extends RecyclerView.ViewHolder{

        ItemSenderBinding binding;
        public sentViewholder(@NonNull View itemView) {
            super(itemView);
            binding = ItemSenderBinding.bind(itemView);
        }
    }

    public class receiverViewHolder extends RecyclerView.ViewHolder{
        ItemReceiverBinding binding;
        public receiverViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemReceiverBinding.bind(itemView);
        }
    }
}
