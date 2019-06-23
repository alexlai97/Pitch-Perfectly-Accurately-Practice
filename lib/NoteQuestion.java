import java.util.Random;

class NoteQuestion extends Question {
  Note questionNote;

  public void generate_random_question() {
    int rnd = new Random().nextInt(candidate_notes.length);
    questionNote = candidate_notes[rnd];
    text = questionNote.text(true);
  }

  public static void main(String args[]) {
    NoteQuestion nq = new NoteQuestion();
    Note [] notes = { new Note("C2#"), new Note("A2#"), new Note("D3") };
    System.out.println(notes[1].text(true));
    nq.set_candidates(notes);
    nq.generate_random_question();
    System.out.println(nq.text());
  }
}
