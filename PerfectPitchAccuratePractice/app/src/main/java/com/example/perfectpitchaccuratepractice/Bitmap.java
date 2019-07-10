package com.example.perfectpitchaccuratepractice;

/**
 * An abstract class, its children are: NotesBitmap, IntervalsBitmap
 */
abstract class Bitmap {
  /**
   * A boolean array
   */
  boolean[] bitmap; 
  
  /**
   * the size of boolean array
   */
  private final static int size=-1;


  /**
   * bit wise 'and' operation on two bitmap and return the result Bitmap
   */
  // just a template
  static Bitmap bitmapAnd(Bitmap bm1, Bitmap bm2) {
    return bm1;
  }

  /**
   * print bitmap to stdout  (debugging)
   */
  void printBitmap() {

  }
}
