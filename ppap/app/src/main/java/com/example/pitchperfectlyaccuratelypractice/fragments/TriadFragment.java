package com.example.pitchperfectlyaccuratelypractice.fragments;


import android.graphics.Color;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.TextView;

import com.example.pitchperfectlyaccuratelypractice.R;

/**
 * a children of general fragment
 * it has a three question note view, (base, middle, soprano note)
 */
public class TriadFragment extends GeneralFragment {
    private static String TAG = "TriadFragment";

    /**
     * question Note on the bottom
     */
    private TextView questionTriadBaseNoteText;
    private TextView baseNoteArrowText;
    /**
     * question Note in the middle
     */
    private TextView questionTriadMiddleNoteText;
    private TextView middleNoteArrowText;
    /**
     * question Note on top of three
     */
    private TextView questionTriadSopranoNoteText;
    private TextView sopranoNoteArrowText;

    /**
     * constructor of TriadFragment
     * setup resource (see parent onCreateView for use)
     */
    public TriadFragment() {
        resource = R.layout.fragment_triad;
        background_color = Color.parseColor("#C2DFEE");
    }

    /**
     * set up views of the three triad notes
     */
    @Override
    void setupAdditionalView() {
        Log.d(TAG, "setupAdditionalView: ");
        questionTriadBaseNoteText = constraintLayout.findViewById(R.id.triadBaseNoteTextView);
        questionTriadMiddleNoteText = constraintLayout.findViewById(R.id.triadMiddleNoteTextView);
        questionTriadSopranoNoteText = constraintLayout.findViewById(R.id.triadSopranoNoteTextView);
        baseNoteArrowText = constraintLayout.findViewById(R.id.baseNotearrowTextView);
        middleNoteArrowText = constraintLayout.findViewById(R.id.middleNotearrowTextView);
        sopranoNoteArrowText = constraintLayout.findViewById(R.id.sopranoNotearrowTextView);

        if (questionTriadBaseNoteText == null || questionTriadMiddleNoteText == null||questionTriadSopranoNoteText == null) {
            throw new AssertionError("triadFragment some view  is null");
        }
    }

    /**
     * update arrow text views
     * @param arrowTexts
     */
    @Override
    public void updateArrowTexts(String[] arrowTexts){
        if(!onCreated) return;
        baseNoteArrowText.setText(arrowTexts[0]);
        middleNoteArrowText.setText(arrowTexts[1]);
        sopranoNoteArrowText.setText(arrowTexts[2]);
    }

    /**
     * update the three triad notes
     * @param texts
     */
    @Override
    public void updateQuestionTexts(String [] texts){
        if(!onCreated) return;
        if (texts.length != 3) { throw new AssertionError("expecting texts' length is 3"); }
        questionTriadBaseNoteText.setText(texts[0]);
        questionTriadMiddleNoteText.setText(texts[1]);
        questionTriadSopranoNoteText.setText(texts[2]);
    }

    @Override
    public void updateArrowAnimation(Animation myAnimation){
        if(!onCreated) return;
        // FIXME disable it for now
//        baseNoteArrowText.setAnimation(myAnimation);
    }
}

