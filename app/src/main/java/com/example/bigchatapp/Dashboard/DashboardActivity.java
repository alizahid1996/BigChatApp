package com.example.bigchatapp.Dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.bigchatapp.Adapters.FragmentAdapter;
import com.example.bigchatapp.Menu.SettingActivity;
import com.example.bigchatapp.R;
import com.example.bigchatapp.databinding.ActivityDashboardBinding;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    @Override
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
    }
}