package com.example.perfectpitchaccuratepractice;
/**
 * A Interval can represent an interval (in String and interval index)
 * <p>
 * Restriction:
 * <p>
 *  range from -Octave (-12) to Octave (12)
 * <p>
 * When index is non-negative
 * <table border="1">
 * <tr>
 *  <td> index </td>
 * <td>0</td> <td>1</td> <td>2</td> <td>3</td> <td>4</td> <td>5</td> <td>6</td> <td>7</td> <td>8</td> <td>9</td> <td>10</td> <td>11</td> <td>12</td> 
 * </tr>
 * <tr>
 * <td> string </td> 
 * <td>U</td> <td>m2</td> <td>M2</td> <td>m3</td> <td>M3</td> <td>P4</td> <td>A4</td> <td>P5</td> <td>m6</td> <td>M6</td> <td>m7</td> <td>M7</td> <td>O</td>
 * </tr>
 * </table>
 *
 */
public class Interval {
  /**
   * internal index where actuall range is [-12, 12]
   */
  private int index;

  /**
   * all interval strings, hardcoded
   * <p>
   * TODO to support more flexible encoding 
   */
  private final static String [] INTERVAL_STRINGS = { 
    "U", "m2", "M3", "m3", "M3", "P4", "A4", /* 0-6*/
    "P5", "m6", "M6", "m7", "M7", "O"  /*7-12*/
  };

  /**
   * just store a string to indicate it is a minus
   */
  private final static String NEGATIVE_STRING = "- ";

  /**
   * constructor for Inteval from index
   */
  Interval(int index) {
    this.index = index;
  }

  /**
   * getter for internal index
   */
  int getIndex() {
    return index;
  }

  /**
   * getter for the text of the interval
   */
  String getText() {
    String text = INTERVAL_STRINGS[Math.abs(index)];
    return (index <0)? NEGATIVE_STRING + text:text;
  }

  /**
   * generate a set of intervals given lower, upper range indexs
   */
  static Note [] generateIntervalsWithRange(int from_index, int to_index) {
    int num = upper - lower + 1;
    intervals = new Interval[num];
    for (int i =0; i < num; i++) {
      intervals[i] = new Interval(lower + i);
    }
    return notes;
  }


  /**
   * test (ignore me)
   */
  public static void main(String args[]) {
    System.out.println("index | text");
    for (int i = -12; i <= 12; i++) {
      Interval itv = new Interval (i);
      System.out.println("" + itv.getIndex() + " " + itv.getText());
    }
  }
}
