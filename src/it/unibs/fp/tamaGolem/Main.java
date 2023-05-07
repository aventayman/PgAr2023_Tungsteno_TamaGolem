package it.unibs.fp.tamaGolem;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        do {
            int difficulty = GameInit.getDifficulty();

            int golemMaxHp = 10;
            var game = new Game(difficulty, golemMaxHp);

            Fight.startMatch(game);

            game.getBalance().printBalance();
        } while (GameInit.quitGame());
    }

}
