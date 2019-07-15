package com.example.pitchperfectlyaccuratelypractice.activities;
import android.view.animation.Animation;

public interface updateViewInterface {
    // Declaration of the template function for the interface
    public void updateFrequencyText(Long freq, Double expectedFreq);
    public void updateArrowText(String myString);
    public void updateCurrentPitchText(String myString);
    public void updateQuestionTexts(String [] texts);
    public void updateArrowAnimation(Animation myAnimation);
}
