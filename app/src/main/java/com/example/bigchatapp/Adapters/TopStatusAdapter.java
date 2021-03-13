package com.example.bigchatapp.Adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.bigchatapp.Models.StatusModel;
import com.example.bigchatapp.Models.UserStatusModel;
import com.example.bigchatapp.R;
import com.example.bigchatapp.databinding.ItemStatusViewBinding;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.callback.StoryClickListeners;
import omari.hamza.storyview.model.MyStory;

public class TopStatusAdapter extends RecyclerView.Adapter<TopStatusAdapter.TopStatusViewHolder> {

    Context context;
    ArrayList<UserStatusModel> userStatuses;

    public TopStatusAdapter(Context context, ArrayList<UserStatusModel> userStatuses) {
        this.context = context;
        this.userStatuses = userStatuses;
    }

    @NonNull
    @Override
    public TopStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_status_view, parent, false);
        return new TopStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopStatusViewHolder holder, int position) {

        UserStatusModel userStatus = userStatuses.get(position);

        StatusModel lastStatus = userStatus.getStatus().get(userStatus.getStatus().size() - 1);

        Glide.with(context).load(lastStatus.getImageUrl()).into(holder.binding.image);

        holder.binding.circularStatusView.setPortionsCount(userStatus.getStatus().size());

        holder.binding.circularStatusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MyStory> myStories = new ArrayList<>();
                for(StatusModel status : userStatus.getStatus()) {
                    myStories.add(new MyStory(status.getImageUrl()));
                }

                new StoryView.Builder(((FragmentActivity)context).getSupportFragmentManager())
                        .setStoriesList(myStories) // Required
                        .setStoryDuration(5000) // Default is 2000 Millis (2 Seconds)
                        .setTitleText(userStatus.getName()) // Default is Hidden
                        .setSubtitleText("") // Default is Hidden
                        .setTitleLogoUrl(userStatus.getProfileImage()) // Default is Hidden
                        .setStoryClickListeners(new StoryClickListeners() {
                            @Override
                            public void onDescriptionClickListener(int position) {
                                //your action
                            }

                            @Override
                            public void onTitleIconClickListener(int position) {
                                //your action
                            }
                        }) // Optional Listeners
                        .build() // Must be called before calling show method
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userStatuses.size();
    }

    public class TopStatusViewHolder extends RecyclerView.ViewHolder {

        ItemStatusViewBinding binding;

        public TopStatusViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStatusViewBinding.bind(itemView);
        }
    }
}
