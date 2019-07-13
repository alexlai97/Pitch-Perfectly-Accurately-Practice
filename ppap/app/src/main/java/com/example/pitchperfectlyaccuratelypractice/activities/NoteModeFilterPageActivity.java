package com.example.pitchperfectlyaccuratelypractice.activities;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ToggleButton;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.bitmap.NotesBitmap;
import com.example.pitchperfectlyaccuratelypractice.filter.Filter;
import com.example.pitchperfectlyaccuratelypractice.filter.FilterHandler;
import com.example.pitchperfectlyaccuratelypractice.filter.NotesRangeFilter;
import com.example.pitchperfectlyaccuratelypractice.filter.NotesScaleFilter;
import com.example.pitchperfectlyaccuratelypractice.note.Note;
import com.example.pitchperfectlyaccuratelypractice.note.NotesScale;


public class NoteModeFilterPageActivity extends Activity {

    private static final String TAG = "NOTE FILTER";

//    ModelController modelController;

    LayoutInflater layoutInflater;
    TableLayout notesTableView;

    Note[] generated_notes;
    NotesBitmap tmpData = NotesBitmap.getAllTrueNotesBitmap();
    Spinner fromSpinner  ;
    Spinner toSpinner    ;
    Spinner scaleSpinner ;
    Spinner keySigSpinner;
    static String[] notes_strings;
    static String[] scale_strings;
    static String[] keySig_strings;

    static NotesRangeFilter rangeFilter = new NotesRangeFilter(Note.getLowestNote(), Note.getHighestNote());
    static NotesScaleFilter scaleFilter = new NotesScaleFilter(new Note('A'), NotesScale.Major);
    static FilterHandler filterHandler;
    static {
        Note [] notes = Note.getAllNotes();
        notes_strings = Note.getStringsFromNotes(notes);
        scale_strings = NotesScale.getAllNotesScales();
        keySig_strings = Note.getAllKeySignatures();
        filterHandler = new FilterHandler(NotesBitmap.getAllTrueNotesBitmap(), new Filter[] { rangeFilter, scaleFilter } );
    }

    private Note fromNote = Note.getLowestNote();
    private Note toNote = Note.getHighestNote();
    private NotesScale scale = NotesScale.Major;
    private Note keySigNote = new Note("A");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_mode_filter);

//        Intent recevied_modelController_intent = getIntent();
//        modelController = (ModelController) recevied_modelController_intent.getSerializableExtra("modelController");

        layoutInflater = LayoutInflater.from(this);
        notesTableView = findViewById(R.id.note_pool_table);

        findViewById(R.id.backButton).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                backToMain(v);
            }
        });

        setSpinners();
    }

    private void setSpinners() {
        // Put it declare
        fromSpinner   = findViewById(R.id.fromSpinner);
        toSpinner     = findViewById(R.id.toSpinner);
        scaleSpinner  = findViewById(R.id.scaleSpinner);
        keySigSpinner = findViewById(R.id.keySigSpinner);

        ArrayAdapter<String> all_notes_string_adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, notes_strings);
        ArrayAdapter<String> all_scales_string_adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, scale_strings);
        ArrayAdapter<String> all_keySig_string_adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, keySig_strings);

        fromSpinner.setAdapter(all_notes_string_adapter);
        toSpinner.setAdapter(all_notes_string_adapter);
        scaleSpinner.setAdapter(all_scales_string_adapter);
        keySigSpinner.setAdapter(all_keySig_string_adapter);

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fromNote = new Note(i);
                Log.i(TAG, "FROM selected " + fromNote.getText(i));

                if (fromNote.getIndex() > toNote.getIndex()) {
                    // TODO alert user
                    Log.w(TAG, "ToNote selected smaller than fromNote");
                    fromSpinner.setSelection(toNote.getIndex());
                }

                // FIXME reduce duplicate code
                // set range filter
                rangeFilter = new NotesRangeFilter(fromNote, toNote);
                filterHandler.updateFilterAt(0, rangeFilter);
                filterHandler.applyFilters();

                generated_notes = ((NotesBitmap)filterHandler.getResultBitmap()).toNotes();

                update_using_note_pool();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i(TAG, "FROM (on nothing) select item ");
            }
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toNote = new Note(i);
                Log.i(TAG, "TO selected " + toNote.getText(i));

                if (fromNote.getIndex() > toNote.getIndex()) {
                    // TODO alert user
                    Log.w(TAG, "ToNote selected smaller than fromNote");
                    toSpinner.setSelection(fromNote.getIndex());
                }

                // set range filter
                rangeFilter = new NotesRangeFilter(fromNote, toNote);
                filterHandler.updateFilterAt(0, rangeFilter);
                filterHandler.applyFilters();

                generated_notes = ((NotesBitmap)filterHandler.getResultBitmap()).toNotes();

                update_using_note_pool();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i(TAG, "TO (on nothing) select item ");
            }
        });


        scaleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                scale = NotesScale.values()[i];
                Log.i(TAG, "SCALE selected " + scale.toString());

                scaleFilter = new NotesScaleFilter(keySigNote, scale);
                filterHandler.updateFilterAt(1, scaleFilter);
                filterHandler.applyFilters();

                generated_notes = ((NotesBitmap)filterHandler.getResultBitmap()).toNotes();

                update_using_note_pool();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i(TAG, "SCALE (on nothing) select item ");
            }
        });

        keySigSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                keySigNote = new Note(i);
                Log.i(TAG, "keySig selected " + keySigNote.getText(i));

                scaleFilter = new NotesScaleFilter(keySigNote, scale);
                filterHandler.updateFilterAt(1, scaleFilter);
                filterHandler.applyFilters();

                generated_notes = ((NotesBitmap)filterHandler.getResultBitmap()).toNotes();

                update_using_note_pool();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i(TAG, "keySig (on nothing) select item ");
            }
        });
    }

    void update_using_note_pool() {
        // Log
        Note.logNotes(TAG, generated_notes);

        // reset
        notesTableView.removeAllViews();
        tmpData = new NotesBitmap(generated_notes);

        // generate buttons

        int num_of_notes = generated_notes.length;
        int num_of_rows = (num_of_notes - 1) / 3 + 1;
        int num_of_notes_last_row = (num_of_notes - 1) % 3 + 1;
        int note_index = 0;

        for (int i = 0; i < num_of_rows - 1; i++) {
            TableRow row = (TableRow) layoutInflater.inflate(R.layout.note_table_row, null, false);
            for (int j = 0; j < 3; j++) {
                ToggleButton note_button = (ToggleButton) layoutInflater.inflate(R.layout.note_button, null, false);
                Note this_note = generated_notes[note_index];
                note_button = updateButton(note_button, this_note);
                row.addView(note_button);
                note_index++;
            }
            notesTableView.addView(row);
        }
        TableRow last_row = (TableRow) layoutInflater.inflate(R.layout.note_table_row, null, false);
        for (int i = 0; i < num_of_notes_last_row; i++) {
            ToggleButton note_button = (ToggleButton) layoutInflater.inflate(R.layout.note_button, null, false);
            Note this_note = generated_notes[note_index];
            note_button = updateButton(note_button, this_note);
            last_row.addView(note_button);
            note_index++;
        }
        notesTableView.addView(last_row);
    }

    ToggleButton updateButton(ToggleButton button, final Note note) {
        String note_text = note.getText();
        button.setText(note_text);
        button.setTextOff(note_text);
        button.setTextOn(note_text);
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tmpData.toggleNote(note);
            }
        });
        return button;
    }

    // FIXME Alex: I might miss set the on/off in opossite way but oculnt'd find where I went wrong, maybe it's fine

    void backToMain(View view){
        Note[] notes_to_return = tmpData.toNotes();
        Note.logNotes(TAG, notes_to_return);
        // TODO update ModelController
        Intent note_pool_intent = new Intent(this, MainActivity.class);
        note_pool_intent.putExtra("notePool", Note.NotesToInts(notes_to_return));
        startActivity(note_pool_intent);

//        finish();
    }

}
