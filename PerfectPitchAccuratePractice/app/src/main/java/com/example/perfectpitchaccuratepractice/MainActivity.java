package com.example.perfectpitchaccuratepractice;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;

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
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN";
    private static PlaySound theSound = new PlaySound();
    private TextView arrow;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    private static final int MY_PERMISSIONS_REQUEST_AUDIO = 1;

    private ModelController modelController;
    private boolean created = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "s"
                + created);
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

        created = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerList = (ListView) findViewById(R.id.myNavi);
        addDrawerItems();

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
    }

    private void addDrawerItems() {
        String[] osArray = { "Android", "iOS", "Windows", "OS X", "Linux" };
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, osArray);
        Log.w(TAG, Integer.toString(mAdapter.getCount()) );

        mDrawerList.setAdapter(mAdapter);
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

}
