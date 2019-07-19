package com.example.pitchperfectlyaccuratelypractice.TabFragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.bitmap.NotesBitmap;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment2 extends Fragment{

    private IntervalModeFilterActivity filter;
    public View view;

    private NotesBitmap tmpData;

    public TabFragment2(IntervalModeFilterActivity filter) {
        this.filter = filter;
        tmpData = new NotesBitmap();
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_tab_fragment2, container, false);
        view = layout;
//        view.findViewById(R.id.backButton).setOnClickListener(new Button.OnClickListener() {
//            public void onClick(View v) {
//                filter.returnToMainActivity(tmpData);
//            }
//        });
        return view;
    }
}
