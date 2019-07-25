package com.example.pitchperfectlyaccuratelypractice.perModeSetting;

import com.example.pitchperfectlyaccuratelypractice.bitmap.IntervalsBitmap;
import com.example.pitchperfectlyaccuratelypractice.bitmap.NotesBitmap;
import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.enums.NotesScale;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Triad;

import java.io.Serializable;

public abstract class PerModeSetting implements Serializable {
    public Mode mode;
    public PerModeSetting() { /* FIXME tmp */}

    public PerModeSetting(Mode mode){
        this.mode = mode;
    }

    public NotesBitmap getNotesBitmap() {
        if (mode == Mode.SongPlaying || mode == Mode.SongPractice) {
            throw new AssertionError("Song mode cannot set notesbitmap");
        }
        switch (mode) {
            case NotePractice:
                return ((NoteModeSetting)this).notesBitmap ;
            case NoteGraphPractice:
                return ((NoteGraphModeSetting)this).notesBitmap ;
            case IntervalPractice:
                return ((IntervalModeSetting)this).notesBitmap ;
            case TriadPractice:
                return ((TriadModeSetting)this).notesBitmap ;
        }
        return null;
    }
    public void setNotesBitmap(NotesBitmap notesBitmap) {
        if (mode == Mode.SongPlaying || mode == Mode.SongPractice) {
            throw new AssertionError("Song mode cannot set notesbitmap");
        }
        switch (mode) {
            case NotePractice:
                ((NoteModeSetting)this).notesBitmap = notesBitmap;
                break;
            case NoteGraphPractice:
                ((NoteGraphModeSetting)this).notesBitmap = notesBitmap;
                break;
            case IntervalPractice:
                ((IntervalModeSetting)this).notesBitmap = notesBitmap;
                break;
            case TriadPractice:
                ((TriadModeSetting)this).notesBitmap = notesBitmap;
                break;
        }
    }
    public void setIntervalsBitmap(IntervalsBitmap intervalsBitmap) {
        if (mode != Mode.IntervalPractice) {
            throw new AssertionError("only interval mode can set interval bitmap");
        } else {
            ((IntervalModeSetting)this).intervalsBitmap = intervalsBitmap;
        }
    }

    public void setFromNote(Note from_note) {
        if (mode == Mode.SongPlaying || mode == Mode.SongPractice) {
            throw new AssertionError("Song mode cannot set fromNote");
        }
        switch (mode) {
            case NotePractice:
                ((NoteModeSetting)this).from_note = from_note;
                break;
            case NoteGraphPractice:
                ((NoteGraphModeSetting)this).from_note = from_note;
                break;
            case IntervalPractice:
                ((IntervalModeSetting)this).from_note = from_note;
                break;
            case TriadPractice:
                ((TriadModeSetting)this).from_note = from_note;
                break;
        }
    }
    public void setToNote(Note to_note) {
        if (mode == Mode.SongPlaying || mode == Mode.SongPractice) {
            throw new AssertionError("Song mode cannot set toNote");
        }
        switch (mode) {
            case NotePractice:
                ((NoteModeSetting)this).to_note = to_note;
                break;
            case NoteGraphPractice:
                ((NoteGraphModeSetting)this).to_note = to_note;
                break;
            case IntervalPractice:
                ((IntervalModeSetting)this).to_note = to_note;
                break;
            case TriadPractice:
                ((TriadModeSetting)this).to_note = to_note;
                break;
        }
    }

    public void setNoteScale(NotesScale scale) {
        if (mode == Mode.SongPlaying || mode == Mode.SongPractice) {
            throw new AssertionError("Song mode cannot set NoteScale");
        }
        switch (mode) {
            case NotePractice:
                ((NoteModeSetting)this).scale = scale;
                break;
            case NoteGraphPractice:
                ((NoteGraphModeSetting)this).scale = scale;
                break;
            case IntervalPractice:
                ((IntervalModeSetting)this).scale = scale;
                break;
            case TriadPractice:
                ((TriadModeSetting)this).scale = scale;
                break;
        }
    }

    public void setKeySigNote(Note keySigNote) {
        if (mode == Mode.SongPlaying || mode == Mode.SongPractice) {
            throw new AssertionError("Song mode cannot set keysignote");
        }
        switch (mode) {
            case NotePractice:
                ((NoteModeSetting)this).keySigNote = keySigNote;
                break;
            case NoteGraphPractice:
                ((NoteGraphModeSetting)this).keySigNote = keySigNote;
                break;
            case IntervalPractice:
                ((IntervalModeSetting)this).keySigNote = keySigNote;
                break;
            case TriadPractice:
                ((TriadModeSetting)this).keySigNote = keySigNote;
                break;
        }
    }
}
