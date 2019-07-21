package com.example.pitchperfectlyaccuratelypractice.ModeFragments;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
    private TextView questionNoteText;

    /**
     * constructor of NoteFragment
     * setup resource (see parent onCreateView for use)
     */
    public NoteFragment() {
        resource = R.layout.fragment_note;
        background_color = Color.parseColor("#E6FBBA");
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

}
