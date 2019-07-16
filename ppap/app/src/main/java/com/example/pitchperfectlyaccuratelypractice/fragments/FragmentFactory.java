package com.example.pitchperfectlyaccuratelypractice.fragments;

import android.util.Log;

import com.example.pitchperfectlyaccuratelypractice.enums.Mode;

/**
 * a factory to produce different fragments
 */
public class FragmentFactory {
    private static String TAG = "Factory";

    public GeneralFragment create(Mode mode){
        switch(mode){
            case NotePractice:
                Log.d(TAG, "FragmentCreated: notemode");
                return new NoteFragment();
            case IntervalPractice:
                Log.d(TAG, "FragmentCreated: intervalmode");
                return new IntervalFragment();
            case TriadPractice:
                Log.d(TAG, "FragmentCreated: chordmode");
                return new TriadFragment();
            case NoteGraphPractice:
                Log.d(TAG, "FragmentCreated: notegraph");
                return new NoteGraphFragment();
//            case R.id.song_mode:
//                return new SongFragment();
//                break;
            default:
                Log.d(TAG, "FragmentCreated: default");
                return new NoteFragment();
        }
    }
}
