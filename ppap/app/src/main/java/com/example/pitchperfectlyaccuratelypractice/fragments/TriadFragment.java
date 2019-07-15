package com.example.pitchperfectlyaccuratelypractice.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.updateViewInterface;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TriadFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TriadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class TriadFragment extends GeneralFragment {
    private static String TAG = "TriadFragment";

    TextView questionTriadBaseNoteText;
    TextView questionTriadMiddleNoteText;
    TextView questionTriadSopranoNoteText;

    public TriadFragment() {
        resource = R.layout.fragment_triad;
    }

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
