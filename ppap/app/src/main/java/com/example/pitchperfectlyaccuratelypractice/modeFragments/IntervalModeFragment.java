package com.example.pitchperfectlyaccuratelypractice.modeFragments;

import android.graphics.Color;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.R;

/**
 * a children of general fragment
 * it has questionNoteText view, questionIntervalText view
 */
public class IntervalModeFragment extends ModeFragment {
    private static String TAG = "IntervalModeFragment";

    /**
     * question note on top of question interval
     */
    //private TextView questionNoteText;
    /**
     * question interval on botton of question note
     */
    private TextView questionIntervalText;
    private ConstraintLayout noteLayout;

    private TextView arrowText;

    /**
     * constructor of IntervalModeFragment
     * setup resource (see parent onCreateView for use)
     */
    public IntervalModeFragment() {
        mode = Mode.IntervalPractice;
        resource = R.layout.modefragment_interval;
        background_color = Color.parseColor("#c1e6da");
        instruction_string = "Please sing the note plus or minus the interval \n\n" +
                "Single tap the start_playing button will start_playing the base note\n\n" +
                "Long press the start_playing button will start_playing base note and answer note\n\n" +
                "You can select note pool and interval pool in Filter Page (pineapple button)";
    }


    /**
     * set up views of questionNoteText and questionIntervalText
     */
    @Override
    void setupAdditionalView() {
        Log.d(TAG, "setupAdditionalView: ");
        //questionNoteText = constraintLayout.findViewById(R.id.prevNoteTextView);
        //if (questionNoteText == null) {
            //throw new AssertionError("questionNoteText is null");
        //}
        questionIntervalText = constraintLayout.findViewById(R.id.questionIntervalTextView);
        if (questionIntervalText == null) { throw new AssertionError("questionIntervalText is null"); }
        arrowText = constraintLayout.findViewById(R.id.arrowTextView);
        noteLayout = constraintLayout.findViewById(R.id.interval_include);
    }

    /**
     * update questions, question text + interval text
     *
     * @param texts
     */
    @Override
    public void updateQuestionTexts(String [] texts) {
        if (!onCreated) return;
        if (texts.length != 2) {
            throw new AssertionError("expecting texts' length is 2");
        }
        updateSingleNoteText(noteLayout, texts[0]);
        questionIntervalText.setText(texts[1]);
    }

    /**
     * update arrow text views
     * @param arrowTexts
     */
    @Override
    public void updateArrowTexts(String[] arrowTexts){
        if(!onCreated) return;
        arrowText.setText(arrowTexts[0]);
    }

    @Override
    public void updateArrowAnimation(Animation myAnimation){
        if(!onCreated) return;
        arrowText.setAnimation(myAnimation);
    }


}
