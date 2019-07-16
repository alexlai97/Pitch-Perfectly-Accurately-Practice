package com.example.pitchperfectlyaccuratelypractice.question;

import com.example.pitchperfectlyaccuratelypractice.R;

public class QuestionFactory {
    private static String TAG = "Factory";

    public Question create(int id){
        switch (id) {
            case R.id.note_mode:
                return new NoteQuestion();
            case R.id.interval_mode:
                return new IntervalQuestion();
            case R.id.triad_mode:
                return new TriadQuestion();
            case R.id.notegraph_mode:
                return new NoteQuestion();
            //FIXME not implemented (Add song mode)
            default:
                return new NoteQuestion();
        }
    }
}
