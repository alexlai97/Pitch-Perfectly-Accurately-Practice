package com.example.pitchperfectlyaccuratelypractice.FilterPages;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    FilterActivity filter;
    public PagerAdapter(@NonNull FragmentManager fm, int mNumOfTabs, FilterActivity filter) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mNumOfTabs = mNumOfTabs;
        this.filter = filter;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new NotePoolSelectionTab(filter);
            case 1: return new IntervalPoolSelectionTab(filter);
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
