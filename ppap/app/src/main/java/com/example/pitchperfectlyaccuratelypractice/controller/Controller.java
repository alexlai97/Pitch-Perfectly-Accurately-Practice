package com.example.pitchperfectlyaccuratelypractice.controller;

import android.app.Activity;
import android.graphics.Color;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.util.Log;

//import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;
import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;
import com.example.pitchperfectlyaccuratelypractice.tools.Microphone;
import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.enums.OffTrackLevel;
import com.example.pitchperfectlyaccuratelypractice.fragments.GeneralFragment;
import com.example.pitchperfectlyaccuratelypractice.model.Config;
import com.example.pitchperfectlyaccuratelypractice.model.Model;
import com.example.pitchperfectlyaccuratelypractice.music.Note;

import static org.junit.Assert.assertNotNull;

import com.example.pitchperfectlyaccuratelypractice.question.Question;
import com.example.pitchperfectlyaccuratelypractice.question.QuestionFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Stores states and controls views
 */

public class Controller implements Observer ,
        PropertyChangeListener
{
  private static final String TAG = "CONTROLLER";

  private double current_frequency = -2000;

  /**
   * set note pool in current question in model
   * @param notes
   */
  public void setNotePool(Note[] notes) {
    model.setNotePool(notes);
  }

  private Question curQuestion;
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
   * access to MainActivity public methods
   */
  private MainActivity mainActivity;
  /**
   * access to the current fragment public methods
   */
  private GeneralFragment curFragment;

  private Config curConfig;
  private Mode curMode; // Don't need it probably

  private Microphone microphone;

  private Model model;


  private QuestionFactory questionFactory = new QuestionFactory();
  /**
   * setup config, question, activity, textviews, arrowAnimations
   */
  public Controller(Activity activity) {
    mainActivity = (MainActivity)activity;
    model = mainActivity.getModel();
    // generate NoteQuestion
    model.setCurrentQuestion(questionFactory.create(model.getCurrentMode()));
    curQuestion = model.getCurrentQuestion();
    curConfig = model.getCurrentConfig();
    model.addChangeListener(this);
    microphone = mainActivity.getMicrophone();
    microphone.addObserver(this);
    // TODO add property
    curFragment = model.getCurrentFragment();
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
//  public void changeCurrentMode(Mode mode) {
////    curFragment = mainActivity.getCurFragment();
//    Log.v(TAG, mode.toString());
//    model.setCurrentQuestion(questionFactory.create(mode));
//    next_question();
//  }

  /**
   * get answer frequency to the note practice question
   * <p>
   * FIXME only used in note pratice mode, not for future use
   *
   */
  public double[] getExpectedFrequencies() {
    return Note.toFrequencies(curQuestion.getAnswerNotes());
  }

  /**
   * generate a random question, update questionTextView
   */
  public void next_question() {
    curQuestion.generate_random_question();
    curFragment.updateQuestionTexts(curQuestion.getTexts());
  }

  /**
   * update arrowsTextView, can do other things (e.g. change background)
   */
  void show_correct() {
    curFragment.updateArrowText("✓");
    curFragment.setBackgroundColor(Color.GREEN);
  }


  // FIXME adjust according to closeness
  public void handleAnimation(int speed) {
    arrowAnimation.setDuration(speed);
    curFragment.updateArrowAnimation(arrowAnimation);
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
//    curFragment.updateQuestionTexts(curQuestion.getTexts());

    current_frequency = freq;
    curFragment.updateFrequencyText(Math.round(current_frequency), getExpectedFrequencies()[0]);
    curFragment.updateCurrentPitchText("U: " + (new Note(current_frequency)).getText());

    double expected_freq = getExpectedFrequencies()[0];
    double error_allowance_rate = curConfig.get_error_allowance_rate();
    OffTrackLevel ofl = OffTrackLevel.get_OffTrackLevel(expected_freq, current_frequency, error_allowance_rate);
    String arrow = ofl.get_ArrowSuggestion();
    long dT = curConfig.get_least_stable_time_in_milliseconds();

    if (answerCorrect) {
      if (!hasShownCorrect) {
          show_correct();
         hasShownCorrect = true;
      } else if (now - t_correct < MILLISECONDS_TO_SHOW_CORRECT) {
        // do nothing
      } else {
          curFragment.resetBackgroundColor();
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
           curFragment.updateArrowText("...");
        }
      } else { // was out of error range
        t_enter = now;
      }
      isInErrorRange = true;
    } else {
      isInErrorRange = false;
      t_out = now;
      curFragment.updateArrowText(arrow);
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

  @Override
  public void update(Observable observable, Object o) {
    processFrequency((float)o);
  }

  @Override
  public void propertyChange(PropertyChangeEvent event) {
    if (event.getNewValue() == null) {
      throw new AssertionError("property change event object is null");
    }
      switch (event.getPropertyName()) {
        case "currentConfig":
          curConfig = (Config) event.getNewValue();
          break;
        case "currentQuestion":
          curQuestion = (Question) event.getNewValue();
          Log.d(TAG, "propertyChange: Question" + curQuestion.getClass());
          break;
        case "currentMode":
          curMode = (Mode) event.getNewValue();
          break;
        case "currentFragment":
          curFragment = (GeneralFragment) event.getNewValue();
          Log.d(TAG, "propertyChange: Fragment" + curFragment.getClass());
          break;
      }
  }
}
