package com.example.pitchperfectlyaccuratelypractice.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.data.HistoryData;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SummaryActivity extends Activity {

    /**
     * layout inflater
     */
    LayoutInflater layoutInflater;

    HistoryData historyData;

    private static final String TAG = "SUMMARY PAGE";

    JSONObject top10 = new JSONObject();
    JSONObject bot5 = new JSONObject();

    private GraphView graph;
    private final Note[] notes = Note.getAllNotes();

    float bestOfBot = 1;
    float worstOfTop = 0;

    private int numOfNotesOnScreen = 17;

    float overallPercentage = 0;

    int bestOfBotIndex = -1;
    int worstOfTopIndex = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_layout);


        historyData = new HistoryData(this, true);

        findViewById(R.id.backButton).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                returnToMainActivity();
            }
        });

        layoutInflater = LayoutInflater.from(this);
        graph = (GraphView) findViewById(R.id.graph);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(1);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(numOfNotesOnScreen);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);

        getAllData();
    }

    private void getAllData(){
        JSONObject history = historyData.retrieveData();
        Iterator<String> myNotes = history.keys();
        String curNote;
        boolean hackFirst = true;
        while(myNotes.hasNext()){
            curNote = myNotes.next();
            try{
//                Log.v(TAG, history.get("12").toString());

                Log.v(TAG, curNote);
                if (curNote != "12") Log.e(TAG, curNote);
                JSONObject current_note = (JSONObject) history.get(curNote);
                int right = (Integer) current_note.get("correct");
                int total = (Integer) current_note.get("total");

                Log.v(TAG, "correct: " + Integer.toString(right));
                Log.v(TAG, "total: " + Integer.toString(total));

                // HACK TO MAKE SURE OVERALL DOESNT WORK MUST CHANGE LATER
                // TODO: FIIIIIIXXXXX THIIISISISISISISISII
                if(total > 0 && hackFirst != true){
                    checkIfTop10(curNote, ((double)right)/total);
                    checkIfBot5(curNote, ((double)right)/total);
                } else if (curNote == "overall") {
                    overallPercentage = right/total;
                }
                hackFirst = false;
            } catch (Exception e){
                Log.e(TAG, e.toString());
                Log.e(TAG, "Current note cannot be found in JSON");
            }
            myNotes.remove();
        }
        graphNotes();
    }


    private void checkIfTop10(String curNote, double percentCorrect){
//        if(worstOfTop < percentCorrect){
            try{
                top10.put(curNote, percentCorrect);
            } catch (Exception  e){
                Log.e(TAG, "Failed to add entry");
            }
//        }
    }

    private void checkIfBot5(String curNote, double percentCorrect){
//        if(bestOfBot >= percentCorrect){
            try{
                bot5.put(curNote, percentCorrect);
            } catch (Exception  e){
                Log.e(TAG, "Failed to add entry");
            }
//        }
    }

    private void graphNotes(){
        int it = notes.length + 1;
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>();

//        series.appendData(new DataPoint(0, 0), true, 10);
        Log.v(TAG, Integer.toString(it));
       for(int i = 1; i < it; i++) {
            Log.v(TAG, "hi" + i);

            double pair = 0.;
            try {
                pair= (double) top10.get(Integer.toString(i));
                Log.e(TAG, Double.toString(pair));
            } catch (Exception e){
//                pair= 1;
                Log.e(TAG, e.toString());
            }
            series.appendData(new DataPoint(i, pair) , true, 74);
        }
//        series.setAnimated(true);
        series.setSpacing(40);
//        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
//        staticLabelsFormatter.setHorizontalLabels(formatter);



        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX){
                if(isValueX) {
                    int index = Math.round((float)value);
                    if(index > 72) return "";
                    return notes[index].getText();
                }
                return super.formatLabel(value, isValueX);
            }
        });

        graph.getGridLabelRenderer().setNumHorizontalLabels(numOfNotesOnScreen);
        graph.getGridLabelRenderer().setLabelVerticalWidth(200);
//        series.setDataWidth(00);

        graph.addSeries(series);
        graph.getViewport().setScrollable(true);

//        graphTop10
    }

    /**
     * pass note [] as int [] in intent back to MainActivity
     */
    private void returnToMainActivity() {
//        Intent summaryDone = new Intent(this, MainActivity.class);
        // PutExtras
//        setResult(RESULT_OK, summaryDone);
        finish();
    }

}
