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

import com.jjoe64.graphview.*;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class NoteGraphFragment extends GeneralFragment {

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


    @Override
    public void updateArrowText(String myString){
        if(!onCreated) return;
    }

    @Override
    public void updateArrowAnimation(Animation myAnimation){
        if(!onCreated) return;
    }
}
