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

import android.graphics.Color;

public class NoteGraphFragment extends GeneralFragment {
    private Runnable mTimer;
    private Long lastFreq;
    private GraphView graph;
    private LineGraphSeries<DataPoint> series;
    private LineGraphSeries<DataPoint> series2;
    private int yes = 5;
    private final Handler mHandler = new Handler();
    private double graphLastXValue = 5d;
    private double questionFreq;

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
        graph.getViewport().setMaxX(3);
        graph.getGridLabelRenderer().setLabelVerticalWidth(100);

        series = new LineGraphSeries<>();
        series.setDrawDataPoints(true);
        series.setDrawBackground(true);

        series2 = new LineGraphSeries<>();
        series2.setDrawDataPoints(true);
        series2.setColor(Color.RED);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-10);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.addSeries(series);
        graph.addSeries(series2);

        return view;
    }

    public void onResume(){
        super.onResume();
        mTimer = new Runnable() {
            @Override
            public void run() {
                graphLastXValue += 0.25d;
                series.appendData(new DataPoint(graphLastXValue, lastFreq), true, 22);
                series2.appendData(new DataPoint(graphLastXValue, questionFreq), true, 22);
                mHandler.postDelayed(this, 330);
            }
        };
        mHandler.postDelayed(mTimer, 100);
    }

    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mTimer);
    }

    @Override
    public void updateFrequencyText(Long freq, Double expectedFreq){
        if(!onCreated) return;
        String temp = Long.toString(freq);
        frequencyText.setText(temp + "Hz");
        graph.getViewport().setMaxY(expectedFreq*2);
        lastFreq = freq;
        questionFreq = expectedFreq;
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
