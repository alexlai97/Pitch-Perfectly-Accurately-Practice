package com.example.pitchperfectlyaccuratelypractice.bitmap;

import android.util.Log;

import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;
import com.example.pitchperfectlyaccuratelypractice.enums.NotesScale;

import java.util.ArrayList;


/**
 * Bitmap of representing all the notes
 */
public class NotesBitmap extends Bitmap {
  /**
   * size of boolean array 
   */
  private static int size = Note.NUM_OF_NOTES;

  /**
   * template bitmap for major scale
   */
  final static boolean[] template_Major;
  /**
   * template bitmap for harmonic minor scale
   */
  final static boolean[] template_HarmonicMinor;
  /**
   * template bitmap for natural minor scale
   */
  final static boolean[] template_NaturalMinor;
  /**
   * template bitmap for melodic minor scale
   */
  final static boolean[] template_MelodicMinor;

  final static boolean[] template_None;

  // initializing those templates above
  static {
    template_None = new boolean[]
    { true/*0*/, true/*1*/, true/*2*/, true/*3*/, true/*4*/, true/*5*/, true/*6*/, true/*7*/, true/*8*/, true/*9*/, true/*10*/, true/*11*/ };
    template_Major = new boolean[]
    { true/*0*/, false/*1*/, true/*2*/, false/*3*/, true/*4*/, true/*5*/, false/*6*/, true/*7*/, false/*8*/, true/*9*/, false/*10*/, true/*11*/ };
    template_HarmonicMinor = new boolean[]
    { true/*0*/, false/*1*/, true/*2*/, true/*3*/, false/*4*/, true/*5*/, false/*6*/, true/*7*/, true/*8*/, false/*9*/, false/*10*/, true/*11*/ };
    template_NaturalMinor = new boolean[]
    { true/*0*/, false/*1*/, true/*2*/, true/*3*/, false/*4*/, true/*5*/, false/*6*/, true/*7*/, true/*8*/, false/*9*/, true/*10*/, false/*11*/ };
    template_MelodicMinor = new boolean[]
    { true/*0*/, false/*1*/, true/*2*/, true/*3*/, false/*4*/, true/*5*/, false/*6*/, true/*7*/, false/*8*/, true/*9*/, false/*10*/, true/*11*/ };
  }

  public int getSize() {
    return size;
  }

  /**
   * constructor, default to all 0 in the bitmap
   */
  public NotesBitmap() {
    this.bitmap = new boolean[this.size]; // primitive type default to be false
  }

  /**
   * constructor, takes the boolean array as setter
   */
  public NotesBitmap(boolean[] bitmap) {
    this.bitmap = bitmap;
  }

  public NotesBitmap(int[] arr) {
    assert(arr.length == size);
    Log.d("intsToNoteBitmap", "NotesBitmap: " + arr.length);
    this.bitmap = new boolean[this.size];
    for(int i = 0; i < size; ++i){
      if(arr[i] == 1){
        bitmap[i] = true;
      } else {
        bitmap[i] = false;
      }
    }
  }

  /**
   * construct NotesBitmap given notes
   * @param notes
   */
  public NotesBitmap(Note[] notes) {
    this.bitmap = new boolean[this.size]; // primitive type default to be false

    for (Note n: notes) {
        this.bitmap[n.getIndex()] = true;
    }
  }

  /**
   * toggle note in the bitmap
   * @param note
   */
  public void toggleNote(Note note) {
    int index = note.getIndex();
    this.bitmap[index] = ! this.bitmap[index];
  }


  /**
   * print bitmap to stdout  (debugging)
   */
  public void printBitmap() {
    for (int i = 0; i < this.size; i++) {
      System.out.println((new Note(i)).getText() + " " + (this.bitmap[i]? "1":"0") + " ");
    }
    System.out.println();
  }
  
  /**
   * return a NotesBitmap given a low Note and high Note as parameters
   */
  public static NotesBitmap getNotesBitmapFromRange(Note from_note, Note to_note) {
    int from_index = from_note.getIndex();
    int to_index = to_note.getIndex();
    NotesBitmap nbm = new NotesBitmap();
    for (int i = from_index; i <= to_index; i++) {
      nbm.bitmap[i] = true;
    }
    return nbm;
  }


  /**
   * return a NotesBitmap of all 1
   */
  public static NotesBitmap getAllTrueNotesBitmap() {
    return getNotesBitmapFromRange(new Note(0), new Note(size-1));
  }

  /**
   * apply the template defined above to the entire bitmap and returns the modified bitmap
   */
  private static boolean[] apply_template_to_whole_bitmap(boolean[] template, boolean[] whole, int start_index) {
    for (int i = 0; i < size; i++) {
      whole[i] = template[(72 - start_index+i) % 12];
    }
    return whole;
  }

  /**
   * return a NotesBitmap given key signature note and scale pairs
   */
  public static NotesBitmap getNotesBitmapFromScale(Note key_note, NotesScale scale) {
    NotesBitmap nbm = new NotesBitmap();

    switch (scale) {
      case None:
        nbm.bitmap = apply_template_to_whole_bitmap(template_None, nbm.bitmap, key_note.getIndex());
        break;
      case Major:
        nbm.bitmap = apply_template_to_whole_bitmap(template_Major, nbm.bitmap, key_note.getIndex());
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

  /**
   * bit wise 'and' operation on two Notesbitmap and return the result NotesBitmap
   * FIXME delete it as well as bitmapAnd in Interval
   */
  public NotesBitmap bitmapAnd(Bitmap new_bitmap) {
    boolean[] result_bitmap = new boolean [this.size];
    for (int i = 0; i< size; i++) {
      result_bitmap[i] = this.bitmap[i] && new_bitmap.bitmap[i];
    }
    return new NotesBitmap(result_bitmap);
  }

  public int[] toIntArray(){
    return Note.NotesToInts(this.toNotes());
  }

  /**
   * convert bitmap to array of notes that are true (1) in bitmap, return the array, useful for implementing the buttons in NotesFilterPage
   */
  public Note[] toNotes() {
    ArrayList<Note> notes_arr = new ArrayList<>();
    for (int i = 0; i < this.size; i ++) {
      if (this.bitmap[i]) notes_arr.add(new Note(i));
    }

    return Note.ArrayListToNotes(notes_arr);
  }

  /**
   * A way to use the class
   */
  public static void main(String args[]) {
    NotesBitmap m1 = getNotesBitmapFromRange(new Note(Note.INDEX_LOWER_BOUND),new Note(Note.INDEX_UPPER_BOUND));
    System.out.println("Printing m1");
    m1.printBitmap();
    NotesBitmap m2 = getNotesBitmapFromRange(new Note("A2"),new Note("A2"));
    System.out.println("Printing m2");
    m2.printBitmap();
    NotesBitmap m3 = getNotesBitmapFromScale(new Note("C1"), NotesScale.NaturalMinor);
    System.out.println("C Natural Minor Scale");
    m3.printBitmap();
    NotesBitmap m4 = getNotesBitmapFromScale(new Note("C"), NotesScale.Major);
    System.out.println("C Major Scale");
    m4.printBitmap();
  }
}
