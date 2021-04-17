package com.example.memorybox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public static int PAGE_MAX = 3;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new AlbumFragment();
            case 2:
                return new OnlineFragment();
            default:
                return new PhotoFragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_MAX;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String pageTitle = "";
        switch (position) {
            case 1:
                pageTitle = "ALBUMS";
                break;
            case 2:
                pageTitle = "ONLINE";
                break;
            default:
                pageTitle = "PHOTOS";
                break;
        }
        return pageTitle;
    }
}
