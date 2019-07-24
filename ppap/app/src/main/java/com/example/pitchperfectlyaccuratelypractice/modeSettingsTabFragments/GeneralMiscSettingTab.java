package com.example.pitchperfectlyaccuratelypractice.modeSettingsTabFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.PerModeSettingActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class GeneralMiscSettingTab extends Fragment {

    PerModeSettingActivity permodeSettingActivity;
    int resource;
    View view;

    Switch autoPlayback_switch;
    EditText error_allowance_editText;
    EditText least_stable_time_editText;
    EditText show_correct_time_editText;

    public GeneralMiscSettingTab(PerModeSettingActivity permodeSettingActivity) {
        this.permodeSettingActivity = permodeSettingActivity;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(resource, container, false);
        this.view = layout;

//        autoPlayback_switch = layout.findViewById(R.id.autoPlayBackSwitch);
//        error_allowance_editText = layout.findViewById(R.id.errorAllowance);
//        least_stable_time_editText;
//        show_correct_time_editText;

        return view;
    }

}
