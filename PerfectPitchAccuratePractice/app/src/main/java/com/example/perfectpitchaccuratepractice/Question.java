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
   * Setter for candidate notes
   */ 
  void set_candidates_notes(Note [] notes) {
    this.candidate_notes = notes;
  }


  /**
   * getter of the text of the question
   */
  String getText() {
    return this.text;
  }

  /**
   * abstract function to generate random question given current fields
   */
  void generate_random_question() {
  }
}
