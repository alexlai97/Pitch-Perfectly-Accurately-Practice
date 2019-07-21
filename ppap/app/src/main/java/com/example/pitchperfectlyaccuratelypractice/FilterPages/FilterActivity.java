package com.example.pitchperfectlyaccuratelypractice.FilterPages;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.bitmap.IntervalsBitmap;
import com.example.pitchperfectlyaccuratelypractice.bitmap.NotesBitmap;
import com.example.pitchperfectlyaccuratelypractice.enums.NotesScale;
import com.example.pitchperfectlyaccuratelypractice.filter.Filter;
import com.example.pitchperfectlyaccuratelypractice.filter.FilterHandler;
import com.example.pitchperfectlyaccuratelypractice.filter.NotesRangeFilter;
import com.example.pitchperfectlyaccuratelypractice.filter.NotesScaleFilter;
import com.example.pitchperfectlyaccuratelypractice.music.Note;
import com.google.android.material.tabs.TabLayout;


/**
 * filter page activity for note practice mode
 */
public class FilterActivity extends AppCompatActivity {

    private static final String TAG = "IntervalModeFilterAc";

    /**
     * layout inflater
     */
    LayoutInflater layoutInflater;
    /**
     * notes table  (dynamically generated notes buttons)
     */
    TableLayout notesTableView;

    /**
     * currently generated notes
     */
    Note[] generated_notes;

    /**
     * fromSpinner
     */
    Spinner fromSpinner  ;
    /**
     * toSpinner
     */
    Spinner toSpinner    ;
    /**
     * scaleSpinner
     */
    Spinner scaleSpinner ;
    /**
     * key signature spinner
     */
    Spinner keySigSpinner;

    /**
     * strings to put in from and to spinner
     */
    static String[] notes_strings;
    /**
     * strings to put in scale spinner
     */
    static String[] scale_strings;
    /**
     * strings to put in the key signature spinner
     */
    static String[] keySig_strings;

    /**
     * current rangefilter
     */
    static NotesRangeFilter rangeFilter = new NotesRangeFilter(Note.getLowestNote(), Note.getHighestNote());
    /**
     * current scalefilter
     */
    static NotesScaleFilter scaleFilter = new NotesScaleFilter(new Note('A'), NotesScale.Major);
    /**
     * current filter handler (see filterHandler class for detail)
     */
    static FilterHandler filterHandler;

    // set them up at statically
    static {
        Note [] notes = Note.getAllNotes();
        notes_strings = Note.getStringsFromNotes(notes);
        scale_strings = NotesScale.getAllNotesScales();
        keySig_strings = Note.getAllKeySignatures();
        filterHandler = new FilterHandler(NotesBitmap.getAllTrueNotesBitmap(), new Filter[] { rangeFilter, scaleFilter } );
    }

    private IntervalsBitmap intervalsBitmap;
    private NotesBitmap notesBitmap;

    public FilterPageOption filterPageOption;


    /**
     * current from Note
     */
    private Note fromNote = Note.getLowestNote();
    /**
     * current to Note
     */
    private Note toNote = Note.getHighestNote();
    /**
     * current scale
     */
    private NotesScale scale = NotesScale.Major;
    /**
     * current key signature
     */
    private Note keySigNote = new Note("A");

    private String mode;

    private int pageNum;
    /**
     * setup views
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval_mode_filter);

        Intent intent = getIntent();
        filterPageOption = (FilterPageOption)intent.getSerializableExtra("Mode");
        pageNum = filterPageOption.getFilterPageNum();
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
        if(pageNum == 1){
            tabLayout.addTab(tabLayout.newTab().setText("Note"));
        } else if(pageNum == 2){
            tabLayout.addTab(tabLayout.newTab().setText("Note"));
            tabLayout.addTab(tabLayout.newTab().setText("Interval"));
        }

        // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Use PagerAdapter to manage page views in fragments.
        // Each page is represented by its own fragment.
        final ViewPager viewPager = findViewById(R.id.pager);
        final com.example.pitchperfectlyaccuratelypractice.FilterPages.PagerAdapter adapter = new
                com.example.pitchperfectlyaccuratelypractice.FilterPages.PagerAdapter(
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
        Intent intent = new Intent();
        intent.putExtra("Mode", filterPageOption);
        setResult(RESULT_OK, intent);
        finish();
    }

}


