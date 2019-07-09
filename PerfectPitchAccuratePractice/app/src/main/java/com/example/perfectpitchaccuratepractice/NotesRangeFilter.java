package com.example.perfectpitchaccuratepractice;
/**
 * A filter that will filter out the notes that does not belong to this range
 */
class NotesRangeFilter extends NotesFilter {
  NotesRangeFilter(Note from_note, Note to_note) {
    this.bitmap = NotesBitmap.getNotesBitmapFromRange(from_note, to_note);
  }
}
