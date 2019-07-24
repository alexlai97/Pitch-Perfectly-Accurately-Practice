package com.example.pitchperfectlyaccuratelypractice.perModeSetting;

import com.example.pitchperfectlyaccuratelypractice.bitmap.IntervalsBitmap;
import com.example.pitchperfectlyaccuratelypractice.bitmap.NotesBitmap;
import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;

import java.io.Serializable;

public class PerModeSetting implements Serializable {
    public Mode mode;
    private NotesBitmap notesBitmap;
    private IntervalsBitmap intervalsBitmap;
    /*
    TODO change from, to -> Note, change scale to NotesScale, change keySignature to Note
     */
    public int from = Note.getIndex("A3");
    public int to = Note.getIndex("A4");
    public int scale = 1;
    public int keySignature = 0;

    public PerModeSetting(){
    }
    public PerModeSetting(Mode mode){
        this.mode = mode;
    }


    public void setNotesBitmap(NotesBitmap notesBitmap) {
        this.notesBitmap = notesBitmap;
    }

    public void setIntervalsBitmap(IntervalsBitmap intervalsBitmap) {
        this.intervalsBitmap = intervalsBitmap;
    }

    public NotesBitmap getNotesBitmap() {
        return notesBitmap;
    }

    public IntervalsBitmap getIntervalsBitmap() {
        return intervalsBitmap;
    }


}
