package com.example.pitchperfectlyaccuratelypractice.bitmap;


import com.example.pitchperfectlyaccuratelypractice.musicComponent.Interval;

import java.io.Serializable;

/**
 * An abstract class, its children are: NotesBitmap, IntervalsBitmap
 */
public abstract class Bitmap implements Serializable {
  /**
   * A boolean array
   */
  public boolean[] bitmap;
  
  /**
   * the size of boolean array
   */
  private int size=-1;


  public boolean isAllFalse() {
    for (boolean b: bitmap) {
      if (b) return false;
    }
    return true;
  }

  /**
   * abstract method, bit wise 'and' operation on two bitmap and return the result Bitmap
   */
  public abstract Bitmap bitmapAnd(Bitmap new_bitmap);

  /**
   * print bitmap to stdout  (debugging)
   */
  public void printBitmap() {
  }

  public static void main() {
  }


}
