package org.grangert.schoolquizz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by grangert on 10/6/14.
 */
public class QuestionGenerator {
    private final static Random RANDOM = new Random();

    public static int getGameDuration() {
        return 5000;
    }

    public static List<String> getResponseToPickFrom(int amountChoiceOnScreen) {
        List<String> responseChoices = new ArrayList<String>();
        Set<Integer> alreadyPickedIdx = new HashSet<Integer>();
        for (int i = 0; i < amountChoiceOnScreen; ) {
            int a = RANDOM.nextInt(40);
            if (alreadyPickedIdx.contains(a)) continue;
            alreadyPickedIdx.add(a);
            responseChoices.add(String.valueOf(a));
            i++;
        }
        return responseChoices;
    }
}
