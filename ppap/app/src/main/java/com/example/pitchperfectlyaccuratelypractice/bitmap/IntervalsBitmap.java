package com.example.pitchperfectlyaccuratelypractice.bitmap;

import com.example.pitchperfectlyaccuratelypractice.music.Interval;

/**
 * Bitmap of representing all the intervals
 */
public class IntervalsBitmap extends Bitmap {
  /**
   * size of boolean array 
   */
  private static int size = Interval.NUM_OF_INTERVALS;

  
  /**
   * constructor, default to all 0 in the bitmap
   */
  public IntervalsBitmap() {
    this.bitmap = new boolean[this.size]; // primitive type default to be false

  }

  /**
   * constructor, takes the boolean array as setter
   */
  public IntervalsBitmap(boolean[] bitmap) {
    this.bitmap = bitmap; 
  }

  /**
   * return a IntervalsBitmap given a low Interval and high Interval as parameters
   */
  public static IntervalsBitmap getIntervalsBitmapFromRange(Interval from_interval, Interval to_interval) {
    int from_index = from_interval.getIndex();
    int to_index = to_interval.getIndex();
    IntervalsBitmap ibm = new IntervalsBitmap();
    for (int i = from_index; i <= to_index; i++) {
      ibm.bitmap[i] = true;
    }
    return ibm;
  }

  /**
   * return a IntervalsBitmap of all 1
   */
  public static IntervalsBitmap getAllTrueIntervalsBitmap() {
    return getIntervalsBitmapFromRange(new Interval(0), new Interval(size-1));
  }

  /**
   * print bitmap to stdout  (debugging)
   */
  void printBitmap() {
    for (int i = 0; i < this.size; i++) {
      System.out.println((new Interval(i)).getText() + " " + (this.bitmap[i]? "1":"0") + " ");
    }
    System.out.println();
  }

  /**
   * bit wise 'and' operation on two Intervalsbitmap and return the result IntervalsBitmap
   */
  public IntervalsBitmap bitmapAnd(Bitmap new_bitmap) {
    System.out.println("Interval Bitmap add");
    boolean[] result_bitmap = new boolean [this.size];
    for (int i = 0; i< size; i++) {
      result_bitmap[i] = this.bitmap[i] && new_bitmap.bitmap[i];
    }
    return new IntervalsBitmap(result_bitmap);
  }

  /**
   * A way to use the class
   */
  public static void main(String args[]) {
    IntervalsBitmap m1 = getIntervalsBitmapFromRange(new Interval(Interval.INDEX_LOWER_BOUND+1),new Interval(Interval.INDEX_UPPER_BOUND-1));
    System.out.println("Printing m1");
    m1.printBitmap();
  }
}
