package com.example.perfectpitchaccuratepractice;
/*
 * A Note can represent a note (in frequency, and String)
 *
 * Restriction:
 *  - range [A1 - A7] 6 * 12 + 1
 *
 * Example: 
 *  index:     0  12   24  36  48  60   72
 *  Freqency: 55 110  220 440 880 1760 3520 (Hz)
 *  String:   A1  A2   A3  A4  A5  A6   A7
 *
 *
 * Constructors:
 *  Note n = new  Note(1); // A1#
 *  Note n = new  Note("A1#");
 *
 * Setter:
 *  Note n = new Note(1)
 *  n.setTo(1);
 *  n.setTo("A1#");
 *
 * Getters:
 *  n.getText() -> A1#
 *  n.getFrequency() -> 58.27047018976124
 *  n.getIndex() -> 1
 *  
 */
public class Note {
  int index;  // actual range: [0, 72]

  final static int    NUM_OF_NOTES = 73;
  final static double FREQ_OF_A1 = 55.0; // Hz
  final static double[] frequency_array;
  static {
    frequency_array = new double[NUM_OF_NOTES];
    for (int i = 0; i< NUM_OF_NOTES; i++) {
      frequency_array[i] = FREQ_OF_A1 * Math.pow(2, (double)i/12);
    }
  }

  // Constructing Note with i
  // e.g. i = 0  => Note is A1, 
  //      i = 12 => Note is A2
  public Note(int i) {
    this.setTo(i);
  }
  public void setTo(int i) {
    this.index = i;
  }

  // Constructing Note via String text (e.g. "A4#")
  // FIXME only support sharp right now
  // only accepting: A A# B C C# D D# E F F# G G# 
  public Note(String text) {
    this.setTo(text);
  }
  public void setTo(String text) {
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

  /*
   * TODO Do we need to construct with frequency ?
   * public Note(double frequency) {
   * }
   */

  // Return the String representation of Note
  // e.g. Note(1).getText(true) -> "A1#"
  // FIXME only sharp representation for now
  // TODO  Note(1).getText(false) -> "B1b"
  public String getText(boolean sharp) {
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

  // Return the internal index (for debugging purpose)
  public int getIndex() {
    return this.index;
  }

  public double getFrequency() {
    return frequency_array[this.index];
  }


  // test example
  public static void main(String[] args) {
    System.out.println("index | string | frequency ");

    for (int i= 0; i < NUM_OF_NOTES; i++) {
      Note n = new Note(i);
      String str = n.getText(true);
      Note n1 = new Note(str);
      assert(n1.getIndex() == i);
      assert(n1.getText(true) == str);
      System.out.println("  " + n.getIndex() + " | " + str + " | " + n.getFrequency());
    }

    Note n1 = new Note("A3");
    System.out.println(n1.getText(true));
  }
}
