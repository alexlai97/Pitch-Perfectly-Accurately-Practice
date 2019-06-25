package com.example.perfectpitchaccuratepractice;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.widget.ImageView;

import android.animation.ObjectAnimator;
import android.view.animation.Animation;
import android.animation.AnimatorSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN";
    private static PlaySound theSound = new PlaySound();

    private ImageView arrow;

    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new Model(new Config());
        model.setArrowTextView((TextView) findViewById(R.id.arrowsTextView));
        model.setQuestionTextView((TextView) findViewById(R.id.questionTextView));
        model.next_question();

        VoiceListener voicelistener = new VoiceListener(model);
        voicelistener.startListening();

//        arrow = findViewById(R.id.arrowsTextView);
//        handleAnimation();
    }

    public void handleAnimation() {
        Animation mAnimation = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, 1f,
                TranslateAnimation.RELATIVE_TO_SELF, 1.3f);
        mAnimation.setDuration(300);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setInterpolator(new LinearInterpolator());
        arrow.setAnimation(mAnimation);
    }

    public void myToner(View view){
        theSound.genTone(440, 1);
        theSound.playSound();
        Log.i(TAG, "PLAYED");
    }

}
