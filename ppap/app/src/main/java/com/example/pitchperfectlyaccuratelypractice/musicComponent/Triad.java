package com.example.pitchperfectlyaccuratelypractice.musicComponent;

import java.io.Serializable;
import java.util.Random;

/**
 * Triad is a subset of Chord, it has 3 notes, 4 type of scales
 */
public class Triad implements Serializable {
  /**
   * A static Random
   */
  private static Random random = new Random();

  /**
   * TriadScale: Major, Minor, Diminished, Augmented
   */
  public enum TriadScale {
    Major, Minor, Diminished, Augmented;
  }

  /**
   * return a random TriadScale among those four triad scales
   */
  public static TriadScale getRandomTriadScale() {
    return TriadScale.values()[random.nextInt(TriadScale.values().length)];
  }

  /**
   * NOT IMPLMENTING SO FAR
   */
  public enum TriadInversion { // FIXME not implmented
    None, FirstInversion, SecondInversion;
  }

  /**
   * root note of a triad
   */
  private Note rootNote;
  /**
   * scale of a triad
   */
  private TriadScale scale;
  /**
   * NOT IMPLMENTING SO FAR
   */
  private TriadInversion inversion;
  /**
   * 3 notes stored in side the Triad from low to high
   */
  private Note[] notes; // from low to high

  /**
   * Constructor given root note and triad scale
   */
  public Triad(Note root, TriadScale scale) {

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


  /**
   * get notes array inside Triad
   */
  public Note[] getNotes() {
    return notes;
  }


  /**
   * A way to use this class
   */
  public static void main(String args[]) {
    Triad t = new Triad(new Note("C3"), TriadScale.Diminished);
    Note [] notes = t.getNotes();
    for (int i =0; i< 3; i++) {
      System.out.println(notes[i].getText());
    }
  }
}
