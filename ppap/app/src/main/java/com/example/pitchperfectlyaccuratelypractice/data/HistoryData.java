package com.example.pitchperfectlyaccuratelypractice.data;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;

public class HistoryData {
    JSONObject history;
    String myJson;
    File directory;
    Activity currentAct;
    private static final String TAG = "HISTORY";

    // force makes the history file from default
    public HistoryData(Activity ac, boolean force){
        currentAct = ac;
        if(force){
            resetData();
            return;
        }

        try{
            directory = ac.getFilesDir();
            for(int i = 0; i < ac.fileList().length; i++){
                Log.v(TAG, ac.fileList()[i]);
            }

            InputStream is = ac.openFileInput("history.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            myJson = new String(buffer, "UTF-8");
            history = new JSONObject(myJson);
            Log.v(TAG, history.toString(1));
        } catch (Exception e) {
            Log.e(TAG, "Couldnt find file, using default");
            try{
                resetData();
            } catch(Exception s) {
                Log.e(TAG, "Couldnt use default file");
            }
        }


    }

    public void addData(int note, boolean correct){
        increaseData(note, correct);
        increaseData(-1, correct);

//        File file = new File(currentAct.getFilesDir(), );
        writeToFile(history.toString(), currentAct, "history.json");
        Log.e(TAG, "Saved new config");

    }

    private void increaseData(int note, boolean correct){
        String entry = note < 0 ? "overall" : Integer.toString(note);
        try{
            JSONObject current_note = (JSONObject) history.get(entry);
            int right = (Integer) current_note.get("correct");
            int total = (Integer) current_note.get("total");

            if(correct){
                right ++;
            }
            total++;

            JSONObject newEntry = new JSONObject();
            newEntry.put("correct", right);
            newEntry.put("total", total);

            history.put(entry, newEntry);
        } catch (Exception e){
            try {
                JSONObject newEntry = new JSONObject();
                if (correct) newEntry.put("correct", 1);
                else newEntry.put("correct", 0);
                newEntry.put("total", 1);

                history.put(entry, newEntry);
            }catch (Exception p){
                Log.e(TAG, "Couldn't create new data");
            }
        }

        writeToFile(history.toString(), currentAct, "history.json");
    }

    public JSONObject retrieveData(){
        return history;
    }

    public void resetData(){
        Log.e(TAG, "forced reset file");
        try{
//            Log.v(TAG, ac.getAssets().list("history.json"));
            InputStream is = currentAct.getAssets().open("history.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            myJson = new String(buffer, "UTF-8");
            history = new JSONObject(myJson);
        } catch(Exception s) {
            Log.e(TAG, "forced reset file failed");
        }

        writeToFile(history.toString(), currentAct, "history.json");
    }

    private void writeToFile(String data, Activity context, String fileName) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}
