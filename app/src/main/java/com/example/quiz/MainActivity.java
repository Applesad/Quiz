package com.example.quiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public static final String KEY_EXTRA_ANSWER = "com.example.quiz.correctAnswer";
    private static final String QUIZ_TAG = "MainActivity";
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private static final int REQUEST_CODE_PROMPT = 0;
    private boolean answerWasShown;

    private Button buttonTrue;
    private Button buttonFalse;
    private Button buttonNext;
    private Button buttonHelp;
    private TextView textViewQuestion;

    private int currentIndex = 0;

    private Question[] questions = new Question[]{

            new Question(R.string.qActivity,true),
            new Question(R.string.qFindResources,false),
            new Question(R.string.qListener,true),
            new Question(R.string.qResources,true),
            new Question(R.string.qVersion,false)
    };

    private void checkAnswer(boolean userAnswer){
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();

        String resultMessage;
        if(answerWasShown){
            resultMessage = "Była pokazana!";
        } else {
            if(userAnswer == correctAnswer){
                resultMessage = "Odpowiedź prawidłowa";
            } else {
                resultMessage = "Odpowiedź błędna";
            }
        }

        Toast.makeText(this,resultMessage,Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion(){
        textViewQuestion.setText(questions[currentIndex].getQuestionId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(QUIZ_TAG, "zostało wywołane onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(QUIZ_TAG, "zostało wywołane onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(QUIZ_TAG, "zostało wywołane onStart");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(QUIZ_TAG, "zostało wywołane onPause");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(QUIZ_TAG, "zostało wywołane onResume");
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG,"Wywołana");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode != RESULT_OK) return;
        if(requestCode == REQUEST_CODE_PROMPT){
            if(data == null) return;
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        buttonTrue = findViewById(R.id.buttonTrue);
        buttonFalse = findViewById(R.id.buttonFalse);
        buttonNext = findViewById(R.id.buttonNext);
        buttonHelp = findViewById(R.id.buttonHelp);
        textViewQuestion = findViewById(R.id.textViewQuestion);


        buttonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        buttonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerWasShown = false;
                currentIndex = (currentIndex + 1)%questions.length;
                setNextQuestion();
            }
        });
        setNextQuestion();
        buttonHelp.setOnClickListener((v) -> {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
                startActivityForResult(intent, REQUEST_CODE_PROMPT);

        });
        textViewQuestion.setText(questions[currentIndex].getQuestionId());




    }
}