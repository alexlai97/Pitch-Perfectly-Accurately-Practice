package com.example.pitchperfectlyaccuratelypractice.perModeSetting;

import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;

public class IntervelModeSetting extends PerModeSetting {
    public int from = Note.getIndex("A3");
    public int to = Note.getIndex("A4");
    public int scale = 1;
    public int keySignature = 0;
}
