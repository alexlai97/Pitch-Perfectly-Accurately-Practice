package com.example.pitchperfectlyaccuratelypractice.TabFragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    IntervalModeFilterActivity filter;
    public PagerAdapter(@NonNull FragmentManager fm, int mNumOfTabs, IntervalModeFilterActivity filter) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mNumOfTabs = mNumOfTabs;
        this.filter = filter;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1: return new TabFragment1(filter);
            case 2: return new TabFragment2(filter);
            default: return null;
        }
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
