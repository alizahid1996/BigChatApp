package com.example.bigchatapp.Dashboard.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.bigchatapp.Adapters.TopStatusAdapter;
import com.example.bigchatapp.ChatModule.ChatActivity;
import com.example.bigchatapp.Models.StatusModel;
import com.example.bigchatapp.Models.UserStatusModel;
import com.example.bigchatapp.R;
import com.example.bigchatapp.databinding.FragmentStatusBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class StatusFragment extends Fragment {
    TopStatusAdapter adapter;
    ArrayList<UserStatusModel> statusModels;
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog dialog;
    FragmentStatusBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStatusBinding.inflate(getLayoutInflater(),container,false);
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();



        statusModels = new ArrayList<>();

        adapter = new TopStatusAdapter(getActivity(),statusModels);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

       binding.statusList.setLayoutManager(layoutManager);
        binding.statusList.setAdapter(adapter);


        database.getReference().child("stories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    statusModels.clear();
                    for(DataSnapshot storySnapshot : snapshot.getChildren()) {
                        UserStatusModel status = new UserStatusModel();
                        status.setName(storySnapshot.child("name").getValue(String.class));
                        status.setProfileImage(storySnapshot.child("profileImage").getValue(String.class));
                        status.setLastUpdated(storySnapshot.child("lastUpdated").getValue(Long.class));

                        ArrayList<StatusModel> statuses = new ArrayList<>();

                        for(DataSnapshot statusSnapshot : storySnapshot.child("statuses").getChildren()) {
                            StatusModel sampleStatus = statusSnapshot.getValue(StatusModel.class);
                            statuses.add(sampleStatus);
                        }

                        status.setStatus(statuses);
                        statusModels.add(status);
                    }
                    binding.statusList.hideShimmerAdapter();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.sendMessage.setOnClickListener(v ->{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 75);
            return;
        });


        return  binding.getRoot();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null) {
            if(data.getData() != null) {
                //dialog.show();
                FirebaseStorage storage = FirebaseStorage.getInstance();
                Date date = new Date();
                StorageReference reference = storage.getReference().child("status").child(date.getTime() + "");

                reference.putFile(data.getData()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    UserStatusModel userStatus = new UserStatusModel();
                                    userStatus.setName(userStatus.getName());
                                    userStatus.setProfileImage(userStatus.getProfileImage());
                                    userStatus.setLastUpdated(date.getTime());

                                    HashMap<String, Object> obj = new HashMap<>();
                                    obj.put("name", userStatus.getName());
                                    obj.put("profileImage", userStatus.getProfileImage());
                                    obj.put("lastUpdated", userStatus.getLastUpdated());

                                    String imageUrl = uri.toString();
                                    StatusModel status = new StatusModel(imageUrl, userStatus.getLastUpdated());

                                    database.getReference()
                                            .child("stories")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .updateChildren(obj);

                                    database.getReference().child("stories")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .child("statuses")
                                            .push()
                                            .setValue(status);

                                    //dialog.dismiss();
                                }
                            });
                        }
                    }
                });
            }
        }
    }
}