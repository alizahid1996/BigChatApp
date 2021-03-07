package com.example.bigchatapp.Dashboard.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.bigchatapp.Adapters.TopStatusAdapter;
import com.example.bigchatapp.Models.UserStatusModel;
import com.example.bigchatapp.R;
import com.example.bigchatapp.databinding.FragmentStatusBinding;

import java.util.ArrayList;

public class StatusFragment extends Fragment {
    TopStatusAdapter adapter;
    ArrayList<UserStatusModel> statusModels;

    FragmentStatusBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStatusBinding.inflate(getLayoutInflater(),container,false);

        statusModels = new ArrayList<>();

        adapter = new TopStatusAdapter(getActivity(),statusModels);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
        return  binding.getRoot();
    }
}