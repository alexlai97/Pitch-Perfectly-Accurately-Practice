package com.example.perfectpitchaccuratepractice;

import android.app.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;


public class FilterActivity extends Activity {

    private static final String TAG = "NOTE FILTER";

    Note [] note_pool;

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

        setSpinners();
    }

    private void setSpinners() {
        final Spinner fromSpinner = findViewById(R.id.fromSpinner);
        final Spinner toSpinner = findViewById(R.id.toSpinner);
        final Spinner scaleSpinner = findViewById(R.id.scaleSpinner);
        Spinner keySigSpinner = findViewById(R.id.keySigSpinner);

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

                note_pool = ((NotesBitmap)filterHandler.getResultBitmap()).toNotes();

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

                note_pool = ((NotesBitmap)filterHandler.getResultBitmap()).toNotes();

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

                note_pool = ((NotesBitmap)filterHandler.getResultBitmap()).toNotes();

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

                note_pool = ((NotesBitmap)filterHandler.getResultBitmap()).toNotes();

                update_using_note_pool();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i(TAG, "keySig (on nothing) select item ");
            }
        });


    }

    void update_using_note_pool() {
        // just print them out
        for (Note note: note_pool) {
            Log.i(TAG, note.getText());
        }
        // TODO generate buttons and Note pool and view
    }


    public void backToMain(View view){
        // Can add intent later
        // TODO update ModelController
        finish();
    }

}
