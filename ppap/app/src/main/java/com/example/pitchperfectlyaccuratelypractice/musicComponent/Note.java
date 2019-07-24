package com.example.pitchperfectlyaccuratelypractice.musicComponent;
import com.example.pitchperfectlyaccuratelypractice.model.Config;


import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A Note can represent a note (in frequency, and String, and internal index)
 *
 *  <p>
 * Restriction:
 *  <p>
 *   range from A1 to A7 (73 total notes)
 *  <p>
 *
 * Example:
 *  <table border="1">
 *   <tr>
 *     <td> index </td> 
 *  <td> 0  </td> <td> 12 </td> <td> 24 </td> <td> 36 </td> <td> 48 </td> <td> 60 </td> <td> 72 </td>
 *   </tr>
 *   <tr>
 *     <td> frequency (Hz)</td>
 * <td> 55   </td> <td> 110  </td> <td> 220  </td> <td> 440  </td> <td> 880  </td> <td> 1760 </td> <td> 3520 </td> 
 *   </tr>
 *   <tr>
 *     <td> string </td> 
 * <td> A1   </td> <td> A2   </td> <td> A3   </td> <td> A4   </td> <td> A5   </td> <td> A6   </td> <td> A7   </td>
 *   </tr>
 * </table>
 */

public class Note implements Serializable {


  /**
   * internal index where actual range is [0, 72]
   */
  private int index;


  /**
   * number of notes in actual range which is 72+1 = 73
   */
  public final static int NUM_OF_NOTES = 73;

  public final static int INDEX_LOWER_BOUND = 0;
  public final static int INDEX_UPPER_BOUND = NUM_OF_NOTES - 1;
  /**
   * frequency of A1, which is 55 Hz
   */
  private final static double FREQ_OF_A1 = 55.0; // Hz
  /**
   * stores the frequencies from A1 to A7
   */
  private final static double[] frequency_array;

  // initialize frequency_array
  static {
    frequency_array = new double[NUM_OF_NOTES];
    for (int i = 0; i< NUM_OF_NOTES; i++) {
      frequency_array[i] = FREQ_OF_A1 * Math.pow(2, (double)i/12);
    }
  }

  /**
   * Construct A Note with index
   * <p>
   *
   * e.g. i = 0  gives Note A1,
   * <p>
   *      i = 12 gives Note is A2
   *
   */
  public Note(int i) {
    this.setTo(i);
  }

  /**
   * Set Note's index to i
   */
  void setTo(int i) {
    if (index < 0 || index >= NUM_OF_NOTES) {
      throw new AssertionError("index ERROR");
    }
    this.index = i;
  }

  /**
   * Constructing Note via String text (e.g. "A4#")
   * <p>
   * FIXME only support sharp right now
   * <p>
   * only accepting: A A# B C C# D D# E F F# G G# 
   *
   */
  public Note(String text) {
    this.setTo(text);
  }

  private boolean isValidSymbol(char s) { return s >= 'A' && s <= 'G'; }
  private boolean isValidOctave(char o) { return o >= '1' && o <= '7'; }
  /**
   * Set Note's index via String (e.g "A4#")
   * <p>
   * FIXME only support sharp right now
   * <p>
   * only accepting: A A# B C C# D D# E F F# G G# 
   *
   */
  public void setTo(String text) {
    int octave;
    boolean flag;
    int len = text.length();
    if (len < 1 || len >3) {
      throw new AssertionError("setTo length should be in {1,2,3}");
    }
    char symbol = text.charAt(0);
    if (! isValidSymbol(symbol)) {
      throw new AssertionError("symbol not in ['A', 'G']");
    }
    if (len == 1) {
      octave = 1;
      flag = false;
    } else {
      char tmp_char = text.charAt(1);
      if (isValidOctave(tmp_char)) {
        octave = Character.getNumericValue(tmp_char);
        flag = len == 3;
      } else if (tmp_char == '#') {
        octave = 1;
        flag = true;
      } else {
        throw new AssertionError("cannot parse second char in setTo");
      }
    }

    int r = -2000; // To see error if anything goes wrong

    switch (symbol) {
      case 'A':
        r = flag? 1:0;
        break;
      case 'B':
        r = 2;
        break;
      case 'C':
        r = flag? 4:3;
        break;
      case 'D':
        r = flag? 6:5;
        break;
      case 'E':
        r = 7;
        break;
      case 'F':
        r = flag? 9:8;
        break;
      case 'G':
        r = flag? 11:10;
        break;
    }

    if (octave == 1) {
      this.index = r;
    } else if (symbol == 'A' || symbol == 'B') {
      this.index = r + (octave-1) * 12;
    } else {
      this.index = r + (octave-2) * 12;
    }
  }

  /**
   * Construct A Note from frequency
   * <p>
   * Error within a semitone
   */
  public Note(double frequency) {
    if (frequency < Config.LOWEST_RECOGNIZED_FREQ) {
      index = -1;
    } else {
      double tmp = Math.log(frequency / FREQ_OF_A1) / Math.log(2) * 12;
      index = (int) Math.round(tmp);
    }
  }

  /**
   * Return the String representation of Note
   * <p>
   * e.g. Note(1).getText() gives  "A1#"
   * <p>
   * FIXME only sharp representation for now
   */
  public String getText() {
    if (index < 0 || index > 72) { return "??"; }
    int octave;
    int remainder = this.index % 12;
    boolean flag = false;
    if (this.index < 3) {
      octave = 1;
    } else {
      octave = (index - 3) / 12 + 2;
    }
    char symbol = '?';
    switch (remainder) {
      case 0:
        flag = false;
        symbol = 'A';
        break;
      case 1:
        flag = true;
        symbol = 'A';
        break;
      case 2:
        flag = false;
        symbol = 'B';
        break;
      case 3:
        flag = false;
        symbol = 'C';
        break;
      case 4:
        flag = true;
        symbol = 'C';
        break;
      case 5:
        flag = false;
        symbol = 'D';
        break;
      case 6:
        flag = true;
        symbol = 'D';
        break;
      case 7:
        flag = false;
        symbol = 'E';
        break;
      case 8:
        flag = false;
        symbol = 'F';
        break;
      case 9:
        flag = true;
        symbol = 'F';
        break;
      case 10:
        flag = false;
        symbol = 'G';
        break;
      case 11:
        flag = true;
        symbol = 'G';
        break;
    }
    char sharp_symbol = flag? '#':' ';
    return "" + symbol + octave + sharp_symbol;
  }

  /**
   * print all possible key signatures
   * FIXME currenlly only has sharp mode (no flat mode)
   * @return
   */
  public static String[] getAllKeySignatures() {
    return new String[]{"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#" };
  }


  /**
   * get the internal index 
   */
  public int getIndex() {
    return this.index;
  }

  /**
   * get the index given a Note
   */
  public static int getIndex(Note note) {
    return note.getIndex();
  }

  /**
   * get the string given a Note
   */
  public static String getText(Note note) {
    return note.getText();
  }

  /**
   * get the string given an index
   */
  public static String getText(int index) {
    return new Note(index).getText();
  }

  /**
   * get lowest note possible in this class, which is index 0, which is A1
   * @return
   */
  public static Note getLowestNote() {
    return new Note(INDEX_LOWER_BOUND);
  }
  /**
   * get highest note possible in this class, which is index 72, which is A7
   * @return
   */
  public static Note getHighestNote() {
    return new Note(INDEX_UPPER_BOUND);
  }

  /**
   * get index given note string
   */
  public static int getIndex(String str) {
    Note n = new Note(str);
    return n.getIndex();
  }

  /**
   * compute the frequency from internal index
   */
  public double getFrequency() {
    if (index < 0 || index >= NUM_OF_NOTES) {
      throw new AssertionError("index ERROR");
    }
    return frequency_array[this.index];
  }

  /**
   * convert an array of notes to array of frequencies
   * @param notes
   * @return
   */
  public static double[] toFrequencies(Note[] notes) {
      double[] frequencies = new double[notes.length];
      for (int i = 0; i < notes.length; i ++) {
        frequencies[i] = notes[i].getFrequency();
      }
      return frequencies;
  }

  /**
   * generate a set of notes given index range 
   */
  public static Note [] generateNotesWithRange(int from_index, int to_index) {
    int num = to_index - from_index + 1;
    Note notes[] = new Note[num];
    for (int i =0; i < num; i++) {
      notes[i] = new Note(from_index + i);
    }
    return notes;
  }

  /**
   * convert notes to strings
   */
  public static String[] getStringsFromNotes(Note[] notes) {
    String[] strings = new String[notes.length];
    for (int i = 0; i < notes.length; i ++ ) {
      strings[i] = notes[i].getText();
    }
    return strings;
  }

  /**
   * notes to ints, for parsing as intent
   * @param notes
   * @return
   */
  public static int[] NotesToInts(Note[] notes) {
    int [] ints = new int[notes.length];
    for (int i =0; i < notes.length; i++) {
      ints[i] = notes[i].getIndex();
    }
    return ints;
  }

  public static Note[] IntsToNotes(int[] ints) {
    Note [] notes = new Note[ints.length];
    for (int i =0; i < ints.length; i++) {
      notes[i] = new Note(ints[i]);
    }
    return notes;
  }

  /**
   * log the notes
   * @param tag
   * @param notes
   */
  public static void logNotes(String tag, Note[] notes) {
    String[] strings = getStringsFromNotes(notes);
    String text = "";
    for (String s: strings) {
      text += s + ",";
    }
    Log.d(tag, text);
  }

  /**
   * get all Notes
   */
  public static Note[] getAllNotes(){ return generateNotesWithRange(Note.INDEX_LOWER_BOUND, Note.INDEX_UPPER_BOUND); }

  /**
   * get reasonable Notes (set in construtor of Question)
   * Please change it... or put it in config, currently is like male range
   */
  public static Note[] getReasonableNotes(){ return generateNotesWithRange(Note.getIndex("A2"), Note.getIndex("A3")); }

  /**
   * A way to use this class, will print a table of notes
   */
  public static void main(String[] args) {
    System.out.println("index | string | frequency ");

    for (int i= 0; i < NUM_OF_NOTES; i++) {
      Note n = new Note(i);
      String str = n.getText();
      Note n1 = new Note(str);
      assert(n1.getIndex() == i);
      assert(n1.getText() == str);
      System.out.println("  " + n.getIndex() + " | " + str + " | " + n.getFrequency());
    }

    System.out.println("Using generateNotesWithRange");

    Note [] notes = generateNotesWithRange(0, 72);
    String[] strings = getStringsFromNotes(notes);
    assert(notes.length == 73);
    for (int i=0; i< 73; i++) {
      System.out.println("  " + notes[i].getIndex() + " | " + strings[i] + " | " + notes[i].getFrequency());
    }

    Note n = new Note("A#");
    System.out.println(n.getText());
  }

  public static Note[] ArrayListToNotes(ArrayList<Note> noteArrayList) {
    int length = noteArrayList.size();
    Note[] notes = new Note[length];
    for (int i = 0; i < length; i++) {
      notes[i] = noteArrayList.get(i);
    }
    return notes;
  }
}
