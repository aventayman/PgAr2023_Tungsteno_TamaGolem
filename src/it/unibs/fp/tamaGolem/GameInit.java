package it.unibs.fp.tamaGolem;

import it.kibo.fp.lib.InputData;
import it.kibo.fp.lib.Menu;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Classe che genera i menu d'inizializzazione della partita, con i relativi dati che andranno a influenzare
 * numero di golem, pietre, ecc.
 */
public class GameInit {

    private static final String INCORRECT_ELEMENT =
            "Incorrect value entered. Please insert another value for the element choice: ";
    private static final String CHOOSE_ELEMENT =
            "Choose the element number: ";
    private static final String NEXT_ELEMENT =
            "Choose the next element from the previous list: ";
    private static final String CHOOSE_DIFFICULTY =
            "Choose the difficulty of the game: ";
    private static final String EXIT =
            "Do you want to play again? (Y/N) ";

    /**
     * Metodo che genera il menu iniziale, fa scegliere la difficoltà e aggiunge gli elementi alla lista di elementi nel
     * game, in modo che successivamente si possa lavorare direttamente su quella
     */
    public static int getDifficulty() {
        int elementAmount = 0;
        String [] mode = {"Easy", "Normal", "Hardcore"};
        Menu modeMenu = new Menu("TamaGolem", mode, false, true, true);
        System.out.println(CHOOSE_DIFFICULTY);
        int choice = modeMenu.choose();

        switch (choice) {
            case 1 -> elementAmount = 5;
            case 2 -> elementAmount = 7;
            case 3 -> elementAmount = 10;
        }

        return elementAmount;
    }



    /**
     * Metodo di uscita dal gioco
     * @return true se viene inserito Y, false se viene inserito N
     */
    public static boolean quitGame () {
        return InputData.readYesOrNo(EXIT);
    }

}
