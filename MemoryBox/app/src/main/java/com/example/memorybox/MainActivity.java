package com.example.memorybox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout_main;
    private ViewPager viewPager_main;
    private ViewPagerAdapter viewPagerAdapter_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
    }

    private void addControls() {
        tabLayout_main = findViewById(R.id.tablayout_main_act);
        viewPager_main = findViewById(R.id.viewpager_main_act);
        viewPagerAdapter_main = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager_main.setAdapter(viewPagerAdapter_main);

        tabLayout_main.setupWithViewPager(viewPager_main);
    }
}