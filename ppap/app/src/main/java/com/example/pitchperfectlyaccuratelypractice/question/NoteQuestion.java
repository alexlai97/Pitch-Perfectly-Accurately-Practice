package com.example.pitchperfectlyaccuratelypractice.question;
import com.example.pitchperfectlyaccuratelypractice.note.Note;

import java.util.Random;

/**
 * A Question in Note practice mode
 */
public class NoteQuestion extends Question {
  /**
   * The note that will be questioned 
   */
  private Note questionNote;

  /**
   * generate question from note pool, remember to set note pool first
    */
  public void generate_random_question() {
    int rnd = new Random().nextInt(notePool.length);
    this.questionNote = notePool[rnd];
    this.texts = new String[1];
    this.texts[0] = questionNote.getText();
  }

  /**
   *
   */
  public Note[] getAnswerNotes() {
    Note[] notes = new Note[1];
    notes[0] = questionNote;

    return notes;
  }

  /**
   * test example (ignore moe)
   */
  public static void main(String args[]) {
    NoteQuestion nq = new NoteQuestion();
    Note [] notes = Note.generateNotesWithRange(0,72);
    nq.setNotePool(notes);
    for (int i =0; i < notes.length; i++) {
      nq.generate_random_question();
      nq.print_question_texts();
    }
  }
}
