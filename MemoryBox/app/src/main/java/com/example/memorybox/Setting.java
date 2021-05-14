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
    String[] themeArr;

    public static String appTheme = "Light";

    ArrayAdapter<String> languageAdt;
    ArrayAdapter<String> themeAdt;

    AutoCompleteTextView lang_autoCompleteTextView;
    AutoCompleteTextView theme_autoCompleteTextView;

    Context context;
    Resources resource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        lang_autoCompleteTextView = findViewById(R.id.lang_autoCompleteTextView);
        theme_autoCompleteTextView = findViewById(R.id.theme_autoCompleteTextView);

        languageArr = getResources().getStringArray(R.array.languages);
        themeArr = getResources().getStringArray(R.array.themes);

        languageAdt = new ArrayAdapter<String>(Setting.this, R.layout.item_dropdown, languageArr);
        try {
            lang_autoCompleteTextView.setAdapter(languageAdt);
        } catch (Exception e) {
            Log.e("TIL", e.toString());
        }

        lang_autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String language = parent.getItemAtPosition(position).toString();
//                Toast.makeText(Setting.this, language + " Chosen", Toast.LENGTH_SHORT).show();
                 if (language.equals("Tiếng Việt")){
                     Locale locale = new Locale("vi-rVN");
                 }
            }
        });

        themeAdt = new ArrayAdapter<String>(Setting.this, R.layout.item_dropdown, themeArr);
        try {
            theme_autoCompleteTextView.setAdapter(themeAdt);
        } catch (Exception e) {
            Log.e("TIL", e.toString());
        }

        theme_autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String theme = parent.getItemAtPosition(position).toString();
//                oast.makeText(SetTting.this, language + " Chosen", Toast.LENGTH_SHORT).show();
                if (theme.equals("Dark")){
                    Toast.makeText(Setting.this, "Dark", Toast.LENGTH_SHORT).show();
                } else if (theme.equals("Light")){
                    Toast.makeText(Setting.this, "Light", Toast.LENGTH_SHORT).show();
                }

                Setting.this.setAppTheme(theme);
            }
        });
    }

    public void setAppTheme(String theme){
        this.appTheme = theme;
    }
}