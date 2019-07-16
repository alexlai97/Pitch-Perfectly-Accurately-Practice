package com.example.pitchperfectlyaccuratelypractice.question;

import android.util.Log;

import com.example.pitchperfectlyaccuratelypractice.enums.Mode;

/**
 * a factory to produce different questions
 */
public class QuestionFactory {
    private static String TAG = "QuestionFactory";

    public Question create(Mode mode){
        switch (mode) {
            case NotePractice:
                Log.d(TAG, "NoteQuestion");
                return new NoteQuestion();
            case IntervalPractice:
                Log.d(TAG, "IntervalQuestion");
                return new IntervalQuestion();
            case TriadPractice:
                Log.d(TAG, "TriadQuestion");
                return new TriadQuestion();
            case NoteGraphPractice:
                Log.d(TAG, "NoteGraphQuestion");
                return new NoteQuestion();
            //FIXME not implemented (Add song mode)
            default:
                return new NoteQuestion();
        }
    }
}
