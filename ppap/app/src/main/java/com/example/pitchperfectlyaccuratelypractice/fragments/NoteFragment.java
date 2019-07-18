package com.example.pitchperfectlyaccuratelypractice.fragments;

import android.graphics.Color;
import android.util.Log;
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

    /**
     * constructor of NoteFragment
     * setup resource (see parent onCreateView for use)
     */
    public NoteFragment() {
        resource = R.layout.fragment_note;
        background_color = Color.parseColor("#E6FBBA");
    }

    /**
     * set up views of questionNoteText
     */
    void setupAdditionalView() {
        Log.d(TAG, "setupAdditionalView: ");
        questionNoteText = constraintLayout.findViewById(R.id.questionNoteTextView);
        if (questionNoteText == null) { throw new AssertionError("questionNoteText is null"); }
    }

    /**
     * update question text
     * @param texts
     */
    public void updateQuestionTexts(String[] texts){
        if(!onCreated) return;
        questionNoteText.setText(texts[0]);
    }

    @Override
    public String getPopupText() {
        return "This is the note mode. The note displayed is the note that you want to sing. \n\n" +
                "An arrow will show to tell you to go higher or lower depending on your current pitch" +
                ", go ahead and try getting that perfect pitch!";
    }
}
