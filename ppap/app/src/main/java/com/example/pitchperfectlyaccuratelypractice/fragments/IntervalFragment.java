package com.example.pitchperfectlyaccuratelypractice.fragments;

import android.util.Log;
import android.widget.TextView;

import com.example.pitchperfectlyaccuratelypractice.R;

/**
 * a children of general fragment
 * it has questionNoteText view, questionIntervalText view
 */
public class IntervalFragment extends GeneralFragment {
    private static String TAG = "IntervalFragment";

    /**
     * question note on top of question interval
     */
    private TextView questionNoteText;
    /**
     * question interval on botton of question note
     */
    private TextView questionIntervalText;

    /**
     * constructor of IntervalFragment
     * setup resource (see parent onCreateView for use)
     */
    public IntervalFragment() {
        resource =R.layout.fragment_interval;
    }

    /**
     * set up views of questionNoteText and questionIntervalText
     */
    void setupAdditionalView() {
        Log.d(TAG, "setupAdditionalView: ");
        questionNoteText = constraintLayout.findViewById(R.id.questionNoteTextView);
        if (questionNoteText == null) { throw new AssertionError("questionNoteText is null"); }
        questionIntervalText = constraintLayout.findViewById(R.id.questionIntervalTextView);
        if (questionIntervalText == null) { throw new AssertionError("questionIntervalText is null"); }
    }

    /**
     * update questions, question text + interval text
     * @param texts
     */
    public void updateQuestionTexts(String [] texts){
        if(!onCreated) return;
        if (texts.length != 2) { throw new AssertionError("expecting texts' length is 2"); }
        questionNoteText.setText(texts[0]);
        questionIntervalText.setText(texts[1]);
    }

}
