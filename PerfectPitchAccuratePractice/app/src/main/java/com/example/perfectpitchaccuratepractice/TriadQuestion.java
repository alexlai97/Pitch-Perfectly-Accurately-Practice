
class TriadQuestion extends Question{

  /**
   * generate question from note pool
   * <p>
   * remember to set note pool first
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
