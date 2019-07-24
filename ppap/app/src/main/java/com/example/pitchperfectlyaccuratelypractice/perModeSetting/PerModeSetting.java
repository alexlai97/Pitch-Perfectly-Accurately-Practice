package com.example.pitchperfectlyaccuratelypractice.perModeSetting;

import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;

import java.io.Serializable;

public class PerModeSetting implements Serializable {
    public Mode mode;
    private int[] notesBitmap = null;
    private int[] intervalsBitmap = null;
    public int from = Note.getIndex("A3");
    public int to = Note.getIndex("A4");
    public int scale = 1;
    public int keySignature = 0;

    public PerModeSetting(){
    }


    public void setNotesBitmap(int[] notesBitmap) {
        this.notesBitmap = notesBitmap;
    }

    public void setIntervalsBitmap(int[] intervalsBitmap) {
        this.intervalsBitmap = intervalsBitmap;
    }

    public int[] getNotesBitmap() {
        return notesBitmap;
    }

    public int[] getIntervalsBitmap() {
        return intervalsBitmap;
    }

    public int getFilterPageNum(){
        switch (mode){
            case NotePractice:
                return 1;
            case IntervalPractice:
                return 2;
            case TriadPractice:
                return 1;
            case NoteGraphPractice:
                return 1;
            case SongPractice:
                return 1;
            default:
                return 1;
        }
    }


}
