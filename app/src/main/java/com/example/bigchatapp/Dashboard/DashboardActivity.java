package com.example.bigchatapp.Dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bigchatapp.Adapters.FragmentAdapter;
import com.example.bigchatapp.ChatModule.ChatActivity;
import com.example.bigchatapp.Menu.SettingActivity;
import com.example.bigchatapp.R;
import com.example.bigchatapp.databinding.ActivityDashboardBinding;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        binding.menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(DashboardActivity.this, binding.menuIcon);
                popupMenu.inflate(R.menu.dashboard_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.newGroup:
                                Toast.makeText(DashboardActivity.this, "Opens a New Group Activity", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.setting:
                                Intent i = new Intent(DashboardActivity.this, SettingActivity.class);
                                startActivity(i);
                                break;

                            case R.id.logout:
                                //Toast.makeText(DashboardActivity.this, "Opens a Logout Dialog", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                finish();
                                break;

                            case R.id.chat:
                                Intent in = new Intent(DashboardActivity.this, ChatActivity.class);
                                startActivity(in);
                                break;
                        }

                        return false;
                    }
                });

                popupMenu.show();
            }
        });
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.dashboard_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Intent aboutIntent = new Intent(DashboardActivity.this, SettingActivity.class);
                startActivity(aboutIntent);
                break;
            case R.id.logout:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/
}