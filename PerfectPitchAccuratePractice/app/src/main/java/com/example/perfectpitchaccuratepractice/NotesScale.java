package com.example.perfectpitchaccuratepractice;
/**
 * The scale options in filter page when selecting 'scale'
 */
enum NotesScale {
  None, Major, NaturalMinor, HarmonicMinor, MelodicMinor;

  static String[] getAllNotesScales() {
    NotesScale[] values =  NotesScale.values();
    String[] strings = new String[values.length];
    for (int i =0; i < values.length; i++) {
      strings[i] = values[i].toString();
    }
    return strings;
  }
}
