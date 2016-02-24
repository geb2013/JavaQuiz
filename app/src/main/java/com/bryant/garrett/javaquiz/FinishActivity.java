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
    private static final String SCORE_KEY = "score";
    private static final String PERFECT_SCORE_KEY = "isPerfectScore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");
        setContentView(R.layout.activity_finish);

        Intent myIntent = getIntent(); // gets the previously created intent
        String scoreText = myIntent.getStringExtra(SCORE_KEY);
        boolean isPerfectScore = myIntent.getBooleanExtra(PERFECT_SCORE_KEY, false);

        ((TextView) findViewById(R.id.score_text)).setText(scoreText);

        if (isPerfectScore) {
            ((TextView) findViewById(R.id.complete_text)).setText("Congratulations!\nYou got:");
        }

        (findViewById(R.id.return_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadQuiz();
            }
        });
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
