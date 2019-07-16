package com.example.pitchperfectlyaccuratelypractice.note;
/**
 * The scale options in filter page when selecting 'scale'
 */
public enum NotesScale {
  /**
   * no scale. all notes
   */
  None,
  /**
   * major  scale
   */
  Major,
  /**
   * natural minor scale
   */
  NaturalMinor,
  /**
   * harmonic minor scale
   */
  HarmonicMinor,
  /**
   * melodic minor scale
   */
  MelodicMinor;

  /**
   * get all NotesScales, which is {"None", "Major", "NaturalMinor", "HarmonicMinor", "MelodicMinor"}
   * @return
   */
  public static String[] getAllNotesScales() {
    NotesScale[] values =  NotesScale.values();
    String[] strings = new String[values.length];
    for (int i =0; i < values.length; i++) {
      strings[i] = values[i].toString();
    }
    return strings;
  }
}
