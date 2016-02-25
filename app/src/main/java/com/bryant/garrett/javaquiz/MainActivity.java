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
    private static final String QUESTION_INDEX_KEY = "questionIndex";
    private static final String GIVEN_ANSWERS_KEY = "givenAnswers";
    private static final String SCORE_KEY = "score";
    private static final String PERFECT_SCORE_KEY = "isPerfectScore";
    private static final String CORRECT_ANSWER = "correctAnswer";
    private static final String DISPLAYING_ANSWER = "displayingAnswer";
    private static final int REQUEST_CODE_CHEAT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");
        setContentView(R.layout.activity_main);

        Question startingQuestion;
        if (savedInstanceState != null && savedInstanceState.getInt(QUESTION_INDEX_KEY) > 0) {
            // Re-init Question Bank
            questionBank = new QuestionBank();
            startingQuestion = questionBank.getQuestionAt(savedInstanceState.getInt(QUESTION_INDEX_KEY));
        } else {
            // Initialize Question Bank
            questionBank = new QuestionBank();
            startingQuestion = questionBank.getNextQuestion();
        }

        if (savedInstanceState != null) {
            questionBank.setGivenAnswers(savedInstanceState.getIntegerArrayList(GIVEN_ANSWERS_KEY));
        }

        loadQuestion(startingQuestion);
        setNavigationListeners();
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

        outState.putInt(QUESTION_INDEX_KEY, questionBank.getCurrentQuestionIndex());
        outState.putIntegerArrayList(GIVEN_ANSWERS_KEY, questionBank.getGivenAnswers());
    }

    private void setNavigationListeners() {
        // Set the previous button
        (findViewById(R.id.prev_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadQuestion(questionBank.getPrevQuestion());
            }
        });

        // Set the next button
        (findViewById(R.id.next_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionBank.onLastQuestion()) {
                    loadFinishedActivity();
                } else {
                    loadQuestion(questionBank.getNextQuestion());
                }
            }
        });

        // Set the cheat button
        (findViewById(R.id.cheat_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCheatActivity();
            }
        });
    }

    public void loadFinishedActivity() {
        Log.d(TAG, "loadFinishedActivity() called");
        Intent intent = new Intent(this, FinishActivity.class);

        intent.putExtra(SCORE_KEY, questionBank.getScoreText());
        intent.putExtra(PERFECT_SCORE_KEY, questionBank.isPerfectScore());

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }

    public void loadCheatActivity() {
        Log.d(TAG, "loadCheatActivity() called");
        Intent intent = new Intent(this, CheatActivity.class);

        Question currentQuestion = questionBank.getCurrentQuestion();
        intent.putExtra(CORRECT_ANSWER, currentQuestion.getCorrectAnswer());

        startActivityForResult(intent, REQUEST_CODE_CHEAT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT &&
                data != null && data.getBooleanExtra(DISPLAYING_ANSWER, false)) {
            questionBank.getCurrentQuestion().setUsedCheat();
        }
    }


    private void loadQuestion(Question currentQuestion) {
        setOnClickHandlers();

        TextView mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setText(currentQuestion.getQuestionText());
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
    }

    public void onClickEvent(boolean response) {
        Question currentQuestion = questionBank.getCurrentQuestion();
        currentQuestion.setGivenAnswer(response);

        if (currentQuestion.getUsedCheat()) {
            Toast.makeText(MainActivity.this, R.string.cheat_response, Toast.LENGTH_SHORT).show();
        } else if (currentQuestion.gaveCorrectAnswer()) {
            Toast.makeText(MainActivity.this, R.string.correct_response, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, R.string.wrong_response, Toast.LENGTH_SHORT).show();
        }
    }
}
