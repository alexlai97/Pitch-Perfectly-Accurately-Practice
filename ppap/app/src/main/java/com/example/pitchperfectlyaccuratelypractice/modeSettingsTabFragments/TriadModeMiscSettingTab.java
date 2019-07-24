package com.example.pitchperfectlyaccuratelypractice.modeSettingsTabFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.PerModeSettingActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class TriadModeMiscSettingTab extends GeneralMiscSettingTab {


    public TriadModeMiscSettingTab(PerModeSettingActivity filter) {
        super(filter);
        resource = R.layout.triad_scale_misc_settings_page_toggle_button_group;
        // Required empty public constructor
    }

}
