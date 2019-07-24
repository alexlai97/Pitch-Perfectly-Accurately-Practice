package com.example.pitchperfectlyaccuratelypractice.question;

import com.example.pitchperfectlyaccuratelypractice.musicComponent.Interval;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;

import java.util.Random;

/**
 * an abstract class of Question
 */
public abstract class Question {
  /** an array of texts that constitute the question */
  String[] texts;
  /**  getter of the text of the question */
  public String[] getTexts() {
    return this.texts;
  }
  /** index in note pool (for inorder next question) */
  int inorder_index;
  /** index in note pool (for reverseorder next question) */
  int reverse_order_index;
  /** the note pool, which can be generated from the filter page, used to form a question */
  Note[] notePool;
  /** Setter for note pool */

  /**
   * Constructor
   */
//  public Question() {
//    // note pool initialized by some reasonable notes (where average human can reach)
//    notePool = Note.getReasonableNotes();
//    generate_random_question();
//  }
//    public Question(){};

  /**
   * children will implement this
   */
//  public abstract Note[] getAnswerNotes();

//  public void setIntervalPool(Interval[] intervals){}

  /**
   * Setter for note pool
   */
  public void setNotePool(Note[] notes) {
    this.notePool = notes;
    inorder_index = 0;
    reverse_order_index = notes.length -1;
  }

  protected Random random = new Random();

  /** empty parent Constructor */
  public Question(){};

  /** notes expected to be sung */
  public abstract Note[] getExpectedNotes();

  /** (debugging) print texts separated by space in stdout */
  public void print_question_texts() {
    for (String t : this.getTexts()) {
      System.out.print(t + " ");
    }
    System.out.println();
  }

  /** A strategy to select next note in note pool */
  public enum NextQuestionStrategy {
    Random,
    InOrder,
    ReverseOrder
  }

  /** next key note based on nextquestion strategy, other things random */
  public abstract void next_question(NextQuestionStrategy nextQuestionStrategy);
}
