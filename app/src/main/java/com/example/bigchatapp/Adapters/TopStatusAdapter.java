package com.example.bigchatapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bigchatapp.Models.UserStatusModel;
import com.example.bigchatapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

public class TopStatusAdapter extends RecyclerView.Adapter<TopStatusAdapter.ViewHolder> {
    Context context;
    ArrayList<UserStatusModel> statusModels;

    public TopStatusAdapter(Context context, ArrayList<UserStatusModel> statusModels) {
        this.context = context;
        this.statusModels = statusModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_status_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return statusModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
