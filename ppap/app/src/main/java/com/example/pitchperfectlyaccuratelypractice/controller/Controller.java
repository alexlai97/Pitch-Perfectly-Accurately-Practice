package com.example.pitchperfectlyaccuratelypractice.controller;

import android.app.Activity;
import android.graphics.Color;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.util.Log;

//import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;
import androidx.fragment.app.FragmentManager;

import com.example.pitchperfectlyaccuratelypractice.R;
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
 * Listen to views, model changes
 * Can update view, modify model
 */
public class Controller implements Observer ,
        PropertyChangeListener
{
  private static final String TAG = "CONTROLLER";

  private double current_frequency = -2000;
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
   * microphone owned by main activity
   * can set listener to it
   */
  private Microphone microphone;
  /**
   *  current fragment (will change if model's current question is changed)
   */
  private GeneralFragment curFragment;
  /**
   *  current question (will change if model's current question is changed)
   */
  private Question curQuestion;
  /**
   *  current config (will change if model's current question is changed)
   */
  private Config curConfig;
  /**
   *  current mode (will change if model's current question is changed)
   */
  private Mode curMode; // Don't need it probably
  /**
   * model owned by main activity
   */
  private Model model;

  /**
   * a question factory to produce different type of questions
   */
  private QuestionFactory questionFactory = new QuestionFactory();

  /**
   * set note pool in current question in model
   * @param notes
   */
  public void setNotePool(Note[] notes) {
    model.setNotePool(notes);
  }

  /**
   *
   */
  public Controller(Model a_model, Activity activity) {
    mainActivity = (MainActivity)activity;
    model = a_model;
    // generate NoteQuestion
    model.setCurrentQuestion(questionFactory.create(model.getCurrentMode()));
    curQuestion = model.getCurrentQuestion();
    curConfig = model.getCurrentConfig();
    model.addChangeListener(this);    // add this class as an observer of model
    refreshCurFragment();
    microphone = mainActivity.getMicrophone();
    microphone.addObserver(this);
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
   * get answer frequencies from current question stored in model
   */
  public double[] getExpectedFrequencies() {
    return Note.toFrequencies(curQuestion.getAnswerNotes());
  }

  /**
   * generate a random question, update questionTextView
   */
  public void next_question() {
    curQuestion.generate_random_question();
    updateQuestionView();
  }

  /**
   * update views related to question
   */
  private void updateQuestionView() {
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
   * process a frequency
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
    updateQuestionView(); // FIXME it is here because for the first question, it doesn't update view (maybe asynchronous problem)

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

  /**
   * refresh current fragment, since current mode is changed
   */
  private void refreshCurFragment() {
    model.refreshCurrentFragment();
    mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.flContent, model.getCurrentFragment()).commit();
    mainActivity.getSupportFragmentManager().executePendingTransactions();
  }

  /**
   * frequency updates from microphone
   * @param observable
   * @param o
   */
  @Override
  public void update(Observable observable, Object o) {
    processFrequency((float)o);
  }

  /**
   * updates from model, this is called when notified by model change
   * @param event
   */
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
          break;
        case "currentMode":
          curMode = (Mode) event.getNewValue();
          model.setCurrentQuestion(questionFactory.create(curMode));
          refreshCurFragment();
          break;
        case "currentFragment":
          curFragment = (GeneralFragment) event.getNewValue();
          Log.d(TAG, "propertyChange: Fragment" + curFragment.getClass());
          break;
      }
  }
}
