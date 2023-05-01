package it.unibs.fp.tamaGolem;

import it.kibo.fp.lib.Menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        do {
            //Creazione della partita
            Game game = new Game();
            //Inizializzazione di tutti i dati relativi alla partita
            GameInit.startGame(game);
            System.out.print("\033[H\\033[2J");
            TamaGolem golem = new TamaGolem();
            System.out.println(game);
            Fight.stoneChoices(game, golem);
            Fight.stoneChoices(game, golem);
        } while (GameInit.quitGame());
    }
}
