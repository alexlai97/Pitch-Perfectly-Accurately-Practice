package com.example.pitchperfectlyaccuratelypractice.activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.pitchperfectlyaccuratelypractice.bitmap.IntervalsBitmap;
import com.example.pitchperfectlyaccuratelypractice.bitmap.NotesBitmap;
import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.perModeSetting.PerModeSetting;
import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Interval;
import com.example.pitchperfectlyaccuratelypractice.tools.PagerAdapter;
import com.google.android.material.tabs.TabLayout;


/**
 * filter page activity for note practice mode
 */
public class PerModeSettingActivity extends AppCompatActivity {

    private static final String TAG = "PerModeSettingActivity";


    public PerModeSetting perModeSetting;

    public Mode mode;
    private int pageNum;

    public IntervalsBitmap generated_interval;

    public NotesBitmap generated_note;

    /**
     * setup views
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.wrapper_tabfragment);

        Intent intent = getIntent();
        perModeSetting = (PerModeSetting)intent.getSerializableExtra("Mode");
        if(perModeSetting == null){
            Log.d(TAG, "onCreate: perModeSetting is null ");
            throw new AssertionError("perMose");
        }
        mode = perModeSetting.mode;
        generated_interval = perModeSetting.getIntervalsBitmap();
        generated_note = perModeSetting.getNotesBitmap();
        pageNum = perModeSetting.getFilterPageNum();
        CreateTabFragments();

        setBackButtonListener();

    }

    public void setBackButtonListener(){
        ImageView backButton = findViewById(R.id.backButtonWrapper);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToMainActivity();
            }
        });
    }

    private void CreateTabFragments(){
        // Create an instance of the tab layout from the view.
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        // Set the text for each tab.
        switch (mode){
            case NotePractice:
                tabLayout.addTab(tabLayout.newTab().setText("Note"));
                tabLayout.addTab(tabLayout.newTab().setText("Misc"));
                break;
            case IntervalPractice:
                tabLayout.addTab(tabLayout.newTab().setText("Note"));
                tabLayout.addTab(tabLayout.newTab().setText("Interval"));
                tabLayout.addTab(tabLayout.newTab().setText("Misc"));
                break;
            case TriadPractice:
                tabLayout.addTab(tabLayout.newTab().setText("Note"));
                tabLayout.addTab(tabLayout.newTab().setText("Misc"));
                break;
            case NoteGraphPractice:
                tabLayout.addTab(tabLayout.newTab().setText("Note"));
                tabLayout.addTab(tabLayout.newTab().setText("Misc"));
                break;
            case SongPlaying:
//                tabLayout.addTab(tabLayout.newTab().setText("Misc"));
                break;
            default:
        }

        // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Use PagerAdapter to manage page views in fragments.
        // Each page is represented by its own fragment.
        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new
                PagerAdapter(
                getSupportFragmentManager(), tabLayout.getTabCount(), this);

        viewPager.setAdapter(adapter);
        // Setting a listener for clicks.

        // Setting a listener for clicks.
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
               @Override
               public void onTabSelected(TabLayout.Tab tab) {
                   Log.d(TAG, "onTabSelected: tab.getPosition()= " + tab.getPosition());
                   viewPager.setCurrentItem(tab.getPosition());
               }

               @Override
               public void onTabUnselected(TabLayout.Tab tab) {
               }

               @Override
               public void onTabReselected(TabLayout.Tab tab) {
                   }
               });
    }


    /**
     * pass note [] as int [] in intent back to MainActivity
     */
    void returnToMainActivity(){
//        Note.logNotes(TAG, notes_to_return);
        perModeSetting.setIntervalsBitmap(generated_interval);
        perModeSetting.setNotesBitmap(generated_note);
        Intent intent = new Intent();
        intent.putExtra("Mode", perModeSetting);
        setResult(RESULT_OK, intent);
        finish();
    }

}


