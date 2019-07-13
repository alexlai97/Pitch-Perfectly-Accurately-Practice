package com.example.pitchperfectlyaccuratelypractice.note;
/**
 * The scale options in filter page when selecting 'scale'
 */
public enum NotesScale {
  None, Major, NaturalMinor, HarmonicMinor, MelodicMinor;

  public static String[] getAllNotesScales() {
    NotesScale[] values =  NotesScale.values();
    String[] strings = new String[values.length];
    for (int i =0; i < values.length; i++) {
      strings[i] = values[i].toString();
    }
    return strings;
  }
}
