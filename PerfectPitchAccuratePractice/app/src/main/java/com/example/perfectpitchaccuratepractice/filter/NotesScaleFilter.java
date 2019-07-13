package com.example.perfectpitchaccuratepractice.filter;

import com.example.perfectpitchaccuratepractice.bitmap.NotesBitmap;
import com.example.perfectpitchaccuratepractice.note.Note;
import com.example.perfectpitchaccuratepractice.note.NotesScale;

/**
 * A Filter that filters out the bits which does not belong to this pair (key signature, scale)
 *
 * <p>
 * For example, input is C2 - C3, and configuration is (C, major) it will only leave with the notes in that scale
 */
public class NotesScaleFilter extends NotesFilter {
  public NotesScaleFilter(Note key_note, NotesScale scale) {
    this.bitmap = NotesBitmap.getNotesBitmapFromScale(key_note, scale);
  }
}
