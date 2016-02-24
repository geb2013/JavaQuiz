package com.bryant.garrett.javaquiz;

import java.util.ArrayList;

/**
 * Maintains a collection of Question objects.  The following methods can be used to navigate the
 * collection: getNextQuestion and getPrevQuestion.
 * @author Garrett
 * @version 1.2
 */
class QuestionBank {
    private final ArrayList<Question> allQuestions;
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

    public Question getCurrentQuestion() {
        return allQuestions.get(currentQuestionIndex);
    }

    public Question getNextQuestion() {
        if (currentQuestionIndex < allQuestions.size() - 1) {
            currentQuestionIndex++;
        }

        return allQuestions.get(currentQuestionIndex);
    }

    public Question getQuestionAt(int index) {
        if (index < allQuestions.size() && index >= 0) {
            currentQuestionIndex = index;
        } else {
            currentQuestionIndex = 0;
        }

        return allQuestions.get(currentQuestionIndex);
    }

    public Question getPrevQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
        }

        return allQuestions.get(currentQuestionIndex);
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public boolean onLastQuestion() {
        return currentQuestionIndex == allQuestions.size() - 1;
    }

    public ArrayList<Integer> getGivenAnswers() {
        ArrayList<Integer> givenAnswers = new ArrayList<>();

        for (int i = 0; i < allQuestions.size(); i++) {
            if (allQuestions.get(i).getGivenAnswer() == null) {
                // Not answered yet
                givenAnswers.add(0);
            } else if (allQuestions.get(i).getGivenAnswer()) {
                givenAnswers.add(1);
            } else if (!allQuestions.get(i).getGivenAnswer()) {
                givenAnswers.add(2);
            }
        }

        return givenAnswers;
    }

    public void setGivenAnswers(ArrayList<Integer> givenAnswers) {
        for (int i = 0; i < allQuestions.size(); i++) {
            if (givenAnswers.get(i) == 1) {
                allQuestions.get(i).setGivenAnswer(true);
            } else if (givenAnswers.get(i) == 2) {
                allQuestions.get(i).setGivenAnswer(false);
            }
        }
    }

    public String getScore() {
        int correctQuestions = 0;
        for (int i = 0; i < allQuestions.size(); i++) {
            if (allQuestions.get(i).gaveCorrectAnswer()) {
                correctQuestions++;
            }
        }

        return correctQuestions + " out of " + allQuestions.size();
    }
}
