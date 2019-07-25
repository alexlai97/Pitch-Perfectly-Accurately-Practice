package com.example.pitchperfectlyaccuratelypractice.tools;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.modeSettingsTabFragments.GraphModeMiscSettingTab;
import com.example.pitchperfectlyaccuratelypractice.modeSettingsTabFragments.IntervalPoolSelectionTab;
import com.example.pitchperfectlyaccuratelypractice.modeSettingsTabFragments.NoteModeMiscSettingTab;
import com.example.pitchperfectlyaccuratelypractice.modeSettingsTabFragments.NotePoolSelectionTab;
import com.example.pitchperfectlyaccuratelypractice.activities.PerModeSettingActivity;
import com.example.pitchperfectlyaccuratelypractice.modeSettingsTabFragments.SongModeMiscSettingTab;
import com.example.pitchperfectlyaccuratelypractice.modeSettingsTabFragments.TriadModeMiscSettingTab;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Mode mode;
    PerModeSettingActivity filter;
    public PagerAdapter(@NonNull FragmentManager fm, int mNumOfTabs, PerModeSettingActivity filter) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mNumOfTabs = mNumOfTabs;
        this.filter = filter;
        mode = filter.perModeSetting.mode;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (mode){
            case NotePractice:
                switch (position){
                    case 0: return new NotePoolSelectionTab(filter);
                    case 1: return new NoteModeMiscSettingTab(filter);
                    default: return null;
                }
            case IntervalPractice:
                switch (position){
                    case 0: return new NotePoolSelectionTab(filter);
                    case 1: return new IntervalPoolSelectionTab(filter);
                    case 2: return new NoteModeMiscSettingTab(filter);
                    default: return null;
                }
            case TriadPractice:
                switch (position){
                    case 0: return new NotePoolSelectionTab(filter);
                    case 1: return new TriadModeMiscSettingTab(filter);
                    default: return null;
                }
            case NoteGraphPractice:
                switch (position){
                    case 0: return new NotePoolSelectionTab(filter);
                    case 1: return new GraphModeMiscSettingTab(filter);
                    default: return null;
                }
            case SongPlaying:
                switch (position){
                    case 0: return new SongModeMiscSettingTab(filter);
                    default: return null;
                }
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
