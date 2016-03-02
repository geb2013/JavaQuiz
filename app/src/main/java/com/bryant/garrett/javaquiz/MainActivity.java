package com.bryant.garrett.javaquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private QuestionBank questionBank;
    private boolean justCheated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");
        setContentView(R.layout.activity_main);

        Question startingQuestion;
        if (savedInstanceState != null && savedInstanceState.getInt(ConstantValues.QUESTION_INDEX_KEY) > 0) {
            // Re-init Question Bank
            questionBank = new QuestionBank();
            startingQuestion = questionBank.getQuestionAt(savedInstanceState.getInt(ConstantValues.QUESTION_INDEX_KEY));
        } else {
            // Initialize Question Bank
            questionBank = new QuestionBank();
            startingQuestion = questionBank.getNextQuestion();
        }

        if (savedInstanceState != null) {
            questionBank.setGivenAnswers(savedInstanceState.getIntegerArrayList(ConstantValues.GIVEN_ANSWERS_KEY));
            questionBank.setCheats(savedInstanceState.getBooleanArray(ConstantValues.ANSWERS_CHEATED));
            justCheated = savedInstanceState.getBoolean(ConstantValues.JUST_CHEATED);
        }

        loadQuestion(startingQuestion);
        setOnClickHandlers();
    }

    @Override
     public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState() called");

        outState.putInt(ConstantValues.QUESTION_INDEX_KEY, questionBank.getCurrentQuestionIndex());
        outState.putIntegerArrayList(ConstantValues.GIVEN_ANSWERS_KEY, questionBank.getGivenAnswers());
        outState.putBooleanArray(ConstantValues.ANSWERS_CHEATED, questionBank.getCheats());
        outState.putBoolean(ConstantValues.JUST_CHEATED, justCheated);
    }

    private void setNavigationListeners() {
        // Set the previous button
        (findViewById(R.id.prev_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadQuestion(questionBank.getPrevQuestion());
                justCheated = false;
            }
        });

        // Set the next button
        (findViewById(R.id.next_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadQuestion(questionBank.getNextQuestion());
                justCheated = false;
            }
        });

        // Set the cheat button
        (findViewById(R.id.cheat_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCheatActivity();
            }
        });

        // Set the cheat button
        (findViewById(R.id.view_score_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadScoreActivity();
            }
        });

        // Enable/disable prev/next buttons if needed
        refreshNavigationControls();
    }

    public void loadScoreActivity() {
        Log.d(TAG, "loadScoreActivity() called");
        Intent intent = new Intent(this, FinishActivity.class);

        intent.putExtra(ConstantValues.SCORE_KEY, questionBank.getScore());
        intent.putExtra(ConstantValues.TOTAL_SCORE_KEY, questionBank.getPerfectScore());
        intent.putExtra(ConstantValues.CHEAT_SCORE_KEY, questionBank.getCheatScore());

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void loadCheatActivity() {
        Log.d(TAG, "loadCheatActivity() called");
        Intent intent = new Intent(this, CheatActivity.class);

        Question currentQuestion = questionBank.getCurrentQuestion();
        intent.putExtra(ConstantValues.CORRECT_ANSWER_KEY, currentQuestion.getCorrectAnswer());

        startActivityForResult(intent, ConstantValues.REQUEST_CODE_CHEAT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == ConstantValues.REQUEST_CODE_CHEAT &&
                data != null && data.getBooleanExtra(ConstantValues.SHOWN_ANSWER_KEY, false) &&
                !questionBank.getCurrentQuestion().gaveCorrectAnswer()) {
            // Mark the question as cheated if the cheat activity returns saying that the
            // answer was shown and that question was not previously answered correctly.
            questionBank.getCurrentQuestion().setUsedCheat();
            justCheated = true;
        }
    }


    private void loadQuestion(Question currentQuestion) {
        TextView mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setText(currentQuestion.getQuestionText());

        refreshNavigationControls();
    }

    private void refreshNavigationControls() {
        (findViewById(R.id.next_button)).setEnabled(!questionBank.onLastQuestion());
        (findViewById(R.id.prev_button)).setEnabled(!questionBank.onFirstQuestion());
    }

    private void setOnClickHandlers() {
        // Set the true button response
        Button mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickEvent(true);
            }
        });

        // Set the false button response
        Button mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickEvent(false);
            }
        });

        setNavigationListeners();
    }

    public void onClickEvent(boolean response) {
        Question currentQuestion = questionBank.getCurrentQuestion();

        if (justCheated) {
            Toast.makeText(MainActivity.this, R.string.cheat_response, Toast.LENGTH_SHORT).show();
        } else {
            currentQuestion.setGivenAnswer(response);

            if (currentQuestion.gaveCorrectAnswer()) {
                Toast.makeText(MainActivity.this, R.string.correct_response, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, R.string.wrong_response, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
