package it.unibs.fp.tamaGolem;

import java.util.ArrayList;

import it.ayman.fp.lib.PrettyStrings;
import it.ayman.fp.lib.Title;

public class Main {
    private static final String title = "TamaGolem";
    public static void main(String[] args) throws InterruptedException {
        do {
            System.out.println(Title.createTitle(title, true));
            int difficulty = GameInit.getDifficulty();
            var game = new Game(difficulty, 10);
            Fight.stoneChoices(game, new TamaGolem(new ArrayList<>(), 10));
            Fight.stoneChoices(game, new TamaGolem(new ArrayList<>(), 10));
        } while (GameInit.quitGame());
    }

}
