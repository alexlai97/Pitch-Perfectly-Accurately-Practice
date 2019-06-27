
/**
 * A Chord can represent a chord, basically an array of ordered Note 
 */
class Chord {
  /**
   * an array of ordered notes
   */
  Note[] notes;

  /**
   * constructor for chord by an array of Note
   */
  Chord(Note[] notes) {
    this.notes = notes;
  }

  /**
   * getter for the number of notes in the chord
   */
  int getNumOfNotes() {
    return notes.length();
  }

  /**
   * test (ignore me)
   */
  public static void main(String args[]) {
  }
}
