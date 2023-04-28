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
     * @param amount numero N di elementi della partita
     * @return il numero di pietre per golem per la partita
     */
    public static int stonesNum (int amount) {
        float s;
        s = (float)(amount+1)/3;
        s += 1;
        s = Math.round(s);
        return (int) s;
    }

    /**
     * Metodo che ritorna il numero di golem per giocatore di questa partita
     * @param amount numero N di elementi della partita
     * @return il numero di golem per la partita
     */
    public static int golemNum (int amount, int stonesNum) {
        float g;
        g = Math.round((float)(amount-1)*(amount-2)/2*stonesNum);
        return(int) g;
    }


}
