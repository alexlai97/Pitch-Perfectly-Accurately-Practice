package com.example.perfectpitchaccuratepractice;

/**
 * A filter that defines that cannot pass through
 */
abstract class Filter {
  /**
   * A bitmap can be NotesBitmap or IntervalsBitmap
   */
  Bitmap bitmap;

  /**
   * Apply this filter to an input data, return the output data
   */
  Bitmap applyFilterTo(Bitmap input_bitmap) {
    return bitmap.bitmapAnd(input_bitmap);
  }
}
