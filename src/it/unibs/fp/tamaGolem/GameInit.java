package it.unibs.fp.tamaGolem;

import it.ayman.fp.lib.InputData;
import it.ayman.fp.lib.Menu;
import it.ayman.fp.lib.Title;

/**
 * Classe che genera i menu d'inizializzazione della partita, con i relativi dati che andranno a influenzare
 * numero di golem, pietre, ecc.
 */
public class GameInit {
    private static final String TITLE = "TamaGolem";
    private static final String WELCOME = "> Welcome to TamaGolem, are you ready to fight to the last stone?";
    private static final String CHOOSE_DIFFICULTY = "Choose the difficulty of the game:";
    private static final String EASY = "Easy (There will be 5 elements in the game)";
    private static final String MEDIUM = "Medium (There will be 7 elements in the game)";
    private static final String HARD = "Hard (There will be 10 elements in the game)";
    private static final String THANK_YOU = "> Thank you, the game will begin shortly";
    private static final String CREATING_GOLEMS = "> Summoning all the TamaGolems";
    private static final String INITIALIZING_ELEMENTS = "> Raising all the elements needed from the depths of the Earth";
    private static final String BALANCING_ELEMENTS = "> Creating a perfect balance between all the elements";
    private static final String ALMOST_DONE = "> Preparing the fighting arena";
    private static final String PLAY_AGAIN = "Do you want to play again?";
    private static final String INSERT_PLAYER_NAME = "Insert the name of the %s player:";
    private static final String INSERT_GOLEM_HEALTH =
            "Insert the initial health of all the TamaGolems (it must be between 5 and 100):";
    private static final String [] PLAYER_COLORS = new String[] {"red", "blue"};
    private static final int DELAY = 500;

    public static void printTitle() throws InterruptedException {
        System.out.println(Title.createTitle(TITLE, true) + "\n");
        System.out.println(WELCOME + "\n");
        Menu.wait(DELAY * 5);
    }

    /**
     * Metodo che genera il menu iniziale, fa scegliere la difficoltà e aggiunge gli elementi alla lista di elementi nel
     * game, in modo che successivamente si possa lavorare direttamente su quella
     */
    public static int getDifficulty() {
        int elementAmount = 0;
        String [] mode = {EASY, MEDIUM, HARD};
        Menu modeMenu = new Menu(CHOOSE_DIFFICULTY, mode);
        int choice = modeMenu.choose(true, false);

        switch (choice) {
            case 1 -> elementAmount = 5;
            case 2 -> elementAmount = 7;
            case 3 -> elementAmount = 10;
        }

        return elementAmount;
    }

    public static void printLoadingText() throws InterruptedException {
        Menu.loadingMessage(THANK_YOU);
        Menu.wait(DELAY);
        System.out.println(CREATING_GOLEMS);
        Menu.wait(DELAY);
        System.out.println(INITIALIZING_ELEMENTS);
        Menu.wait(DELAY);
        System.out.println(BALANCING_ELEMENTS);
        Menu.wait(DELAY);
        Menu.loadingMessage(ALMOST_DONE);
    }

    public static String getPlayerName(int playerNumber) {
        return InputData.readNonEmptyString(
                String.format(INSERT_PLAYER_NAME, PLAYER_COLORS[playerNumber - 1]), true);
    }

    public static int chooseGolemMaxHp() {
        return InputData.readIntegerBetween(INSERT_GOLEM_HEALTH, 5, 100);
    }

    /**
     * Metodo di uscita dal gioco
     * @return true se viene inserito Y, false se viene inserito N
     */
    public static boolean quitGame () {
        return InputData.readYesOrNo(PLAY_AGAIN);
    }

}
