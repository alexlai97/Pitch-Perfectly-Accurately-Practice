package com.example.pitchperfectlyaccuratelypractice.TabFragment;

import android.graphics.drawable.Drawable;
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
import com.example.pitchperfectlyaccuratelypractice.bitmap.NotesBitmap;
import com.example.pitchperfectlyaccuratelypractice.enums.NotesScale;
import com.example.pitchperfectlyaccuratelypractice.filter.Filter;
import com.example.pitchperfectlyaccuratelypractice.filter.FilterHandler;
import com.example.pitchperfectlyaccuratelypractice.filter.NotesRangeFilter;
import com.example.pitchperfectlyaccuratelypractice.filter.NotesScaleFilter;
import com.example.pitchperfectlyaccuratelypractice.music.Note;

public class GeneralTabFragment extends Fragment {
    private static final String TAG = "NOTE FILTER";

//    /**
//     * layout inflater
//     */
//    LayoutInflater layoutInflater;
    /**
     * notes table  (dynamically generated notes buttons)
     */
    TableLayout notesTableView;


    View view;

    /**
     * layout inflater
     */
    LayoutInflater layoutInflater;

    /**
     * currently generated notes
     */
    Note[] generated_notes;
    /**
     * generated bitmap data from generated notes
     */
    NotesBitmap tmpData = NotesBitmap.getAllTrueNotesBitmap();
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

    private IntervalModeFilterActivity filterActivity;
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

    public GeneralTabFragment(IntervalModeFilterActivity filter){
        filterActivity = filter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(resource, container, false);
        view = layout;
        layoutInflater = LayoutInflater.from(getContext());
        notesTableView = view.findViewById(R.id.note_pool_table);
        view.findViewById(R.id.backButton).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                filterActivity.returnToMainActivity(tmpData);
            }
        });
        setSpinners();

        return layout;
    }

    /**
     * set up spinner and listeners
     */
    private void setSpinners() {
        // Put it declare
        fromSpinner = view.findViewById(R.id.fromSpinner);
        toSpinner = view.findViewById(R.id.toSpinner);
        scaleSpinner = view.findViewById(R.id.scaleSpinner);
        keySigSpinner = view.findViewById(R.id.keySigSpinner);

        ArrayAdapter<String> all_notes_string_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, notes_strings);
        ArrayAdapter<String> all_scales_string_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, scale_strings);
        ArrayAdapter<String> all_keySig_string_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, keySig_strings);

        fromSpinner.setAdapter(all_notes_string_adapter);
        toSpinner.setAdapter(all_notes_string_adapter);
        scaleSpinner.setAdapter(all_scales_string_adapter);
        keySigSpinner.setAdapter(all_keySig_string_adapter);

        fromSpinner.setSelection(Note.getIndex("A3"));
        toSpinner.setSelection(Note.getIndex("A4"));
        scaleSpinner.setSelection(1); // Major

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                fromNote = new Note(position);
                Log.i(TAG, "FROM selected " + fromNote.getText(position));

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

                generated_notes = ((NotesBitmap) filterHandler.getResultBitmap()).toNotes();

                update_tableview_using_note_pool();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i(TAG, "FROM (on nothing) select item ");
            }
        });
    }

    /**
     * update tableview using the generated notes
     */
    void update_tableview_using_note_pool() {
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
                tmpData.toggleNote(note);
            }
        });
        return button;
    }


}
