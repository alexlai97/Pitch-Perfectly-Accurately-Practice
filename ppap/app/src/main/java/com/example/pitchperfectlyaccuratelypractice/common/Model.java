package com.example.pitchperfectlyaccuratelypractice.common;

import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.fragments.FragmentFactory;
import com.example.pitchperfectlyaccuratelypractice.fragments.GeneralFragment;
import com.example.pitchperfectlyaccuratelypractice.note.Note;
import com.example.pitchperfectlyaccuratelypractice.question.NoteQuestion;
import com.example.pitchperfectlyaccuratelypractice.question.Question;
import com.example.pitchperfectlyaccuratelypractice.question.QuestionFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;


public class Model {
    private static final String TAG  = "Model";
    private Config currentConfig;
    private Question currentQuestion;
    private Mode currentMode;
    private GeneralFragment currentFragment;
    private FragmentFactory fragmentFactory = new FragmentFactory();
    private QuestionFactory questionFactory = new QuestionFactory();

    private List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();

    public Model() {
        currentConfig = new Config();
        currentMode = Mode.NotePractice;
        currentQuestion = questionFactory.create(currentMode);
        currentFragment = fragmentFactory.create(currentMode);
    }


    public GeneralFragment getCurrentFragment() {
        return currentFragment;
    }

    public void setNotePool(Note[] notes) {
        Question oldQuestion = currentQuestion;
        currentQuestion.setNotePool(notes);
        notifyListeners(this, "currentQuestion", oldQuestion, this.currentQuestion);
    }

    public void setCurrentQuestion(Question q) {
        notifyListeners(this, "currentQuestion", this.currentQuestion, this.currentQuestion = q);
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentConfig(Config config) {
        notifyListeners(this, "currentConfig", this.currentConfig, this.currentConfig = config);
    }

    public Config getCurrentConfig() {
        return currentConfig;
    }

    public void setCurrentMode(Mode mode) {
        notifyListeners(this, "currentMode", this.currentMode, this.currentMode = mode);
        setCurrentQuestion(questionFactory.create(mode));
        notifyListeners(this, "currentFragment", this.currentFragment, this.currentFragment = fragmentFactory.create(mode));

        if (currentQuestion.getAnswerNotes().length == 0) {
            throw new AssertionError("property change; question has no notes");
        }
        Note.logNotes(TAG, currentQuestion.getAnswerNotes());
    }

    public Mode getCurrentMode() {
        return currentMode;
    }

    public void addChangeListener(PropertyChangeListener new_listener) {
        listeners.add(new_listener);
    }

    private void notifyListeners(Object object, String property, Object oldValue, Object newValue) {
        for (PropertyChangeListener listener: listeners) {
            listener.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }
}
