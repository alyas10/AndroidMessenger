package com.example.androidmessenger;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class TestResult {
    private int correct;
    private int incorrect;

    public TestResult() {
        // Пустой конструктор для Firebase
    }

    public TestResult(int correct, int incorrect) {
        this.correct = correct;
        this.incorrect = incorrect;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(int incorrect) {
        this.incorrect = incorrect;
    }
}