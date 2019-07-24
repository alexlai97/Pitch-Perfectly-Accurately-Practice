package com.example.pitchperfectlyaccuratelypractice.question;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;


/**
 * A Question in Note practice mode
 */
public class NoteQuestion extends Question {

  /**
   * The note that will be questioned 
   */
  private Note questionNote;

  public NoteQuestion() {
    notePool = Note.getReasonableNotes();
    next_question(NextQuestionStrategy.Random);
  }

  public void next_question(NextQuestionStrategy nextQuestionStrategy) {
    switch (nextQuestionStrategy) {
      case Random:
        int rnd = random.nextInt(notePool.length);
        this.questionNote = notePool[rnd];
        this.texts = new String[] { questionNote.getText()};
        break;
      case InOrder:
        this.questionNote = notePool[inorder_index];
        this.texts = new String[] { questionNote.getText()};
        inorder_index += 1;
        if (inorder_index >= notePool.length) {
          inorder_index = 0;
        }
        break;
      case ReverseOrder:
        this.questionNote = notePool[reverse_order_index];
        this.texts = new String[] { questionNote.getText()};
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
    return new Note[]{questionNote};
  }

  /**
   * test example (ignore moe)
   */
  public static void main(String args[]) {
    NoteQuestion nq = new NoteQuestion();
    Note [] notes = Note.generateNotesWithRange(0,72);
    nq.setNotePool(notes);
    for (int i =0; i < notes.length * 2; i++) {
      nq.next_question(NextQuestionStrategy.ReverseOrder);
      nq.print_question_texts();
    }
  }
}
