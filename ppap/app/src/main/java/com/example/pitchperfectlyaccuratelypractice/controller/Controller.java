package com.example.pitchperfectlyaccuratelypractice.controller;

import android.app.Activity;
import android.graphics.Color;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.util.Log;


import com.example.pitchperfectlyaccuratelypractice.modeFragments.ModeFragment;
import com.example.pitchperfectlyaccuratelypractice.modeFragments.NoteGraphModeFragment;
import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;
import com.example.pitchperfectlyaccuratelypractice.perModeSetting.NoteModeSetting;
import com.example.pitchperfectlyaccuratelypractice.perModeSetting.PerModeSetting;
import com.example.pitchperfectlyaccuratelypractice.question.SongQuestion;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pitchperfectlyaccuratelypractice.data.HistoryData;
import com.example.pitchperfectlyaccuratelypractice.tools.Microphone;
import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.enums.OffTrackLevel;
import com.example.pitchperfectlyaccuratelypractice.model.Config;
import com.example.pitchperfectlyaccuratelypractice.model.Model;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;

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


  /** access to MainActivity public methods */
  private MainActivity mainActivity;
  /** microphone owned by main activity , can set listener to it */
  private Microphone microphone;
  /**  current fragment (will change if model's current question is changed) */
  private ModeFragment curFragment;
  /**  current question (will change if model's current question is changed) */
  private Question curQuestion;
  /**  current config (will change if model's current question is changed) */
  private Config curConfig;
  /**  current mode (will change if model's current question is changed) */
  private Mode curMode = Mode.NotePractice; // Don't need it probably



  /** model owned by main activity */
  private Model model;


  public HistoryData historyData;

  /**
   * a question factory to produce different type of questions
   */
  private QuestionFactory questionFactory = new QuestionFactory();




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
    refreshCurFragment();
    model.addChangeListener(this);
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
    // Forcing data to reset each time
    historyData = new HistoryData(mainActivity, false);
//    historyData.addData(1);
  }


  public Mode getCurMode() {
    return curMode;
  }


  /**
   * get answer frequencies from current question stored in model
   */
  public double[] getExpectedFrequencies() {
    return Note.toFrequencies(curQuestion.getExpectedNotes());
  }

  public Question getCurQuestion() {
    return curQuestion;
  }

  /**
   * generate a random question, update questionTextView
   */
  public void next_question() {
    if (curMode == Mode.SongPractice) {
      curQuestion.next_question(Question.NextQuestionStrategy.InOrder);
    } else {
      curQuestion.next_question(Question.NextQuestionStrategy.Random);
    }
    updateQuestionView();
    correct_mask = new boolean[curQuestion.getExpectedNotes().length];
    Log.d(TAG, "next_question: current length " + curQuestion.getExpectedNotes().length);
    if (curMode == Mode.NoteGraphPractice) {
      ((NoteGraphModeFragment) curFragment).setCurrentExpectedFrequency(curQuestion.getExpectedNotes()[0].getFrequency());
    }
  }

  /**
   * update views related to question
   */
  public void updateQuestionView() {
    curFragment.updateQuestionTexts(curQuestion.getTexts());
  }


  /**
   * what to do when in correct state
   * now it changes background colour
   * // FIXME different mode different colour
   */
  private void show_correct() {
    curFragment.setBackgroundColor(Color.GREEN);
    curFragment.play_answer();
  }


  // FIXME adjust according to closeness
  public void handleAnimation(int speed) {
    arrowAnimation.setDuration(speed);
    curFragment.updateArrowAnimation(arrowAnimation);
  }

  /* ########################################################
      MAIN LOGIC STARTS HERE
    ########################################################
   */

  /**
   * a mask to be applied to arrow texts, also help knowing what questions notes are let to answer
   */
  private boolean[] correct_mask = new boolean[1]; // first would be in note question mode;

  /**
   * return true if all boolean in mask are correct
   * @param mask
   * @return
   */
  private boolean are_all_correct(boolean []mask) {
    for (boolean b: mask) {
        if (!b) return false;
    }
    return true;
  }

  /**
   * if a boolean in correct_mask is true, mask that arrowText to checkmark
   * @param arrowTexts
   * @return
   */
  private String[] apply_mask_to_arrow_texts(String[] arrowTexts) {
    int len;
    if (correct_mask.length != arrowTexts.length) {
      throw new AssertionError("correct_mask's len:" + correct_mask.length + " arrowTexts' len:" + arrowTexts.length);
    }
    len = correct_mask.length;
    String[] results = new String[len];
    for (int i =0; i < len; i ++) {
      results[i] = correct_mask[i]? "✓":arrowTexts[i];
    }
    return results;
  }

  /**
   * return the first InErrorRange in ofls, return -1 if none
   * @param ofls
   * @return
   */
  private int get_indexs_of_who_is_in_error_range(OffTrackLevel[] ofls) {
      for (int i = 0; i < ofls.length; i ++) {
        if (ofls[i] == OffTrackLevel.InErrorRange) return i;
      }
      return -1;
  }

  /** keeps the current frequency */
  private double current_frequency = -2000;
  /** last time pitch enter error range */
  private long t_enter;
  /** if in error range, t_in is now */
  private long t_in;
  /** if out of error range, t_out is now */
  private long t_out;
  /** the moment just when user passes the question (stay in error range for least stable time) */
  private long t_correct;
  /** stores whether is(was) in error range */
  private boolean isInErrorRange = false;
  /** used to setup t's correctly at the first time */
  private boolean firstTimeProcessFreq = true;
  /* used to check how long it has been since the last check */
  private long t_firstStart;
  /** stores whether the user has passed the question */
  private boolean answerCorrect = false;
  /** used to show correct (do things when user passed question) for once */
  private boolean hasShownCorrect = false;

  /** animation */
  private Animation arrowAnimation;
  /** currentAnimeSpeed */
  private int currentAnimeSpeed;


  public void mark_incorrect_question(){
    historyData.addData(curQuestion.getExpectedNotes()[0].getIndex(), false);
  }

  /**
   * process a frequency
   *
   * FIXME when sing correct and immediate switch fragment, the timer continues and change question
   * FIXME animation currently not working in interval mode, why?
   */
  public void processFrequency(double freq) {
    long now = System.currentTimeMillis();
    // assume all in error range at first time
    if (firstTimeProcessFreq) {
      t_enter = now;
      t_in = now;
      t_out = now;
      firstTimeProcessFreq = false;
      t_firstStart = now;
    }

    // setting up variables
    current_frequency = freq;
    double error_allowance_rate = curConfig.get_error_allowance_rate();
    long dT = curConfig.get_least_stable_time_in_milliseconds();

    double[] expected_frequencies = getExpectedFrequencies();
    int num_of_notes = expected_frequencies.length;
    OffTrackLevel[] offTrackLevels = new OffTrackLevel[num_of_notes];
    String[] arrows = new String[num_of_notes];
    for (int i =0; i < num_of_notes; i ++) {
      offTrackLevels[i] = OffTrackLevel.get_OffTrackLevel(expected_frequencies[i], current_frequency, error_allowance_rate);
      arrows[i] = offTrackLevels[i].get_ArrowSuggestion();
    }

    int index_of_who_is_in_error_range = get_indexs_of_who_is_in_error_range(offTrackLevels);

    curFragment.updateFrequencyText(Math.round(current_frequency));
    curFragment.updateCurrentPitchText("" + (new Note(current_frequency)).getText());

    if (answerCorrect)
    // in a showing correct state i.e. arrow is check mark and back ground is urgent colour
    // will show correct for MILLISECONDS_TO_SHOW_CORRECT
    {
      if (!hasShownCorrect) {  // at the beginning of the show correct state
          curFragment.updateArrowTexts(apply_mask_to_arrow_texts(arrows));
          show_correct();
         hasShownCorrect = true;
      } else if (now - t_correct < curConfig.get_milli_seconds_to_show_correct()) { // in show correct state
        // do nothing
      } else { // at the end of the show correct state
          // gets the current answer's first note (No triad yet since we dont know how we want to handle it
          historyData.addData(curQuestion.getExpectedNotes()[0].getIndex(), answerCorrect);
          curFragment.resetBackgroundColor();
          next_question();
          answerCorrect = false;

      }
    } else if (index_of_who_is_in_error_range != -1) { // currently in error range
      t_in = now;
      if (isInErrorRange) { // was in error range
        if ((t_enter > t_out) && (t_in - t_enter > dT)) { // time in error range greater than delta T
            t_correct = now;
            correct_mask[index_of_who_is_in_error_range] = true;
            if (are_all_correct(correct_mask)) {
              answerCorrect = true;
              hasShownCorrect = false;
            }
        } else { // time in error range smaller than delta T
           curFragment.updateArrowTexts(apply_mask_to_arrow_texts(arrows));
        }
      } else { // was out of error range
        t_enter = now;
      }
      isInErrorRange = true;
    } else { // currently not in error range
      isInErrorRange = false;
      t_out = now;
      curFragment.updateArrowTexts(apply_mask_to_arrow_texts(arrows));
    }

    // FIXME when very close to expected frequency, it doesn't work well
    // FIXME didn't generalize for Triad mode
    // FIXME why is it jumping when show correct, kind of annoying
    if (now - t_firstStart > 1000){
      if(offTrackLevels[0] == OffTrackLevel.LittleHigh || offTrackLevels[0] == OffTrackLevel.LittleLow ){
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
      t_firstStart = now;
    }
  }

  /* ########################################################
      MAIN LOGIC ENDS HERE
    ########################################################
   */

  /**
   * refresh current fragment, since current mode is changed
   */
  private void refreshCurFragment() {
    model.setCurrentFragmentUsingCurrentMode();
    FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.flContent, model.getCurrentFragment())
            .addToBackStack(null)
            .commit();
    fragmentManager.executePendingTransactions();
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
        case "filteredResult":
          /* add code here to response filtered result change:
           for example call ModeFragment method to update view or generate next question*/

          break;
        case "currentMode":
          curMode = (Mode) event.getNewValue();
          // FIXME SongPlaying mode doesn't need question
          if (curMode == Mode.SongPlaying) {
            model.setCurrentQuestion(new SongQuestion(model.getSongList().getSong(R.raw.auld_lang_syne)));
          } else if  (curMode == Mode.SongPractice){
          } else {
            model.setCurrentQuestion(questionFactory.create(curMode));
          }
          refreshCurFragment();
          break;
        case "currentFragment":
//          if (event.getOldValue().getClass() == SongPlayingFragment.class && event.getNewValue().getClass() == SongPracticingFragment.class) {
//            return;
//          }
          correct_mask = new boolean[curQuestion.getExpectedNotes().length]; // FIXME move it to somewhere
          curFragment = (ModeFragment) event.getNewValue();
          if (curMode == Mode.NoteGraphPractice) { // FIXME remove it to the constructor of the fragment
            ((NoteGraphModeFragment) curFragment).setCurrentExpectedFrequency(curQuestion.getExpectedNotes()[0].getFrequency());
          }
          break;
      }
  }
}
