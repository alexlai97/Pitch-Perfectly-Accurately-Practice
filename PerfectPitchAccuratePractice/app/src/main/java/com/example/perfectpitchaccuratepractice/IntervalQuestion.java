package com.example.perfectpitchaccuratepractice;
import java.util.Random;

class IntervalQuestion extends Question {
  private Note questionNote;
  private Interval questionInterval;
  private Note answerNote;
  private Interval[] intervalPool;

  /**
   * Setter for interval pool
   */ 
  void setIntervalPool(Interval [] intervals) {
    this.intervalPool = intervals;
  }

  /**
   * generate random question from note pool and interval pool
   * <p>
   * remember to set notes pool and interval pool first
   */
  void generate_random_question() {
    Random r = new Random();
    int rnd = r.nextInt(intervalPool.length);
    questionInterval = intervalPool[rnd];
    rnd = r.nextInt(notePool.length);
    questionNote = notePool[rnd];
    text = questionNote.getText(true) + " " +  questionInterval.getText();
  }

  /**
   * test (ignore me)
   */
  public static void main(String args[]) {
    IntervalQuestion iq = new IntervalQuestion();
    iq.setNotePool(Note.generateNotesWithRange(24,36));
    iq.setIntervalPool(Interval.generateIntervalsWithRange(-12,12));

    int some_num = 100;
    System.out.println("Printing " + some_num + " random interval questions");
    for (int i = 0; i < some_num; i++) {
      iq.generate_random_question();
      System.out.println(iq.getText());
    }
  }
}
