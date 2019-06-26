package com.example.perfectpitchaccuratepractice;

import android.content.Intent;
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

import org.junit.runner.manipulation.Filterable;


/**
 * Main activity
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN";
    private static PlaySound theSound = new PlaySound();
    private TextView arrow;

    private ModelController modelController;
    private boolean created = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "s"
                + created);
        created = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        modelController = new ModelController(new Config(), this);
        modelController.next_question();

        VoiceListener voicelistener = new VoiceListener(modelController);
        voicelistener.startListening();

        Log.w(TAG, "ONCREATE");
//        // FIXME ...
//        arrow = findViewById(R.id.arrowTextView);
//        handleAnimation(300);
    }


    protected void onRestart() {
        super.onRestart();
        Log.w(TAG, "ONRESTART");
    }

    // FIXME  crashes after several plays
    public void myToner(View view){
        theSound.genTone((int)modelController.getExpectedFrequency(), 1);
        theSound.playSound();
        Log.i(TAG, "PLAYED");
    }
    public void openFilter(View view){
        startActivity(new Intent(this, FilterActivity.class));
    }
}
