package com.example.pitchperfectlyaccuratelypractice.fragments;

import androidx.fragment.app.Fragment;

import android.util.Log;
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
    TextView questionTriadBaseNoteText;
    /**
     * question Note in the middle
     */
    TextView questionTriadMiddleNoteText;
    /**
     * question Note on top of three
     */
    TextView questionTriadSopranoNoteText;

    /**
     * constructor of TriadFragment
     * setup resource (see parent onCreateView for use)
     */
    public TriadFragment() {
        resource = R.layout.fragment_triad;
    }

    /**
     * set up views of the three triad notes
     */
    void setupAdditionalView() {
        Log.d(TAG, "setupAdditionalView: ");
        questionTriadBaseNoteText = constraintLayout.findViewById(R.id.triadBaseNoteTextView);
        questionTriadMiddleNoteText = constraintLayout.findViewById(R.id.triadMiddleNoteTextView);
        questionTriadSopranoNoteText = constraintLayout.findViewById(R.id.triadSopranoNoteTextView);

        if (questionTriadBaseNoteText == null ||
                questionTriadMiddleNoteText == null||questionTriadSopranoNoteText == null) {
            throw new AssertionError("triadFragment some view  is null");
        }
    }

    /**
     * update the three triad notes
     * @param texts
     */
    public void updateQuestionTexts(String [] texts){
        if(!onCreated) return;
        if (texts.length != 3) {
            throw new AssertionError("expecting texts' length is 3");
        }
        questionTriadBaseNoteText.setText(texts[0]);
        questionTriadMiddleNoteText.setText(texts[1]);
        questionTriadSopranoNoteText.setText(texts[2]);
    }
}
