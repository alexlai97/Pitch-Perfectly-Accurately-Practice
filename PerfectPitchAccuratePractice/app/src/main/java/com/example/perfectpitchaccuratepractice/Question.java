package com.example.perfectpitchaccuratepractice;

/**
 * an abstract class of Question
 */
public abstract class Question {
  String text;
  Note[] candidate_notes;

  /**
   * Constructor, at default text "n/a"
   */
  Question() {
    this.text = "n/a";
  }


  /**
   * Setter for Candidate notes
   */ 
  void set_candidates(Note [] notes) {
    this.candidate_notes = notes;
  }

  /**
   * generate candidate notes using lower note index and upper note index
   * <p>
   * E.g.  0 - 13 means A1 - A2
   */ 
  void set_candidates_with_range(int lower, int upper) {
    int num = upper - lower + 1;
    candidate_notes = new Note[num];
    for (int i =0; i < num; i++) {
      candidate_notes[i] = new Note(lower + i);
    }
  }

  /**
   * getter of the text of the question
   */
  String getText() {
    return this.text;
  }

  /**
   * abstract function, currently only for note question
   */
  void generate_random_question() {
  }
}
