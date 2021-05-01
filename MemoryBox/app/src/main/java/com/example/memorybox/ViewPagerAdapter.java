package com.example.memorybox;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter implements PhotoListener{
    public static int PAGE_MAX = 3;
    public Context context;

//    Button addToAlbum;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        this.context = context;
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
                pageTitle = context.getString(R.string.photo_fragment);
                break;
            case 2:
                pageTitle = context.getString(R.string.album_fragment);
                break;
            default:
                pageTitle = context.getString(R.string.online_fragment);
                break;
        }
        return pageTitle;
    }

    @Override
    public void onPhotoShowAction(boolean isSelected) {

    }

//    @Override
//    public void onPhotoShowAction(boolean isSelected) {
//        if (isSelected){
//            addToAlbum.setVisibility(View.VISIBLE);
//        }  else {
//            addToAlbum.setVisibility(View.GONE);
//        }
//    }
}
