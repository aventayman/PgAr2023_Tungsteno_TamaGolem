package it.unibs.fp.tamaGolem;

import it.kibo.fp.lib.Menu;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        do {
            //Creazione della partita
            Game game = new Game();
            //Inizializzazione di tutti i dati relativi alla partita
            GameInit.startGame(game);

            System.out.println(game);
        } while (GameInit.quitGame());
    }
}
