package com.example.pitchperfectlyaccuratelypractice.common;

import com.example.pitchperfectlyaccuratelypractice.question.NoteQuestion;
import com.example.pitchperfectlyaccuratelypractice.question.Question;


public class Model {
    private Config config;
    private Question currentQuestion;
    private Mode currentMode;

    public Model() {
        config = new Config();
        currentQuestion = new NoteQuestion();
        currentMode = Mode.NotePractice;
    }

    public void setCurrentQuestion(Question q) {
        currentQuestion = q;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Config getConfig() {
        return config;
    }

    public void setCurrentMode(Mode currentMode) {
        this.currentMode = currentMode;
    }

    public Mode getCurrentMode() {
        return currentMode;
    }
}
