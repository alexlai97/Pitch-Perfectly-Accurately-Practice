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

    private GraphView graphTop10;
    private GraphView graphBot5;

    float bestOfBot = 1;
    float worstOfTop = 0;

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
        graphTop10 = (GraphView) findViewById(R.id.graph_top10);
        graphTop10.getViewport().setMinY(0);
        graphTop10.getViewport().setMaxY(1);
        graphTop10.getViewport().setMinX(0);
        graphTop10.getViewport().setMaxX(21);
        graphTop10.getViewport().setYAxisBoundsManual(true);
        graphTop10.getViewport().setXAxisBoundsManual(true);

        graphBot5 = (GraphView) findViewById(R.id.graph_bot5);
        graphBot5.getViewport().setMinY(0);
        graphBot5.getViewport().setMaxY(1);
        graphBot5.getViewport().setMinX(0);
        graphBot5.getViewport().setMaxX(11);
        graphBot5.getViewport().setYAxisBoundsManual(true);
        graphBot5.getViewport().setXAxisBoundsManual(true);

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
        graphTop10();
        graphBot5();
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

    private void graphTop10(){
        Iterator<String> it = top10.keys();
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>();
        int cur = 1;
        String[] formatter = new String[11];
        for(int i = 0; i < 11; i++){
            formatter[i] = " ";
        }

//        series.appendData(new DataPoint(0, 0), true, 10);
        Log.v(TAG, "hi");
        while (it.hasNext()) {
            Log.v(TAG, "hi" + cur);
            String currentString = it.next();
            double pair = 0.;
            try {
                pair= (double) top10.get(currentString);
                Log.e(TAG, Double.toString(pair));
            } catch (Exception e){
                Log.e(TAG, e.toString());
            }
            series.appendData(new DataPoint(cur*2, pair) , true, 10);
            formatter[cur-1] = currentString;
            it.remove(); // avoids a ConcurrentModificationException
            cur++;
            if(cur == 11) break;
        }
//        series.setAnimated(true);
        series.setSpacing(50);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphTop10);
        staticLabelsFormatter.setHorizontalLabels(formatter);
        graphTop10.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graphTop10.addSeries(series);

//        graphTop10
    }

    private void graphBot5(){
        Iterator<String> it = bot5.keys();
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>();
        int cur = 1;
        String[] formatter = new String[6];
        for(int i = 0; i < 6; i++){
            formatter[i] = " ";
        }

//        series.appendData(new DataPoint(0, 0), true, 5);
        Log.v(TAG, "hi");
        while (it.hasNext()) {
            Log.v(TAG, "hi" + cur);
            String currentString = it.next();
            double pair = 0.;
            try {
                 pair= (double) bot5.get(currentString);
            } catch (Exception e){
                Log.e(TAG, e.toString());
            }
            series.appendData(new DataPoint(cur*2, pair) , true, 5);
            formatter[cur-1] = currentString;
            it.remove(); // avoids a ConcurrentModificationException
            cur++;
            if(cur == 6) break;
        }
//        series.setAnimated(true);
        series.setSpacing(50);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphBot5);
        staticLabelsFormatter.setHorizontalLabels(formatter);
        graphBot5.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graphBot5.addSeries(series);
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
