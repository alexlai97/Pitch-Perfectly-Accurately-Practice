package com.example.pitchperfectlyaccuratelypractice.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.example.pitchperfectlyaccuratelypractice.modeFragments.ModeFragment;
import com.example.pitchperfectlyaccuratelypractice.perModeSetting.IntervalModeSetting;
import com.example.pitchperfectlyaccuratelypractice.perModeSetting.NoteGraphModeSetting;
import com.example.pitchperfectlyaccuratelypractice.perModeSetting.NoteModeSetting;
import com.example.pitchperfectlyaccuratelypractice.perModeSetting.PerModeSetting;
import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.perModeSetting.TriadModeSetting;
import com.example.pitchperfectlyaccuratelypractice.tools.Microphone;
import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.model.Model;
import com.example.pitchperfectlyaccuratelypractice.controller.Controller;
import com.example.pitchperfectlyaccuratelypractice.tools.NotesPlayer;
import com.example.pitchperfectlyaccuratelypractice.tools.MyMidiTool;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.util.Log;
import android.view.MenuItem;

/**
 * Main Activity which stores model, controller, noteplayer, microphone
 */
public class MainActivity extends AppCompatActivity implements
        ModeFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    public static final int REQUEST_CODE_FROM_FILTER = 1;
    private static final int MY_PERMISSIONS_REQUEST_AUDIO = 1;

    /** indicate onCreate has been called */
    private boolean created = false;

    /** only controller (and main activity) should have access, so no getter */
    private Model model;

    public Model getModel() {
        return model;
    }

    /** controlling how user voice affects model and update view */
    private Controller controller;

    /**
     * return the Controller (currently used by fragments)
     * @return
     */
    public Controller getController() {
        return controller;
    }

    /** a speaker which can start_playing note(s) */
    private NotesPlayer notesPlayer = new NotesPlayer();


    /**
     * getter for note player
     * @return
     */
    public NotesPlayer getNotesPlayer() {
        return notesPlayer;
    }

    /** a microphone which contains a frequency, controller will listen to its current frequency */
    private Microphone microphone = new Microphone(this);


    public boolean FilterPageReturn;
    /**
     * getter for microphone
     * @return
     */
    public Microphone getMicrophone() {
        return microphone;
    }

    private MyMidiTool myMidiTool = new MyMidiTool();
    public MyMidiTool getMyMidiTool() {
        return myMidiTool;
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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.wrapper_navigation_menu);

        // setup navigation menu listener
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        model = new Model(this);
        controller = new Controller(model, this);
       // handleIntents(); // intents from NotePracticeFilterPage which contains the note pool
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
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: get intent back from filter page");

        if (resultCode == RESULT_OK) {
            PerModeSetting perModeSetting = (PerModeSetting) data.getSerializableExtra("perModeSetting");
            model.setPerModeSetting(perModeSetting);

            switch (perModeSetting.mode) {
                case IntervalPractice:
                    IntervalModeSetting intervalModeSetting = (IntervalModeSetting)perModeSetting;
                    model.setIntervalPool(intervalModeSetting.intervalsBitmap.toIntervals());
                    model.setNotePool(intervalModeSetting.notesBitmap.toNotes());
                    break;
                case NotePractice:
                    NoteModeSetting noteModeSetting = (NoteModeSetting) perModeSetting;
                    model.setNotePool(noteModeSetting.notesBitmap.toNotes());
                    break;
                case NoteGraphPractice:
                    NoteGraphModeSetting noteGraphModeSetting = (NoteGraphModeSetting) perModeSetting;
                    model.setNotePool(noteGraphModeSetting.notesBitmap.toNotes());
                    break;
                case TriadPractice:
                    TriadModeSetting triadModeSetting = (TriadModeSetting) perModeSetting;
                    model.setNotePool(triadModeSetting.notesBitmap.toNotes());
                    break;
                case SongPlaying:
                    /** TODO */
                case SongPractice:
                    /** TODO */
                    break;
            }

            controller.next_question();
        } else if (resultCode == RESULT_CANCELED){

        } else {

        }
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
        if(id == R.id.summary){
            Intent summary_intent = new Intent(this, SummaryActivity.class);
            // let the main activity handle the intent
            this.startActivityForResult(summary_intent, REQUEST_CODE_FROM_FILTER);
        }

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

}
