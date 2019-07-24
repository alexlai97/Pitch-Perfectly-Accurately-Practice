package com.example.pitchperfectlyaccuratelypractice.tools;

import android.content.Context;
import android.util.Log;

import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;
import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * MyMidiTool has a set of useful methods (e.g. convert MidiFile to array of notes)
 */
public class MyMidiTool {

    private static final String TAG = "MyMidiTool";

    /** empty constructor */
    public MyMidiTool() {
    }

    /** MidiFile to array of MidiTrack where each MidiTrack contains only a note */
    public static ArrayList<MidiTrack> parseMidiFileToNoteTracks(MidiFile midifile) {
        ArrayList<MidiTrack> midiTracks = new ArrayList<MidiTrack>();
        boolean note_pair_first = true;
        long first_note_tick = 0;
        int first_note_velocity = 0;
        int first_note_value = 0;
        MidiTrack noteTrack = new MidiTrack();
        for (MidiEvent event: midifile.getTracks().get(1).getEvents()) {
            if (event.getClass() == NoteOn.class) {
                NoteOn noteOnEvent = (NoteOn) event;
//                Log.d(TAG, "NoteOnEvent: " + new Note(-33+((NoteOn)event).getNoteValue()).getText() + " tick: " + noteOnEvent.getTick() + " velocity: " + noteOnEvent.getVelocity());
                if (note_pair_first) {
                    noteTrack = new MidiTrack();
                    first_note_tick = noteOnEvent.getTick();
                    first_note_velocity = noteOnEvent.getVelocity();
                    first_note_value = noteOnEvent.getNoteValue();
                } else {
                    noteTrack.insertEvent(noteOnEvent);
//                    Log.d(TAG, "onEvent: playthis note");
                    noteTrack.insertNote(noteOnEvent.getChannel(), first_note_value, first_note_velocity, 0, noteOnEvent.getTick() - first_note_tick);
//                    notesPlayer.playNoteTrack(noteTrack);
                    midiTracks.add(noteTrack);
                }
                note_pair_first = !note_pair_first;
            } else if (event.getClass() == NoteOff.class) {
            }
        }
        return midiTracks;
    }

    /** get midi file provided a context and resource id */
    public static MidiFile getMidiFileFromId(Context context, int resource_id) {
        if (context == null) {
            throw new AssertionError("getMidiFromID context is null");
        }
        InputStream inputStream = context.getResources().openRawResource(resource_id);
        MidiFile midiFile = null;
        try {
            midiFile = new MidiFile(inputStream);
        } catch (Exception e) {
            Log.d(TAG, "getMidiFileFromId: " + e);
        }
        return midiFile;
    }


    /** get an array of notes from MidiFile */
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
