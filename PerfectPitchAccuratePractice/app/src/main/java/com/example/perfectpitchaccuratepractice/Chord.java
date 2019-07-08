package com.example.perfectpitchaccuratepractice;

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
//  int getNumOfNotes(Note[] notes) {
//    return notes.length();
//  }

  /**
   * generatae a chord randomly from notes
   */
  Chord generateRandomChord(Note [] some_notes, int numOfNotesInChord) {
    Random r = new Random();
    Note[] notes_in_chord = new Note[numOfNotesInChord];
    for (int i = 0; i < numOfNotes; i++) {
      int rnd = r.nextInt(some_notes.length);
      notes_in_chord[i] = some_notes[rnd];
    }
    return Chord(notes_in_chord);
  }

  /**
   * get string from chord
   */
  String getText() {
    String text = "";
    for (int i= 0; i< this.notes.length; i++) {
      text += this.notes[i].getText() + " ";
    }
    return text;
  }

  /**
   * test (ignore me)
   */
  public static void main(String args[]) {
  }
}
