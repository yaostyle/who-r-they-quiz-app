package com.android.chrishsu.who_r_theyquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Normalizer;

public class FormActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        //Prevent keyboard from popping up when starting
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }
    //Start Quiz Button
    public void startBtn(View view){
        EditText mNameView = (EditText)findViewById(R.id.name_edittext);
        String mName = mNameView.getText().toString().trim();

        if (mName.equals("")){
            //If empty string, make a toast message
            String enter_name = getString(R.string.txt_pls_enter_ur_name);
            Toast.makeText(FormActivity.this,
                    enter_name,
                    Toast.LENGTH_SHORT).show();
        }else{
            //Go to quiz
            goToQuiz(mName);
        }
    }

    //Go to Quiz Intent
    private void goToQuiz(String mname) {
        Intent intent_main = new Intent(FormActivity.this, MainActivity.class);
        intent_main.putExtra("mName", mname);
        startActivity(intent_main);
    }

    //Prevent Back Button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            //do nothing
        }
        return false;
    }
}
