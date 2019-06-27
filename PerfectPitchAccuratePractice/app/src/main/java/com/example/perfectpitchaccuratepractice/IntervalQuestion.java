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
   * generate question from candidate notes
   * <p>
   * remember to set candidate notes and candidate intervals first
    */
  void generate_random_question() {
    int rnd = new Random().nextInt(candidate_intervals.length);
    questionInterval = candidate_intervals[rnd];
    text = questionInterval.getText();
  }

  /**
   * test (ignore me)
   */
  class IntervalQuestion {
    public static void main(String args[]) {
      IntervalQuestion iq = new IntervalQuestion();
      iq.set_candidates_notes(Note.generate_random_question(24,36));
      iq.set_intervals(24,36);
    }
  }
}
