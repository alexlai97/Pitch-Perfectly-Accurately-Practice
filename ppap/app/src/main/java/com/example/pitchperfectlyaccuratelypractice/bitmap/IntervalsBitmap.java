package com.example.pitchperfectlyaccuratelypractice.bitmap;

import android.util.Log;

import com.example.pitchperfectlyaccuratelypractice.musicComponent.Interval;

import java.util.ArrayList;

/**
 * Bitmap of representing all the intervals
 */
public class IntervalsBitmap extends Bitmap {

  private static final String TAG = "IntervalsBitmap";
  /**
   * size of boolean array 
   */
  private static final int size = Interval.NUM_OF_INTERVALS;

  
  /**
   * constructor, default to all 0 in the bitmap
   */
  public IntervalsBitmap() {
    this.bitmap = new boolean[this.size]; // primitive type default to be false

  }

  public IntervalsBitmap(int[] arr) {
    assert(arr.length == size);
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
   * constructor, takes the boolean array as setter
   */
  public IntervalsBitmap(boolean[] bitmap) {
    this.bitmap = bitmap; 
  }


  /**
   * construct NotesBitmap given notes
   * @param intervals
   */
  public IntervalsBitmap(Interval[] intervals) {
    this.bitmap = new boolean[size]; // primitive type default to be false

    for (Interval n: intervals) {
      this.bitmap[n.getIndex()] = true;
    }
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
   * toggle interval in the bitmap
   * @param interval
   */
  public void toggleNote(Interval interval) {
    int index = interval.getIndex();
    this.bitmap[index] = ! this.bitmap[index];
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
  public void printBitmap() {
    for (int i = 0; i < size; i++) {
      System.out.println((new Interval(i)).getText() + " " + (this.bitmap[i]? "1":"0") + " ");
    }
    System.out.println();
  }

  /**
   * convert bitmap to array of intervals that are true in bitmap, return the array
   */
  public Interval[] toIntervals() {
    ArrayList<Interval> intervals_arr = new ArrayList<>();

    for (int i = 0; i < size; i ++) {
      if (this.bitmap[i]) intervals_arr.add(new Interval(i));
    }

    int length = intervals_arr.size();
//    Log.d(TAG, "toIntervals: " + length);
    Interval[] intervals = new Interval[length];
    for (int i = 0; i < length; i++) {
      intervals[i] = intervals_arr.get(i);
    }

    return intervals;
  }

  /**
   * bit wise 'and' operation on two Intervalsbitmap and return the result IntervalsBitmap
   */
  public IntervalsBitmap bitmapAnd(Bitmap new_bitmap) {
    System.out.println("Interval Bitmap add");
    boolean[] result_bitmap = new boolean [size];
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
    for (Interval i: m1.toIntervals()) {
      System.out.println(i.getText());
    }
  }
}
