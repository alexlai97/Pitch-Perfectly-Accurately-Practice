package com.example.pitchperfectlyaccuratelypractice.filter;

import com.example.pitchperfectlyaccuratelypractice.bitmap.Bitmap;
import com.example.pitchperfectlyaccuratelypractice.bitmap.NotesBitmap;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;
import com.example.pitchperfectlyaccuratelypractice.enums.NotesScale;

/**
 * A convenience class to handle filters
 */
public class FilterHandler {
  /**
   * An array of filters, order should matter (but not matter so far in current filters)
   */
  Filter[] filters;

  /**
   * current data stored in the handler
   */
  Bitmap current_bitmap;

  Bitmap initial_bitmap;

  /**
   * set the current data, should be used at first
   * @param bm
   */
  public void setInitialBitmap(Bitmap bm) {
    initial_bitmap = bm;
  }

  /**
   * set filters in handler
   * @param filters
   */
  public void setFilters(Filter[] filters) {
    this.filters = filters;
  }

  public void updateFilterAt(int i, Filter f) {
    if (i<0 || i > filters.length) {
      throw new AssertionError("invalid index");
    }
    filters[i] = f;
  }

  public FilterHandler() {

  }

  public FilterHandler(Bitmap initialData, Filter[] filters) {
    setInitialBitmap(initialData);
    this.filters = filters;
  }

  /**
   * apply filters in handler
   */
  public void applyFilters() {
      current_bitmap = initial_bitmap;
    for (Filter f: this.filters) {
      current_bitmap = f.applyFilterTo(current_bitmap);
    }
  }

  /**
   * get current data inside handler
   * @return Bitmap
   */
  public Bitmap getResultBitmap() {
    return current_bitmap;
  }

  /**
   * a way to use this class
   */
  public static void main(String args[]) {
    // Filtering Notes
    int num_of_filters = 2;
    Filter[] filters  = new Filter[num_of_filters];
    FilterHandler fh = new FilterHandler();
    /*
    filters[0] = new NotesRangeFilter(new Note("A2"), new Note("C4"));
    filters[1] = new NotesScaleFilter(new Note("C1"), NotesScale.Major);

    NotesBitmap initialData = NotesBitmap.getAllTrueNotesBitmap();
    fh.setInitialBitmap(initialData);
    fh.setFilters(filters);
//    fh.filters[1].bitmap.printBitmap();
    fh.applyFilters();
    Note[] result = ((NotesBitmap) fh.getResultBitmap()).toNotes();

    for (Note n: result) {
      System.out.println(n.getText());
    }

    */

    // Filtering Intervals
    /*
    num_of_filters = 1;
    filters  = new Filter[num_of_filters];
    filters[0] = new IntervalsFilter(IntervalsBitmap.getIntervalsBitmapFromRange(new Interval(1), new Interval(23)));
    fh = new FilterHandler();
    fh.setInitialBitmap(IntervalsBitmap.getAllTrueIntervalsBitmap());
    fh.setFilters(filters);
//    System.out.println(filters.length);
    fh.applyFilters();
    fh.getResultBitmap().printBitmap();
    */

    // test
    System.out.println("testing");
    NotesRangeFilter rangeFilter = new NotesRangeFilter(new Note("A1"), new Note("A6"));
    NotesScaleFilter scaleFilter = new NotesScaleFilter(new Note("C"), NotesScale.HarmonicMinor);
//    scaleFilter.bitmap.printBitmap();
    fh = new FilterHandler(NotesBitmap.getAllTrueNotesBitmap(), new Filter[] { rangeFilter, scaleFilter } );
    filters = new Filter[]{rangeFilter, scaleFilter};
    fh.setFilters(filters);
    fh.applyFilters();
    Note[] notes_result = ((NotesBitmap) fh.getResultBitmap()).toNotes();
    for (Note n: notes_result) {
      System.out.println(n.getText());
    }
  }
}
