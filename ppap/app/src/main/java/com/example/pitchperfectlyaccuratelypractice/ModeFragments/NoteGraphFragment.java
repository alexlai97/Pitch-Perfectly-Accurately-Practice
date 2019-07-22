package com.example.pitchperfectlyaccuratelypractice.ModeFragments;

import android.content.Intent;
import android.os.Handler;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.View;

import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

import com.example.pitchperfectlyaccuratelypractice.FilterPages.FilterActivity;
import com.example.pitchperfectlyaccuratelypractice.FilterPages.PerModeSetting;
import com.example.pitchperfectlyaccuratelypractice.R;

import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;
import com.jjoe64.graphview.*;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import android.graphics.Color;

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

    //private TextView questionNoteText;
    private ConstraintLayout noteLayout;
    private TextView arrowText;

    /**
     * constructor of IntervalFragment
     * setup resource (see parent onCreateView for use)
     */
    public NoteGraphFragment() {
        resource = R.layout.fragment_note_graph;
        background_color = Color.parseColor("#c0ecef");
        instruction_string = "Please sing the note in the center \n\n" +
                "Single the tap start_playing button will start_playing the answer note.\n\n" +
                "You can select note pool in Filter Page (pineapple button)";
    }


    @Override
    void setupAdditionalView() {
        Log.d(TAG, "setupAdditionalView: ");
        noteLayout = constraintLayout.findViewById(R.id.note_include);
       // if (questionNoteText == null) { throw new AssertionError("questionNoteText is null"); }
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
    public void listenerSetUp() {
        filterPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent filter_intent = new Intent(getActivity(), FilterActivity.class);
                filter_intent.putExtra("Mode", new PerModeSetting("noteGraph"));

                // let the main activity handle the intent
                getActivity().startActivityForResult(filter_intent, MainActivity.REQUEST_CODE_FROM_FILTER); // why this REQUEST_CODE_FROM_FILTER can't be found using getActivity().REQUEST_CODE_FROM_FILTER
            }
        });
    }

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
    public void updateFrequencyText(Long currentFrequency){
        if(!onCreated) return;
        String temp = Long.toString(currentFrequency);
        frequencyText.setText(temp + " Hz");
        graph.getViewport().setMaxY(questionFreq*2);
        lastFreq = currentFrequency;
    }


    /**
     * update question text
     * @param texts
     */
    @Override
    public void updateQuestionTexts(String[] texts){
        if(!onCreated) return;
        char[] charArray = texts[0].toCharArray();
        for (int i = 0; i < charArray.length; ++i) {
            if (i == 0) {
                ((TextView) noteLayout.findViewById(R.id.NoteTextView)).setText("" + charArray[i]);
            } else if (i == 1) {
                ((TextView) noteLayout.findViewById(R.id.NoteScaleTextView)).setText("" + charArray[i]);
            } else if (i == 2) {
                ((TextView) noteLayout.findViewById(R.id.NoteSigView)).setText("" + charArray[i]);
            }
        }
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
     * FIXME can get directly from controller
     * @param expected_freq
     */
    public void setCurrentExpectedFrequency(double expected_freq) {
        questionFreq = expected_freq;
    }


}
