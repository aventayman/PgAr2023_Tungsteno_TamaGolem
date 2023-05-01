package it.unibs.fp.tamaGolem;

public class Main {
    public static void main(String[] args) {
        do {
            //Creazione della partita
            Game game = new Game();
            //Inizializzazione di tutti i dati relativi alla partita
            GameInit.startGame(game);
            TamaGolem golem = new TamaGolem();
            System.out.println(game);
            Balance.createBalance(game);
        } while (GameInit.quitGame());
    }
}
