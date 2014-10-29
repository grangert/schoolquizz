package org.grangert.schoolquizz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by grangert on 10/6/14.
 */
public class ABCActivity extends GameActivity {


    private final static Random RANDOM = new Random();
    //==============================================================================================
    //==============================================================================================
    private final static String[] LETTER = new String[]{
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"
    };

    @Deprecated
    protected int getGameDuration() {
        return 2000;
    }

    @Override
    protected List<String> getResponseToPickFrom(int amountWordOnScreen) {
        List<String> selectedSightWords = new ArrayList<String>();
        Set<Integer> alreadyPickedIdx = new HashSet<Integer>();
        for (int i = 0; i < amountWordOnScreen; ) {
            int a = RANDOM.nextInt(LETTER.length);
            if (alreadyPickedIdx.contains(a)) continue;
            alreadyPickedIdx.add(a);
            selectedSightWords.add(LETTER[a]);
            i++;
        }
        return selectedSightWords;
    }

    @Override
    protected int getAmountOfChoice() {
        return 8;
    }
}

