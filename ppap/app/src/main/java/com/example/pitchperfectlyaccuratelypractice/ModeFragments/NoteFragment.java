package com.example.pitchperfectlyaccuratelypractice.ModeFragments;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.animation.Animation;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.pitchperfectlyaccuratelypractice.FilterPages.FilterActivity;
import com.example.pitchperfectlyaccuratelypractice.FilterPages.FilterPageOption;
import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.NoteModeFilterPageActivity;

/**
 * a children of general fragment
 * it has questionNoteText view, questionIntervalText view
 */
public class NoteFragment extends GeneralFragment {

    private static String TAG = "NoteFragment";

    private final int REQUEST_CODE= 1;
    /**
     * a question note in the middle of the screen
     */
    //private TextView questionNoteText;
    private TextView arrowText;
    private ConstraintLayout noteLayout;
    //private TextView questionNote;
    // TextView questonScale;
    //private TextView questionSig;

    /**
     * constructor of NoteFragment
     * setup resource (see parent onCreateView for use)
     */
    public NoteFragment() {
        resource = R.layout.fragment_note;
        background_color = Color.parseColor("#E6FBBA");
        instruction_string = "Please sing the note in the center \n\n" +
                "Single the tap start_playing button will start_playing the answer note.\n\n" +
                "You can select note pool in Filter Page (pineapple button)";
    }

    @Override
    public void listenerSetUp() {
        filterPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent filter_intent = new Intent(getActivity(), FilterActivity.class);
                filter_intent.putExtra("Mode", new FilterPageOption("note"));
                // let the main activity handle the intent
                startActivityForResult(filter_intent, REQUEST_CODE_FROM_FILTER);
            }
        });

    }

    /**
     * set up views of questionNoteText
     */
    @Override
    void setupAdditionalView() {
        Log.d(TAG, "setupAdditionalView: ");
        //questionNoteText = constraintLayout.findViewById(R.id.prevNoteTextView);
        //questionNote = constraintLayout.findViewById(R.id.NoteTextView);
        //questonScale = constraintLayout.findViewById(R.id.NoteScaleTextView);
        //questionSig = constraintLayout.findViewById(R.id.NoteSigView);
        noteLayout = constraintLayout.findViewById(R.id.note_mode_include);
        //if (questionNote == null) { throw new AssertionError("questionNoteText is null"); }

        arrowText = constraintLayout.findViewById(R.id.arrowTextView);
    }

    /**
     * update question text
     * @param texts
     */
    @Override
    public void updateQuestionTexts(String[] texts){
        if(!onCreated) return;
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

        //questionNote.setText(texts[0]);
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
