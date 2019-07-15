package com.example.pitchperfectlyaccuratelypractice.fragments;

import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.pitchperfectlyaccuratelypractice.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class NoteFragment extends GeneralFragment {

    TextView questionNoteText;

    public NoteFragment() {
        resource = R.layout.fragment_note;
        questionNoteText = constraintLayout.findViewById(R.id.questionNoteTextView);
        if (questionNoteText == null) {
            throw new AssertionError("questionNoteText is null");
        }
    }

    public void updateQuestionTexts(String[] texts){
        if(!onCreated) return;
        questionNoteText.setText(texts[0]);
    }

}
