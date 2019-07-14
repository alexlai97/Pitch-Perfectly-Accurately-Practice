package com.example.pitchperfectlyaccuratelypractice.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.widget.TextView;
import android.util.Log;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.MyCallback;

import com.jjoe64.graphview.*;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class NoteGraphFragment extends Fragment implements MyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private NoteGraphFragment.OnFragmentInteractionListener mListener;

    /**
     * stores questionTextView
     */
    private TextView questionText;
    /**
     * stores frequencyTextView
     */
    private TextView frequencyText;
    /**
     * stores currentPitchTextView
     */
    private TextView currentPitchText;

    private boolean onCreated = false;

    public NoteGraphFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteGraphFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteGraphFragment newInstance(String param1, String param2) {
        NoteGraphFragment fragment = new NoteGraphFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.v("NOTEGRAPH", "fdsadound!");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("NOTEGRAPH", "onCreateView!");
        onCreated = true;
        View view = inflater.inflate(R.layout.fragment_note_graph, container, false);
        ConstraintLayout included = view.findViewById(R.id.notegraph_include);
        frequencyText = included.findViewById(R.id.currentFrequencyTextView);
        questionText = included.findViewById(R.id.questionTextView);

        currentPitchText  = included.findViewById(R.id.currentPitchTextView);
        GraphView graph = (GraphView) included.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        Log.v("NOTEGRAPH", "Attached!");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void updateFrequencyText(String myString){
        if(!onCreated) return;
        frequencyText.setText(myString);
    }

    public void updateArrowText(String myString){
        if(!onCreated) return;
    }

    public void updateCurrentPitchText(String myString){
        if(!onCreated) return;
        currentPitchText.setText(myString);
    }

    public void updateQuestionText(String myString){
        if(!onCreated) return;
        questionText.setText(myString);
    }

    public void updateArrowAnimation(Animation myAnimation){
        if(!onCreated) return;
    }
}
