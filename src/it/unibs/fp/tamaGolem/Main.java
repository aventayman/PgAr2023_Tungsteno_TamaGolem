package it.unibs.fp.tamaGolem;

public class Main {
    public static void main(String[] args) {
        do {
            var game = new Game(GameInit.getDifficulty(), 10);
            TamaGolem golem = new TamaGolem();
            System.out.println(game);
        } while (GameInit.quitGame());
    }
}
