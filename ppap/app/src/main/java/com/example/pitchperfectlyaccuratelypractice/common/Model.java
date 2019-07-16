package com.example.pitchperfectlyaccuratelypractice.common;

import com.example.pitchperfectlyaccuratelypractice.question.Question;


public class Model {
    private Config config;
    private Question currentQuestion;
    private Mode curMode;

    public Config getConfig() {
        return config;
    }

    public Mode getCurMode() {
        return curMode;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }
}
