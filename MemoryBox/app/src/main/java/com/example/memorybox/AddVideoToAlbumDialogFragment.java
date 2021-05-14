package com.example.memorybox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddVideoToAlbumDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddVideoToAlbumDialogFragment  extends DialogFragment {
    RecyclerView recyclerView;
    public static AddVideosToAlbumAdapter addVideosToAlbumAdapter;
    public static List<Album> listAlbumsDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddVideoToAlbumDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddVideoToAlbumDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddVideoToAlbumDialogFragment newInstance(String param1, String param2) {
        AddVideoToAlbumDialogFragment fragment = new AddVideoToAlbumDialogFragment();
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

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_add_video_to_album_dialog, container, false);
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.fragment_add_video_to_album_dialog,null);
        recyclerView=view.findViewById(R.id.recyclerview_album);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listAlbumsDialog=HandleAlbum.getAlbum(getContext());
        addVideosToAlbumAdapter = new AddVideosToAlbumAdapter(getContext(),listAlbumsDialog); //Xong
        recyclerView.setAdapter(addVideosToAlbumAdapter);
        dialog.setView(view);



        dialog.setNegativeButton(R.string.deny_album, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return dialog.create();

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