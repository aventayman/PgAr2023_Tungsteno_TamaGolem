package it.unibs.fp.tamaGolem;

import it.kibo.fp.lib.Menu;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        do {
            // Stampa del menu principale
            int n = GameInit.startGame();

            //Inizializzazione dei dati relativi alla partita
            int g, s, c, spe;
            g = s = c = spe = 0;
            GameInit.dataInit(n, s, g, c, spe);
            Balance balance = new Balance(n);
            //Creazione della partita
            Game game = new Game(g, spe, n, c, balance);

            //Creazione dei giocatori con i loro golem, inizialmente senza pietre
            Player player1 = new Player(TamaGolem.createGolemList(n));
            Player player2 = new Player(TamaGolem.createGolemList(n));

            List<Element> elementChoice = new ArrayList<>();

            GameInit.stoneElementsChoice(elementChoice, game);
        }while (GameInit.quitGame());
    }
}
