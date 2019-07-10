package com.example.perfectpitchaccuratepractice;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;

import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Button;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.widget.ImageView;

import android.animation.ObjectAnimator;
import android.view.animation.Animation;
import android.animation.AnimatorSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

import org.junit.runner.manipulation.Filterable;
import android.widget.TableLayout;

/**
 * Main activity
 */
public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {
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
        setContentView(R.layout.wrapper);

        modelController = new ModelController(new Config(), this);
        modelController.next_question();

        Button parentLayout;
        parentLayout = findViewById(R.id.helpButton);
        parentLayout.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                modelController.next_question();
                return false;
            }
        });

        VoiceListener voicelistener = new VoiceListener(modelController);
        voicelistener.startListening();

        Log.w(TAG, "ONCREATE");
//        // FIXME ...
//        arrow = findViewById(R.id.arrowTextView);
//        handleAnimation(300);



        // add listener on hamburger button to open drawer
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Button nav_button = findViewById(R.id.naviButton);
        nav_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
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

    // handle drawer closing
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // listener to handle selected item
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.string.note_mode:
                Intent 
            case R.string.interval_mode:
                break;
            case R.string.chord_mode:
                break;
            case R.string.song_mode:
                break;
            default:
                break;
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
