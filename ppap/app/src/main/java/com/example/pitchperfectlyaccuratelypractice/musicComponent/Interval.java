package com.example.pitchperfectlyaccuratelypractice.musicComponent;

import android.util.Log;

import java.io.Serializable;

/**
 * A Interval can represent an interval (in String and interval index)
 * <p>
 * Restriction:
 * <p>
 *  range from -Octave (0) to Octave (24)
 * <p>
 */
public class Interval implements Serializable {
  /**
   * internal index where actual range is [0, 24]
   */
  private int index;

  /**
   * number of intervals: 
   */
  public final static int NUM_OF_INTERVALS = 25;

  public final static int INDEX_LOWER_BOUND = 0;
  public final static int INDEX_UPPER_BOUND = NUM_OF_INTERVALS - 1;

  /**
   * all interval strings, hardcoded
   * <p>
   * TODO to support more flexible encoding 
   */
  private final static String [] INTERVAL_STRINGS = { 
    "U", "m2", "M2", "m3", "M3", "P4", "A4", /* 0-6*/
    "P5", "m6", "M6", "m7", "M7", "P8"  /*7-12*/
  };

  /**
   * a minus sign
   */
  private final static String NEGATIVE_STRING = "- ";

  /**
   * a plus sign
   */
  private final static String POSITIVE_STRING = "+ ";

  /**
   * constructor for Interval from index
   */
  public Interval(int index) {
    this.index = index;
  }

  /**
   * getter for internal index
   */
  public int getIndex() {
    return index;
  }

  public int getRelativeIndex()  { return index - 12; }

  public String getTextWithoutSign() {
    String text = INTERVAL_STRINGS[Math.abs(index-12)];
    return text;
  }


  /**
   * notes to ints, for parsing as intent
   * @param intervals
   * @return
   */
  public static int[] IntervalsToInts(Interval[] intervals) {
    int [] ints = new int[intervals.length];
    for (int i =0; i < intervals.length; i++) {
      ints[i] = intervals[i].getIndex();
    }
    return ints;
  }

  public static Interval[] IntsToIntervals(int[] ints) {
    Interval [] intervals = new Interval[ints.length];
    for (int i =0; i < ints.length; i++) {
      intervals[i] = new Interval(ints[i]);
    }
    return intervals;
  }

  /**
   * getter for the text of the interval
   */
  public String getText() {
    String text = INTERVAL_STRINGS[Math.abs(index-12)];
    return (index - 12<0)? NEGATIVE_STRING + text: POSITIVE_STRING + text;
  }

  /**
   * generate a set of intervals given indexes of a range
   */
  public static Interval [] generateIntervalsWithRange(int from_index, int to_index) {
    int num = to_index - from_index + 1;
    Interval intervals[] = new Interval[num];
    for (int i =0; i < num; i++) {
      intervals[i] = new Interval(from_index + i);
    }
    return intervals;
  }

  public static Interval[] getAllTrueIntervals() {
    return generateIntervalsWithRange(Interval.INDEX_LOWER_BOUND, Interval.INDEX_UPPER_BOUND);
  }

  public static String[] getStringsFromIntervals(Interval[] intervals) {
    String[] strings = new String[intervals.length];
     for (int i =0 ; i < intervals.length; i++ ) {
         strings[i] = intervals[i].getText();
     }
     return strings;
  }

  /**
   * log the notes
   * @param tag
   * @param intervals
   */
  public static void logIntervals(String tag, Interval[] intervals) {
    String[] strings = getStringsFromIntervals(intervals);
    String text = "";
    for (String s: strings) {
      text += s + ",";
    }
    Log.d(tag, text);
  }

  /**
   * test (ignore me)
   */
  public static void main(String args[]) {
    System.out.println("index | text");
    for (int i = Interval.INDEX_LOWER_BOUND; i <= Interval.INDEX_UPPER_BOUND; i++) {
      Interval itv = new Interval (i);
      System.out.println("" + itv.getIndex() + " | " + itv.getText());
    }

    System.out.println();
    System.out.println("index | text");
    Interval [] intervals = generateIntervalsWithRange(Interval.INDEX_LOWER_BOUND, Interval.INDEX_UPPER_BOUND);
    for (int i = Interval.INDEX_LOWER_BOUND; i <= Interval.INDEX_UPPER_BOUND; i++) {
      System.out.println("" + intervals[i].getIndex() + " | " + intervals[i].getText());
    }
  }
}
