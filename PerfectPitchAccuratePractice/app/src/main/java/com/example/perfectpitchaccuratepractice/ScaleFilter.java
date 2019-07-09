class ScaleFilter extends Filter {
  ScaleFilter(Note key_note, NotesScale scale) {
    this.bitmap = NotesBitMap.getBitMapFromScale(key_note, scale);
  }
}
