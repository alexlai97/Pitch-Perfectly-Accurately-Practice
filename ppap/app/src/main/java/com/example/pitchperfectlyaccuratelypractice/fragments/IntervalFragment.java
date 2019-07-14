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
 * {@link IntervalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IntervalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntervalFragment extends GeneralFragment {

    public IntervalFragment() {
        resource =R.layout.fragment_interval;
    }
}
