package com.example.perfectpitchaccuratepractice;
class IntervalsBitmap extends Bitmap {
  private final static int size = Interval.NUM_OF_INTERVALS;

  IntervalsBitmap() {
    this.bitmap = new boolean[this.size]; // primitive type default to be false

  }

  IntervalsBitmap(boolean[] bitmap) {
    this.bitmap = bitmap; 
  }

  static IntervalsBitmap getIntervalsBitmapFromRange(Interval from_interval, Interval to_interval) {
    int from_index = from_interval.getIndex();
    int to_index = to_interval.getIndex();
    IntervalsBitmap ibm = new IntervalsBitmap();
    for (int i = from_index; i <= to_index; i++) {
      ibm.bitmap[i] = true;
    }
    return ibm;
  }

  static IntervalsBitmap getAllTrueIntervalsBitmap() {
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

  static IntervalsBitmap bitmapAnd(IntervalsBitmap bm1, IntervalsBitmap bm2) {
    boolean[] result_bitmap = new boolean [size];
    for (int i = 0; i< size; i++) {
      result_bitmap[i] = bm1.bitmap[i] && bm2.bitmap[i];
    }
    return new IntervalsBitmap(result_bitmap);
  }

  public static void main(String args[]) {
    IntervalsBitmap m1 = getIntervalsBitmapFromRange(new Interval(Interval.INDEX_LOWER_BOUND+1),new Interval(Interval.INDEX_UPPER_BOUND-1));
    System.out.println("Printing m1");
    m1.printBitmap();
  }
}
