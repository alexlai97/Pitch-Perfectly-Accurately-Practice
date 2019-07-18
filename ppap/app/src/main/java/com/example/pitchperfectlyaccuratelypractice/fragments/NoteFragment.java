package com.example.pitchperfectlyaccuratelypractice.fragments;

import android.graphics.Color;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.TextView;

import com.example.pitchperfectlyaccuratelypractice.R;

/**
 * a children of general fragment
 * it has questionNoteText view, questionIntervalText view
 */
public class NoteFragment extends GeneralFragment {

    private static String TAG = "NoteFragment";
    /**
     * a question note in the middle of the screen
     */
    private TextView questionNoteText;
    private TextView arrowText;

    /**
     * constructor of NoteFragment
     * setup resource (see parent onCreateView for use)
     */
    public NoteFragment() {
        resource = R.layout.fragment_note;
        background_color = Color.parseColor("#E6FBBA");
    }
    public void updateArrowAnimation(Animation myAnimation){
        if(!onCreated) return;
        arrowText.setAnimation(myAnimation);
    }

    /**
     * set up views of questionNoteText
     */
    void setupAdditionalView() {
        Log.d(TAG, "setupAdditionalView: ");
        questionNoteText = constraintLayout.findViewById(R.id.questionNoteTextView);
        if (questionNoteText == null) { throw new AssertionError("questionNoteText is null"); }
        arrowText = constraintLayout.findViewById(R.id.arrowTextView);
    }
    /**
     * update arrow text views
     * @param arrowTexts
     */
    public void updateArrowTexts(String[] arrowTexts){
        if(!onCreated) return;
        arrowText.setText(arrowTexts[0]);
    }

    /**
     * update question text
     * @param texts
     */
    public void updateQuestionTexts(String[] texts){
        if(!onCreated) return;
        questionNoteText.setText(texts[0]);
    }

}
