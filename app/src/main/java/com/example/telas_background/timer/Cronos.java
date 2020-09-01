package com.example.telas_background.timer;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

public class Cronos {

    private CronosInterface cronosInterface;
    private CountDownTimer count;
    private long millisPassed;
    private long millisInit;
    private Context context;
    private Boolean isRunning;
    private Boolean isFinish;

    public Cronos(CronosInterface cronosInterface , int seconds) {
        millisInit = seconds * 1000;
        this.cronosInterface = cronosInterface;
        isRunning = false;
    }

        public void startOrPlay(){
            isFinish = false;
            if(isRunning){
                play();
            }else{
                start();
            }
        }

        private void start(){
                if(!isRunning) {
                    count = new CountDownTimer(millisInit, 1000) {

                        public void onTick(long millisUntilFinished) {
                            //   mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                            millisPassed = millisUntilFinished;
                            //Log.d("Mili start", millisPassed + "");
                        }

                        public void onFinish() {
                            finish();
                        }
                    }.start();

                     //isFinish = false;
                }
            }

        private void play(){
            if(isRunning) {
                count = new CountDownTimer( millisPassed, 1000) {
                    public void onTick(long millisUntilFinished) {
                        //   mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                        millisPassed = millisUntilFinished;
                       // Log.d("Mili play", millisPassed + "");
                    }
                    public void onFinish() {
                        finish();
                    }
                }.start();
            }
        }

        public void pause(){
            if (count != null) {
                if (!isFinish) {
                    Log.d("Timer pause", millisPassed + "");
                    isRunning = true;
                    count.cancel();
                } else {
                    stop();
                }
            }
        }

        public void stop(){
            if (count != null) {
                Log.d("Timer stop", millisPassed + "");
                isFinish = true;
                isRunning = false;
                millisPassed = 0;
                count.cancel();
                finish();
            }
        }



        public void finish(){
            isRunning = false;
            isFinish = true;
            isFinish();
        }

        public void isFinish(){
            cronosInterface.cronosFinish();
        }

        public long getMillisPassed(){
            return millisPassed;
        }
}


