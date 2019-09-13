package com.example.pitchperfectlyaccuratelypractice.modeSettingsTabFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.PerModeSettingActivity;
import com.example.pitchperfectlyaccuratelypractice.bitmap.NotesBitmap;
import com.example.pitchperfectlyaccuratelypractice.enums.NotesScale;
import com.example.pitchperfectlyaccuratelypractice.filter.Filter;
import com.example.pitchperfectlyaccuratelypractice.filter.FilterHandler;
import com.example.pitchperfectlyaccuratelypractice.filter.NotesRangeFilter;
import com.example.pitchperfectlyaccuratelypractice.filter.NotesScaleFilter;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;

public class NotePoolSelectionTab extends Fragment {
    private static final String TAG = "NotePoolSelectionTab";

    /**
     * notes table  (dynamically generated notes buttons)
     */
    TableLayout notesTableView;

    boolean selected = false;


    View view;

    /**
     * layout inflater
     */
    LayoutInflater layoutInflater;


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

    // whatever layout should be attached to current fragment
    int resource;

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

    protected PerModeSettingActivity permodeSettingActivity;
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

    public NotePoolSelectionTab(PerModeSettingActivity permodeSettingActivity){
        this.permodeSettingActivity = permodeSettingActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        resource = R.layout.tabfragment_note_pool_selection;
        View layout = inflater.inflate(resource, container, false);
        view = layout;
        notesTableView = view.findViewById(R.id.note_pool_table);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        layoutInflater = LayoutInflater.from(getContext());

        setSpinners();
        setButtonListener();
    }

    /**
     * set up spinner and listeners
     */
    private void setSpinners() {
        // Put it declare
        fromSpinner   = view.findViewById(R.id.fromSpinner);
        toSpinner     = view.findViewById(R.id.showFrequencySpinner);
        scaleSpinner  = view.findViewById(R.id.leastStableTimeEditText);
        keySigSpinner = view.findViewById(R.id.showCorrectTimeEditText);

        ArrayAdapter<String> all_notes_string_adapter= new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, notes_strings);
        ArrayAdapter<String> all_scales_string_adapter= new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, scale_strings);
        ArrayAdapter<String> all_keySig_string_adapter= new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, keySig_strings);

        fromSpinner.setAdapter(all_notes_string_adapter);
        toSpinner.setAdapter(all_notes_string_adapter);
        scaleSpinner.setAdapter(all_scales_string_adapter);
        keySigSpinner.setAdapter(all_keySig_string_adapter);



        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                fromNote = new Note(position);
                Log.i(TAG, "FROM selected " + fromNote.getText(position));

                if (fromNote.getIndex() > toNote.getIndex()) {
                    // TODO alert user
                    Log.w(TAG, "ToNote selected smaller than fromNote");
                    toSpinner.setSelection(fromNote.getIndex());
                    fromSpinner.setSelection(fromNote.getIndex());
                    toNote = fromNote;
                }

                permodeSettingActivity.perModeSetting.setFromNote(new Note(position));
                // set range filter
                rangeFilter = new NotesRangeFilter(fromNote, toNote);
                filterHandler.updateFilterAt(0, rangeFilter);
                filterHandler.applyFilters();

                permodeSettingActivity.perModeSetting.setNotesBitmap((NotesBitmap)filterHandler.getResultBitmap());

                update_tableview_using_note_pool();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i(TAG, "FROM (on nothing) select item ");
            }
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                toNote = new Note(position);
                Log.i(TAG, "TO selected " + toNote.getText(position));

                if (fromNote.getIndex() > toNote.getIndex()) {
                    // TODO alert user
                    Log.w(TAG, "ToNote selected smaller than fromNote");
                    fromSpinner.setSelection(toNote.getIndex());
                    toSpinner.setSelection(toNote.getIndex());
                    fromNote = toNote;
                }

                permodeSettingActivity.perModeSetting.setToNote(new Note(position));

                // set range filter
                rangeFilter = new NotesRangeFilter(fromNote, toNote);
                filterHandler.updateFilterAt(0, rangeFilter);
                filterHandler.applyFilters();

                permodeSettingActivity.perModeSetting.setNotesBitmap((NotesBitmap)filterHandler.getResultBitmap());

                update_tableview_using_note_pool();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i(TAG, "TO (on nothing) select item ");
            }
        });


        scaleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                scale = NotesScale.values()[position];
                Log.i(TAG, "SCALE selected " + scale.toString());

                scaleFilter = new NotesScaleFilter(keySigNote, scale);
                filterHandler.updateFilterAt(1, scaleFilter);
                filterHandler.applyFilters();
                permodeSettingActivity.perModeSetting.setNoteScale(NotesScale.values()[position]);

                permodeSettingActivity.perModeSetting.setNotesBitmap((NotesBitmap)filterHandler.getResultBitmap());

                update_tableview_using_note_pool();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i(TAG, "SCALE (on nothing) select item ");
            }
        });

        keySigSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                keySigNote = new Note(position);
                Log.i(TAG, "keySig selected " + keySigNote.getText(position));

                scaleFilter = new NotesScaleFilter(keySigNote, scale);
                filterHandler.updateFilterAt(1, scaleFilter);
                filterHandler.applyFilters();

                permodeSettingActivity.perModeSetting.setNotesBitmap((NotesBitmap)filterHandler.getResultBitmap());
                permodeSettingActivity.perModeSetting.setKeySigNote(new Note(position));

                update_tableview_using_note_pool();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i(TAG, "keySig (on nothing) select item ");
            }
        });

        fromSpinner.setSelection(Note.getIndex("A3"));
        toSpinner.setSelection(Note.getIndex("A4"));
        scaleSpinner.setSelection(1); // Major
        keySigSpinner.setSelection(0);
    }

    protected void setButtonListener(){

        final Button selectButton = view.findViewById(R.id.general_select_all);
        final Button cancelButton = view.findViewById(R.id.general_cancel_all);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selected == false) {
                    selected = true;
                    selectButton.setText("UNSELECT ALL");
                    for (int row = 0; row < notesTableView.getChildCount(); row++) {
                        TableRow tableRow = (TableRow) notesTableView.getChildAt(row);
                        for (int col = 0; col < tableRow.getChildCount(); col++) {
                            ToggleButton toggleButton = (ToggleButton) tableRow.getChildAt(col);
                            if (!toggleButton.isChecked()) {
                                toggleButton.setChecked(true);
                            }
                        }
                    }
                }else{
                    selected = false;
                    selectButton.setText("SELECT ALL");
                    for (int row = 0; row < notesTableView.getChildCount(); row++) {
                        TableRow tableRow = (TableRow) notesTableView.getChildAt(row);
                        for (int col = 0; col < tableRow.getChildCount(); col++) {
                            ToggleButton toggleButton = (ToggleButton) tableRow.getChildAt(col);
                            if (toggleButton.isChecked()) {
                                toggleButton.setChecked(false);
                            }
                        }
                    }
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int row = 0; row < notesTableView.getChildCount(); row++) {
                    TableRow tableRow = (TableRow)notesTableView.getChildAt(row);
                    for(int col = 0; col < tableRow.getChildCount(); col++){
                        ToggleButton toggleButton = (ToggleButton) tableRow.getChildAt(col);
                        if(toggleButton.isChecked()){
                            toggleButton.setChecked(false);
                        }
                    }
                }
            }
        });
    }

    /**
     * update tableview using the generated notes
     */
    void update_tableview_using_note_pool() {

        // reset
        notesTableView.removeAllViews();

        // generate buttons
        Note[] noteArray = permodeSettingActivity.perModeSetting.getNotesBitmap().toNotes();

        int num_of_notes = noteArray.length;
        int num_of_rows = (num_of_notes - 1) / 4 + 1;
        int num_of_notes_last_row = (num_of_notes - 1) % 4 + 1;
        int note_index = 0;

        for (int i = 0; i < num_of_rows - 1; i++) {
            TableRow row = (TableRow) layoutInflater.inflate(R.layout.table_row_note, null, false);
            for (int j = 0; j < 4; j++) {
                ToggleButton note_button = (ToggleButton) layoutInflater.inflate(R.layout.togglebutton_single_note, null, false);
                Note this_note = noteArray[note_index];
                note_button = updateButton(note_button, this_note);
                row.addView(note_button);
                note_index++;
            }
            notesTableView.addView(row);
        }
        TableRow last_row = (TableRow) layoutInflater.inflate(R.layout.table_row_note, null, false);
        for (int i = 0; i < num_of_notes_last_row; i++) {
            ToggleButton note_button = (ToggleButton) layoutInflater.inflate(R.layout.togglebutton_single_note, null, false);
            Note this_note = noteArray[note_index];
            note_button = updateButton(note_button, this_note);
            last_row.addView(note_button);
            note_index++;
        }
        notesTableView.addView(last_row);
    }

    /**
     * update note button so that it has the note text as button text
     * and listener when toggle is toggled
     * @param button
     * @param note
     * @return
     */
    ToggleButton updateButton(ToggleButton button, final Note note) {
        String note_text = note.getText();
        button.setText(note_text);
        button.setTextOff(note_text);
        button.setTextOn(note_text);
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                NotesBitmap notesBitmap = permodeSettingActivity.perModeSetting.getNotesBitmap();
                notesBitmap.toggleNote(note);
                permodeSettingActivity.perModeSetting.setNotesBitmap(notesBitmap);
            }
        });
        return button;
    }

}
