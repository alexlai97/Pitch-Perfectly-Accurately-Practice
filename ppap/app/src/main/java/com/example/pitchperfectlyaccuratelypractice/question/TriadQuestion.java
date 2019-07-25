package com.example.pitchperfectlyaccuratelypractice.question;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Triad;


/**
 * A Question in Triad practice mode
 */
public class TriadQuestion extends Question{

  /**
   * The triad that will be questioned 
   */
  Triad questionTriad;

  Note root;


  public TriadQuestion() {
    notePool = Note.getReasonableNotes();
    next_question(NextQuestionStrategy.Random);
  }


  public void setup_random_triad() {
//    int rnd = random.nextInt(notePool.length);
//    root = notePool[rnd];
    this.questionTriad = new Triad(root, Triad.getRandomTriadScale());
    Note[] triad_notes = this.questionTriad.getNotes();
    this.texts = new String[3];
    for (int i =0; i< 3; i++) {
      this.texts[i] = triad_notes[i].getText();
    }
  }

  public void next_question(NextQuestionStrategy nextQuestionStrategy) {
    switch (nextQuestionStrategy) {
      case Random:
        int rnd = random.nextInt(notePool.length);
        this.root = notePool[rnd];
        setup_random_triad();
        break;
      case InOrder:
        this.root = notePool[inorder_index];
        setup_random_triad();
        inorder_index += 1;
        if (inorder_index >= notePool.length) {
          inorder_index = 0;
        }
        break;
      case ReverseOrder:
        this.root = notePool[reverse_order_index];
        setup_random_triad();
        reverse_order_index -= 1;
        if (reverse_order_index < 0) {
          reverse_order_index = notePool.length - 1;
        }
        break;
    }
  }

  /**
   *
   */
  public Note[] getExpectedNotes() {
    return questionTriad.getNotes();
  }


  /**
   * a way to use this class
   */
  public static void main(String args[]) {
    TriadQuestion tq = new TriadQuestion();
    tq.setNotePool(Note.generateNotesWithRange(24,36));

    int some_num = 100;
    System.out.println("Printing " + some_num + " random triad questions");
    for (int i = 0; i < some_num; i++) {
      tq.next_question(NextQuestionStrategy.ReverseOrder);
      tq.print_question_texts();
    }
  }
}
