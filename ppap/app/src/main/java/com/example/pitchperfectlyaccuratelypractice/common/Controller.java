package com.example.pitchperfectlyaccuratelypractice.common;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.util.Log;

//import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;
import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;
import com.example.pitchperfectlyaccuratelypractice.fragments.GeneralFragment;
import com.example.pitchperfectlyaccuratelypractice.note.Note;
import com.example.pitchperfectlyaccuratelypractice.question.IntervalQuestion;
import com.example.pitchperfectlyaccuratelypractice.question.NoteQuestion;
import com.example.pitchperfectlyaccuratelypractice.question.Question;

import static org.junit.Assert.assertNotNull;

import com.example.pitchperfectlyaccuratelypractice.question.QuestionFactory;
import com.example.pitchperfectlyaccuratelypractice.question.TriadQuestion;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;

/**
 * Stores states and controls views
 */

public class Controller {
  private static final String TAG = "MODEL";

  private double current_frequency = -2000;

  /**
   * set note pool in current question in model
   * @param notes
   */
  public void setNotePool(Note[] notes) {
    model.getCurrentQuestion().setNotePool(notes);
  }

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
   * stores MainActivity
   */
  private MainActivity mainActivity;
  /**
   * stores the current fragment
   */
  private GeneralFragment curFragment;

  private Model model;

  private QuestionFactory questionFactory = new QuestionFactory();
  /**
   * setup config, question, activity, textviews, arrowAnimations
   */
  public Controller(Model a_model, Activity activity) {
    model = a_model;
    // generate NoteQuestion
    model.setCurrentQuestion(new NoteQuestion());
  /**
   * setup config, question, activity, textviews, arrowAnimations
   */
    model.setCurrentQuestion(questionFactory.create(model.getCurrentMode()));
    mainActivity = (MainActivity)activity;
    mainActivity.getMicrophone().setVoiceListener(
        new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult res, AudioEvent e) {
                final float frequency = res.getPitch();
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        processFrequency(frequency);
                    }
                });
            }
        });
    curFragment = mainActivity.getCurFragment();
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
  public void changeCurrentMode(Mode mode) {
    curFragment = mainActivity.getCurFragment();
    Log.v(TAG, mode.toString());
    model.setCurrentQuestion(questionFactory.create(mode));
    next_question();
  }

  /**
   * get answer frequency to the note practice question
   * <p>
   * FIXME only used in note pratice mode, not for future use
   *
   */
  public double[] getExpectedFrequencies() {
    return Note.toFrequencies(model.getCurrentQuestion().getAnswerNotes());
  }

  /**
   * generate a random question, update questionTextView
   */
  public void next_question() {
    model.getCurrentQuestion().generate_random_question();
    curFragment.updateQuestionTexts(model.getCurrentQuestion().getTexts());
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
    curFragment.updateQuestionTexts(model.getCurrentQuestion().getTexts());

    current_frequency = freq;
    curFragment.updateFrequencyText(Math.round(current_frequency), getExpectedFrequencies()[0]);
    curFragment.updateCurrentPitchText("U: " + (new Note(current_frequency)).getText());

    double expected_freq = getExpectedFrequencies()[0];
    double error_allowance_rate = model.getConfig().get_error_allowance_rate();
    OffTrackLevel ofl = OffTrackLevel.get_OffTrackLevel(expected_freq, current_frequency, error_allowance_rate);
    String arrow = ofl.get_ArrowSuggestion();
    long dT = model.getConfig().get_least_stable_time_in_milliseconds();

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
}
