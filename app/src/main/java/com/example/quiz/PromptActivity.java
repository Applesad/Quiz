package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PromptActivity extends AppCompatActivity {

    private boolean correctAnswer;
    private Button showButton;
    private TextView answerTextView;

    public static final String KEY_EXTRA_ANSWER_SHOWN = "com.example.quiz.answerShown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);

        correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER, true);
        showButton = findViewById(R.id.pokaz_odpowiedz);
        answerTextView = findViewById(R.id.odpowiedz);

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answer = correctAnswer ? R.string.buttonTrue : R.string.buttonFalse;
                answerTextView.setText(answer);
                setAnswerShownResult(true);
            }
        });
    }
    private void setAnswerShownResult(boolean answerWasShown){
        Intent intent = new Intent();
        intent.putExtra(KEY_EXTRA_ANSWER_SHOWN, answerWasShown);
        setResult(RESULT_OK, intent);
    }
}