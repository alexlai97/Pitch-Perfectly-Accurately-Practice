package com.example.pitchperfectlyaccuratelypractice.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.tools.Microphone;
import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.model.Model;
import com.example.pitchperfectlyaccuratelypractice.controller.Controller;
import com.example.pitchperfectlyaccuratelypractice.tools.NotePlayer;
import com.example.pitchperfectlyaccuratelypractice.fragments.GeneralFragment;
import com.example.pitchperfectlyaccuratelypractice.music.Note;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.MenuItem;

/**
 * Main Activity which stores model, controller, noteplayer, microphone
 */
public class MainActivity extends AppCompatActivity implements
        GeneralFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    public static final int REQUEST_CODE_FROM_FILTER = 1;
    private static final int MY_PERMISSIONS_REQUEST_AUDIO = 1;

    /**
     * indicate onCreate has been called
     */
    private boolean created = false;

    /**
     * only controller (and main activity) should have access, so no getter
     */
    private Model model = new Model();


    /**
     * controlling how user voice affects model and update view
     */
    private Controller controller;

    /**
     * return the Controller (currently used by fragments)
     * @return
     */
    public Controller getController() {
        return controller;
    }

    /**
     * a speaker which can play note(s)
     */
    private static NotePlayer notePlayer = new NotePlayer();

    /**
     * getter for note player
     * @return
     */
    public NotePlayer getNotePlayer() {
        return notePlayer;
    }

    /**
     * a microphone which contains a frequency, controller will listen to its current frequency
     */
    private Microphone microphone = new Microphone(this);

    /**
     * getter for microphone
     * @return
     */
    public Microphone getMicrophone() {
        return microphone;
    }


    /**
     * 1. check for microphone permission
     * 2. set up content view
     * 3. setup navigation manu listener
     * 4. setup controller
     * 5. handle potential intents
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        created = true;
        Log.e(TAG, "s" + created);
        Log.w(TAG, "ONCREATE");

        // check microphone permission
        checkMicrophonePermission();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.navi_wrapper);

        // setup navigation menu listener
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        controller = new Controller(model, this);
        handleIntents(); // intents from NotePracticeFilterPage which contains the note pool
    }


    /**
     * currently
     * TODO will hand back intervals as well (for interval filter page)
     * filter page hand back result notes (to be put in question note pool)
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResult: get intent back from filter page");
        if (requestCode == REQUEST_CODE_FROM_FILTER) {
            if (resultCode == RESULT_OK) {
                Note [] result_notes = Note.IntsToNotes(data.getIntArrayExtra("notePool"));
                Note.logNotes("back to main activity", result_notes);

                // pass the notes generated from filter to controller, start next question(generated from note pool)
                controller.setNotePool(result_notes);
                controller.next_question();
            } else if (resultCode == RESULT_CANCELED){

            } else {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    /**
     *
     */
    protected void onRestart() {
        super.onRestart();
        Log.w(TAG, "ONRESTART");
    }


    /**
     * handle when back button is pressed
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            drawer.openDrawer(GravityCompat.START);
            super.onBackPressed();
        }
    }


    /**
     * handle events when navigation bar's menu item is selected
     * <p>
     * it change mode in model, which will notify controller, which will change current fragment in model, etc.
     * </p>
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.d(TAG, "onNavigationItemSelected: " + Mode.idToMode(id));
        model.setCurrentMode(Mode.idToMode(id));

        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);
        // Set action bar title
        setTitle(item.getTitle());

        // Close the navigation drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawers();
        return true;
    }

    /**
     * handle permission result
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
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


    /**
     * potential use
     * @param uri
     */
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
     * handle intents ( currently only handles intents from filter pages )
     */
    void handleIntents() {
        Intent notes_ints_intent = getIntent();
        int[] notes_ints = notes_ints_intent.getIntArrayExtra("notePool");
        if (notes_ints != null) {
            if (notes_ints.length == 0) {
                controller.setNotePool(Note.getAllNotes());
            } else {
                controller.setNotePool(Note.IntsToNotes(notes_ints));
            }
        }
    }

}
