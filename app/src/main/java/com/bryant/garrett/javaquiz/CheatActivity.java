package com.bryant.garrett.javaquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CheatActivity extends AppCompatActivity {
    private static final String TAG = "CheatActivity";
    private static final String CORRECT_ANSWER = "correctAnswer";
    private static final String DISPLAYING_ANSWER = "displayingAnswer";

    private boolean correctAnswer;
    private boolean displayingCurrentAnswer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");
        setContentView(R.layout.activity_cheat);

        Intent myIntent = getIntent(); // gets the previously created intent
        correctAnswer = myIntent.getBooleanExtra(CORRECT_ANSWER, false);

        if (savedInstanceState != null && savedInstanceState.getBoolean(DISPLAYING_ANSWER)) {
            displayAnswer();
        } else {
            (findViewById(R.id.show_answer_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayAnswer();
                }
            });
        }
    }

    public void displayAnswer() {
        displayingCurrentAnswer = true;
        TextView answerTextView = (TextView) findViewById(R.id.answer_text);
        if (correctAnswer) {
            answerTextView.setText("True");
        } else {
            answerTextView.setText("False");
        }

        setAnswerShownResult();
    }

    private void setAnswerShownResult() {
        Intent data = new Intent();
        data.putExtra(DISPLAYING_ANSWER, displayingCurrentAnswer);
        setResult(RESULT_OK, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState() called");

        outState.putBoolean(DISPLAYING_ANSWER, displayingCurrentAnswer);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
