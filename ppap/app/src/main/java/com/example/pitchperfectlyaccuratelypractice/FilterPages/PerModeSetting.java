package com.example.pitchperfectlyaccuratelypractice.FilterPages;

import android.os.Bundle;

import com.example.pitchperfectlyaccuratelypractice.bitmap.IntervalsBitmap;
import com.example.pitchperfectlyaccuratelypractice.bitmap.NotesBitmap;
import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.music.Interval;

import java.io.Serializable;

public class PerModeSetting implements Serializable {
    String mode;
    int[] notesBitmap;
    int[] intervalsBitmap;
    int from;
    int to;
    int scale;
    int keySignature;

    public PerModeSetting(String mode){
        this.mode = mode;
        notesBitmap = null;
        intervalsBitmap = null;
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

    int getFilterPageNum(){
        switch (mode){
            case "note":
                return 1;
            case "interval":
                return 2;
            case "triad":
                return 1;
            case "noteGraph":
                return 1;
            default:
                return 1;
        }
    }


}
