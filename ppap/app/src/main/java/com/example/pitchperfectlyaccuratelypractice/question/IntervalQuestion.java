package com.example.pitchperfectlyaccuratelypractice.question;

import com.example.pitchperfectlyaccuratelypractice.musicComponent.Interval;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;


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

  public Note getQuestionNote() {
    return questionNote;
  }
  public Note [] getQuestionAndAnserNote() {
    return new Note[] {questionNote, answerNote};
  }

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
    this.next_question(NextQuestionStrategy.Random);
  }


  /**
   *
   */
  public Note[] getExpectedNotes() {
    return new Note[]{answerNote};
  }

  public void next_question(NextQuestionStrategy nextQuestionStrategy) {
    switch (nextQuestionStrategy) {
      case Random:
        int rnd = random.nextInt(notePool.length);
        this.questionNote = notePool[rnd];
        setup_random_interval_and_answer();
//        this.texts = new String[] { questionNote.getText()};
        break;
      case InOrder:
        this.questionNote = notePool[inorder_index];
        setup_random_interval_and_answer();
//        this.texts = new String[] { questionNote.getText()};
        inorder_index += 1;
        if (inorder_index >= notePool.length) {
          inorder_index = 0;
        }
        break;
      case ReverseOrder:
        this.questionNote = notePool[reverse_order_index];
        setup_random_interval_and_answer();
//        this.texts = new String[] { questionNote.getText()};
        reverse_order_index -= 1;
        if (reverse_order_index < 0) {
          reverse_order_index = notePool.length - 1;
        }
        break;
    }
  }

  public void setup_random_interval_and_answer() {
//    int rnd = random.nextInt(notePool.length);
//    questionNote = notePool[rnd];
    boolean validInterval = false;
    int result_index = 0;
    while (!validInterval) {
//      Log.d(TAG, "generate_random_question: " + validInterval + intervalPool.length);
      int rnd = random.nextInt(intervalPool.length);
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

//    Log.d("IntervalQuestion", "generate_random_question: " + " questionNote" + questionNote.getText() + " questionInterval" + questionInterval.getText() + " answerNote" + answerNote.getText());
  }

  /**
   * a way to use this class, can print all intervals in a table
   */
  public static void main(String args[]) {
    IntervalQuestion iq = new IntervalQuestion();
    iq.setNotePool(Note.generateNotesWithRange(24,36));
    iq.setIntervalPool(Interval.generateIntervalsWithRange(0,24));

    int some_num = 100;
    System.out.println("Printing " + some_num + " random interval questions");
    for (int i = 0; i < some_num * 2; i++) {
      iq.next_question(NextQuestionStrategy.ReverseOrder);
      iq.print_question_texts();
    }
  }
}
