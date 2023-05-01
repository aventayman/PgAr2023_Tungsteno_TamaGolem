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
    private static final ArrayList<String> elements = new ArrayList<>(Arrays.asList(
            "WATER", "FIRE", "EARTH", "AIR", "ETHER", "PLASMA", "ELECTRO", "ANTIMATTER", "LIGHT", "DARKNESS"
    ));


    /**
     * Metodo che genera il menu iniziale, fa scegliere la difficoltà e aggiunge gli elementi alla lista di elementi nel
     * game, in modo che successivamente si possa lavorare direttamente su quella
     */
    public static void startGame(Game game) {
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

        //Aggiunta degli elementi che saranno presenti in partita
        for (int i = 0; i < elementAmount; i++)
            game.addElement(elements.get(i));


        game.setStonesPerGolem(GameInit.stonesPerGolem(elementAmount));
        game.setGolemNum(GameInit.golemNum(elementAmount, game.getStonesPerGolem()));
        game.setChestDim(GameInit.chestDim(elementAmount, game.getStonesPerGolem(), game.getGolemNum()));
        game.setStonesPerElement(GameInit.stonesPerElement(game.getChestDim(), elementAmount));
        game.setPlayer1(new Player(TamaGolem.createGolemList(elementAmount)));
        game.setPlayer2(new Player(TamaGolem.createGolemList(elementAmount)));
        createStoneChest(game);
    }

    /**
     * Metodo che ritorna il numero di pietre per golem per questa partita
     * Utilizza le formule fornite dal regolamento per calcolare il valore richiesto
     * @param amount numero N di elementi della partita
     * @return il numero di pietre per golem per la partita
     */
    private static int stonesPerGolem (int amount) {
        return (int)(Math.ceil((float)(amount+1)/3))+1;
    }

    /**
     * Metodo che ritorna il numero di golem per giocatore di questa partita
     * Utilizza le formule fornite dal regolamento per calcolare il valore richiesto
     * @param amount numero N di elementi della partita
     * @param stonesPerGolem il numero di pietre per golem per la partita
     * @return il numero di golem per la partita
     */
    private static int golemNum (int amount, int stonesPerGolem) {
        return (int)Math.ceil(((float)(amount-1)*(amount-2)/(2*stonesPerGolem)));
    }

    /**
     * Metodo che ritorna il numero di pietre che saranno presenti nella sacca comune
     * Utilizza le formule fornite dal regolamento per calcolare il valore richiesto
     * @param amount numero N di elementi della partita
     * @param stonesPerGolem il numero di pietre per golem per la partita
     * @param golemNum il numero di golem per la partita
     * @return il numero di pietre presenti nella sacca comune
     */
    private static int chestDim (int amount, int stonesPerGolem, int golemNum) {
        return (int)(Math.ceil((float)(2*golemNum*stonesPerGolem)/amount)) * amount;
    }

    /**
     * Metodo che fornisce il numero di pietre per ciascun elemento che ci saranno della sacca comune
     * Utilizza la formula fornita dal regolamento per calcolare il valore richiesto
     * @param chestDim il numero di pietre presenti nella sacca comune
     * @param amount numero N di elementi della partita
     * @return il numero di pietre per elemento
     */
    private static int stonesPerElement (int chestDim, int amount) {
        return chestDim/amount;
    }

    private static void createStoneChest (Game game){
        List<List<Stone>> chest = new ArrayList<>();
        for (int i = 0; i < game.getElements().size(); i++) {
            chest.add(i, new ArrayList<>());
            for (int j = 0; j < game.getStonesPerElement(); j++) {
                chest.get(i).add(new Stone(game.getElements().get(i)));
            }
        }
        game.setChest(chest);
    }

    /**
     * Metodo di uscita dal gioco
     * @return true se viene inserito Y, false se viene inserito N
     */
    public static boolean quitGame () {
        return InputData.readYesOrNo(EXIT);
    }

}
