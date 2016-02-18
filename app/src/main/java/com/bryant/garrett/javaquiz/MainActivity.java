package com.bryant.garrett.javaquiz;

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
    }

    private void setNavigationListeners() {
        // Set the previous button response
        Button mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadQuestion(questionBank.getPrevQuestion());
            }
        });

        // Set the next button response
        Button mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionBank.onLastQuestion()) {
                    loadFinishedActivity();
                } else {
                    loadQuestion(questionBank.getNextQuestion());
                }
            }
        });
    }

    public void loadFinishedActivity() {
        Log.d(TAG, "loadFinishedActivity() called");
        Intent intent = new Intent(this, FinishActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }

    private void loadQuestion(Question currentQuestion) {
        if (currentQuestion.getCorrectAnswer()) {
            setTrueAnswerResponses();
        } else {
            setFalseAnswerResponses();
        }

        TextView mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setText(currentQuestion.getQuestionText());
    }

    private void setTrueAnswerResponses() {
        // Set the true button response
        Button mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, R.string.correct_response, Toast.LENGTH_SHORT).show();
            }
        });

        // Set the false button response
        Button mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, R.string.wrong_response, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setFalseAnswerResponses() {
        // Set the true button response
        Button mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, R.string.wrong_response, Toast.LENGTH_SHORT).show();
            }
        });

        // Set the false button response
        Button mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, R.string.correct_response, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
