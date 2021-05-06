package com.example.memorybox;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.memorybox.PhotoListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoFragment extends Fragment implements PhotoListener{
    public static int REQUEST_IMAGE_CAPTURE = 1;
    public static int ACTION_TAKE_VIDEO_RESULT_CODE = 1;
    public static int ACTION_USE_CAMERA_ALL_FEATURE = 1;

    RecyclerView recyclerView;
    Button addToAlbum;
    PhotoListener photoListener;

    public static PhotoAdapter photoAdapter;
    public static Map<String, ArrayList<Photo>> groupHashMap;
    List<String> getOnlyDate;
    List<Photo> photoList;
    private static final int MY_READ_PERMISSION_CODE = 101;
    public static int positionPhotos = -1;
    public static String getDatePhotos = "";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PhotoFragment() {
        this.photoListener = this;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhotoFragment newInstance(String param1, String param2) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //Chỗ đỗ ảnh
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setHasOptionsMenu(true);
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//
//        // Inflate the layout for this fragment
//        View v = inflater.inflate(R.layout.fragment_photo, container, false);
//        recyclerView = v.findViewById(R.id.recyclerview_photo);
//        addToAlbum = v.findViewById(R.id.buttonAddToAlbum);
//
//
//        ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
//            Log.e("dtbbb", "OKOKbb");
//
//            if (isGranted) {
//                // Permission is granted. Continue the action or workflow in your app
//                Log.e("dtee1", "OKOK");
//                //Change
//                recyclerView.setHasFixedSize(true);
//                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//                photoList = VideoGallery.listOfImages(getContext());
//                getOnlyDate = ShowInforPhotos.onlyGetDate(photoList);
//                groupHashMap = ShowInforPhotos.groupPhotosFollow(photoList, getOnlyDate);
//                photoAdapter = new PhotoAdapter(getContext(), getOnlyDate, this); //Xong
//                photoAdapter.notifyDataSetChanged();
//                recyclerView.setAdapter(photoAdapter);
//            } else {
//                Log.e("dtee", "EError");
//            }
//        });
//        Log.e("dt115", "EError115");
//        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
//        Log.e("dt116", "EError116");
//        Log.e("dt117", "dt117");
//        return v;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_photo, container, false);
        recyclerView = v.findViewById(R.id.recyclerview_photo);

        ActivityResultLauncher<String[]> requestPermissionLauncher =registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), isGranted -> {
            int countPermission=0;
            for(Map.Entry<String, Boolean> b:isGranted.entrySet())
            {
                if(b.getValue())
                {
                    Toast.makeText(getActivity(),b.getKey()+"",Toast.LENGTH_LONG).show();
                    countPermission+=1;
                }
//                Log.e("DEBUG",b.getKey()+" --- "+b.getValue());
            }
            Log.e("DEBUG",""+countPermission);
            if (countPermission==isGranted.size()){
                // Permission is granted. Continue the action or workflow in your app
                Log.e("dtee1","OKOK");
                //Change
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                photoList=VideoGallery.listOfImages(getContext());
                getOnlyDate=ShowInforPhotos.onlyGetDate(photoList);
                groupHashMap=ShowInforPhotos.groupPhotosFollow(photoList,getOnlyDate);
                photoAdapter=new PhotoAdapter(getContext(),getOnlyDate); //Xong
                photoAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(photoAdapter);
            } else {
                Log.e("dtee","EError");
            }
        });
        Log.e("dt115","EError115");
//        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
//        requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        requestPermissionLauncher.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE});
        Log.e("dt116","EError116");
        Log.e("dt117","dt117");
        return v;
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_actionbar_phototab_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main_search:
                Log.e("Option", "Search Selected");
                return true;
            case R.id.action_main_addPhoto:
                Intent cameraIntent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                getActivity().startActivityForResult(cameraIntent, ACTION_USE_CAMERA_ALL_FEATURE);
                Log.e("Option", "Camera Selected");
                photoList.add(new Photo());
                getOnlyDate.add("");
                photoAdapter.notifyDataSetChanged();
                return true;
            case R.id.action_main_select:
                Log.e("Option", "Select Selected");
                return true;
            case R.id.action_main_setting:
                Intent settingIntent = new Intent(this.getContext(), Setting.class);
                startActivity(settingIntent);
                Log.e("Option", "Setting Selected");
                return true;
            default:
                return true;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPhotoShowAction(boolean isSelected) {
        if (isSelected){
            addToAlbum.setVisibility(View.VISIBLE);
        }  else {
            addToAlbum.setVisibility(View.GONE);
        }
    }
}