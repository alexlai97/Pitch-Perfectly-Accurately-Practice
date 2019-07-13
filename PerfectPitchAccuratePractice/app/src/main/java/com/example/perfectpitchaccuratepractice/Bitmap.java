package com.example.perfectpitchaccuratepractice;

import android.icu.text.SymbolTable;

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
  private int size=-1;



  /**
   * bit wise 'and' operation on two bitmap and return the result Bitmap
   */
  // just a template
  Bitmap bitmapAnd(Bitmap new_bitmap) {
    return new_bitmap;
  }

  /**
   * print bitmap to stdout  (debugging)
   */
  void printBitmap() {
  }

  public static void main() {
  }

}
