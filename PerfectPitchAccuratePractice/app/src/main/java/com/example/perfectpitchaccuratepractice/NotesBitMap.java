class NotesBitMap {
  private boolean[] bitmap; // size: NUM_OF_NOTES (73)

  final static boolean[] template_major;
  final static boolean[] template_HarmonicMinor;
  final static boolean[] template_NaturalMinor;
  final static boolean[] template_MelodicMinor;

  static {
    template_major = new boolean[]
    { true/*0*/, false/*1*/, true/*2*/, false/*3*/, true/*4*/, true/*5*/, false/*6*/, true/*7*/, false/*8*/, true/*9*/, false/*10*/, true/*11*/ };
    template_HarmonicMinor = new boolean[]
    { true/*0*/, false/*1*/, true/*2*/, true/*3*/, false/*4*/, true/*5*/, false/*6*/, true/*7*/, true/*8*/, false/*9*/, false/*10*/, true/*11*/ };
    template_NaturalMinor = new boolean[]
    { true/*0*/, false/*1*/, true/*2*/, true/*3*/, false/*4*/, true/*5*/, false/*6*/, true/*7*/, true/*8*/, false/*9*/, true/*10*/, false/*11*/ };
    template_MelodicMinor = new boolean[]
    { true/*0*/, false/*1*/, true/*2*/, true/*3*/, false/*4*/, true/*5*/, false/*6*/, true/*7*/, false/*8*/, true/*9*/, false/*10*/, true/*11*/ };
  }

  NotesBitMap() {
    this.bitmap = new boolean[Note.NUM_OF_NOTES]; // primitive type default to be false
  }

  /**
   * print bitmap to stdout  (debugging)
   */
  private void printBitMap() {
    for (int i = 0; i < Note.NUM_OF_NOTES; i++) {
      System.out.println((new Note(i)).getText() + " " + (this.bitmap[i]? "1":"0") + " ");
    }
    System.out.println();
  }

  static NotesBitMap getBitMapFromRange(Note from_note, Note to_note) {
    NotesBitMap nbm = new NotesBitMap();
    for (int i = from_note.getIndex(); i <= to_note.getIndex(); i++) {
      nbm.bitmap[i] = true;
    }
    return nbm;
  }

  static private boolean[] apply_template_to_whole_bitmap(boolean[] template, boolean[] whole, int start_index) {
    for (int i = 0; i < Note.NUM_OF_NOTES; i++) {
      whole[i] = template[(12 - start_index+i) % 12];
    }
    return whole;
  }

  static NotesBitMap getBitMapFromScale(Note key_note, NotesScale scale) {
    NotesBitMap nbm = new NotesBitMap();

    switch (scale) {
      case Major:
        nbm.bitmap = apply_template_to_whole_bitmap(template_major, nbm.bitmap, key_note.getIndex());
        break;
      case NaturalMinor:
        nbm.bitmap = apply_template_to_whole_bitmap(template_NaturalMinor, nbm.bitmap, key_note.getIndex());
        break;
      case HarmonicMinor:
        nbm.bitmap = apply_template_to_whole_bitmap(template_HarmonicMinor, nbm.bitmap, key_note.getIndex());
        break;
      case MelodicMinor:
        nbm.bitmap = apply_template_to_whole_bitmap(template_MelodicMinor, nbm.bitmap, key_note.getIndex());
        break;
    }

    return nbm;
  }

  public static void main(String args[]) {
    NotesBitMap m1 = getBitMapFromRange(new Note(0),new Note(72));
    System.out.println("Printing m1");
    m1.printBitMap();
    NotesBitMap m2 = getBitMapFromRange(new Note(10),new Note(62));
    System.out.println("Printing m2");
    m2.printBitMap();
    NotesBitMap m3 = getBitMapFromScale(new Note("C1"), NotesScale.NaturalMinor);
    System.out.println("C Natural Minor Scale");
    m3.printBitMap();
    NotesBitMap m4 = getBitMapFromScale(new Note("C2"), NotesScale.Major);
    System.out.println("C Major Scale");
    m4.printBitMap();
  }
}
