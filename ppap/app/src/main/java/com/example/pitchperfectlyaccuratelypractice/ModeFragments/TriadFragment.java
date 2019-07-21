package com.example.pitchperfectlyaccuratelypractice.ModeFragments;


import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.pitchperfectlyaccuratelypractice.FilterPages.FilterActivity;
import com.example.pitchperfectlyaccuratelypractice.FilterPages.FilterPageOption;
import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;

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
    /**
     * question Note in the middle
     */
    private TextView questionTriadMiddleNoteText;
    /**
     * question Note on top of three
     */
    private TextView questionTriadSopranoNoteText;

    /**
     * constructor of TriadFragment
     * setup resource (see parent onCreateView for use)
     */
    public TriadFragment() {
        resource = R.layout.fragment_triad;
        background_color = Color.parseColor("#C2DFEE");
    }

    @Override
    public void listenerSetUp() {
        filterPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent filter_intent = new Intent(getActivity(), FilterActivity.class);
                filter_intent.putExtra("Mode", new FilterPageOption("triad"));

                // let the main activity handle the intent
                getActivity().startActivityForResult(filter_intent, MainActivity.REQUEST_CODE_FROM_FILTER); // why this REQUEST_CODE_FROM_FILTER can't be found using getActivity().REQUEST_CODE_FROM_FILTER
            }
        });
    }

    /**
     * set up views of the three triad notes
     */
    void setupAdditionalView() {
        Log.d(TAG, "setupAdditionalView: ");
        questionTriadBaseNoteText = constraintLayout.findViewById(R.id.triadBaseNoteTextView);
        questionTriadMiddleNoteText = constraintLayout.findViewById(R.id.triadMiddleNoteTextView);
        questionTriadSopranoNoteText = constraintLayout.findViewById(R.id.triadSopranoNoteTextView);

        if (questionTriadBaseNoteText == null || questionTriadMiddleNoteText == null||questionTriadSopranoNoteText == null) {
            throw new AssertionError("triadFragment some view  is null");
        }
    }

    /**
     * update the three triad notes
     * @param texts
     */
    public void updateQuestionTexts(String [] texts){
        if(!onCreated) return;
        if (texts.length != 3) { throw new AssertionError("expecting texts' length is 3"); }
        questionTriadBaseNoteText.setText(texts[0]);
        questionTriadMiddleNoteText.setText(texts[1]);
        questionTriadSopranoNoteText.setText(texts[2]);
    }
}
