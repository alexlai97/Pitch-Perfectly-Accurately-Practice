package com.example.perfectpitchaccuratepractice;
import java.util.Random;

class IntervalQuestion extends Question {
  private Note questionNote;
  private Interval questionInterval;
  private Note answerNote;
  private Interval[] candidate_intervals;

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
    text = questionNote.getText(true) + " " +  questionInterval.getText();
  }

  /**
   * test (ignore me)
   */
  public static void main(String args[]) {
    IntervalQuestion iq = new IntervalQuestion();
    iq.set_candidates_notes(Note.generateNotesWithRange(24,36));
    iq.set_candidates_intervals(Interval.generateIntervalsWithRange(-12,12));

    int some_num = 100;
    System.out.println("Printing " + some_num + " random interval questions");
    for (int i = 0; i < some_num; i++) {
      iq.generate_random_question();
      System.out.println(iq.getText());
    }
  }
}
