
public abstract class Question {
  String text;
  Note[] candidate_notes;

  public Question() {
    this.text = "n/a";
  }

  public void set_candidates(Note [] notes) {
    this.candidate_notes = notes;
  }

  public String getText() {
    return this.text;
  }

  // must set canditates before
  public void generate_random_question() {
  }
}
