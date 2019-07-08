class Triad {
  public enum TriadScale {
    Major, Minor, Diminished, Augmented;
  }

  public enum TriadInversion { // FIXME not implmented
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
    notes[0] = this.rootNote;
    switch (scale) {
      case Major:
        break;
      case Minor:
        break;
      case Diminished:
        break;
      case Augmented:
        break;
    }
  }

  Note[] getNotes() {
    return notes;
  }

  public static void main(String args[]) {
    Triad t = new Triad(new Note("A4"), Minor, FirstInversion);
    Note [] notes = t.getNotes();
    for (int i =0; i< 3; i++) {
      System.out.println(notes[i].getText());
    }
  }
}
