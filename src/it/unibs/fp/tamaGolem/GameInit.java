package it.unibs.fp.tamaGolem;

import it.ayman.fp.lib.InputData;
import it.ayman.fp.lib.Menu;

/**
 * Classe che genera i menu d'inizializzazione della partita, con i relativi dati che andranno a influenzare
 * numero di golem, pietre, ecc.
 */
public class GameInit {
    private static final String CHOOSE_DIFFICULTY =
            "Choose the difficulty of the game:";
    private static final String EASY = "Easy (There will be 5 elements in the game)";
    private static final String MEDIUM = "Medium (There will be 7 elements in the game)";
    private static final String HARD = "Hard (There will be 10 elements in the game)";
    private static final String THANK_YOU = "> Thank you for your choice, the game will begin shortly";
    private static final String PLAY_AGAIN =
            "Do you want to play again?";

    /**
     * Metodo che genera il menu iniziale, fa scegliere la difficoltà e aggiunge gli elementi alla lista di elementi nel
     * game, in modo che successivamente si possa lavorare direttamente su quella
     */
    public static int getDifficulty() throws InterruptedException {
        int elementAmount = 0;
        String [] mode = {EASY, MEDIUM, HARD};
        Menu modeMenu = new Menu(CHOOSE_DIFFICULTY, mode);
        int choice = modeMenu.choose(true, false);

        switch (choice) {
            case 1 -> elementAmount = 5;
            case 2 -> elementAmount = 7;
            case 3 -> elementAmount = 10;
        }

        Menu.loadingMessage(THANK_YOU);

        return elementAmount;
    }



    /**
     * Metodo di uscita dal gioco
     * @return true se viene inserito Y, false se viene inserito N
     */
    public static boolean quitGame () {
        return InputData.readYesOrNo(PLAY_AGAIN);
    }

}
