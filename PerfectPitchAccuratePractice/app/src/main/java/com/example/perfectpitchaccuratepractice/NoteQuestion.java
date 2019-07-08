package com.example.perfectpitchaccuratepractice;
import java.util.Random;

/**
 * A Question in Note practice mode
 */
class NoteQuestion extends Question {
  private Note questionNote;

  /**
   * generate question from candidate notes, remember to set candidate notes first
    */
  void generate_random_question() {
    int rnd = new Random().nextInt(candidate_notes.length);
    questionNote = candidate_notes[rnd];
    text = questionNote.getText(true);
  }

  /**
   * getter for questionNote
   */
  Note getQuestionNote() {
      return questionNote;
  }

  /**
   * test example (ignore moe)
   */
  public static void main(String args[]) {
    NoteQuestion nq = new NoteQuestion();
    Note [] notes = Note.generateNotesWithRange(0,72);
    nq.set_candidates_notes(notes);
    for (int i =0; i < notes.length; i++) {
      nq.generate_random_question();
      System.out.println(nq.getText());
    }
  }
}
