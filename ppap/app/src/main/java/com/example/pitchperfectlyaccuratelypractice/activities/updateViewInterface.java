package com.example.pitchperfectlyaccuratelypractice.activities;
import android.view.animation.Animation;

public interface updateViewInterface {
    // Declaration of the template function for the interface
    public void updateFrequencyText(String myString);
    public void updateArrowText(String myString);
    public void updateCurrentPitchText(String myString);
    public void updateQuestionText(String myString);
    public void updateArrowAnimation(Animation myAnimation);
}
