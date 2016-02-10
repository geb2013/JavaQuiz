package com.bryant.garrett.javaquiz;

import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private QuestionBank questionBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Question Bank
        questionBank = new QuestionBank();
        loadQuestion(questionBank.getNextQuestion());
        setNavigationListeners();
    }

    private void setNavigationListeners() {
        // Set the true button response
        Button mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadQuestion(questionBank.getPrevQuestion());
            }
        });

        // Set the false button response
        Button mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadQuestion(questionBank.getNextQuestion());
            }
        });
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
