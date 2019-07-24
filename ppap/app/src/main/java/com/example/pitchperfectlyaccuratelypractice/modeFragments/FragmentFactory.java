package com.example.pitchperfectlyaccuratelypractice.modeFragments;

import android.util.Log;

import com.example.pitchperfectlyaccuratelypractice.enums.Mode;

/**
 * a factory to produce different fragments
 */
public class FragmentFactory {
    private static String TAG = "Factory";

    public ModeFragment create(Mode mode){
        switch(mode){
            case NotePractice:
                Log.d(TAG, "FragmentCreated: notemode");
                return new NoteModeFragment();
            case IntervalPractice:
                Log.d(TAG, "FragmentCreated: intervalmode");
                return new IntervalModeFragment();
            case TriadPractice:
                Log.d(TAG, "FragmentCreated: chordmode");
                return new TriadFragment();
            case NoteGraphPractice:
                Log.d(TAG, "FragmentCreated: notegraph");
                return new NoteGraphModeFragment();
            case SongPlaying:
                Log.d(TAG, "FragmentCreated: songplaying");
                return new SongPlayingFragment();
            case SongPractice:
                Log.d(TAG, "FragmentCreated: songpracticing");
                return new SongPracticingFragment();
            default:
                Log.d(TAG, "FragmentCreated: default");
                return new NoteModeFragment();
        }
    }
}
