package com.example.pitchperfectlyaccuratelypractice.tools;

import android.content.Context;
import android.util.Log;

import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;
import com.leff.midi.MidiFile;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOn;

import java.io.InputStream;
import java.util.ArrayList;

public class MyMidiTool {
    private static final String TAG = "MyMidiTool";
    public MyMidiTool() {
    }


    public static MidiFile getMidiFileFromId(Context context, int id) {
        if (context == null) {
            throw new AssertionError("getMidiFromID context is null");
        }
        InputStream inputStream = context.getResources().openRawResource(id);
        MidiFile midiFile = null;
        try {
            midiFile = new MidiFile(inputStream);
        } catch (Exception e) {
            Log.d(TAG, "getMidiFileFromId: " + e);
        }
        return midiFile;
    }

    public static Note[] parseMidiToNotes(MidiFile midiFile) {
        if (midiFile == null) {
            throw new AssertionError("parseMidiToNotes midifile is null");
        }

        ArrayList<Note> notes_arr = new ArrayList<>();
        boolean flag = true;
        for (MidiEvent event: midiFile.getTracks().get(1).getEvents()) {
            if (event.getClass() == NoteOn.class) {
                if (flag) {
                    notes_arr.add(new Note(-33 + ((NoteOn) event).getNoteValue()));
                }
                flag = !flag;
            }
        }

        return Note.ArrayListToNotes(notes_arr);
    }
}
