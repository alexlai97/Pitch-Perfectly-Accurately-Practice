package com.example.pitchperfectlyaccuratelypractice.question;

import android.util.Log;

import com.example.pitchperfectlyaccuratelypractice.R;

public class QuestionFactory {
    private static String TAG = "NoteFactory";

    public Question create(int id){
        switch (id) {
            case R.id.note_mode:
                Log.d(TAG, "NoteQuestion");
                return new NoteQuestion();
            case R.id.interval_mode:
                Log.d(TAG, "IntQuestion");
                return new IntervalQuestion();
            case R.id.triad_mode:
                Log.d(TAG, "TriadQuestion");
                return new TriadQuestion();
            case R.id.notegraph_mode:
                Log.d(TAG, "NoteGraphQuestion");
                return new NoteQuestion();
            //FIXME not implemented (Add song mode)
            default:
                return new NoteQuestion();
        }
    }
}
