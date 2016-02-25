package com.bryant.garrett.javaquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinishActivity extends AppCompatActivity {

    private static final String TAG = "FinishActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");
        setContentView(R.layout.activity_finish);

        Intent myIntent = getIntent(); // gets the previously created intent
        setScoreText(myIntent.getIntExtra(ConstantValues.SCORE_KEY, 0),
                myIntent.getIntExtra(ConstantValues.TOTAL_SCORE_KEY, 0),
                myIntent.getIntExtra(ConstantValues.CHEAT_SCORE_KEY, 0));

        (findViewById(R.id.return_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadQuiz();
            }
        });
    }

    private void setScoreText(int score, int totalScore, int cheatedScore) {
        TextView scoreTextView = (TextView) findViewById(R.id.score_text);
        TextView completeTextView = (TextView) findViewById(R.id.complete_text);

        scoreTextView.setText(score + " out of " + totalScore);

        if (cheatedScore > 0) {
            completeTextView.setText(R.string.text_cheater_score);
            scoreTextView.setText(score + " out of " + totalScore + "\n(Cheating on " + cheatedScore + ")");
        } else if (score == totalScore) {
            completeTextView.setText(R.string.text_congratulations_score);
        } else {
            completeTextView.setText(R.string.text_score);
        }
    }

    public void reloadQuiz() {
        Log.d(TAG, "reloadQuiz() called");
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
