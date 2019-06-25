public class Model {
  double current_frequency = -2000;
  Question current_question;
  Mode current_Mode;
  Config current_config;
  long t_enter, t_in, t_out;
  boolean isInErrorRange = false;
  boolean firstTimeProcessFreq = true;


  public Model(Config c) {
    current_config = c;
    // generate NoteQuestion 
    current_question = new NoteQuestion();
    current_question.set_candidates_with_range(24,48);
    next_question();
  }


  // set_current_Mode(Mode m):
  // 1. set this.current_Mode
  // 2. set this.current_question
  public void set_current_Mode(Mode m) {
    current_Mode = m;
    switch (current_Mode) {
      case NotePractice:
        next_question();
        break;
      case IntervalPractice: 
        //FIXME not implemented
        // current_question = new IntervalQuestion();
        break;
      case ChordPractice: 
        //FIXME not implemented
        // current_question = new ChordQuestion();
        break;
      case SongPractice: 
        //FIXME not implemented
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

  // TODO next question, or specific to different modes
  void next_question() {
    current_question.generate_random_question();
    System.out.println("Next Question Generated");
    System.out.println("Update view after new Question");
  }

  // TODO
  void show_in_arrow_textbox(String str) {
    System.out.println("Arrow: " + str);
  }

  // Called by run in PitchDetectionHandler
  // things it will do:
  // 1. 
  public void processFrequency(double freq) {
    long now = System.currentTimeMillis();
    // assume all in error range at first time
    if (firstTimeProcessFreq) {
      t_enter = now;
      t_in = now;
      t_out = now;
      firstTimeProcessFreq = false;
    }
    current_frequency = freq;
    double expected_freq = ((NoteQuestion)current_question).getQuestionNote().getFrequency();
    double error_allowance_rate = current_config.get_error_allowance_rate();
    OffTrackLevel ofl = OffTrackLevel.get_OffTrackLevel(expected_freq, current_frequency, error_allowance_rate);
    String arrow = ofl.get_ArrowSuggestion();
    long dT = current_config.get_least_stable_time_in_milliseconds();

    if (ofl == OffTrackLevel.InErrorRange) {
      t_in = now;
      if (isInErrorRange) { // was in error range
        if ((t_enter > t_out) && (t_in - t_enter > dT)) { 
          next_question();
        } else { 
          show_in_arrow_textbox("...");
        }
      } else { // was out
        t_enter = now;
      }
      isInErrorRange = true;
    } else {
      isInErrorRange = false;
      t_out = now;
      show_in_arrow_textbox(arrow);
    }
  }

  // test example
  public static void main(String args[]) {
    Config c = new Config();
    Model m = new Model(c);
  }
}
