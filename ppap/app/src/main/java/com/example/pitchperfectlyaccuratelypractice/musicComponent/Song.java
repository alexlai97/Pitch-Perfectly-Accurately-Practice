package com.example.pitchperfectlyaccuratelypractice.musicComponent;

import com.example.pitchperfectlyaccuratelypractice.tools.MyMidiTool;
import com.leff.midi.MidiFile;

public class Song {
    int id;
    String title;
    MidiFile midiFile;
    Note[] notes;

    public Song(int id, String title, MidiFile midiFile) {
        this.id = id;
        this.title = title;
        this.midiFile = midiFile;
        this.notes = MyMidiTool.parseMidiToNotes(midiFile);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public MidiFile getMidiFile() {
        return midiFile;
    }

    public Note[] getNotes() {
        return notes;
    }
}
