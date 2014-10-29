package org.grangert.schoolquizz;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

/**
 * Created by grangert on 10/5/14.
 */
public abstract class GameActivity extends AbstractGameActivity {


    //==============================================================================================
    //==============================================================================================
    private GridView responseGridView;
    private int amountResponseOnScreen = 0;

    protected abstract List<String> getResponseToPickFrom(int amountResponseOnScreen);

    //==============================================================================================
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doOnCreate(R.layout.game_activity);
        responseGridView = GridView.class.cast(findViewById(R.id.response_gridview));
        amountResponseOnScreen = getAmountOfChoice();

    }

    protected abstract int getAmountOfChoice();

    //==============================================================================================
    //==============================================================================================


    protected void newGame() {
        final List<String> responseChoices = getResponseToPickFrom(amountResponseOnScreen);
        restartProgressBar();
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
}