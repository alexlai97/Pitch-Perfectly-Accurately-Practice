package com.example.pitchperfectlyaccuratelypractice.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("PEPE", "onCreateView!");
        onCreated = true;
        View view = inflater.inflate(R.layout.fragment_triad, container, false);
        ConstraintLayout included = view.findViewById(R.id.note_include);
        frequencyText = included.findViewById(R.id.currentFrequencyTextView);
        questionText = included.findViewById(R.id.questionTextView);

        arrowText = included.findViewById(R.id.arrowTextView1);
        currentPitchText  = included.findViewById(R.id.currentPitchTextView);
        return view;
    }

}
