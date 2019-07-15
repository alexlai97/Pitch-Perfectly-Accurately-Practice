package com.example.pitchperfectlyaccuratelypractice.activities;
import android.view.animation.Animation;

/**
 * TODO may delete this interace since no need
 * an interface of some functions to update view
 */
public interface updateViewInterface {
    /**
     * update frequency text view
     * @param freq
     * @param expectedFreq
     */
    public void updateFrequencyText(Long freq, Double expectedFreq);
    /**
     * update arrow text view (TODO need to generalize for tirad mode)
     * @param myString
     */
    public void updateArrowText(String myString);
    /**
     * update current pitch text showing on the right top corner
     * @param myString
     */
    public void updateCurrentPitchText(String myString);
    /**
     * update question texts
     * <p>
     *     A note has one text ("A4")
     *     A interval has two texts ("A4", "+ m3")
     *     A triad has three texts  ("C3", "E3", "G3")
     * </p>
     * @param texts
     */
    public void updateQuestionTexts(String [] texts);
    /**
     * update arrow animation (TODO need to generalize for triad mode)
     * @param myAnimation
     */
    public void updateArrowAnimation(Animation myAnimation);
}
