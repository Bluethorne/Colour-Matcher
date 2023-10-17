package firstapp.main;

import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Game {

    private final Four answer = Four.getRandom();

    private int guessNum = 0;
    private Four currentGuess;

    public Game() {
        currentGuess = new Four();
    }

    public void guess() {
        if(!currentGuess.isFull()) {
            return;
        }
        fillAnswer();
        if(answer.isEqual(currentGuess)) {
            MainActivity.m.end();
            MainActivity.m.openPopUp(true, answer);
        } else {
            if(guessNum >= 7) {
                MainActivity.m.end();
                MainActivity.m.openPopUp(false, answer);
            } else {
                guessNum++;
                currentGuess = new Four();
            }
        }
    }

    public void fillAnswer() {
        int black = ContextCompat.getColor(MainActivity.m.getBaseContext(), R.color.black);
        int white = ContextCompat.getColor(MainActivity.m.getBaseContext(), R.color.white);
        ArrayList<Integer> ansCols = new ArrayList<Integer>();
        Integer[] a1 = answer.getCopy();
        Integer[] a2 = currentGuess.getCopy();
        for(int i = 0; i < 4; i++) {
            if(a1[i].equals(a2[i])) {
                ansCols.add(black);
                a1[i] = null;
                a2[i] = null;
            }
        }
        ArrayList<Integer> al1 = new ArrayList<Integer>(Arrays.asList(a1));
        ArrayList<Integer> al2 = new ArrayList<Integer>(Arrays.asList(a2));
        al1.removeIf((i) -> {return i == null;});
        al2.removeIf((i) -> {return i == null;});
        for(Integer i: al1) {
            if(al2.contains(i)) {
                al2.remove(i);
                ansCols.add(white);
            }
        }
        Collections.shuffle(ansCols);
        int id = MainActivity.m.getResources().getIdentifier("guess" + (MainActivity.game.guessNum+1), "id", "firstapp.main");
        View guess = MainActivity.m.findViewById(id);
        ViewGroup ans = guess.findViewById(guess.getResources().getIdentifier("cl8", "id", "firstapp.main"));
        for(int i = 0; i < ansCols.size(); i++) {
            AnswerCircle ac = (AnswerCircle) ans.getChildAt(i);
            ac.setColor(ansCols.get(i));
        }
    }

    public int getCurrentGuess() {
        return guessNum;
    }

    public Four getCurrentFour() {
        return currentGuess;
    }

}
