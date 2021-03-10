package com.example.bigchatapp.Dashboard.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bigchatapp.Adapters.UserAdapter;
import com.example.bigchatapp.Models.User;
import com.example.bigchatapp.Models.UserModel;
import com.example.bigchatapp.R;
import com.example.bigchatapp.databinding.FragmentChatBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChatFragment extends Fragment {
    FragmentChatBinding binding;
    FirebaseDatabase database;
    ArrayList<User> list;
    UserAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(getLayoutInflater(), container, false);

        database = FirebaseDatabase.getInstance();

        binding.chatsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();


        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    User userModel = snapshot1.getValue(User.class);
                    list.add(userModel);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new UserAdapter(list, getActivity());
        binding.chatsRecycler.setAdapter(adapter);

        return binding.getRoot();
    }

}