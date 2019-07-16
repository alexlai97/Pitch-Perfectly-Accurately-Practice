package com.example.pitchperfectlyaccuratelypractice.question;
import android.util.Log;

import com.example.pitchperfectlyaccuratelypractice.music.Interval;
import com.example.pitchperfectlyaccuratelypractice.music.Note;

import java.util.Random;

/**
 * A Question in Interval practice mode
 */
public class IntervalQuestion extends Question {
  private final static String TAG = "IntervalQuestion";
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
   * Setter for interval pool
   */ 
  public void setIntervalPool(Interval [] intervals) {
    this.intervalPool = intervals;
  }

  /**
   * default constructor of interval question
   * which sets the note pool to all notes
   * which sets the interval pool to all intervals
   */
  public IntervalQuestion() {
    this.notePool = Note.getReasonableNotes();
    this.intervalPool = Interval.getAllTrueIntervals();
    if (this.intervalPool == null)  {
      throw new AssertionError("Interval pool is null");
    }
    this.generate_random_question();
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
    if (this.intervalPool == null)  {
      throw new AssertionError("Interval pool is null");
    }
    int rnd = new Random().nextInt(notePool.length);
    questionNote = notePool[rnd];
    boolean validInterval = false;
    int result_index = 0;
    while (!validInterval) {
      Log.d(TAG, "generate_random_question: " + validInterval + intervalPool.length);
      rnd = new Random().nextInt(intervalPool.length);
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
