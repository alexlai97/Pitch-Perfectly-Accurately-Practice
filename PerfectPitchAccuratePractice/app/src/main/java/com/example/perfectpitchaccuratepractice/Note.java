package com.example.perfectpitchaccuratepractice;

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

class Note {
  

  /**
   * internal index where actual range is [0, 72]
   */
  private int index;  


  /**
   * number of notes in actual range which is 72+1 = 73
   */
  final static int NUM_OF_NOTES = 73;

  final static int INDEX_LOWER_BOUND = 0;
  final static int INDEX_UPPER_BOUND = NUM_OF_NOTES - 1;
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
  Note(int i) {
    this.setTo(i);
  }

  /**
   * Set Note's index to i
   */
  void setTo(int i) {
    assert(i >= 0 && i < NUM_OF_NOTES);
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
  Note(String text) {
    this.setTo(text);
  }

  /**
   * Set Note's index via String (e.g "A4#")
   * <p>
   * FIXME only support sharp right now
   * <p>
   * only accepting: A A# B C C# D D# E F F# G G# 
   *
   */
  void setTo(String text) {
    int len = text.length();
    assert (len == 2 || len == 3);
    boolean flag = len == 3? true: false;
    char tmp_char = text.charAt(1);
    int octave = Character.getNumericValue(tmp_char);
    char symbol = text.charAt(0);
    assert(symbol >= 'A' && symbol <= 'G');

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
  Note(double frequency) {
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
  String getText() {
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
   * get the internal index 
   */
  int getIndex() {
    return this.index;
  }

  /**
   * 
   */
  static int getIndex(Note note) {
    return note.getIndex();
  }

  /**
   * 
   */
  static int getIndex(String str) {
    Note n = new Note(str);
    return n.getIndex();
  }

  /**
   * compute the frequency from internal index
   */
  double getFrequency() {
    assert(index >= 0 && index < NUM_OF_NOTES);
    return frequency_array[this.index];
  }

  /**
   * generate a set of notes given index range 
   */
  static Note [] generateNotesWithRange(int from_index, int to_index) {
    int num = to_index - from_index + 1;
    Note notes[] = new Note[num];
    for (int i =0; i < num; i++) {
      notes[i] = new Note(from_index + i);
    }
    return notes;
  }

  /**
   * test (ignore me)
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
    assert(notes.length == 73);
    for (int i=0; i< 73; i++) {
      System.out.println("  " + notes[i].getIndex() + " | " + notes[i].getText() + " | " + notes[i].getFrequency());
    }
  }
}
