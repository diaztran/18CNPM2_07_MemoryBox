package com.example.memorybox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoInformationFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //    Value
    TextView nameMedia,pathMedia,dateTakenMedia,sizeMedia,dimensionMedia,displayDuration,addressMedia;
    public VideoInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhotoInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoInformationFragment newInstance(String param1, String param2) {
        VideoInformationFragment fragment = new VideoInformationFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentV
        View v=inflater.inflate(R.layout.fragment_video_information, container, false);
        PlayVideoActivity activity= (PlayVideoActivity) getActivity();
        ArrayList<String> listInfoPhoto=activity.getListInfoVideo();
        nameMedia=v.findViewById(R.id.nameMedia);
        pathMedia=v.findViewById(R.id.pathMedia);
        dateTakenMedia=v.findViewById(R.id.dateTakenMedia);
        sizeMedia=v.findViewById(R.id.sizeMedia);
        dimensionMedia=v.findViewById(R.id.dimensionMedia);
        displayDuration=v.findViewById(R.id.displayDuration);
        addressMedia=v.findViewById(R.id.addressMedia);

        nameMedia.setText(listInfoPhoto.get(0));
        pathMedia.setText(listInfoPhoto.get(1));
        dateTakenMedia.setText(listInfoPhoto.get(2));
        sizeMedia.setText(listInfoPhoto.get(3));
        dimensionMedia.setText(listInfoPhoto.get(4));
        displayDuration.setText(listInfoPhoto.get(5));
        addressMedia.setText(listInfoPhoto.get(6));
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onStart() {
        super.onStart();
        int dialogWidth = getResources().getDisplayMetrics().widthPixels;
        int dialogHeight = getResources().getDisplayMetrics().heightPixels;
        getDialog().getWindow().setLayout(dialogWidth,dialogHeight);
    }
}