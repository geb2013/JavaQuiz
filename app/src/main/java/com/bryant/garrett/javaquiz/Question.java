package com.bryant.garrett.javaquiz;

import android.support.annotation.StringRes;

/**
 * Contains both a question and the correct answer.  The question is stored
 * as a reference to the corresponding string resource.
 * @author Garrett
 * @version 1.2
 */
public class Question {
    @StringRes
    private final int  questionResource;
    private final Boolean correctAnswer;
    private Boolean givenAnswer;

    Question(int question, boolean answer) {
        questionResource = question;
        correctAnswer = answer;
    }

    public int getQuestionText() {
        return questionResource;
    }

    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }

    public Boolean getGivenAnswer() { return givenAnswer; }

    public void setGivenAnswer(boolean value) { givenAnswer = value; }

    public Boolean gaveCorrectAnswer() { return correctAnswer == givenAnswer; }
}
