package com.example.pitchperfectlyaccuratelypractice.model;

import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.ModeFragments.FragmentFactory;
import com.example.pitchperfectlyaccuratelypractice.ModeFragments.GeneralFragment;
import com.example.pitchperfectlyaccuratelypractice.music.Interval;
import com.example.pitchperfectlyaccuratelypractice.music.Note;
import com.example.pitchperfectlyaccuratelypractice.question.Question;
import com.example.pitchperfectlyaccuratelypractice.question.QuestionFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;


/**
 * model stores current config, current question, current fragment, current mode
 */
public class Model {
    private static final String TAG  = "Model";

    /**
     * stores current config
     */
    private Config currentConfig = new Config();
    /**
     * stores current question
     */
    private Question currentQuestion;
    /**
     * stores current current mode
     */
    private Mode currentMode;
    /**
     * stores current fragment
     */
    private GeneralFragment currentFragment;
    /**
     * factory to produce different fragments
     */
    private FragmentFactory fragmentFactory = new FragmentFactory();
    /**
     * factory to produce different questions
     */
    private QuestionFactory questionFactory = new QuestionFactory();
    /**
     * observers (to observe data change of this model)
     */
    private List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();

    /**
     *
     */
    public Model() {
        currentConfig = new Config();
        currentMode = Mode.NotePractice;
        currentQuestion = questionFactory.create(currentMode);
        currentFragment = fragmentFactory.create(currentMode);
    }

    /**
     * getter for current fragment
     * @return
     */
    public GeneralFragment getCurrentFragment() {
        return currentFragment;
    }

    /**
     * change current fragment given current mode and notify observers
     */
    public void refreshCurrentFragment() {
        notifyListeners(this, "currentFragment", this.currentFragment, this.currentFragment = fragmentFactory.create(currentMode));
    }

    /**
     * set note pool in current question and notify observers
     * @param notes
     */
    public void setNotePool(Note[] notes) {
        Question oldQuestion = currentQuestion;
        currentQuestion.setNotePool(notes);
        notifyListeners(this, "currentQuestion", oldQuestion, this.currentQuestion);
    }

    /**
     * set note pool in current question and notify observers
     * @param intervals
     */
    public void setIntervalPool(Interval[] intervals) {
        Question oldQuestion = currentQuestion;
        currentQuestion.setIntervalPool(intervals);
        notifyListeners(this, "currentQuestion", oldQuestion, this.currentQuestion);
    }

    /**
     * set current question and notify observers
     */
    public void setCurrentQuestion(Question q) {
        notifyListeners(this, "currentQuestion", this.currentQuestion, this.currentQuestion = q);
    }
    /**
     * getter for current question
     */
    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    /**
     * set current config and notify observers
     */
    public void setCurrentConfig(Config config) {
        notifyListeners(this, "currentConfig", this.currentConfig, this.currentConfig = config);
    }

    /**
     * getter for current config
     */
    public Config getCurrentConfig() {
        return currentConfig;
    }

    /**
     * set current mode and notify observers
     */

    public void setCurrentMode(Mode mode) {
        notifyListeners(this, "currentMode", this.currentMode, this.currentMode = mode);
    }


    /**
     * getter for current mode
     */
    public Mode getCurrentMode() {
        return currentMode;
    }

    /**
     * add an observer
     * @param new_listener
     */
    public void addChangeListener(PropertyChangeListener new_listener) {
        listeners.add(new_listener);
    }

    /**
     * notify all observers about an object change
     * @param object
     * @param property
     * @param oldValue
     * @param newValue
     */
    private void notifyListeners(Object object, String property, Object oldValue, Object newValue) {
        for (PropertyChangeListener listener: listeners) {
            listener.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }
}
