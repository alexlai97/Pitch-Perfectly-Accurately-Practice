package com.example.perfectpitchaccuratepractice;

public abstract class Question {
  String text;
  Note[] candidate_notes;

  public Question() {
    this.text = "n/a";
  }

  public void set_candidates(Note [] notes) {
    this.candidate_notes = notes;
  }

  public void set_candidates_with_range(int lower, int upper) {
    int num = upper - lower + 1;
    candidate_notes = new Note[num];
    for (int i =0; i < num; i++) {
      candidate_notes[i] = new Note(lower + i);
    }
  }

  public String getText() {
    return this.text;
  }

  // must set canditates before
  public void generate_random_question() {
  }
}
