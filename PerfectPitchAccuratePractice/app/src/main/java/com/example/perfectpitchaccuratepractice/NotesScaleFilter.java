package com.example.perfectpitchaccuratepractice;
/**
 * A Filter that filters out the bits which does not belong to this pair (key signature, scale)
 *
 * <p>
 * For example, input is C2 - C3, and configuration is (C, major) it will only leave with the notes in that scale
 */
class NotesScaleFilter extends NotesFilter {
  NotesScaleFilter(Note key_note, NotesScale scale) {
    this.bitmap = NotesBitmap.getNotesBitmapFromScale(key_note, scale);
  }
}
