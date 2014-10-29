package org.grangert.schoolquizz;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by grangert on 10/8/14.
 */
public abstract class AbstractGameActivity extends Activity {

    protected final static Random RANDOM = new Random();

    protected TextView amountCorrectTextView;
    protected TextView amountWrongTextView;
    protected ProgressBar countDownProgressBar;

    //==============================================================================================
    //==============================================================================================

    protected TextToSpeech textToSpeech;
    protected int amountCorrectResponse = 0;
    protected int amountWrongResponse = 0;
    protected Runnable countDownProgressBarRunnable = new Runnable() {
        public void run() {
            int currentValue = countDownProgressBar.getProgress();
            currentValue -= (getGameDuration() / (getGameDuration() / 10));
            countDownProgressBar.setProgress(currentValue);
            if (currentValue > 0)
                countDownProgressBarHandler.postDelayed(this, 10);
            else {
                amountWrongResponse++;
                refreshScore();
                newGame();
            }

        }
    };
    protected MediaPlayer mpCorrect;
    protected MediaPlayer mpWrong;
    protected Handler countDownProgressBarHandler = new Handler();


    protected void doOnCreate(int layoutId) {
        setContentView(layoutId);
        mpCorrect = MediaPlayer.create(this, R.raw.yes);
        mpWrong = MediaPlayer.create(this, R.raw.nope);
        this.amountCorrectTextView = TextView.class.cast(findViewById(R.id.amount_correct_textView));
        this.amountWrongTextView = TextView.class.cast(findViewById(R.id.amount_wrong_textView));
        this.countDownProgressBar = ProgressBar.class.cast(findViewById(R.id.count_down_progressbar));
        refreshScore();
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                newGame();
            }
        });
        textToSpeech.setSpeechRate(0.7f);
    }

    protected abstract int getGameDuration();

    protected abstract void newGame();

    protected void restartProgressBar() {
        countDownProgressBar.setMax(getGameDuration());
        countDownProgressBar.setProgress(getGameDuration());
        countDownProgressBarHandler.removeCallbacks(countDownProgressBarRunnable);
        countDownProgressBarHandler.post(countDownProgressBarRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownProgressBarHandler.removeCallbacks(countDownProgressBarRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        countDownProgressBarHandler.post(countDownProgressBarRunnable);
    }


    //==============================================================================================
    //==============================================================================================

    protected void onWrongAnswer() {
        mpWrong.start();
        amountWrongResponse++;
        refreshScore();
    }

    protected void onGoodAnswer(View v) {
        mpCorrect.start();
        amountCorrectResponse++;
        refreshScore();
        countDownProgressBarHandler.removeCallbacks(countDownProgressBarRunnable);
        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                newGame();
            }
        }, 1000l);
    }

    private void refreshScore() {
        this.amountCorrectTextView.setText(String.valueOf(amountCorrectResponse));
        this.amountWrongTextView.setText(String.valueOf(amountWrongResponse));
    }
}
