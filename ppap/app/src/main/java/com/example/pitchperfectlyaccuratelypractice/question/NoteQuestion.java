package com.example.pitchperfectlyaccuratelypractice.question;
import com.example.pitchperfectlyaccuratelypractice.music.Note;


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
        this.questionNote = notePool[index_from_start];
        this.texts = new String[] { questionNote.getText()};
        index_from_start += 1;
        if (index_from_start >= notePool.length) {
          index_from_start = 0;
        }
        break;
      case ReverseOrder:
        this.questionNote = notePool[index_from_end];
        this.texts = new String[] { questionNote.getText()};
        index_from_end -= 1;
        if (index_from_end < 0) {
          index_from_end = notePool.length - 1;
        }
        break;
    }
  }

  /**
   *
   */
  public Note[] getAnswerNotes() {
    Note[] notes = new Note[1];
    notes[0] = questionNote;

    return notes;
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
