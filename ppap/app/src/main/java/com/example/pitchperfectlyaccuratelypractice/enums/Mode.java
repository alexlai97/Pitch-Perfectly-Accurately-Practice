package com.example.pitchperfectlyaccuratelypractice.enums;

import com.example.pitchperfectlyaccuratelypractice.R;

/**
 * Different practice modes
 */
public enum Mode {
  /**
   * where you can practice to sing the note on the screen
   */
  NotePractice,
  /**
   * where you need to calculate the answer note (given the question note and question interval)  by yourself and sing that note
   */
  IntervalPractice,
  /**
   * where you can practice singing triad
   */
  TriadPractice,
  /**
   * TODO not implmented
   */
  SongPractice,
  /**
   * where you can see the real time pitch you are producing
   */
  NoteGraphPractice;

  public static Mode idToMode(int id) {
    switch (id) {
      case R.id.note_mode:
        return NotePractice;
      case R.id.interval_mode:
        return IntervalPractice;
      case R.id.triad_mode:
        return TriadPractice;
      case R.id.notegraph_mode:
        return NoteGraphPractice;
      case R.id.song_mode:
        return SongPractice;
      default:
        return NotePractice;
    }
  }
}

