package it.unibs.fp.tamaGolem;

import it.ayman.fp.lib.AnsiColors;
import it.ayman.fp.lib.PrettyStrings;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        GameInit.printTitle();
        do {
            var game = new Game(GameInit.getPlayerName(1), GameInit.getPlayerName(2),
                    GameInit.getDifficulty(), GameInit.chooseGolemMaxHp());

            GameInit.printLoadingText();

            Fight.battle(game);

            game.getBalance().printBalance();
        } while (GameInit.quitGame());
    }

}
