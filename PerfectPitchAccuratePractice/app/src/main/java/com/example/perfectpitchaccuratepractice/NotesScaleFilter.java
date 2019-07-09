class NotesScaleFilter extends NotesFilter {
  NotesScaleFilter(Note key_note, NotesScale scale) {
    this.bitmap = NotesBitmap.getNotesBitmapFromScale(key_note, scale);
  }
}
