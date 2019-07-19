package com.example.pitchperfectlyaccuratelypractice.TabFragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pitchperfectlyaccuratelypractice.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragmentNote extends GeneralTabFragment {


    public TabFragmentNote(IntervalModeFilterActivity filter) {
        super(filter);
        resource = R.layout.fragment_tab_fragment_note;
    }

}
