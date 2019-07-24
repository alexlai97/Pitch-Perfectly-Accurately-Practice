package com.example.pitchperfectlyaccuratelypractice.modeSettingsTabFragments;


import androidx.fragment.app.Fragment;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.PerModeSettingActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteModeMiscSettingTab extends GeneralMiscSettingTab {
    public NoteModeMiscSettingTab(PerModeSettingActivity perModeSettingActivity) {
        super(perModeSettingActivity);
        this.resource = R.layout.tabfragment_note_mode_misc_settings;
    }
}
