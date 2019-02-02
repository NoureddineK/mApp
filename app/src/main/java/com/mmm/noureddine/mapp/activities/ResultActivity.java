package com.mmm.noureddine.mapp.activities;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mmm.noureddine.mapp.R;

public class ResultActivity extends AppCompatActivity {
    /*Button buttonStart;
    ProgressBar progressBar;
    TextView textCounter;
    MyCountDownTimer myCountDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        buttonStart = (Button)findViewById(R.id.start);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        textCounter = (TextView)findViewById(R.id.counter);

        buttonStart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                progressBar.setProgress(0);
                myCountDownTimer = new MyCountDownTimer(30000, 1500);
                myCountDownTimer.start();
                buttonStart.setEnabled(false);
            }});

    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if(millisUntilFinished > 10000){
                textCounter.setText(String.valueOf(millisUntilFinished).substring(0,2));
            }
            else {
                textCounter.setText(String.valueOf(millisUntilFinished).substring(0,1));

            }
            int progress = (int) (millisUntilFinished/300);
            progressBar.setProgress(progress);
        }

        @Override
        public void onFinish() {
            textCounter.setText("Task completed");
            progressBar.setProgress(100);
            buttonStart.setEnabled(true);
        }

    }*/
}