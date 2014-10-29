package org.grangert.schoolquizz;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class CountingFingersActivity extends AbstractGameActivity {
    private ImageView fingersImageView;
    private Drawable[] fingerImages = new Drawable[11];
    private int valueToFind = 0;

    protected int getGameDuration() {
        return 5000;
    }

    @Override
    protected void newGame() {
        valueToFind = RANDOM.nextInt(fingerImages.length);
        this.fingersImageView.setImageDrawable(fingerImages[valueToFind]);
        restartProgressBar();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doOnCreate(R.layout.activity_counting_fingers);

        fingerImages[0] = getResources().getDrawable(R.drawable.finger_0);
        fingerImages[1] = getResources().getDrawable(R.drawable.finger_1);
        fingerImages[2] = getResources().getDrawable(R.drawable.finger_2);
        fingerImages[3] = getResources().getDrawable(R.drawable.finger_3);
        fingerImages[4] = getResources().getDrawable(R.drawable.finger_4);
        fingerImages[5] = getResources().getDrawable(R.drawable.finger_5);
        fingerImages[6] = getResources().getDrawable(R.drawable.finger_6);
        fingerImages[7] = getResources().getDrawable(R.drawable.finger_7);
        fingerImages[8] = getResources().getDrawable(R.drawable.finger_8);
        fingerImages[9] = getResources().getDrawable(R.drawable.finger_9);
        fingerImages[10] = getResources().getDrawable(R.drawable.finger_10);

        Button.class.cast(findViewById(R.id.button0)).setOnClickListener(new AnswerListener(0));
        Button.class.cast(findViewById(R.id.button1)).setOnClickListener(new AnswerListener(1));
        Button.class.cast(findViewById(R.id.button2)).setOnClickListener(new AnswerListener(2));
        Button.class.cast(findViewById(R.id.button3)).setOnClickListener(new AnswerListener(3));
        Button.class.cast(findViewById(R.id.button4)).setOnClickListener(new AnswerListener(4));
        Button.class.cast(findViewById(R.id.button5)).setOnClickListener(new AnswerListener(5));
        Button.class.cast(findViewById(R.id.button6)).setOnClickListener(new AnswerListener(6));
        Button.class.cast(findViewById(R.id.button7)).setOnClickListener(new AnswerListener(7));
        Button.class.cast(findViewById(R.id.button8)).setOnClickListener(new AnswerListener(8));
        Button.class.cast(findViewById(R.id.button9)).setOnClickListener(new AnswerListener(9));
        Button.class.cast(findViewById(R.id.button10)).setOnClickListener(new AnswerListener(10));
        fingersImageView = ImageView.class.cast(findViewById(R.id.imageView));

    }


    private class AnswerListener implements View.OnClickListener {
        int value;

        AnswerListener(int value) {
            this.value = value;
        }

        @Override
        public void onClick(View v) {
            if (valueToFind == value)
                onGoodAnswer(v);
            else
                onWrongAnswer();

        }
    }
}
