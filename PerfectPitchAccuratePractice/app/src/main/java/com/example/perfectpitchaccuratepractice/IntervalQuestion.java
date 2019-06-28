package com.example.com.example.perfectpitchaccuratepractice;
import java.util.Random;

class IntervalQuestion {
  Note questionNote;
  Interval questionInterval;
  Note answerNote;
  Interval [] candidate_intervals;

  /**
   * Setter for candidate intervals
   */ 
  void set_candidates_intervals(Interval [] intervals) {
    this.candidate_intervals = intervals;
  }

  /**
   * generate random question from candidate notes and candidate intervals
   * <p>
   * remember to set candidate notes and candidate intervals first
    */
  void generate_random_question() {
    Random r = new Random();
    int rnd = r.nextInt(candidate_intervals.length);
    questionInterval = candidate_intervals[rnd];
    rnd = r.nextInt(candidate_notes.length);
    questionNote = candidate_notes[rnd];
    text = questionNote.getText() + " " +  questionInterval.getText();
  }

  /**
   * test (ignore me)
   */
  public static void main(String args[]) {
    IntervalQuestion iq = new IntervalQuestion();
    iq.set_candidates_notes(Note.generate_random_question(24,36));
    iq.set_intervals(24,36);
  }
}
