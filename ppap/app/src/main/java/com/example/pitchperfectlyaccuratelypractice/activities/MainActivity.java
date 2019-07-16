package com.example.pitchperfectlyaccuratelypractice.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.common.Config;
import com.example.pitchperfectlyaccuratelypractice.common.Mode;
import com.example.pitchperfectlyaccuratelypractice.common.ModelController;
import com.example.pitchperfectlyaccuratelypractice.fragments.GeneralFragment;
import com.example.pitchperfectlyaccuratelypractice.fragments.FragmentFactory;
import com.example.pitchperfectlyaccuratelypractice.note.Note;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.util.Log;

import android.view.MenuItem;
import android.widget.TextView;

/**
 * NotePracticeMode Activity
 */
public class MainActivity extends AppCompatActivity implements
        GeneralFragment.OnFragmentInteractionListener,
//        NavigationDrawerFragment.NavigationDrawerCallbacks,
        NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MAIN";
    public static final int REQUEST_CODE_FROM_FILTER = 1;
    private static NotePlayer notePlayer = new NotePlayer();
    private TextView arrow;

    private static final int MY_PERMISSIONS_REQUEST_AUDIO = 1;

    private ModelController modelController;
    private boolean created = false;

    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;

    private GeneralFragment curFragment;

    private FragmentFactory fragmentFactory = new FragmentFactory();

    public GeneralFragment getCurFragment() {
        return curFragment;
    }

    /**
     * currently it does the following
     * 1. check mirochphone permission and handles it
     * 2. set navi wrapper
     * 3. setup fragment and begin note fragment
     * 4. set up modelcontroller
     * 5. handle intents (from filter page)
     * 6. set up voice listener
     * @param savedInstanceState
     */
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
        curFragment = fragmentFactory.create(R.id.note_mode);
        if( curFragment.getView() == null ){ Log.w(TAG, "athings"); }

        fragmentManager.beginTransaction().replace(R.id.flContent, curFragment).commit();
        fragmentManager.executePendingTransactions();

        modelController = new  ModelController(new Config(), this);
        handleIntents(); // intents from NotePracticeFilterPage which contains the note pool
        modelController.next_question();

        setupVoiceListener();

        Log.w(TAG, "ONCREATE");
    }


    /**
     * currently only get notes from filter page
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
                modelController.setNotePool(result_notes);
                modelController.next_question();
            } else if (resultCode == RESULT_CANCELED){

            } else {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * set up voice listener and start listening
     */
    void setupVoiceListener() {
        VoiceListener voicelistener = new VoiceListener(modelController);
        voicelistener.startListening();
    }


    /**
     * return the ModelController (currently used by fragments)
     * @return
     */
    public ModelController getModelController() {
        return modelController;
    }

    /**
     * return the current notePlayer (currently used by fragments)
     * @return
     */
    public NotePlayer getNotePlayer() {
        return notePlayer;
    }

    /**
     * restart, doesn't do anything for now
     */
    protected void onRestart() {
        super.onRestart();
        Log.w(TAG, "ONRESTART");
    }


    /**
     * handle when back button, FIXME
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
     * it handles changing fragment and change mode logic
     * </p>
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        curFragment = null;

        int id = item.getItemId();
        Log.d(TAG, "onNavigationItemSelected: " + id);

        curFragment = fragmentFactory.create(id);
        modelController.changeCurrentMode(id);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, curFragment).commit();
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

    /**
     * for check microphone permission
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
     * do nothing, might have potential use for future
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
     * handle intents ( currently only handles intents from filter page )
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

}
