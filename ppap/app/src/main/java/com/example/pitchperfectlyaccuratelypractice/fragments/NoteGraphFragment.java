package com.example.pitchperfectlyaccuratelypractice.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

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
    private Runnable mTimer;
    private Long lastFreq;
    private GraphView graph;
    private LineGraphSeries<DataPoint> series;
    private int yes = 5;
    private final Handler mHandler = new Handler();
    private double graphLastXValue = 5d;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("NOTEGRAPH", "onCreateView!");
        onCreated = true;
        View view = inflater.inflate(R.layout.fragment_note_graph, container, false);
        ConstraintLayout included = view.findViewById(R.id.notegraph_include);
        frequencyText = included.findViewById(R.id.currentFrequencyTextView);
        questionText = included.findViewById(R.id.questionTextView);
        arrowText = included.findViewById(R.id.arrowTextView1);

        currentPitchText  = included.findViewById(R.id.currentPitchTextView);
        graph = (GraphView) included.findViewById(R.id.graph);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(4);

        graph.getGridLabelRenderer().setLabelVerticalWidth(100);

        series = new LineGraphSeries<>();
        series.setDrawDataPoints(true);
        series.setDrawBackground(true);
//        series = new LineGraphSeries<>(new DataPoint[] {
//                new DataPoint(0, 1),
//                new DataPoint(1, 5),
//                new DataPoint(2, 3),
//                new DataPoint(3, 2),
//                new DataPoint(4, 6)
//        });

        graph.addSeries(series);
        return view;
    }

    public void onResume(){
        super.onResume();
        mTimer = new Runnable() {
            @Override
            public void run() {
                graphLastXValue += 0.25d;
                series.appendData(new DataPoint(graphLastXValue, lastFreq), true, 22);
                mHandler.postDelayed(this, 330);
                yes ++;
            }
        };
        mHandler.postDelayed(mTimer, 50);
    }

    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mTimer);
    }

    @Override
    public void updateFrequencyText(Long freq){
        if(!onCreated) return;
        String temp = Long.toString(freq);
        frequencyText.setText(temp + "Hz");
        lastFreq = freq;
    }

    @Override
    public void updateArrowText(String myString){
        if(!onCreated) return;
        arrowText.setText(myString);
    }

    @Override
    public void updateArrowAnimation(Animation myAnimation){
        if(!onCreated) return;
    }
}
