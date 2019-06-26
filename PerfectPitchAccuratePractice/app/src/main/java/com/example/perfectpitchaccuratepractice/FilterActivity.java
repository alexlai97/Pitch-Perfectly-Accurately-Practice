package com.example.perfectpitchaccuratepractice;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class FilterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Spinner dropdown = findViewById(R.id.spinner);
        String[] items = new String[]{"D1", "F3", "G4"};
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        Spinner dropdown2 = findViewById(R.id.spinner2);
        String[] items2 = new String[]{"D1", "F3", "G4"};
        ArrayAdapter<String> adapter2= new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);

        Spinner dropdown3 = findViewById(R.id.spinner3);
        String[] items3 = new String[]{"Major", "Melodic Minor", "Natural Minor", "Harmonic Minor"};
        ArrayAdapter<String> adapter3= new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items3);
        dropdown3.setAdapter(adapter3);

        Spinner dropdown4 = findViewById(R.id.spinner4);
        String[] items4 = new String[]{"C", "C#", "D", "D#/Eb"};
        ArrayAdapter<String> adapter4= new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items4);
        dropdown4.setAdapter(adapter4);

    }

    public void backToMain(View view){
        // Can add intent later
        finish();
    }

}
