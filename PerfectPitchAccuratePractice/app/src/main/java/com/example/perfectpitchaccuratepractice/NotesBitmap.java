package com.example.perfectpitchaccuratepractice;
import java.util.ArrayList;

class NotesBitmap extends Bitmap {
  private final static int size = Note.NUM_OF_NOTES;

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

  NotesBitmap() {
    this.bitmap = new boolean[this.size]; // primitive type default to be false
  }

  NotesBitmap(boolean[] bitmap) {
    this.bitmap = bitmap; 
  }


  /**
   * print bitmap to stdout  (debugging)
   */
  void printBitmap() {
    for (int i = 0; i < this.size; i++) {
      System.out.println((new Note(i)).getText() + " " + (this.bitmap[i]? "1":"0") + " ");
    }
    System.out.println();
  }
  

  static NotesBitmap getNotesBitmapFromRange(Note from_note, Note to_note) {
    int from_index = from_note.getIndex();
    int to_index = to_note.getIndex();
    NotesBitmap nbm = new NotesBitmap();
    for (int i = from_index; i <= to_index; i++) {
      nbm.bitmap[i] = true;
    }
    return nbm;
  }

  static NotesBitmap getAllTrueNotesBitmap() {
    return getNotesBitmapFromRange(new Note(0), new Note(size-1));
  }

  private static boolean[] apply_template_to_whole_bitmap(boolean[] template, boolean[] whole, int start_index) {
    for (int i = 0; i < size; i++) {
      whole[i] = template[(12 - start_index+i) % 12];
    }
    return whole;
  }

  static NotesBitmap getNotesBitmapFromScale(Note key_note, NotesScale scale) {
    NotesBitmap nbm = new NotesBitmap();

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

  static NotesBitmap bitmapAnd(NotesBitmap bm1, NotesBitmap bm2) {
    boolean[] result_bitmap = new boolean [size];
    for (int i = 0; i< size; i++) {
      result_bitmap[i] = bm1.bitmap[i] && bm2.bitmap[i];
    }
    return new NotesBitmap(result_bitmap);
  }

  ArrayList<Note> toNotes() {
    ArrayList<Note> notes = new ArrayList<Note>();

    for (int i = 0; i < this.size; i ++) {
      if (this.bitmap[i]) notes.add(new Note(i));
    }

    return notes;
  }

  public static void main(String args[]) {
    NotesBitmap m1 = getNotesBitmapFromRange(new Note(Note.INDEX_LOWER_BOUND),new Note(Note.INDEX_UPPER_BOUND));
    System.out.println("Printing m1");
    m1.printBitmap();
    NotesBitmap m2 = getNotesBitmapFromRange(new Note(10),new Note(62));
    System.out.println("Printing m2");
    m2.printBitmap();
    NotesBitmap m3 = getNotesBitmapFromScale(new Note("C1"), NotesScale.NaturalMinor);
    System.out.println("C Natural Minor Scale");
    m3.printBitmap();
    NotesBitmap m4 = getNotesBitmapFromScale(new Note("C2"), NotesScale.Major);
    System.out.println("C Major Scale");
    m4.printBitmap();
  }
}
