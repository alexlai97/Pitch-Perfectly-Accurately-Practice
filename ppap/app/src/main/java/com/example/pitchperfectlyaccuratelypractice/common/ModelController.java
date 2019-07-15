package com.example.pitchperfectlyaccuratelypractice.common;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.util.Log;

import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;
//import com.example.pitchperfectlyaccuratelypractice.activities.updateViewInterface;
import com.example.pitchperfectlyaccuratelypractice.note.Note;
import com.example.pitchperfectlyaccuratelypractice.question.IntervalQuestion;
import com.example.pitchperfectlyaccuratelypractice.question.NoteQuestion;
import com.example.pitchperfectlyaccuratelypractice.question.Question;

import static org.junit.Assert.assertNotNull;
import com.example.pitchperfectlyaccuratelypractice.fragments.GeneralFragment;
import com.example.pitchperfectlyaccuratelypractice.question.TriadQuestion;

/**
 * Stores states and controls views
 */

//public class ModelController implements Serializable {
//public class ModelController implements updateViewInterface {
public class ModelController {
  private static final String TAG = "MODEL";

  private double current_frequency = -2000;
  /**
   * Stores current question
   */
  private Question current_question;

  public void setNotePool(Note[] notes) {
    current_question.setNotePool(notes);
  }

  /**
   * Stores current practice mode
   */
  private Mode current_Mode;
  /**
   * Stores current user configuration
   */
  private Config current_config;
  /**
   * last time pitch enter error range
   */
  private long t_enter;
  /**
   * if in error range, t_in is now
   */
  private long t_in; 
  /**
   * if out of error range, t_out is now
   */
  private long t_out;
  /**
   * the moment just when user passes the question (stay in error range for least stable time)
   */
  private long t_correct;
  /**
   * stores whether is(was) in error range
   */
  private boolean isInErrorRange = false;
  /**
   * used to setup t's correctly at the first time
   */
  private boolean firstTimeProcessFreq = true;

  /*
   * used to check how long it has been since the last check
   */
  private long firstStart;

  /**
   * stores whether the user has passed the question
   */
  private boolean answerCorrect = false;
  /**
   * used to show correct (do things when user passed question) for once
   */
  private boolean hasShownCorrect = false;

  /**
   * stores questionTextView
   */
  private TextView questionText;
  /**
   * stores arrowsTextView
   */
  private TextView arrowText;
  /**
   * stores frequencyTextView
   */
  private TextView frequencyText;
  /**
   * stores currentPitchTextView
   */
  private TextView currentPitchText;
  /**
   * stores backgroundView
   * <p>
   * FIXME failed
   */
  private View backGoundView;

  /**
   * animation
   */
  private Animation arrowAnimation;

  /**
   * currentAnimeSpeed
   */
  private int currentAnimeSpeed;

  /**
   * how long between the user pass the question and next question
   */
  private final long MILLISECONDS_TO_SHOW_CORRECT = 2000;

  /**
   * stores MainActivity
   */
  private Activity activity;


//  private updateViewInterface castedActivity;

//  private GeneralFragment curFragment;

  GeneralFragment getCurFragment() {

    return ((MainActivity)activity).getCurFragment();
  }

  /**
   * setup config, question, activity, textviews
   */
  public ModelController(Config c, Activity ac) {
    current_config = c;
    // generate NoteQuestion 
    current_question = new NoteQuestion();
    activity = ac;
//    curFragment = ((MainActivity)ac).getCurFragment();
//    castedActivity = (updateViewInterface) ac;
    arrowAnimation = new TranslateAnimation(
            TranslateAnimation.RELATIVE_TO_SELF, 0f,
            TranslateAnimation.RELATIVE_TO_SELF, 0f,
            TranslateAnimation.RELATIVE_TO_SELF, 0.7f,
            TranslateAnimation.RELATIVE_TO_SELF, 1.2f);
    arrowAnimation.setFillAfter(true);
    currentAnimeSpeed = 0;
    arrowAnimation.setRepeatCount(-1);
    arrowAnimation.setRepeatMode(Animation.REVERSE);
    arrowAnimation.setInterpolator(new LinearInterpolator());
  }


  /**
   * do things when changing pratice mode
   */
  public void changeCurrentMode(Mode m) {
    current_Mode = m;
    switch (current_Mode) {
      case NotePractice:
        current_question = new NoteQuestion();
        next_question();
        break;
      case IntervalPractice:
        current_question = new IntervalQuestion();
        next_question();
        break;
      case TriadPractice:
        current_question = new TriadQuestion();
        next_question();
        break;
      case SongPractice: 
        //FIXME not implemented
        break;
    }
  }

  /**
   * get answer frequency to the note practice question
   * <p>
   * FIXME only used in note pratice mode, not for future use
   *
   */
  public double[] getExpectedFrequencies() {
    return Note.toFrequencies(current_question.getAnswerNotes());
  }


  /**
   * setter for current configuration
   * <p>
   * for best pratice, please do:
   * <p>
   * 1. Config cc = model.get_current_config();
   * <p>
   * 2. cc.set_xx_parameter(xxxx);
   * <p>
   * 3. model.set_current_config(cc);
   */
  public void set_current_config(Config c) {
    current_config = c;
  }
  /**
   * getter for current configuration
   */
  public Config get_current_config() {
    return current_config;
  }

  /**
   * generate a random question, update questionTextView
   */
  public void next_question() {
    current_question.generate_random_question();
    getCurFragment().updateQuestionTexts(current_question.getTexts());
  }

  /**
   * update arrowsTextView, can do other things (e.g. change background)
   */
  void show_correct() {
    getCurFragment().updateArrowText("✓");
//    backGoundView.setBackgroundColor(Color.BLUE);
  }

  // FIXME adjust according to closeness
  public void handleAnimation(int speed) {
    arrowAnimation.setDuration(speed);
    getCurFragment().updateArrowAnimation(arrowAnimation);
  }

  /**
   * process frequency PitchDetectionHandler feeds in and responds with:
   * <p>
   * updating views 
   */
  public void processFrequency(double freq) {
    long now = System.currentTimeMillis();
    // assume all in error range at first time
    if (firstTimeProcessFreq) {
      t_enter = now;
      t_in = now;
      t_out = now;
      firstTimeProcessFreq = false;
      firstStart = now;
    }
    getCurFragment().updateQuestionTexts(current_question.getTexts());

    current_frequency = freq;
    getCurFragment().updateFrequencyText(Math.round(current_frequency), getExpectedFrequencies()[0]);
    getCurFragment().updateCurrentPitchText("U: " + (new Note(current_frequency)).getText());

    double expected_freq = getExpectedFrequencies()[0];
    double error_allowance_rate = current_config.get_error_allowance_rate();
    OffTrackLevel ofl = OffTrackLevel.get_OffTrackLevel(expected_freq, current_frequency, error_allowance_rate);
    String arrow = ofl.get_ArrowSuggestion();
    long dT = current_config.get_least_stable_time_in_milliseconds();

    if (answerCorrect) {
      if (!hasShownCorrect) {
          show_correct();
         hasShownCorrect = true;
      } else if (now - t_correct < MILLISECONDS_TO_SHOW_CORRECT) {
        // do nothing
      } else {
//          backGoundView.setBackgroundColor(Color.GREEN);
          next_question();
          answerCorrect = false;
      }
    } else if (ofl == OffTrackLevel.InErrorRange) {
      t_in = now;
      if (isInErrorRange) { // was in error range
        if ((t_enter > t_out) && (t_in - t_enter > dT)) {
            t_correct = now;
            answerCorrect = true;
            hasShownCorrect = false;
        } else {
           getCurFragment().updateArrowText("...");
        }
      } else { // was out of error range
        t_enter = now;
      }
      isInErrorRange = true;
    } else {
      isInErrorRange = false;
      t_out = now;
      getCurFragment().updateArrowText(arrow);
    }

    if (now - firstStart > 1000){
      if(ofl == OffTrackLevel.LittleHigh || ofl == OffTrackLevel.LittleLow ){
        if(currentAnimeSpeed != 600){
          handleAnimation(600);
          Log.i(TAG,"little");
        }
      }else{
        if(currentAnimeSpeed != 300) {
          handleAnimation(300);
          Log.i(TAG, "ALOT");
        }
      }
      firstStart = now;
    }
  }

//  public void updateFrequencyText(Long freq, Double expectedFreq){
//    getCurFragment().updateFrequencyText(freq, expectedFreq);
//  }
//
//  public void updateArrowText(String myString){
//    getCurFragment().updateArrowText(myString);
//  }
//
//  public void updateCurrentPitchText(String myString){
//    getCurFragment().updateCurrentPitchText(myString);
//  }
//
//  public void updateQuestionTexts(String [] texts){
//    getCurFragment().updateQuestionTexts(texts);
//  }
//
//  public void updateArrowAnimation(Animation myAnimation){
//    getCurFragment().updateArrowAnimation(myAnimation);
//
//  }

}
