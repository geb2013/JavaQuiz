package com.bryant.garrett.javaquiz;

import android.support.annotation.StringRes;

public class Question {
    @StringRes
    private final int  questionResource;
    private final Boolean correctAnswer;

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
