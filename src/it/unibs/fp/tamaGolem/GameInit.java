package it.unibs.fp.tamaGolem;

import it.kibo.fp.lib.Menu;

/**
 * Classe che genera i menu d'inizializzazione della partita, con i relativi dati che andranno a influenzare
 * numero di golem, pietre, ecc.
 */
public class GameInit {
    /**
     * Metodo che genera il menu iniziale e registra la scelta della difficoltà
     * @return il numero di elementi che verranno utilizzati nella partita e nella generazione dell'equilibrio
     */
    public static int startGame() {
        int amount = 0;
        String [] mode = {"Easy", "Normal", "Hardcore"};
        Menu modeMenu = new Menu("TamaGolem", mode, true, true, true);
        int choice = modeMenu.choose();

        switch (choice) {
            case 1 -> amount = 3;
            case 2 -> amount = 6;
            case 3 -> amount = 9;
        }

        return amount;
    }

    /**
     * Metodo che ritorna il numero di pietre per golem per questa partita
     * Utilizza le formule fornite dal regolamento per calcolare il valore richiesto
     * @param amount numero N di elementi della partita
     * @return il numero di pietre per golem per la partita
     */
    public static int stonesNum (int amount) {
        return (int)(Math.ceil((float)(amount+1)/3))+1;
    }

    /**
     * Metodo che ritorna il numero di golem per giocatore di questa partita
     * Utilizza le formule fornite dal regolamento per calcolare il valore richiesto
     * @param amount numero N di elementi della partita
     * @param stonesNum il numero di pietre per golem per la partita
     * @return il numero di golem per la partita
     */
    public static int golemNum (int amount, int stonesNum) {
        return (int)Math.ceil(((float)(amount-1)*(amount-2)/2*stonesNum));
    }

    /**
     * Metodo che ritorna il numero di pietre che saranno presenti nella sacca comune
     * Utilizza le formule fornite dal regolamento per calcolare il valore richiesto
     * @param amount numero N di elementi della partita
     * @param stonesNum il numero di pietre per golem per la partita
     * @param golemNum il numero di golem per la partita
     * @return il numero di pietre presenti nella sacca comune
     */
    public static int chestDim (int amount, int stonesNum, int golemNum) {
        return (int)(Math.ceil((float)(2*golemNum*stonesNum)/amount)) * amount;
    }

    /**
     * Metodo che fornisce il numero di pietre per ciascun elemento che ci saranno della sacca comune
     * Utilizza la formula fornita dal regolamento per calcolare il valore richiesto
     * @param chestDim il numero di pietre presenti nella sacca comune
     * @param amount numero N di elementi della partita
     * @return il numero di pietre per elemento
     */
    public static int stonesPerElement (int chestDim, int amount) {
        return chestDim/amount;
    }

    public static void dataInit (int amount, int s, int g, int c, int spe){
        s = GameInit.stonesNum(amount);
        g = GameInit.golemNum(amount, s);
        c = GameInit.chestDim(amount, s, g);
        spe = GameInit.stonesPerElement(c, amount);
    }
}
