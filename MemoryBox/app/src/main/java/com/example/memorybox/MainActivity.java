package com.example.memorybox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout_main;
    private ViewPager viewPager_main;
    private ViewPagerAdapter viewPagerAdapter_main;
    private Toolbar mainActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.actionbar_main));
        getSupportActionBar().setTitle(null);
        addControls();
        viewPagerAdapter_main.notifyDataSetChanged();
     }

    private void addControls() {
        tabLayout_main = findViewById(R.id.tablayout_main_act);
        viewPager_main = findViewById(R.id.viewpager_main_act);
        mainActionBar = findViewById(R.id.actionbar_main);

        viewPagerAdapter_main = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, this);
        viewPager_main.setAdapter(viewPagerAdapter_main);

        tabLayout_main.setupWithViewPager(viewPager_main);
    }
}