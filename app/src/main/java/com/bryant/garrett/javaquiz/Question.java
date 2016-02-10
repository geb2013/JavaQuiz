package com.bryant.garrett.javaquiz;

public class Question {
    private String questionText;
    private Boolean correctAnswer;

    public Question(String question, Boolean answer) {
        questionText = question;
        correctAnswer = answer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }
}
