package com.example.perfectpitchaccuratepractice;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FilterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
    }

    public void backToMain(View view){
        // Can add intent later
        finish();
    }
}
