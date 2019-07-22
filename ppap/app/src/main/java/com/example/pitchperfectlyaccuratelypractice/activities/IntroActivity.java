package com.example.pitchperfectlyaccuratelypractice.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkMicrophonePermission();

        SliderPage sliderPage1 = new SliderPage();
        sliderPage1.setTitle("Welcome!");
        sliderPage1.setDescription("This app will help you train to get the perfect ear worm! First we need permission for your mic.");
        sliderPage1.setDescColor(Color.BLACK);
        sliderPage1.setTitleColor(Color.BLACK);
        sliderPage1.setBgColor(Color.CYAN);
        addSlide(AppIntroFragment.newInstance(sliderPage1));

        SliderPage sliderPage2 = new SliderPage();
        sliderPage2.setTitle("Song mode");
        sliderPage2.setDescription("To use proper sound, please also give us permissions to your storage.");
        sliderPage2.setBgColor(Color.CYAN);
        sliderPage2.setDescColor(Color.BLACK);
        sliderPage2.setTitleColor(Color.BLACK);
        addSlide(AppIntroFragment.newInstance(sliderPage2));


        askForPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 1); // OR
        askForPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        // Do something when users tap on Done button.

    }
    /**
     * check Microphone Permission and handle it
     */
    void checkMicrophonePermission() {
        if (    ContextCompat.checkSelfPermission(
                this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                        this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                        this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED

        ) {


        } else {
            // Permission has already been granted
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }
}
