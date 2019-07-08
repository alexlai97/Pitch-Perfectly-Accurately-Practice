package com.example.perfectpitchaccuratepractice;
import java.util.Random;

class Triad {
  private static Random random = new Random();

  enum TriadScale {
    Major, Minor, Diminished, Augmented;

  }
  static TriadScale getRandomTriadScale() {
    return TriadScale.values()[random.nextInt(TriadScale.values().length)];
  }

  enum TriadInversion { // FIXME not implmented
    None, FirstInversion, SecondInversion;
  }

  private Note rootNote;
  private TriadScale scale;
  private TriadInversion inversion;
  private Note[] notes; // from low to high

  Triad(Note root, TriadScale scale) {
    this.rootNote = root;
    this.scale = scale;
    this.inversion = inversion; // FIXME not implmented

    // generate notes[3]
    this.notes = new Note[3];
    this.notes[0] = root;
    int root_index = root.getIndex();
    switch (scale) {
      case Major:
        this.notes[1] = new Note(root_index + 4);
        this.notes[2] = new Note(root_index + 7);
        break;
      case Minor:
        this.notes[1] = new Note(root_index + 3);
        this.notes[2] = new Note(root_index + 7);
        break;
      case Diminished:
        this.notes[1] = new Note(root_index + 3);
        this.notes[2] = new Note(root_index + 6);
        break;
      case Augmented:
        this.notes[1] = new Note(root_index + 4);
        this.notes[2] = new Note(root_index + 8);
        break;
    }
  }

  Note[] getNotes() {
    return notes;
  }


  public static void main(String args[]) {
    Triad t = new Triad(new Note("C3"), TriadScale.Diminished);
    Note [] notes = t.getNotes();
    for (int i =0; i< 3; i++) {
      System.out.println(notes[i].getText());
    }
  }
}
