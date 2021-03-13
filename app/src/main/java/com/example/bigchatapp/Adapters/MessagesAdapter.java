package com.example.bigchatapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bigchatapp.ChatModule.ChatModel;
import com.example.bigchatapp.Models.Message;
import com.example.bigchatapp.R;
import com.example.bigchatapp.databinding.DeleteDialogBinding;
import com.example.bigchatapp.databinding.ItemReceiverBinding;
import com.example.bigchatapp.databinding.ItemSenderBinding;
import com.example.bigchatapp.databinding.TryItemRecieveBinding;
import com.example.bigchatapp.databinding.TryItemSentBinding;
import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfig;
import com.github.pgreze.reactions.ReactionsConfigBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessagesAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Message> message;

    final int ITEM_SENT = 1;
    final int ITEM_RECEIVE = 2;

    String senderRoom;
    String receiverRoom;

    public MessagesAdapter(Context context, ArrayList<Message> messages, String senderRoom, String receiverRoom) {
        this.context = context;
        this.message = messages;
        this.senderRoom = senderRoom;
        this.receiverRoom = receiverRoom;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM_SENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.try_item_sent, parent, false);
            return new SentViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.try_item_recieve, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message messages = message.get(position);
        if(FirebaseAuth.getInstance().getUid().equals(messages.getSenderId())) {
            return ITEM_SENT;
        } else {
            return ITEM_RECEIVE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message messages = message.get(position);

        int reactions[] = new int[]{
                R.drawable.ic_fb_like,
                R.drawable.ic_fb_love,
                R.drawable.ic_fb_laugh,
                R.drawable.ic_fb_wow,
                R.drawable.ic_fb_sad,
                R.drawable.ic_fb_angry
        };

        ReactionsConfig config = new ReactionsConfigBuilder(context)
                .withReactions(reactions)
                .build();

        ReactionPopup popup = new ReactionPopup(context, config, (pos) -> {
            if(pos >= 0) {

                if (holder.getClass() == SentViewHolder.class) {
                    SentViewHolder viewHolder = (SentViewHolder) holder;
                    viewHolder.binding.feeling.setImageResource(reactions[pos]);
                    viewHolder.binding.feeling.setVisibility(View.VISIBLE);
                } else {
                    ReceiverViewHolder viewHolder = (ReceiverViewHolder) holder;
                    viewHolder.binding.feeling.setImageResource(reactions[pos]);
                    viewHolder.binding.feeling.setVisibility(View.VISIBLE);


                }

                messages.setFeeling(pos);

                FirebaseDatabase.getInstance().getReference()
                        .child("chats")
                        .child(senderRoom)
                        .child("messages")
                        .child(messages.getMessageId()).setValue(message);

                FirebaseDatabase.getInstance().getReference()
                        .child("chats")
                        .child(receiverRoom)
                        .child("messages")
                        .child(messages.getMessageId()).setValue(message);


            }
            return true; // true is closing popup, false is requesting a new selection
        });


        if(holder.getClass() == SentViewHolder.class) {
            SentViewHolder viewHolder = (SentViewHolder)holder;

            if(messages.getMessage().equals("photo")) {
                viewHolder.binding.image.setVisibility(View.VISIBLE);
                viewHolder.binding.message.setVisibility(View.GONE);
                Glide.with(context)
                        .load(messages.getImageUrl())
                        .placeholder(R.drawable.image)
                        .into(viewHolder.binding.image);
            }

            viewHolder.binding.message.setText(messages.getMessage());

            if(messages.getFeeling() >= 0) {
                viewHolder.binding.feeling.setImageResource(reactions[messages.getFeeling()]);
                viewHolder.binding.feeling.setVisibility(View.VISIBLE);
            } else {
                viewHolder.binding.feeling.setVisibility(View.GONE);
            }

            viewHolder.binding.message.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popup.onTouch(v, event);
                    return false;
                }
            });

            viewHolder.binding.image.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popup.onTouch(v, event);
                    return false;
                }
            });

            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    View view = LayoutInflater.from(context).inflate(R.layout.delete_dialog, null);
                    DeleteDialogBinding binding = DeleteDialogBinding.bind(view);
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("Delete Message")
                            .setView(binding.getRoot())
                            .create();

                    binding.everyone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            messages.setMessage("This message is removed.");
                            messages.setFeeling(-1);
                            FirebaseDatabase.getInstance().getReference()
                                    .child("chats")
                                    .child(senderRoom)
                                    .child("messages")
                                    .child(messages.getMessageId()).setValue(message);

                            FirebaseDatabase.getInstance().getReference()
                                    .child("chats")
                                    .child(receiverRoom)
                                    .child("messages")
                                    .child(messages.getMessageId()).setValue(message);
                            dialog.dismiss();
                        }
                    });

                    binding.delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase.getInstance().getReference()
                                    .child("chats")
                                    .child(senderRoom)
                                    .child("messages")
                                    .child(messages.getMessageId()).setValue(null);
                            dialog.dismiss();
                        }
                    });

                    binding.cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                    return false;
                }
            });
        } else {
            ReceiverViewHolder viewHolder = (ReceiverViewHolder)holder;
            if(messages.getMessage().equals("photo")) {
                viewHolder.binding.image.setVisibility(View.VISIBLE);
                viewHolder.binding.message.setVisibility(View.GONE);
                Glide.with(context)
                        .load(messages.getImageUrl())
                        .placeholder(R.drawable.image)
                        .into(viewHolder.binding.image);
            }
            viewHolder.binding.message.setText(messages.getMessage());

            if(messages.getFeeling() >= 0) {
                //message.setFeeling(reactions[message.getFeeling()]);
                viewHolder.binding.feeling.setImageResource(reactions[messages.getFeeling()]);
                viewHolder.binding.feeling.setVisibility(View.VISIBLE);
            } else {
                viewHolder.binding.feeling.setVisibility(View.GONE);
            }

            viewHolder.binding.message.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popup.onTouch(v, event);
                    return false;
                }
            });

            viewHolder.binding.image.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popup.onTouch(v, event);
                    return false;
                }
            });

            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    View view = LayoutInflater.from(context).inflate(R.layout.delete_dialog, null);
                    DeleteDialogBinding binding = DeleteDialogBinding.bind(view);
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("Delete Message")
                            .setView(binding.getRoot())
                            .create();

                    binding.everyone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            messages.setMessage("This message is removed.");
                            messages.setFeeling(-1);
                            FirebaseDatabase.getInstance().getReference()
                                    .child("chats")
                                    .child(senderRoom)
                                    .child("messages")
                                    .child(messages.getMessageId()).setValue(message);

                            FirebaseDatabase.getInstance().getReference()
                                    .child("chats")
                                    .child(receiverRoom)
                                    .child("messages")
                                    .child(messages.getMessageId()).setValue(message);
                            dialog.dismiss();
                        }
                    });

                    binding.delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase.getInstance().getReference()
                                    .child("chats")
                                    .child(senderRoom)
                                    .child("messages")
                                    .child(messages.getMessageId()).setValue(null);
                            dialog.dismiss();
                        }
                    });

                    binding.cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return message.size();
    }

    public class SentViewHolder extends RecyclerView.ViewHolder {

        TryItemSentBinding binding;
        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TryItemSentBinding.bind(itemView);
        }
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {

        TryItemRecieveBinding binding;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TryItemRecieveBinding.bind(itemView);
        }
    }

}