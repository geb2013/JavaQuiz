package com.bryant.garrett.javaquiz;

import java.util.ArrayList;

public class QuestionBank {
    private ArrayList<Question> allQuestions;
    private int currentQuestionIndex;

    QuestionBank() {
        allQuestions = new ArrayList<>();
        generateAllQuestions();
        currentQuestionIndex = -1;
    }

    private void generateAllQuestions() {
        allQuestions.add(new Question(R.string.question1, true));
        allQuestions.add(new Question(R.string.question2, true));
        allQuestions.add(new Question(R.string.question3, false));
        allQuestions.add(new Question(R.string.question4, false));
    }

    public Question getNextQuestion() {
        if (currentQuestionIndex < allQuestions.size() - 1) {
            currentQuestionIndex++;
        }

        return allQuestions.get(currentQuestionIndex);
    }

    public Question getPrevQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
        }

        return allQuestions.get(currentQuestionIndex);
    }
}
