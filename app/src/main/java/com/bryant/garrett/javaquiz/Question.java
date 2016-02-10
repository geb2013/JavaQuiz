package com.bryant.garrett.javaquiz;

import android.support.annotation.StringRes;

public class Question {
    @StringRes
    private int  questionResource;
    private Boolean correctAnswer;

    Question(int question, Boolean answer) {
        questionResource = question;
        correctAnswer = answer;
    }

    public int getQuestionText() {
        return questionResource;
    }

    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }
}
