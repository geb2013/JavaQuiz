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
    public enum answerTypes { NOT_ANSWERED, TRUE, FALSE }

    QuestionBank() {
        allQuestions = new ArrayList<>();
        generateAllQuestions();
        currentQuestionIndex = -1;
    }

    private void generateAllQuestions() {
        allQuestions.add(new Question(R.string.question1, true));
        allQuestions.add(new Question(R.string.question2, false));
        allQuestions.add(new Question(R.string.question3, false));
        allQuestions.add(new Question(R.string.question4, false));
        allQuestions.add(new Question(R.string.question5, true));
        allQuestions.add(new Question(R.string.question6, false));
        allQuestions.add(new Question(R.string.question7, true));
        allQuestions.add(new Question(R.string.question8, true));
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

    public boolean onFirstQuestion() {
        return currentQuestionIndex == 0;
    }

    public ArrayList<Integer> getGivenAnswers() {
        ArrayList<Integer> givenAnswers = new ArrayList<>();

        for (int i = 0; i < allQuestions.size(); i++) {
            if (allQuestions.get(i).getGivenAnswer() == null) {
                // Not answered yet
                givenAnswers.add(answerTypes.NOT_ANSWERED.ordinal());
            } else if (allQuestions.get(i).getGivenAnswer()) {
                givenAnswers.add(answerTypes.TRUE.ordinal());
            } else if (!allQuestions.get(i).getGivenAnswer()) {
                givenAnswers.add(answerTypes.FALSE.ordinal());
            }
        }

        return givenAnswers;
    }

    public void setGivenAnswers(ArrayList<Integer> givenAnswers) {
        for (int i = 0; i < allQuestions.size(); i++) {
            if (givenAnswers.get(i) == answerTypes.TRUE.ordinal()) {
                allQuestions.get(i).setGivenAnswer(true);
            } else if (givenAnswers.get(i) == answerTypes.FALSE.ordinal()) {
                allQuestions.get(i).setGivenAnswer(false);
            }
        }
    }

    public int getScore() {
        int correctQuestions = 0;

        for (int i = 0; i < allQuestions.size(); i++) {
            if (allQuestions.get(i).gaveCorrectAnswer()) {
                correctQuestions++;
            }
        }

        return correctQuestions;
    }
    
    public int getPerfectScore() {
        return allQuestions.size();
    }

    public int getCheatScore() {
        int questionsCheated = 0;

        for (int i = 0; i < allQuestions.size(); i++) {
            if (allQuestions.get(i).getUsedCheat()) {
                questionsCheated++;
            }
        }

        return questionsCheated;
    }

    public boolean[] getCheats()
    {
        boolean[] questionsCheated = new boolean[allQuestions.size()];

        for (int i = 0; i < allQuestions.size(); i++) {
            questionsCheated[i] = allQuestions.get(i).getUsedCheat();
        }

        return questionsCheated;
    }

    public void setCheats(boolean[] questionsCheated)
    {
        for (int i = 0; i < allQuestions.size(); i++) {
            if (i < questionsCheated.length) {
                if (questionsCheated[i]) {
                    allQuestions.get(i).setUsedCheat();
                }
            } else {
                break;
            }
        }
    }
}
