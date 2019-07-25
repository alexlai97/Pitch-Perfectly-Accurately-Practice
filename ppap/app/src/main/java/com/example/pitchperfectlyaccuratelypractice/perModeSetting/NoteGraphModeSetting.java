package com.example.pitchperfectlyaccuratelypractice.perModeSetting;

import com.example.pitchperfectlyaccuratelypractice.bitmap.NotesBitmap;
import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.enums.NotesScale;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;

public class NoteGraphModeSetting extends PerModeSetting {
    public NotesBitmap notesBitmap;
    public boolean autoPlayBack;
    public double errorAllowance;
    public long leastStableTime;
    public long showCorrectTime;
    public Note from_note;
    public Note to_note ;
    public NotesScale scale;
    public Note keySigNote;

    public NoteGraphModeSetting () {

        mode = Mode.NoteGraphPractice;
        autoPlayBack = true;
        errorAllowance = 0.5;
        leastStableTime = 1000;
        showCorrectTime = 1000;
        from_note = new Note("C3");
        to_note = new Note("C5");
        scale = NotesScale.Major;
        keySigNote = new Note("C");
    }
}
