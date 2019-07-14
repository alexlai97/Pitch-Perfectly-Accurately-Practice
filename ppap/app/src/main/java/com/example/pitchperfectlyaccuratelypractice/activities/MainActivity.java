package com.example.pitchperfectlyaccuratelypractice.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.common.Config;
import com.example.pitchperfectlyaccuratelypractice.common.ModelController;
import com.example.pitchperfectlyaccuratelypractice.fragments.IntervalFragment;
import com.example.pitchperfectlyaccuratelypractice.fragments.NoteFragment;
import com.example.pitchperfectlyaccuratelypractice.fragments.TriadFragment;
import com.example.pitchperfectlyaccuratelypractice.note.Note;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;

import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Button;

/**
 * NotePracticeMode Activity
 */
public class MainActivity extends AppCompatActivity implements
        NoteFragment.OnFragmentInteractionListener,
        IntervalFragment.OnFragmentInteractionListener,
        TriadFragment.OnFragmentInteractionListener,
//        NavigationDrawerFragment.NavigationDrawerCallbacks,
        MyCallback,
        NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MAIN";
    private static PlaySound theSound = new PlaySound();
    private TextView arrow;

    private static final int MY_PERMISSIONS_REQUEST_AUDIO = 1;

    private ModelController modelController;
    private boolean created = false;

    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;

    private String curMode = "Note";
    private Fragment fragment;

    private NoteFragment noteFragment;
    private IntervalFragment intervalFragment;
    private TriadFragment triadFragment;
    private Class fragmentClass = NoteFragment.class;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "s" + created);

        checkMicrophonePermission();
        super.onCreate(savedInstanceState);

        created = true;

        setContentView(R.layout.navi_wrapper);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Start Note Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        noteFragment = new NoteFragment();
        if( noteFragment.getView() == null ){
            Log.w(TAG, "athings");
        }

        fragmentManager.beginTransaction().replace(R.id.flContent, noteFragment).commit();
        fragmentManager.executePendingTransactions();

        modelController = new  ModelController(new Config(), this);
        handleIntents(); // intents from NotePracticeFilterPage which contains the note pool
        modelController.next_question();

//        setupButtons();
        setupVoiceListener();

        Log.w(TAG, "ONCREATE");
    }

    void setupButtons() {
        Button parentLayout;
        parentLayout = findViewById(R.id.helpButton);
        parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                modelController.next_question();
                return false;
            }
        });
    }
    void setupVoiceListener() {
        VoiceListener voicelistener = new VoiceListener(modelController);
        voicelistener.startListening();
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

    public void openFilterPage(View view){
        Intent filter_intent = new Intent(this, NoteModeFilterPageActivity.class);
        startActivity(filter_intent);
    }

    // handle drawer closing
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DrawerLayout mDrawer = findViewById(R.id.drawer_layout);
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.playSoundButton:
                // implement here to handle play button
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // listener to handle selected item
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        fragment = null;

        int id = item.getItemId();
        Log.d(TAG, "onNavigationItemSelected: " + id);
        switch(id){
            case R.id.note_mode:
                Log.d(TAG, "onNavigationItemSelected: notemode");
                fragmentClass = NoteFragment.class;
                curMode = "Note";
                break;
            case R.id.interval_mode:
                Log.d(TAG, "onNavigationItemSelected: intervalmode");
                fragmentClass = IntervalFragment.class;
                curMode = "Interval";
                break;
            case R.id.chord_mode:
                Log.d(TAG, "onNavigationItemSelected: chordmode");
                fragmentClass = TriadFragment.class;
                curMode = "Chord";
                break;
//            case R.id.song_mode:
//                break;
            default:
                Log.d(TAG, "onNavigationItemSelected: default");
                fragmentClass = NoteFragment.class;
                curMode = "Note";
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            switch(id) {
                case R.id.note_mode:
                    noteFragment = (NoteFragment) fragment;
                    break;
                case R.id.interval_mode:
                    intervalFragment = (IntervalFragment) fragment;
                    break;
                case R.id.chord_mode:
                    triadFragment = (TriadFragment) fragment;
                    break;
                default:
                    noteFragment = (NoteFragment) fragment;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        fragmentManager.executePendingTransactions();


        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);
        // Set action bar title
        setTitle(item.getTitle());

        // Close the navigation drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawers();
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_AUDIO: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted

                } else {
                    // permission denied

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //used to communicate between fragments

    }
    /**
     * check Microphone Permission and handle it
     */
    void checkMicrophonePermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_REQUEST_AUDIO);
            }
            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        } else {
            // Permission has already been granted
        }

    }

    /**
     * handle events from e.g. NoteModeFilterPageActivity
     */
    void handleIntents() {
        Intent notes_ints_intent = getIntent();
        int[] notes_ints = notes_ints_intent.getIntArrayExtra("notePool");
        if (notes_ints != null) {
            if (notes_ints.length == 0) {
                modelController.setNotePool(Note.getAllNotes());
            } else {
                modelController.setNotePool(Note.IntsToNotes(notes_ints));
            }
        }
    }

    public void updateFrequencyText(String myString){
        switch(curMode) {
            case "Note":
                noteFragment.updateFrequencyText(myString);
                break;
            case "Interval":
                intervalFragment.updateFrequencyText(myString);
                break;
            case "Triad":
                triadFragment.updateFrequencyText(myString);
                break;
            default:
                noteFragment.updateFrequencyText(myString);
        }
    }

    public void updateArrowText(String myString){
        switch(curMode) {
            case "Note":
                noteFragment.updateArrowText(myString);
                break;
            case "Interval":
                intervalFragment.updateArrowText(myString);
                break;
            case "Triad":
                triadFragment.updateArrowText(myString);
                break;
            default:
                noteFragment.updateArrowText(myString);
        }
    }

    public void updateCurrentPitchText(String myString){
        switch(curMode) {
            case "Note":
                noteFragment.updateCurrentPitchText(myString);
                break;
            case "Interval":
                intervalFragment.updateCurrentPitchText(myString);
                break;
            case "Triad":
                triadFragment.updateCurrentPitchText(myString);
                break;
            default:
                noteFragment.updateCurrentPitchText(myString);
        }
    }

    public void updateQuestionText(String myString){
        switch(curMode) {
            case "Note":
                noteFragment.updateQuestionText(myString);
                break;
            case "Interval":
                intervalFragment.updateQuestionText(myString);
                break;
            case "Triad":
                triadFragment.updateQuestionText(myString);
                break;
            default:
                noteFragment.updateQuestionText(myString);
        }
    }

    public void updateArrowAnimation(Animation myAnimation){
        switch(curMode) {
            case "Note":
                noteFragment.updateArrowAnimation(myAnimation);
                break;
            case "Interval":
                intervalFragment.updateArrowAnimation(myAnimation);
                break;
            case "Triad":
                triadFragment.updateArrowAnimation(myAnimation);
                break;
            default:
                noteFragment.updateArrowAnimation(myAnimation);
        }
    }
}
