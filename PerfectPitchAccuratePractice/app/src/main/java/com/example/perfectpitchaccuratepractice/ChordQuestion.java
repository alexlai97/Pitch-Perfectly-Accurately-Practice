package com.example.perfectpitchaccuratepractice;

class ChordQuestion {
  Chord questionChord;

  /**
   * generate question from candidate notes
   * <p>
   * remember to set candidate notes first
    */
  void generate_random_question() {
    questionChord = Chord.generate_random_question(Note.generateNotesWithRange(0,72),3);
    text = questionChord.getText();
  }

  Chord getQuestionChord() {
    return questionChord;
  }

  public static void main(String args[]) {
  }
}
