package com.kuzco.animetimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar myBar;
    TextView myText;
    EditText timePassed;
    CountDownTimer me;
    MediaPlayer mp3;
    Button setTime;

    public void updateTimer(int secondsLeft){
        int minutes = secondsLeft/60;
        int seconds = secondsLeft - minutes * 60;
        String secondString = Integer.toString(seconds);
        if(secondString == "0"){
            secondString = "00";
        }
        else if(seconds <= 9){
            secondString = "0" + secondString;
        }
        myText.setText(Integer.toString(minutes)  + ":" + secondString);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myBar = (SeekBar) findViewById(R.id.myBar);
        myText = (TextView) findViewById(R.id.timerText);
        timePassed = (EditText) findViewById(R.id.timePassed);
        setTime = (Button) findViewById(R.id.setTime);
        myBar.setProgress(60);
        myBar.setMax(600);

        myBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }
    public void maxTime(View view){
        myBar.setMax(Integer.parseInt(timePassed.getText().toString()) * 60);
    }

    public void startTime(View view){
        myBar.setVisibility(View.INVISIBLE);
        timePassed.animate().alpha(0f).setDuration(2000);
        setTime.animate().alpha(0f).setDuration(2000);
        me = new CountDownTimer(myBar.getProgress() * 1000, 1000){
            public void onTick(long millisecondsUntilDone){
               updateTimer((int) millisecondsUntilDone/1000);

            }
            public void onFinish(){
                myBar.setVisibility(View.VISIBLE);
                mp3 = MediaPlayer.create(getApplicationContext(), R.raw.clocktimer);
                mp3.start();
                timePassed.animate().alpha(1f).setDuration(2000);
                setTime.animate().alpha(1f).setDuration(2000);
            }

        }.start();
    }
    public void stopTime(View view){
        myBar.setProgress(0);
        me.cancel();
        myBar.setVisibility(View.VISIBLE);
        timePassed.animate().alpha(1f).setDuration(2000);
        setTime.animate().alpha(1f).setDuration(2000);
        mp3.stop();
    }


}
