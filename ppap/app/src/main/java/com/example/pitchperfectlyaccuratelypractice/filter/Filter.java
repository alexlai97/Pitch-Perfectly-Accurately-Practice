package com.example.pitchperfectlyaccuratelypractice.filter;

import com.example.pitchperfectlyaccuratelypractice.bitmap.Bitmap;

/**
 * A filter that defines that cannot pass through
 */
public abstract class Filter {
  /**
   * A bitmap can be NotesBitmap or IntervalsBitmap
   */
  Bitmap bitmap;

  /**
   * Apply this filter to an input data, return the output data
   */
  public Bitmap applyFilterTo(Bitmap input_bitmap) {
    return bitmap.bitmapAnd(input_bitmap);
  }
}
