package com.example.memorybox;

import android.Manifest;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumFragment extends Fragment {
    RecyclerView recyclerView;
    ItemAlbumAdapter itemAlbumAdapter;
    List<Bucket> buckets;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AlbumFragment() {
        // Required empty public constructor
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
    public static AlbumFragment newInstance(String param1, String param2) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("lofi","oncreateView");
        View v=inflater.inflate(R.layout.fragment_album, container, false);
        recyclerView=v.findViewById(R.id.recyclerview_albulm);
        Log.e("lofi1","oncreateView1");
        ActivityResultLauncher<String> requestPermissionLauncher =registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            Log.e("lofi2","in request");

            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your app
                Log.e("lofi3","in isGranted");
                //Change
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                buckets=getImageBuckets(getContext());
                Log.e("lofiBucket",buckets.size()+"");

                itemAlbumAdapter=new ItemAlbumAdapter(getContext(),buckets); //Xong
                Log.e("lofiBucketaffter",buckets.size()+"");
                itemAlbumAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(itemAlbumAdapter);
            } else {
//                Log.e("dtee","EError");
            }
        });
        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        return v;
    }

    public static List<Bucket> getImageBuckets(Context mContext){
        List<Bucket> buckets = new ArrayList<>();
        Uri uri= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String [] projection = {MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA};
        List<String> nameAlbum=new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
        if(cursor != null){
            File file;
            while (cursor.moveToNext()){
                String bucketPath = cursor.getString(cursor.getColumnIndex(projection[0]));
                String fisrtImage = cursor.getString(cursor.getColumnIndex(projection[1]));
                file = new File(fisrtImage);
                String bucketName;
                if (file.exists()&&!nameAlbum.contains(bucketPath)) {
                    buckets.add(new Bucket(bucketPath, fisrtImage));
                    nameAlbum.add(bucketPath);
                }
            }
            cursor.close();
        }
        return buckets;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_actionbar_albumtab_menu, menu);
    }
}