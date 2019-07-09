package com.example.perfectpitchaccuratepractice;
import java.util.ArrayList;
class FilterHandler {
  Filter[] filters;
  
  NotesBitMap current_bitmap = NotesBitMap.getFullSetNotesBitMap();

  void setFilters(Filter[] filters) {
    this.filters = filters;
  }

  void applyFilters() {
    for (Filter f: this.filters) {
      current_bitmap = f.applyFilterTo(current_bitmap);
    }
  }

  ArrayList<Note> getResultNotes() {
    return current_bitmap.toNotes();
  }

  public static void main(String args[]) {
    int num_of_filters = 2;
    Filter[] filters  = new Filter[num_of_filters];
    filters[0] = new RangeFilter(new Note("A2"), new Note("C4"));
    filters[1] = new ScaleFilter(new Note("C1"), NotesScale.NaturalMinor);

    FilterHandler fh = new FilterHandler();
    fh.setFilters(filters);
    fh.applyFilters();
    ArrayList<Note> result = fh.getResultNotes();

    for (Note n: result) {
      System.out.println(n.getText());
    }
  }
}
