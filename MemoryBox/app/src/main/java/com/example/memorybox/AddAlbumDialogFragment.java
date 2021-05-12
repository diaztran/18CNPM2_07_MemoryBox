package com.example.memorybox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.memorybox.R;

import java.io.File;


public class AddAlbumDialogFragment extends DialogFragment {

    EditText addAlbum;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.fragment_add_album_dialog,null);
        addAlbum=view.findViewById(R.id.addAlbum);
        dialog.setView(view);
        dialog.setTitle(R.string.add_album_folder);
        dialog.setPositiveButton(R.string.add_album, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(),"thêm thêm",Toast.LENGTH_LONG).show();
                createAlbum();

            }
        });
        dialog.setNegativeButton(R.string.deny_album, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return dialog.create();
    }

    public void createAlbum(){
        String nameAlbum=addAlbum.getText().toString().trim();
        //Init  file
        File file=new File(Environment.getExternalStorageDirectory(),nameAlbum);
        //check album is exist
        if(file.exists())
        {
            Toast.makeText(getActivity(),"Dir exist",Toast.LENGTH_LONG).show();
        }
        else{
            file.mkdirs();
            //check cond
            if(file.isDirectory())
            {
                Toast.makeText(getActivity(),"Dir create success",Toast.LENGTH_LONG).show();
                AlbumFragment.listAlbums.add(new Album(nameAlbum,""));
                AlbumFragment.albumAdapter.notifyDataSetChanged();
            }
            else{
                Toast.makeText(getActivity(),"Message failed\nPath"+Environment.getExternalStorageDirectory()+"\nmkdirs"+file.mkdirs(),Toast.LENGTH_LONG).show();
            }
        }
    }

}