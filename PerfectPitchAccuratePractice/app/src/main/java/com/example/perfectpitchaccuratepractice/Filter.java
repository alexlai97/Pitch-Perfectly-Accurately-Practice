abstract class Filter {
  Bitmap bitmap;

  Bitmap applyFilterTo(Bitmap input_bitmap) {
    return Bitmap.bitmapAnd(this.bitmap, input_bitmap);
  }
}
