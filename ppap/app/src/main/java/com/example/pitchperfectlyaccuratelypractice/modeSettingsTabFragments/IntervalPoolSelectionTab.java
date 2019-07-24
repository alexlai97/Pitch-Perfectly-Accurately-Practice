package com.example.pitchperfectlyaccuratelypractice.modeSettingsTabFragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ToggleButton;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.PerModeSettingActivity;
import com.example.pitchperfectlyaccuratelypractice.bitmap.IntervalsBitmap;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Interval;
import com.example.pitchperfectlyaccuratelypractice.perModeSetting.IntervalModeSetting;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntervalPoolSelectionTab extends Fragment {

    private static final String TAG="IntervalTab";
    private PerModeSettingActivity perModeSettingActivity;
    public View view;


    TableLayout negativeIntervalsTable;
    TableLayout positiveIntervalsTable;

    LayoutInflater layoutInflater;
    private static Interval[] allTrueIntervalArray = IntervalsBitmap.getAllTrueIntervalsBitmap().toIntervals();

    public IntervalPoolSelectionTab(PerModeSettingActivity pma) {
        this.perModeSettingActivity = pma;

        // ??
        ((IntervalModeSetting)this.perModeSettingActivity.perModeSetting).intervalsBitmap = IntervalsBitmap.getAllTrueIntervalsBitmap();
        // Required empty public constructor
    }

//    public IntervalPoolSelectionTab(PerModeSettingActivity filter, IntervalsBitmap intervalsBitmap) {
//        this.filter = filter;
//        this.generated_bitmap = intervalsBitmap;
//        perModeSetting = filter.perModeSetting;
//        generated_bitmap = IntervalsBitmap.getAllTrueIntervalsBitmap();
//        generated_interval = generated_bitmap.toIntervals();
//        // Required empty public constructor
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tabfragment_interval_pool_selection, container, false);
        view = layout;
        negativeIntervalsTable = view.findViewById(R.id.negativeIntervalsTableLayout);
        positiveIntervalsTable = view.findViewById(R.id.positiveIntervalsTableLayout);
        Log.d(TAG, "onCreateView: ");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        view.findViewById(R.id.backButton).setOnClickListener(new Button.OnClickListener() {
//            public void onClick(View v) {
//                filter.returnToMainActivity(tmpData);
//            }
//        });
        setButtonListener();
        layoutInflater = LayoutInflater.from(getContext());
        create_interval_table();
//        restoreButtonState(tmpData);
    }

//    private void restoreButtonState(IntervalsBitmap tmpData){
//        int index = 11;
//        for(int row = 0; row < negativeIntervalsTable.getChildCount(); row++) {
//            TableRow tableRow = (TableRow)negativeIntervalsTable.getChildAt(row);
//            for(int col = 0; col < tableRow.getChildCount(); col++){
//                ToggleButton toggleButton = (ToggleButton) tableRow.getChildAt(col);
//                if()
//            }
//        }
//    }

    private void setButtonListener(){
        Button selectButton = view.findViewById(R.id.select_all);
        Button cancelButton = view.findViewById(R.id.cancel_all);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int row = 0; row < negativeIntervalsTable.getChildCount(); row++) {
                    TableRow tableRow = (TableRow) negativeIntervalsTable.getChildAt(row);
                    for(int col = 0; col < tableRow.getChildCount(); col++){
                        ToggleButton toggleButton = (ToggleButton) tableRow.getChildAt(col);
                        if(!toggleButton.isChecked()){
                            toggleButton.setChecked(true);
                        }
                    }
                }
                for(int row = 0; row < positiveIntervalsTable.getChildCount(); row++) {
                    TableRow tableRow = (TableRow) positiveIntervalsTable.getChildAt(row);
                    for(int col = 0; col < tableRow.getChildCount(); col++){
                        ToggleButton toggleButton = (ToggleButton) tableRow.getChildAt(col);
                        if(!toggleButton.isChecked()){
                            toggleButton.setChecked(true);
                        }
                    }
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int row = 0; row < negativeIntervalsTable.getChildCount(); row++) {
                    TableRow tableRow = (TableRow) negativeIntervalsTable.getChildAt(row);
                    for(int col = 0; col < tableRow.getChildCount(); col++){
                        ToggleButton toggleButton = (ToggleButton) tableRow.getChildAt(col);
                        if(toggleButton.isChecked()){
                            toggleButton.setChecked(false);
                        }
                    }
                }

                for(int row = 0; row < positiveIntervalsTable.getChildCount(); row++) {
                    TableRow tableRow = (TableRow) positiveIntervalsTable.getChildAt(row);
                    for(int col = 0; col < tableRow.getChildCount(); col++){
                        ToggleButton toggleButton = (ToggleButton) tableRow.getChildAt(col);
                        if(toggleButton.isChecked()){
                            toggleButton.setChecked(false);
                        }
                    }
                }
            }
        });
    }

    public void create_interval_table(){

        // generate buttons
        int negative_index = 11;
        int positive_index = 13;

        for (int i = 0; i < 7; i++) {
            int buttonNum = 2;
            if (i == 2 || i == 6){
                buttonNum = 1;
            }
            TableRow row1 = (TableRow) layoutInflater.inflate(R.layout.table_row_note, null, false);
            TableRow row2 = (TableRow) layoutInflater.inflate(R.layout.table_row_note, null, false);
            for (int j = 0; j < buttonNum; j++) {
                ToggleButton note_button1 = (ToggleButton) layoutInflater.inflate(R.layout.togglebutton_single_note, null, false);
                ToggleButton note_button2 = (ToggleButton) layoutInflater.inflate(R.layout.togglebutton_single_note, null, false);
                Interval left_interval = allTrueIntervalArray[negative_index];
                Log.d(TAG, "create_interval_table: " + left_interval.getTextWithoutSign());
                Interval right_interval = allTrueIntervalArray[positive_index];
                note_button1 = updateButton(note_button1, left_interval);
                note_button2 = updateButton(note_button2, right_interval);
                row1.addView(note_button1);
                row2.addView(note_button2);
                negative_index--;
                positive_index++;
            }
            if(i == 6){
                ToggleButton note_button = (ToggleButton) layoutInflater.inflate(R.layout.togglebutton_single_note, null, false);
                Interval interval = allTrueIntervalArray[12];
                note_button = updateButton(note_button, interval);
                row1.addView(note_button);
            }
            negativeIntervalsTable.addView(row1);
            positiveIntervalsTable.addView(row2);
        }

    }


    /**
     * update note button so that it has the note text as button text
     * and listener when toggle is toggled
     * @param button
     * @param interval
     * @return
     */
    ToggleButton updateButton(ToggleButton button, final Interval interval) {
        String interval_text = interval.getTextWithoutSign();
        button.setText(interval_text);
        button.setTextOff(interval_text);
        button.setTextOn(interval_text);
        button.setChecked(true);
        Log.v(TAG, "updateButton: " + interval_text);
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ((IntervalModeSetting)perModeSettingActivity.perModeSetting).intervalsBitmap.toggleNote(interval);
            }
        });
        return button;
    }
}
