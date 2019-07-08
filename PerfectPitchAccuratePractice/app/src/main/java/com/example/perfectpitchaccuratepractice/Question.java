package com.example.perfectpitchaccuratepractice;

/**
 * an abstract class of Question
 */
public abstract class Question {
  /**
   * the text of the question
   */
  String text;
  /**
   * the note pool, which is can be generated from the filter page
   */
  Note[] notePool;

  /**
   * Constructor, at default text "n/a"
   */
  Question() {
    this.text = "n/a";
  }


  /**
   * Setter for note pool
   */ 
  void setNotePool(Note [] notes) {
    this.notePool = notes;
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
