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
public class NoteModeFragment extends ModeFragment {

    private static String TAG = "NoteModeFragment";

    /**
     * a question note in the middle of the screen
     */
    private TextView arrowText;
    private ConstraintLayout noteLayout;

    /**
     * constructor of NoteModeFragment
     * setup resource (see parent onCreateView for use)
     */
    public NoteModeFragment() {
        mode = Mode.NotePractice;
        resource = R.layout.modefragment_note;
        background_color = Color.parseColor("#E6FBBA");
        instruction_string = "Please sing the note in the center \n\n" +
                "Single the tap start_playing button will start_playing the answer note.\n\n" +
                "You can select note pool in Filter Page (pineapple button)";
    }

    /**
     * set up views of questionNoteText
     */
    @Override
    void setupAdditionalView() {
        Log.d(TAG, "setupAdditionalView: ");
        noteLayout = constraintLayout.findViewById(R.id.note_mode_include);
        arrowText = constraintLayout.findViewById(R.id.arrowTextView);
    }

    /**
     * update question text
     * @param texts
     */
    @Override
    public void updateQuestionTexts(String[] texts){
        if(!onCreated) return;
        updateSingleNoteText(noteLayout, texts[0]);
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
