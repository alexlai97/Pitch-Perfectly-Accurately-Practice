package com.example.pitchperfectlyaccuratelypractice.perModeSetting;

import com.example.pitchperfectlyaccuratelypractice.bitmap.IntervalsBitmap;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;

public class NodeModeSetting extends PerModeSetting {
    public boolean autoPlayBack = false;
    public double errorAllowance = 0.5;
    public long leastStableTime = 1000;
    public long showCorrectTime = 1000;
    public int[] nodeBitmap = null;
    public int from = Note.getIndex("A3");
    public int to = Note.getIndex("A4");
    public int scale = 1;
    public int keySignature = 0;

//    NodeModeSetting(){
//        nodeBitmap =
//    }

}
