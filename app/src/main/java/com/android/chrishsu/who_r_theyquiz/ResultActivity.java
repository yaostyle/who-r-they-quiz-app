package com.android.chrishsu.who_r_theyquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    //Setting vars
    private int totalQuestions;
    private int finalScore;
    private TextView mCorrectText;
    private TextView mPercentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Setting views var
        mCorrectText = (TextView)findViewById(R.id.correct_textview);
        mPercentText= (TextView)findViewById(R.id.percent_textview);

        //Getting intent values
        Intent intent = getIntent();
        totalQuestions = intent.getIntExtra("totalQuestions", 0);
        finalScore = intent.getIntExtra("finalScore", 0);

        //Calculate score percentage
        int mPercentScore = finalScore * 100 / totalQuestions;

        //Update views
        mPercentText.setText(String.format("%s%%",Integer.toString(Integer.valueOf(mPercentScore)) ));
        //Getting text vars
        String final_score_txt = getString(R.string.txt_youve_got_x_out_of_x_correct,
                finalScore,totalQuestions);
        mCorrectText.setText(final_score_txt);

    }

    //Restart Quiz Button pressed
    public void restartBtn(View view){
        super.onBackPressed();
    }
}
