package org.grangert.schoolquizz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by grangert on 10/6/14.
 */
public class SightWordsActivity extends GameActivity {


    private final static Random RANDOM = new Random();
    //==============================================================================================
    //==============================================================================================
    private final static String[] SIGHTWORDS = new String[]{
            "at", "his", "her", "be", "this", "have", "had", "or", "by", "all", "were", "we", "can", "there", "when", "what", "said", "them", "fun", "do",
            "the", "like", "you", "went", "to", "of", "and", "in", "is", "that", "it", "he", "for", "was", "on", "are", "as", "with", "she", "they"
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
            int a = RANDOM.nextInt(SIGHTWORDS.length);
            if (alreadyPickedIdx.contains(a)) continue;
            alreadyPickedIdx.add(a);
            selectedSightWords.add(SIGHTWORDS[a]);
            i++;
        }
        return selectedSightWords;
    }
}

