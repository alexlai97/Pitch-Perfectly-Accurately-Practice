package com.example.pitchperfectlyaccuratelypractice.question;
import android.util.Log;

import com.example.pitchperfectlyaccuratelypractice.common.ModelController;
import com.example.pitchperfectlyaccuratelypractice.note.Note;
import com.example.pitchperfectlyaccuratelypractice.interval.*;
import java.util.Random;

/**
 * A Question in Interval practice mode
 */
public class IntervalQuestion extends Question {
  /**
   * The base note in the question
   */
  private Note questionNote;
  /**
   * The interval in the question
   */
  private Interval questionInterval;
  /**
   * The answer of the question
   */
  private Note answerNote;
  /**
   * interval pool, similar to note pool, is a set of intervals that can be used to form a question
   */
  private Interval[] intervalPool;

  /**
   * static Random
   */
  private static Random random = new Random();

  /**
   * Setter for interval pool
   */ 
  public void setIntervalPool(Interval [] intervals) {
    this.intervalPool = intervals;
  }
  private ModelController modelController;

  public IntervalQuestion() {
    super();
    intervalPool = Interval.getAllTrueIntervals();
  }

  /**
   *
   */
  public Note[] getAnswerNotes() {
    Note[] notes = new Note[1];
    notes[0] = answerNote;

    return notes;
  }

  /**
   * generate random question from note pool and interval pool
   * <p>
   * remember to set notes pool and interval pool first
   */
  public void generate_random_question() {
    int rnd = random.nextInt(notePool.length);
    questionNote = notePool[rnd];
    boolean validInterval = false;
    int result_index = 0;
    while (!validInterval) {
      rnd = random.nextInt(intervalPool.length);
      questionInterval = intervalPool[rnd];
      result_index = questionNote.getIndex() + questionInterval.getRelativeIndex();
      if (result_index >= 0) {
        validInterval = true;
      }
    }
    answerNote = new Note(result_index);
    this.texts = new String[2];
    this.texts[0] = questionNote.getText();
    this.texts[1] = questionInterval.getText();

    Log.d("IntervalQuestion", "generate_random_question: " + " questionNote" + questionNote.getText() + " questionInterval" + questionInterval.getText() + " answerNote" + answerNote.getText());
  }

  /**
   * a way to use this class, can print all intervals in a table
   */
  public static void main(String args[]) {
    IntervalQuestion iq = new IntervalQuestion();
    iq.setNotePool(Note.generateNotesWithRange(24,36));
    iq.setIntervalPool(Interval.generateIntervalsWithRange(-12,12));

    int some_num = 100;
    System.out.println("Printing " + some_num + " random interval questions");
    for (int i = 0; i < some_num; i++) {
      iq.generate_random_question();
      iq.print_question_texts();
    }
  }
}
