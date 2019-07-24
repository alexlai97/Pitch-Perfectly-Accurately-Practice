package com.example.pitchperfectlyaccuratelypractice.modeSettingsTabFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pitchperfectlyaccuratelypractice.activities.PerModeSettingActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class GeneralMiscSettingTab extends Fragment {

    PerModeSettingActivity permodeSettingActivity;
    int resource;
    View view;
    public GeneralMiscSettingTab(PerModeSettingActivity permodeSettingActivity) {
        this.permodeSettingActivity = permodeSettingActivity;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(resource, container, false);
        this.view = layout;
        return view;
    }

}
