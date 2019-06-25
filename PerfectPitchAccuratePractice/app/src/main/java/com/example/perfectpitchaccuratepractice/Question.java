package com.example.perfectpitchaccuratepractice;

public abstract class Question {
  String text;
  Note[] candidate_notes;

  Question() {
    this.text = "n/a";
  }

  void set_candidates(Note [] notes) {
    this.candidate_notes = notes;
  }

  void set_candidates_with_range(int lower, int upper) {
    int num = upper - lower + 1;
    candidate_notes = new Note[num];
    for (int i =0; i < num; i++) {
      candidate_notes[i] = new Note(lower + i);
    }
  }

  String getText() {
    return this.text;
  }

  // must set canditates before
  public void generate_random_question() {
  }
}
