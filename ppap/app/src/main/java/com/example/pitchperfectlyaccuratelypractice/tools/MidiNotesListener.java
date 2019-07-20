package com.example.pitchperfectlyaccuratelypractice.tools;

import android.util.Log;

import com.example.pitchperfectlyaccuratelypractice.music.Note;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOn;
import com.leff.midi.util.MidiEventListener;

import java.util.ArrayList;

public class MidiNotesListener implements MidiEventListener {
    private final static String TAG = "MidiEventListener";

    ArrayList<Note> notes_arr = new ArrayList<>();

    int last_note_index = 0;

    MyMidiTool myMidiTool;

    public MidiNotesListener(MyMidiTool myMidiTool) {
        this.myMidiTool = myMidiTool;
    }
    @Override
    public void onStart(boolean fromBeginning) {
        if (fromBeginning) {
            Log.d(TAG, "onStart: from beginning");

        } else {
            Log.d(TAG, "onStart: not from beginning");
        }
    }
    @Override
    public void onEvent(MidiEvent event, long ms) {
        int cur_note_index = -33 + ((NoteOn)event).getNoteValue();
        if (cur_note_index != last_note_index) {
            notes_arr.add(new Note(cur_note_index));
        }
        last_note_index = cur_note_index;
    }
    @Override
    public void onStop(boolean finished) {
        if (finished) {
            Log.d(TAG, "onStop: finished");
//            myMidiTool.updateNotes(Note.ArrayListToNotes(notes_arr));
        } else {
            Log.d(TAG, "onStop: not finished");
        }
    }

}
