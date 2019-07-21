package com.example.pitchperfectlyaccuratelypractice.FilterPages;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pitchperfectlyaccuratelypractice.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment1 extends GeneralTabFragment {

    public TabFragment1(FilterActivity filter) {
        super(filter);
        resource = R.layout.fragment_tab_fragment1;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(resource, container, false);
        view = layout;
        notesTableView = layout.findViewById(R.id.note_pool_table);

//        layout.findViewById(R.id.backButton).setOnClickListener(new Button.OnClickListener() {
//            public void onClick(View v) {
//            filterActivity.returnToMainActivity(tmpData);
//            }
//        });
        return layout;
    }



}
