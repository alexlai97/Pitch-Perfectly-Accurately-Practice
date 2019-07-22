package com.example.pitchperfectlyaccuratelypractice.model;

import java.io.Serializable;

public class PerModeSetting implements Serializable {
    public String mode;
    public int[] notesBitmap;
    public int[] intervalsBitmap;
    public int from;
    public int to;
    public int scale;
    public int keySignature;

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

    public int getFilterPageNum(){
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
