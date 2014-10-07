package org.grangert.schoolquizz;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

/**
 * Created by grangert on 10/5/14.
 */
public class GameActivity extends Activity {


    private final static Random RANDOM = new Random();
    //==============================================================================================
    //==============================================================================================
    private GridView responseGridView;
    private TextView amountCorrectTextView;
    private TextView amountWrongTextView;
    private ProgressBar countDownProgressBar;

    //==============================================================================================
    //==============================================================================================

    private TextToSpeech textToSpeech;
    private int amountResponseOnScreen = 0;
    private int amountCorrectResponse = 0;
    private int amountWrongResponse = 0;
    private MediaPlayer mpCorrect;
    private MediaPlayer mpWrong;

    private Handler countDownProgressBarHandler = new Handler();
    private Runnable countDownProgressBarRunnable = new Runnable() {
        public void run() {
            int currentValue = countDownProgressBar.getProgress();
            currentValue -= (QuestionGenerator.getGameDuration() / (QuestionGenerator.getGameDuration() / 10));
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
    //==============================================================================================
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        mpCorrect = MediaPlayer.create(this, R.raw.yes);
        mpWrong = MediaPlayer.create(this, R.raw.nope);
        this.responseGridView = GridView.class.cast(findViewById(R.id.response_gridview));
        this.amountCorrectTextView = TextView.class.cast(findViewById(R.id.amount_correct_textView));
        this.amountWrongTextView = TextView.class.cast(findViewById(R.id.amount_wrong_textView));
        this.countDownProgressBar = ProgressBar.class.cast(findViewById(R.id.count_down_progressbar));
        refreshScore();
        amountResponseOnScreen = getSharedPreferences(getPackageName(), MODE_PRIVATE).getInt("amountOfChoice", 4);
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                newGame();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownProgressBarHandler.removeCallbacks(countDownProgressBarRunnable);
    }

//==============================================================================================
    //==============================================================================================


    private void newGame() {
        final List<String> responseChoices = QuestionGenerator.getResponseToPickFrom(amountResponseOnScreen);
        countDownProgressBar.setMax(QuestionGenerator.getGameDuration());
        countDownProgressBar.setProgress(QuestionGenerator.getGameDuration());
        countDownProgressBarHandler.removeCallbacks(countDownProgressBarRunnable);
        countDownProgressBarHandler.post(countDownProgressBarRunnable);
        final int pickedOne = RANDOM.nextInt(amountResponseOnScreen);
        textToSpeech.speak(responseChoices.get(pickedOne), MODE_APPEND, null);
        responseGridView.setAdapter(new ResponseChoiceAdapter(this, pickedOne, responseChoices));
        ImageButton.class.cast(findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.speak(responseChoices.get(pickedOne), MODE_APPEND, null);
            }
        });
    }

    //==============================================================================================
    //==============================================================================================

    /**
     *
     */
    private class ResponseChoiceAdapter extends ArrayAdapter<String> implements ListAdapter {

        private int pickedOne = 0;

        /**
         * @param gameActivity
         * @param pickedOne
         * @param responseChoices
         */
        public ResponseChoiceAdapter(GameActivity gameActivity, int pickedOne, List<String> responseChoices) {
            super(gameActivity, R.layout.response_layout, responseChoices);
            this.pickedOne = pickedOne;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(getContext(), R.layout.response_layout, null);
            TextView textView = TextView.class.cast(convertView.findViewById(R.id.response_textview));
            textView.setText(getItem(position));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == pickedOne) {
                        onGoodAnswer(v);
                    } else {
                        onWrongAnswer();
                    }
                }
            });
            return convertView;
        }
    }

    private void onWrongAnswer() {
        mpWrong.start();
        amountWrongResponse++;
        refreshScore();
    }

    private void onGoodAnswer(View v) {
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