package com.android.chrishsu.who_r_theyquiz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Setting vars
    private ImageView mQuizImage;
    private String mAnswer;
    private int mScore = 0;
    private int mQuizNum = 1;
    private int QuestionNum = 0;
    private int cbClicks = 0;
    private TextView mQuestionView;
    private TextView mQuizNumView;
    private TextView mNameView;
    private Questions mQuestions = new Questions();
    final CheckBox[] cbl = new CheckBox[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting views vars
        mQuestionView = (TextView) findViewById(R.id.question_textview);
        mQuizNumView = (TextView) findViewById(R.id.quiznum_textview);

        //Enter name info form
        validateName();

        //Init and update question
        updateQuestion();

        //Create Submit button listener
        Button submit = (Button) findViewById(R.id.button_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Logics for EditText questionary
                if (mQuestions.getType(QuestionNum) == "edittext") {
                    //Validate mAnswer with user's input
                    //Also, make sure both value are in small letters to be compared 
                    if (mQuestions.getCorrectAnswer(QuestionNum).toLowerCase().equals(mAnswer)) {
                        //If correct, score a point
                        mScore++;

                        //Display correct answer in toast
                        displayToastCorrectAnswer();
                    }else{
                        //Display wrong answer in toast
                        displayToastWrongAnswer();
                    }
                }

                //Logics for Checkbox questionary
                if (mQuestions.getType(QuestionNum) == "checkbox") {
                    //Iterate through checkboxes
                    for (CheckBox cb : cbl) {
                        if (cb.isChecked())
                            //Concatenate answers
                            mAnswer += cb.getText().toString();
                    }
                    //Validate answer
                    String correctAns = mQuestions.getCorrectAnswer(QuestionNum);
                    //Split correct answer by commma (,)
                    String[] ckboxAnsList = correctAns.split(",");
                    boolean isCorrect = false;

                    //Loop through the correct answers and compare with user answer.
                    //If any of the correct answer was checked, user scores.
                    for (int ci=0; ci<ckboxAnsList.length; ci++){
                        if (ckboxAnsList[ci].equals(mAnswer)) {
                            isCorrect = true;
                        }
                    }
                    if (isCorrect) {
                        //If correct, score a point
                        mScore++;
                    }

                    //Display correct answer in toast
                    displayToastCorrectAnswer();
                }
                //Logics for Radio Button questionary
                if (mQuestions.getType(QuestionNum) == "radiobutton") {
                    //Validate answer
                    if (mQuestions.getCorrectAnswer(QuestionNum).equals(mAnswer)) {
                        //If correct, score a point
                        mScore++;

                        //Display correct answer in toast
                        displayToastCorrectAnswer();
                    } else {
                        //Display wrong answer in toast
                        displayToastWrongAnswer();
                    }
                }
                //Pause for second
                SystemClock.sleep(1000);

                //If question# is reached total question pool,
                //show score page using intent
                if (QuestionNum == mQuestions.getLength() - 1) {
                    //Finished all quiz, show scores
                    Intent intent_result = new Intent(MainActivity.this,
                            ResultActivity.class);

                    //Also include number of questions and score vars
                    intent_result.putExtra("totalQuestions", mQuestions.getLength());
                    intent_result.putExtra("finalScore", mScore);
                    startActivity(intent_result);

                    //Reset all vars back to init
                    QuestionNum = 0;
                    mQuizNum = 1;
                    mScore = 0;
                } else {
                    //Continue to next questionary
                    QuestionNum++;
                    mQuizNum++;
                }
                //Update question content
                updateQuestion();
            }
        });
    }

    //Make a toast message for number of checkbox clicked
    private void displayToastCheckBoxClick(int count, int q) {
        //Get the string vars
        int maxAns = mQuestions.getMaxAnswer(q);
        String rb_click = getString(R.string.txt_toast_cb_clicks,
                String.valueOf(count), String.valueOf(maxAns));
        //Display toast
        Toast.makeText(MainActivity.this,
                rb_click,
                Toast.LENGTH_SHORT).show();
    }

    //Make a toast message for incorrect answer
    private void displayToastWrongAnswer() {
        //Get the string vars
        String wrong_ans = getString(R.string.txt_toast_wrong_ans,
                String.valueOf(mScore));
        //Display toast
        Toast.makeText(MainActivity.this,
                wrong_ans,
                Toast.LENGTH_SHORT).show();
    }

    //Make a toast message for correct answer
    private void displayToastCorrectAnswer(){

        //Get the string vars
        String correct_ans = getString(R.string.txt_toast_correct_ans,
                String.valueOf(mScore));
        //Display toast
        Toast.makeText(MainActivity.this,
                correct_ans,
                Toast.LENGTH_SHORT).show();
    }

    private void updateQuestion() {
        //Clean up all data from answers_layout
        LinearLayout answers_layout = (LinearLayout) findViewById(R.id.answers_layout);
        answers_layout.removeAllViews();
        mAnswer = "";

        //Set value to views
        mQuizNumView.setText(mQuizNum + "/" + String.valueOf(mQuestions.getLength()));
        mQuestionView.setText(mQuestions.getQuestion(QuestionNum));

        //Check which type of question: checkbox or radio
        if (mQuestions.getType(QuestionNum) == "checkbox") {
            showCheckBoxAnswers(QuestionNum);
        }
        if (mQuestions.getType(QuestionNum) == "radiobutton") {
            showRadioButtonAnswers(QuestionNum);
        }

        if (mQuestions.getType(QuestionNum) == "edittext") {
            showEditText(QuestionNum);
        }


        //Show the question main image
        showMainImage(QuestionNum);

        //Scroll back to Top
        ScrollView sv = (ScrollView) findViewById(R.id.scrollview);
        sv.smoothScrollTo(0, 0);
    }

    private void showMainImage(int qnum) {
        //Setting view var
        mQuizImage = (ImageView) findViewById(R.id.quiz_image);
        String img = mQuestions.getImage(QuestionNum);
        //Update image view
        mQuizImage.setImageResource(getResources().getIdentifier(img, "drawable", getPackageName()));
    }

    private void showEditText(int qnum) {
        //Setting answers_layout (linear view)
        final LinearLayout answers_layout = (LinearLayout) findViewById(R.id.answers_layout);

        //Create EditText
        EditText et = new EditText(this);
        //Set params
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        et.setLayoutParams(lp);
        answers_layout.addView(et);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //skip
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //skip
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mAnswer = editable.toString().toLowerCase();
            }
        });

    }

    private void showCheckBoxAnswers(int qnum) {
        //Setting answers_layout (linear view)
        final LinearLayout answers_layout = (LinearLayout) findViewById(R.id.answers_layout);
        //Reset checkbox click counts
        cbClicks = 0;
        //Create CheckBoxes in array list
        for (int i = 0; i <= 2; i++) {
            //Setting checkbox's params
            cbl[i] = new CheckBox(this);
            cbl[i].setText(mQuestions.getChoice(qnum)[i]);
            cbl[i].setTextColor(Color.BLACK);
            cbl[i].setPadding(8, 16, 8, 16);
            cbl[i].setTextSize(24);
            cbl[i].setId(i);

            //Add checkbox listener
            cbl[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //If click any checkbox, addto the cbClicks count
                    if(((CheckBox)view).isChecked()){
                        //Increment by 1
                        cbClicks++;
                        //Display toast message
                        displayToastCheckBoxClick(cbClicks, QuestionNum);
                    }else{
                        //If uncheck, decrease by 1
                        cbClicks--;
                        displayToastCheckBoxClick(cbClicks, QuestionNum);
                    }
                }
            });

            //Add to answer_layout view
            answers_layout.addView(cbl[i]);
        }

    }

    private void showRadioButtonAnswers(int qnum) {
        //Setting answers_layout (linear view)
        final LinearLayout answers_layout = (LinearLayout) findViewById(R.id.answers_layout);
        //Create RadioGroup
        RadioGroup rg = new RadioGroup(this);
        rg.setOrientation(RadioGroup.VERTICAL);
        //Set params
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        rg.setLayoutParams(lp);

        //Create Radio Buttons in array list
        final RadioButton[] rbl = new RadioButton[3];
        for (int i = 0; i <= 2; i++) {
            //Setting radio button params
            rbl[i] = new RadioButton(this);
            rbl[i].setText(mQuestions.getChoice(qnum)[i]);
            rbl[i].setTextColor(Color.BLACK);
            rbl[i].setPadding(8, 16, 8, 16);
            rbl[i].setTextSize(24);
            rbl[i].setId(i);
            //Add to radio group
            rg.addView(rbl[i]);
        }

        //Create button listener
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int ckId) {
                //Store the answer to mAnswer var when click
                mAnswer = mQuestions.getChoice(QuestionNum)[ckId];
            }
        });
        //Add to answers_layout view
        answers_layout.addView(rg);
    }

    private void validateName(){
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            Intent i = getIntent();
            String iName = i.getStringExtra("mName");
            mNameView = (TextView)findViewById(R.id.name_textview);
            mNameView.setText(String.format("Hi %s", iName.toUpperCase()));
        }else{
            Intent intent_form = new Intent(MainActivity.this,
                    FormActivity.class);
            startActivity(intent_form);
        }
    }
}
