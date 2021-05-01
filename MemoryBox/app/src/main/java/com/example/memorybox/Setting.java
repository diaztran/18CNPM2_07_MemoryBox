package com.example.memorybox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.Resource;

import java.util.Locale;

public class Setting extends AppCompatActivity {
    String[] languageArr;
    ArrayAdapter<String> languageAdt;
    AutoCompleteTextView autoCompleteTextView;
    Context context;
    Resources resource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        languageArr = getResources().getStringArray(R.array.languages);
        languageAdt = new ArrayAdapter<String>(Setting.this, R.layout.item_dropdown, languageArr);
        try {
            autoCompleteTextView.setAdapter(languageAdt);
        } catch (Exception e) {
            Log.e("TIL", e.toString());
        }

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String language = parent.getItemAtPosition(position).toString();
//                Toast.makeText(Setting.this, language + " Chosen", Toast.LENGTH_SHORT).show();
                 if (language.equals("Tiếng Việt")){
                     Locale locale = new Locale("vi-rVN");
                 }
            }
        });
    }
}