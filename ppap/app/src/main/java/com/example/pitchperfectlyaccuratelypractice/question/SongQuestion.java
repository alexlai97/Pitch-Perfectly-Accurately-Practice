package com.example.pitchperfectlyaccuratelypractice.question;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.music.Note;

import java.io.File;

public class SongQuestion extends Question {


    @Override
    public Note[] getExpectedNotes() {
        return new Note[0];
    }

    @Override
    public void next_question(NextQuestionStrategy nextQuestionStrategy) {

    }

    public static void main(String args[]) {
        SongQuestion sq = new SongQuestion();
    }
}
