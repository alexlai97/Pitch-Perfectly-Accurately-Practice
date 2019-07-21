package com.example.pitchperfectlyaccuratelypractice.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.data.HistoryData;

import org.json.JSONObject;

import java.util.Iterator;

public class SummaryActivity extends Activity {

    /**
     * layout inflater
     */
    LayoutInflater layoutInflater;

    HistoryData historyData;

    private static final String TAG = "SUMMARY PAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_layout);
        layoutInflater = LayoutInflater.from(this);

        historyData = new HistoryData(this, false);

        findViewById(R.id.backButton).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                returnToMainActivity();
            }
        });
        getAllData();
    }

    private void getAllData(){
        JSONObject history = historyData.retrieveData();
        Iterator<String> myNotes = history.keys();
        String curNote;
        try {
            Log.v(TAG, history.get("12").toString());
        } catch (Exception e){
            Log.e(TAG, e.toString());
        }

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

            } catch (Exception e){
                Log.e(TAG, e.toString());
                Log.e(TAG, "Current note cannot be found in JSON");
            }
            myNotes.remove();
        }
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
