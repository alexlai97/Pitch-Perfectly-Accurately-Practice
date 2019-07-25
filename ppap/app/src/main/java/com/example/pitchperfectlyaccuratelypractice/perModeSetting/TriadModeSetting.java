package com.example.pitchperfectlyaccuratelypractice.perModeSetting;

import com.example.pitchperfectlyaccuratelypractice.bitmap.IntervalsBitmap;
import com.example.pitchperfectlyaccuratelypractice.bitmap.NotesBitmap;
import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.enums.NotesScale;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;

public class TriadModeSetting extends PerModeSetting {
    public NotesBitmap notesBitmap;
    public boolean autoPlayBack;
    public double errorAllowance;
    public long leastStableTime;
    public long showCorrectTime;
    public Note from_note;
    public Note to_note ;
    public NotesScale scale;
    public Note keySigNote;
    /**
     * TODO
     */
    public TriadModeSetting () {
        mode = Mode.TriadPractice;
    }
}
