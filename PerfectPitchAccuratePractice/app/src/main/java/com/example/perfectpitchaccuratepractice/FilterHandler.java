import java.util.ArrayList;
class FilterHandler {
  Filter[] filters;
  
  Bitmap current_bitmap;

  void setInitialBitmap(Bitmap bm) {
    current_bitmap = bm;
  }

  void setFilters(Filter[] filters) {
    this.filters = filters;
  }

  void applyFilters() {
    for (Filter f: this.filters) {
      current_bitmap = f.applyFilterTo(current_bitmap);
    }
  }

  Bitmap getResultBitmap() {
    return current_bitmap;
  }

  public static void main(String args[]) {
    // Filtering Notes
    int num_of_filters = 2;
    Filter[] filters  = new Filter[num_of_filters];
    filters[0] = new NotesRangeFilter(new Note("A2"), new Note("C4"));
    filters[1] = new NotesScaleFilter(new Note("C1"), NotesScale.Major);

    FilterHandler fh = new FilterHandler();
    fh.setInitialBitmap(NotesBitmap.getAllTrueNotesBitmap());
    fh.setFilters(filters);
    fh.applyFilters();
    ArrayList<Note> result = ((NotesBitmap) fh.getResultBitmap()).toNotes();

    for (Note n: result) {
      System.out.println(n.getText());
    }


    // Filtering Intervals
    num_of_filters = 1;
    filters  = new Filter[num_of_filters];
    filters[0] = new IntervalsFilter(IntervalsBitmap.getIntervalsBitmapFromRange(new Interval(1), new Interval(23)));
    fh = new FilterHandler();
    fh.setInitialBitmap(IntervalsBitmap.getAllTrueIntervalsBitmap());
    fh.setFilters(filters);
    fh.applyFilters();
    ((IntervalsBitmap) fh.getResultBitmap()).printBitmap();
  }
}
