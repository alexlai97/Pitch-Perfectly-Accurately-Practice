package com.example.pitchperfectlyaccuratelypractice.question;

import com.example.pitchperfectlyaccuratelypractice.music.Interval;
import com.example.pitchperfectlyaccuratelypractice.music.Note;

/**
 * an abstract class of Question
 */
public abstract class Question {
  /**
   * an array of texts that constitute the question
   */
  String[] texts;
  /**
   * the note pool, which can be generated from the filter page, used to form a question
   */
  Note[] notePool;

  /**
   * Constructor
   */
//  public Question() {
//    // note pool initialized by some reasonable notes (where average human can reach)
//    notePool = Note.getReasonableNotes();
//    generate_random_question();
//  }
    public Question(){};

  /**
   * children will implement this
   */
  public abstract Note[] getAnswerNotes();

  public void setIntervalPool(Interval[] intervals){}

  /**
   * Setter for note pool
   */
  public void setNotePool(Note[] notes) {
    this.notePool = notes;
  }

  /**
   * getter of the text of the question
   */
  public String[] getTexts() {
    return this.texts;
  }

  /**
   * print texts separated by space in stdout
   */
  void print_question_texts() {
    for (String t : this.getTexts()) {
      System.out.print(t + " ");
    }
    System.out.println();
  }

  /**
   * abstract function to generate random question given current fields
   */
  public abstract void generate_random_question();
}
