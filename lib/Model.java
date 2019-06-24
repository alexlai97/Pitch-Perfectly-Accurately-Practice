public class Model {
  double current_frequency = -2000;
  Question current_question;
  Mode current_Mode;
  Config current_config;
  long t0;
  boolean isInErrorRange = false;


  public Model(Config c) {
    current_config = c;
    // generate NoteQuestion 
    current_question = new NoteQuestion();
    current_question.set_candidates_with_range(24,48);
    current_question.generate_random_question();
    // TODO update view
  }

  // set_current_Mode(Mode m):
  // 1. set this.current_Mode
  // 2. set this.current_question
  public void set_current_Mode(Mode m) {
    current_Mode = m;
    switch (current_Mode) {
      case NotePractice:
        current_question = new NoteQuestion();
        current_question.set_candidates_with_range(24,48);
        current_question.generate_random_question();
        // TODO update view
        break;
      case IntervalPractice: //FIXME not implemented
        current_question = new IntervalQuestion();
        break;
      case ChordPractice: //FIXME not implemented
        current_question = new ChordQuestion();
        break;
      case SongPractice: //FIXME not implemented
        break;
    }
  }


  // please do:
  // 1. Config cc = model.get_current_config();
  // 2. cc.set_xx_parameter(xxxx);
  // 3. model.set_current_config(cc);
  public void set_current_config(Config c) {
    current_config = c;
  }
  public Config get_current_config() {
    return current_config;
  }

  // Called by run in PitchDetectionHandler
  // things it will do:
  // 1. 
  public void processFrequency(double freq) {
    current_frequency = freq;
    double expected_freq = ((NoteQuestion)current_question).getQuestionNote().getFrequency();
    double error_allowance_rate = current_config.get_error_allowance_rate();
    OffTrackLevel ofl = get_OffTrackLevel(expected_freq, current_frequency, error_allowance_rate);
    String arrow = ofl.get_ArrowSuggestion();
    long dT = current_config.get_least_stable_time_in_milliseconds();

    if (ofl == InErrorRange) {
      if (isInErrorRange) {
        long now = System.currentTimeMillis();
        if (now >= t0 + dT) { 
          // TODO next question
        } else { // FIXME might be logic error for a new question
          t0 = now;
        }
      } else {
        t0 = System.currentTimeMillis();
      }
    } else {
      isInErrorRange = false;
      // TODO show arrow in view
    }
  }

  public static void main(String args[]) {
  }
}
