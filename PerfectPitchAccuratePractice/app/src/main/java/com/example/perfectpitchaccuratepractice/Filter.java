abstract class Filter {
  NotesBitMap bitmap;

  NotesBitMap applyFilterTo(NotesBitMap input_bitmap) {
    return NotesBitMap.bitmapAnd(this.bitmap, input_bitmap);
  }
}
