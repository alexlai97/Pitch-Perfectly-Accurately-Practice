package com.example.pitchperfectlyaccuratelypractice.modeSettingsTabFragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.PerModeSettingActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraphModeMiscSettingTab extends GeneralMiscSettingTab {

    Spinner showFrequency;
    public GraphModeMiscSettingTab(PerModeSettingActivity perModeSettingActivity) {
        super(perModeSettingActivity);
        this.resource = R.layout.tabfragment_graph_mode_misc_settings;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
