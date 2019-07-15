package com.example.pitchperfectlyaccuratelypractice.common;

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
}

