package com.example.pitchperfectlyaccuratelypractice.fragments;

import android.util.Log;

import com.example.pitchperfectlyaccuratelypractice.R;

public class FragmentFactory {
    private static String TAG = "Factory";

    public GeneralFragment create(int id){
        switch(id){
            case R.id.note_mode:
                Log.d(TAG, "FragmentCreated: notemode");
                return new NoteFragment();
            case R.id.interval_mode:
                Log.d(TAG, "FragmentCreated: intervalmode");
                return new IntervalFragment();
            case R.id.triad_mode:
                Log.d(TAG, "FragmentCreated: chordmode");
                return new TriadFragment();
            case R.id.notegraph_mode:
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
