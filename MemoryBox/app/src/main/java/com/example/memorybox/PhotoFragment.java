package com.example.memorybox;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoFragment extends Fragment {

    private View v;
    private RecyclerView recyclerView;
    private List<Photo> photoList;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PhotoFragment() {
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
    public static PhotoFragment newInstance(String param1, String param2) {
        PhotoFragment fragment = new PhotoFragment();
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

        photoList = new ArrayList<>();
        photoList.add(new Photo("1","1", R.drawable.ic_launcher_foreground));
        photoList.add(new Photo("1","1", R.drawable.ic_launcher_foreground));
        photoList.add(new Photo("1","1", R.drawable.ic_launcher_foreground));
        photoList.add(new Photo("1","1", R.drawable.ic_launcher_foreground));
        photoList.add(new Photo("1","1", R.drawable.ic_launcher_foreground));
        photoList.add(new Photo("1","1", R.drawable.ic_launcher_foreground));
        photoList.add(new Photo("1","1", R.drawable.ic_launcher_foreground));
        photoList.add(new Photo("1","1", R.drawable.ic_launcher_foreground));
        photoList.add(new Photo("1","1", R.drawable.ic_launcher_foreground));
        photoList.add(new Photo("1","1", R.drawable.ic_launcher_foreground));
        photoList.add(new Photo("1","1", R.drawable.ic_launcher_foreground));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_photo, container, false);
        recyclerView = v.findViewById(R.id.recyclerview_photo);
        PhotoAdapter photoAdapter = new PhotoAdapter(getContext(), photoList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(photoAdapter);
        return v;
    }
}