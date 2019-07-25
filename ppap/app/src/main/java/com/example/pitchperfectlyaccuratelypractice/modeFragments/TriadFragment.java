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
 * it has a three question note view, (base, middle, soprano note)
 */
public class TriadFragment extends ModeFragment {
    private static String TAG = "TriadFragment";

    /**
     * question Note on the bottom
     */
    //private TextView questionTriadBaseNoteText;
    private TextView baseNoteArrowText;
    private ConstraintLayout baseNoteLayout;
    /**
     * question Note in the middle
     */
    //private TextView questionTriadMiddleNoteText;
    private TextView middleNoteArrowText;
    private ConstraintLayout middleNoteLayout;
    /**
     * question Note on top of three
     */
    //private TextView questionTriadSopranoNoteText;
    private TextView sopranoNoteArrowText;
    private ConstraintLayout sopranoNoteLayout;

    /**
     * constructor of TriadFragment
     * setup resource (see parent onCreateView for use)
     */
    public TriadFragment() {
        mode = Mode.TriadPractice;
        resource = R.layout.modefragment_triad;
        background_color = Color.parseColor("#c8dfec");
        instruction_string = "Please the notes in any order\n\n" +
                "Single tap the start_playing button will start_playing the chord\n\n" +
                "Long press the start_playing button will start_playing notes sequentially then chord\n\n" +
                "You can select note pool and interval pool in Filter Page (pineapple button)";
    }

    /**
     * set up views of the three triad notes
     */
    @Override
    void setupAdditionalView() {
        Log.d(TAG, "setupAdditionalView: ");
        baseNoteLayout = constraintLayout.findViewById(R.id.base_include);
        middleNoteLayout = constraintLayout.findViewById(R.id.middle_include);
        sopranoNoteLayout = constraintLayout.findViewById(R.id.soprano_include);
        baseNoteArrowText = constraintLayout.findViewById(R.id.baseNotearrowTextView);
        middleNoteArrowText = constraintLayout.findViewById(R.id.middleNotearrowTextView);
        sopranoNoteArrowText = constraintLayout.findViewById(R.id.sopranoNotearrowTextView);

        //if (questionTriadBaseNoteText == null || questionTriadMiddleNoteText == null||questionTriadSopranoNoteText == null) {
            //throw new AssertionError("triadFragment some view  is null");
        //}
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
        updateSingleNoteText(baseNoteLayout, texts[0]);
        updateSingleNoteText(middleNoteLayout, texts[1]);
        updateSingleNoteText(sopranoNoteLayout, texts[2]);
    }

    @Override
    public void updateArrowAnimation(Animation myAnimation){
        if(!onCreated) return;
        // FIXME disable it for now
//        baseNoteArrowText.setAnimation(myAnimation);
    }
  
}

