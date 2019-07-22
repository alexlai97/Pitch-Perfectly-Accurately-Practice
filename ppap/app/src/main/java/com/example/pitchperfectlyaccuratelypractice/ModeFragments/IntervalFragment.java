package com.example.pitchperfectlyaccuratelypractice.ModeFragments;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.animation.Animation;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.pitchperfectlyaccuratelypractice.FilterPages.PerModeSetting;
import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.FilterPages.FilterActivity;
import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;

/**
 * a children of general fragment
 * it has questionNoteText view, questionIntervalText view
 */
public class IntervalFragment extends GeneralFragment {
    private static String TAG = "IntervalFragment";

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
     * constructor of IntervalFragment
     * setup resource (see parent onCreateView for use)
     */
    public IntervalFragment() {
        resource = R.layout.fragment_interval;
        background_color = Color.parseColor("#c1e6da");
        instruction_string = "Please sing the note plus or minus the interval \n\n" +
                "Single tap the start_playing button will start_playing the base note\n\n" +
                "Long press the start_playing button will start_playing base note and answer note\n\n" +
                "You can select note pool and interval pool in Filter Page (pineapple button)";
    }

    @Override
    public void listenerSetUp() {
        filterPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent filter_intent = new Intent(getActivity(), FilterActivity.class);
                filter_intent.putExtra("Mode", new PerModeSetting("interval"));

                // let the main activity handle the intent
                getActivity().startActivityForResult(filter_intent, MainActivity.REQUEST_CODE_FROM_FILTER); // why this REQUEST_CODE_FROM_FILTER can't be found using getActivity().REQUEST_CODE_FROM_FILTER
            }
        });
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
        char[] charArray = texts[0].toCharArray();
        for (int i = 0; i < charArray.length; ++i) {
            if (i == 0) {
                ((TextView) noteLayout.findViewById(R.id.NoteTextView)).setText("" + charArray[i]);
            } else if (i == 1) {
                ((TextView) noteLayout.findViewById(R.id.NoteScaleTextView)).setText("" + charArray[i]);
            } else if (i == 2) {
                ((TextView) noteLayout.findViewById(R.id.NoteSigView)).setText("" + charArray[i]);
            }
        }
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
