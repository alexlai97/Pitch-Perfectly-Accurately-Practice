package com.example.pitchperfectlyaccuratelypractice.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

import java.util.zip.Inflater;

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(checkAllPermission()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        SliderPage sliderPage1 = new SliderPage();
        sliderPage1.setTitle("Welcome!");
        sliderPage1.setDescription("This app will help you train to get the perfect ear worm! First we need permission for your mic.");
        sliderPage1.setDescColor(Color.BLACK);
        sliderPage1.setTitleColor(Color.BLACK);
        sliderPage1.setBgColor(Color.rgb(152,251,152));
        addSlide(AppIntroFragment.newInstance(sliderPage1));

        SliderPage sliderPage2 = new SliderPage();
        sliderPage2.setTitle("Song mode");
        sliderPage2.setDescription("To use proper sound, please also give us permissions to your storage.");
        sliderPage2.setBgColor(Color.rgb(152,251,152));
        sliderPage2.setDescColor(Color.BLACK);
        sliderPage2.setTitleColor(Color.BLACK);
        addSlide(AppIntroFragment.newInstance(sliderPage2));

        askForPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 1); // OR
        askForPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        if(checkAllPermission()){
            moveToMain();
        } else {
            ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
            LayoutInflater inflater = this.getLayoutInflater();
            View layout = inflater.inflate(R.layout.popout, viewGroup, false);
            PopupWindow popupWindow = new PopupWindow(
                    layout,
//                    LayoutParams.MATCH_PARENT,
//                    LayoutParams.MATCH_PARENT);
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true);;
            popupWindow.showAtLocation(layout, Gravity.CENTER,0,0);
            TextView popupText = popupWindow.getContentView().findViewById(R.id.popup_text);
            popupText.setText("\nWe need your permissions in order to launch the app\n");
//                    popupWindow.update(
//                            (int)(Math.floor(LayoutParams.MATCH_PARENT*0.8)),
//                            (int)(Math.floor(LayoutParams.MATCH_PARENT*0.8)));
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    restartIntro();
                }
            }
            );

        }
        // Do something when users tap on Done button.

    }

    private void moveToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void restartIntro(){
        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
    }
    /**
     * check Microphone Permission and handle it
     */
    private boolean checkAllPermission() {
        if (    ContextCompat.checkSelfPermission(
                this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                        this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                        this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED

        ) {
            return false;
        } else {
            // Permission has already been granted
            return true;
        }

    }
}
