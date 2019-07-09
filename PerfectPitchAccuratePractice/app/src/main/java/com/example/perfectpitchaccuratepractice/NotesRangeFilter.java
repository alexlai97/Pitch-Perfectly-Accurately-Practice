class NotesRangeFilter extends NotesFilter {
  NotesRangeFilter(Note from_note, Note to_note) {
    this.bitmap = NotesBitmap.getNotesBitmapFromRange(from_note, to_note);
  }
}
