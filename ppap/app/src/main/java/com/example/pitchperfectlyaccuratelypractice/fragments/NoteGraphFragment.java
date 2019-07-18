package com.example.pitchperfectlyaccuratelypractice.fragments;

import android.os.Handler;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.View;

import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

import com.example.pitchperfectlyaccuratelypractice.R;

import com.example.pitchperfectlyaccuratelypractice.question.NoteQuestion;
import com.example.pitchperfectlyaccuratelypractice.question.Question;
import com.jjoe64.graphview.*;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import android.graphics.Color;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class NoteGraphFragment extends GeneralFragment {
    private static String TAG = "NoteGraphFragment";
    private Runnable mTimer;
    private Long lastFreq;
    private GraphView graph;
    private LineGraphSeries<DataPoint> series;
    private LineGraphSeries<DataPoint> series2;
    private int yes = 5;
    private final Handler mHandler = new Handler();
    private double graphLastXValue = 5d;
    private double questionFreq;

    private TextView questionNoteText;
    private TextView arrowText;

    /**
     * constructor of IntervalFragment
     * setup resource (see parent onCreateView for use)
     */
    public NoteGraphFragment() {
        resource = R.layout.fragment_note_graph;
        background_color = Color.parseColor("#E6FBBA");
    }


    @Override
    void setupAdditionalView() {
        Log.d(TAG, "setupAdditionalView: ");
        questionNoteText = constraintLayout.findViewById(R.id.questionNoteTextView);
        if (questionNoteText == null) { throw new AssertionError("questionNoteText is null"); }
        arrowText = constraintLayout.findViewById(R.id.arrowTextView);

        graph = (GraphView) constraintLayout.findViewById(R.id.graph);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(3);
        graph.getGridLabelRenderer().setLabelVerticalWidth(100);

        series = new LineGraphSeries<>();
//        series.setDrawDataPoints(true);
        series.setDrawBackground(true);

        series2 = new LineGraphSeries<>();
//        series2.setDrawDataPoints(true);
        series2.setColor(Color.RED);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-10);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        graph.addSeries(series);
        graph.addSeries(series2);
    }

    @Override
    public void onResume(){
        super.onResume();
        Button button = getView().findViewById(R.id.naviButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });
        mTimer = new Runnable() {
            @Override
            public void run() {
                graphLastXValue += 0.25d;
                series.appendData(new DataPoint(graphLastXValue, lastFreq), true, 22);
                series2.appendData(new DataPoint(graphLastXValue, questionFreq), true, 22);
                mHandler.postDelayed(this, 20);
            }
        };
        mHandler.postDelayed(mTimer, 40);
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mTimer);
    }



    @Override
    public void updateFrequencyText(Long freq){
        if(!onCreated) return;
        String temp = Long.toString(freq);
        frequencyText.setText(temp + "Hz");
        graph.getViewport().setMaxY(questionFreq*2);
        lastFreq = freq;
    }


    /**
     * update question text
     * @param texts
     */
    @Override
    public void updateQuestionTexts(String[] texts){
        if(!onCreated) return;
        questionNoteText.setText(texts[0]);
    }

    /**
     * update arrow text views
     * @param arrowTexts
     */
    @Override
    public void updateArrowTexts(String[] arrowTexts){
        if(!onCreated) return;
        arrowText.setText(arrowTexts[0]);
    }


    @Override
    public void updateArrowAnimation(Animation myAnimation){
        if(!onCreated) return;
        arrowText.setAnimation(myAnimation);
    }


    /**
     * only for this general fragment
     * @param freq
     */
    public void setCurrentExpectedFrequency(double freq) {
        questionFreq = freq;
    }
}
