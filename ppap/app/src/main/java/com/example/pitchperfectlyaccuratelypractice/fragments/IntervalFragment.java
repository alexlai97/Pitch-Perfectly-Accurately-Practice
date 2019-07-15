package com.example.pitchperfectlyaccuratelypractice.fragments;

import android.util.Log;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.pitchperfectlyaccuratelypractice.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IntervalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IntervalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntervalFragment extends GeneralFragment {
    private static String TAG = "IntervalFragment";

    TextView questionNoteText;
    TextView questionIntervalText;

    public IntervalFragment() {
        resource =R.layout.fragment_interval;
    }

    void setupAdditionalView() {
        Log.d(TAG, "setupAdditionalView: ");
        questionNoteText = constraintLayout.findViewById(R.id.questionNoteTextView);
        if (questionNoteText == null) {
            throw new AssertionError("questionNoteText is null");
        }
        questionIntervalText = constraintLayout.findViewById(R.id.questionIntervalTextView);

        if (questionIntervalText == null) {
            throw new AssertionError("questionIntervalText is null");
        }
    }

    public void updateQuestionTexts(String [] texts){
        if(!onCreated) return;
        if (texts.length != 2) {
            throw new AssertionError("expecting texts' length is 2");
        }
        questionNoteText.setText(texts[0]);
        questionIntervalText.setText(texts[1]);
    }

}
