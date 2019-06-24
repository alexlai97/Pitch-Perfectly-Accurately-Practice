import java.util.Random;

/*
 * NoteQuestion
 *  Example of how to use ?
 *  1. NoteQuestion nq = new NoteQuestion()
 *  2. nq.set_candidates(notes); // notes is an array of Note
 *  3. nq.generate_random_question(); // generate from candidates
 *  4. System.out.println(nq.getText()); // print this question
 */
class NoteQuestion extends Question {
  Note questionNote;

  // generate question from candidate notes, remember to set candidate notes first
  public void generate_random_question() {
    int rnd = new Random().nextInt(candidate_notes.length);
    questionNote = candidate_notes[rnd];
    text = questionNote.getText(true);
  }

  public Note getQuestionNote() {
      return questionNote;
  }

  // test example
  public static void main(String args[]) {
    NoteQuestion nq = new NoteQuestion();
    Note [] notes = { 
      new Note("A1"), 
      new Note("A1#"),
      new Note("B1"),
      new Note("C2"),
      new Note("C2#"),
      new Note("D2"),
      new Note("D2#"),
      new Note("E2"),
      new Note("F2"),
      new Note("G2"),
      new Note("G2#"),
      new Note("A2"), 
      new Note("A2#"),
      new Note("B2"),
      new Note("C3"),
      new Note("C3#"),
      new Note("D3"),
      new Note("D3#"),
      new Note("E3"),
      new Note("F3"),
      new Note("G3"),
      new Note("G3#"),
      new Note("A3"), 
      new Note("A3#"),
      new Note("B3"),
      new Note("C4"),
      new Note("C4#"),
      new Note("D4"),
      new Note("D4#"),
      new Note("E4"),
      new Note("F4"),
      new Note("G4"),
      new Note("G4#"),
      new Note("A4"), 
      new Note("A4#"),
      new Note("B4"),
      new Note("C5"),
      new Note("C5#"),
      new Note("D5"),
      new Note("D5#"),
      new Note("E5"),
      new Note("F5"),
      new Note("G5"),
      new Note("G5#")
    };
    nq.set_candidates(notes);
    for (int i =0; i < notes.length; i++) {
      // System.out.println(notes[i].getText(true));
      nq.generate_random_question();
      System.out.println(nq.getText());
    }
  }
}
