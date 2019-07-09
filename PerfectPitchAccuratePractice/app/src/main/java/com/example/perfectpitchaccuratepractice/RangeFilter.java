package com.example.perfectpitchaccuratepractice;
class RangeFilter extends Filter {
  RangeFilter(Note from_note, Note to_note) {
    this.bitmap = NotesBitMap.getBitMapFromRange(from_note, to_note);
  }
}
