package com.example.pitchperfectlyaccuratelypractice.question;

import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Song;

public class SongQuestion extends Question {

    Song song;

    Note currentNote;
    Note prevNote;
    Note nextNote;

    public SongQuestion(Song song) {
        this.song = song;
        notePool = song.getNotes();
        next_question(NextQuestionStrategy.InOrder);
    }

    public Song getSong() {
        return song;
    }

    @Override
    public Note[] getExpectedNotes() {
        return new Note[]{currentNote};
    }

    @Override
    public void next_question(NextQuestionStrategy nextQuestionStrategy) {
        switch (nextQuestionStrategy) {
            case Random:
            case ReverseOrder:
                throw new AssertionError("InOrder only on song next question");
            case InOrder:
                this.currentNote = notePool[inorder_index];
                this.prevNote = (inorder_index<=0)? null:notePool[inorder_index-1];
                this.nextNote = (inorder_index>=notePool.length-1)? null:notePool[inorder_index+1];
                this.texts = new String[] { (prevNote == null)? "":prevNote.getText(), currentNote.getText(), (nextNote == null)? "":nextNote.getText()};
                inorder_index += 1;
                if (inorder_index >= notePool.length) {
                    inorder_index = 0;
                }
                break;
        }
    }

    public static void main(String args[]) {
//        SongQuestion sq = new SongQuestion();
    }
}
