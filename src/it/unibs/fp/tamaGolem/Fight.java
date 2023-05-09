package it.unibs.fp.tamaGolem;

import it.ayman.fp.lib.*;

import java.util.ArrayList;
import java.util.List;

public class Fight {
    private static final String RED_ATTENTION = PrettyStrings.colorString("Attention!", AnsiColors.RED);
    private static final String COMMAND_SIGN = "> ";
    private static final String SELECT_STONE = COMMAND_SIGN +
                    "%s, select the stones you want to give to %s from the chest:";
    private static final String STONE_CHOICE = "Insert the index of the stone you want to give to your TamaGolem:";
    private static final String STONES_FINISHED = COMMAND_SIGN + RED_ATTENTION + "\n" +
            COMMAND_SIGN + "The chosen stone has ran out.";
    private static final String DAMAGE_DEALT = COMMAND_SIGN + "%s with %s stone dealt %d damage to %s";
    private static final String NO_DAMAGE_DEALT = COMMAND_SIGN + "None of the two TamaGolems was hurt!";
    private static final String SAME_LIST = COMMAND_SIGN + RED_ATTENTION + "\n" + COMMAND_SIGN
            + "You chose the same elements as the other player. Please choose again.";
    private static final String DEAD_GOLEM = PrettyStrings.colorString("DIED!", AnsiColors.RED);
    private static final String WIN_MESSAGE = "won";
    private static final String TURN = COMMAND_SIGN + PrettyStrings.colorString("TURN %d", AnsiColors.GREEN);
    private static final String PRESS_INSERT = "Press anything and enter to continue:";
    private static final int DELAY = 1000;

    /**
     * Metodo che inserisce nella lista fornita gli elementi scelti dal giocatore
     * @param game in cui ci si trova
     */
    public static List<Element> selectGolemStones (Game game, Player player) {
        List<Element> tempList = new ArrayList<>();

        //Stampa di messaggi e tabella per interazione con l'utente
        System.out.printf(SELECT_STONE + "%n", PrettyStrings.colorString(player.getName(), player.getColor()),
                PrettyStrings.colorString(player.getCurrentGolem().getName(), player.getColor()));

        //Scelta del giocatore
        Element chosenElement;

        game.printChest();

        for (int i = 0; i < game.getStonesPerGolem(); i++) {
            int choice = InputData.readIntegerBetween(STONE_CHOICE, 1, game.getElements().length) - 1;
            chosenElement = game.getElements()[choice];

            while (!game.isStoneInChest(chosenElement)) {
                System.out.println(STONES_FINISHED);
                choice = InputData.readIntegerBetween(STONE_CHOICE, 1, game.getElements().length) - 1;
                chosenElement = game.getElements()[choice];
            }

            tempList.add(chosenElement);
        }

        Menu.clearConsole();
        return tempList;
    }

    /**
     * Metodo che gestisce un ciclo di un fight
     * Per ciclo si intende una fase che inizia con il primo lancio di due pietre fra due golem e
     * finisce con la morte di uno di essi
     * @param golem1 primo golem combattente
     * @param golem2 secondo golem combattente
     * @param game il game in cui ci si trova durante questo ciclo
     * @return  1 -> golem1 è sconfitto / 2 -> golem2 è sconfitto
     */
    private static void fightCycle (TamaGolem golem1, TamaGolem golem2, Game game) throws InterruptedException {

        int stoneIndex = 0;

        //Ciclo while che si ripete fino a quando uno dei due golem non termina gli hp
        while (golem1.getHp() > 0 && golem2.getHp() > 0) {
            //Variabile di supporto che varierà da zero al numero massimo di pietre per golem -1
            //Serve per reiterare al'interno della lista di pietre di un golem, ripetendo i lanci ciclicamente
            stoneIndex = stoneIndex % game.getStonesPerGolem();

            int damageDealt = game.evaluateDamage(golem1.getStoneList().get(stoneIndex),
                    golem2.getStoneList().get(stoneIndex));

            //Se il damageDealt è positivo, vuol dire che golem1 infligge danni, in caso contrario li riceve
            if (damageDealt > 0) {
                golem2.setHp(golem2.getHp() - damageDealt);
                System.out.printf(DAMAGE_DEALT + "\n", PrettyStrings.colorString(golem1.getName(), AnsiColors.RED),
                        golem1.getStoneList().get(stoneIndex) , damageDealt,
                        PrettyStrings.colorString(golem2.getName(), AnsiColors.BLUE));
            }
            else if (damageDealt < 0) {
                golem1.setHp(golem1.getHp() + damageDealt);
                System.out.printf(DAMAGE_DEALT + "\n", PrettyStrings.colorString(golem2.getName(), AnsiColors.BLUE),
                        golem2.getStoneList().get(stoneIndex), -damageDealt,
                        PrettyStrings.colorString(golem1.getName(), AnsiColors.RED));
            }
            else
                System.out.println(NO_DAMAGE_DEALT);
            stoneIndex++;
            Menu.wait(DELAY);
        }

        if (golem1.getHp() <= 0){
            System.out.println(COMMAND_SIGN + PrettyStrings.colorString(
                    golem1.getName(), AnsiColors.RED) + " " + DEAD_GOLEM);
        }

        else{
            System.out.println(COMMAND_SIGN + PrettyStrings.colorString(
                    golem2.getName(), AnsiColors.BLUE) + " " + DEAD_GOLEM);
        }
        InputData.readString(PRESS_INSERT, false);
        Menu.clearConsole();
    }

    private static boolean sameList (TamaGolem golem1, TamaGolem golem2) {
        if (golem1.getStoneList().equals(golem2.getStoneList())){
            System.out.println(SAME_LIST);
            return true;
        }
        return false;
    }

    private static void stoneSelection(TamaGolem golem1, TamaGolem golem2, Game game, Player player) {
        List<Element> stoneList = selectGolemStones(game, player);
        golem2.setStoneList(stoneList);

        while(sameList(golem1, golem2)) {
            stoneList = selectGolemStones(game, player);
            golem2.setStoneList(stoneList);
        }

        game.removeStoneListFromChest(stoneList);
    }

    /**
     * Metodo che gestisce lo scontro fra due giocatori. A ogni ciclo, chiede al giocatore di scegliere le pietre dalla
     * chest, le inserisce nei golem e inizia lo scontro fra i due
     * @param game il game in cui avviene lo scontro
     */
    public static void battle (Game game) throws InterruptedException {
        TamaGolem currentGolem1 = game.getPlayer1().getCurrentGolem();
        TamaGolem currentGolem2 = game.getPlayer2().getCurrentGolem();

        List<Element> stoneList = selectGolemStones(game, game.getPlayer1());
        game.removeStoneListFromChest(stoneList);
        currentGolem1.setStoneList(stoneList);

        stoneSelection(currentGolem1, currentGolem2, game, game.getPlayer2());

        int turn = 1;
        while(currentGolem1 != null && currentGolem2 != null) {
            Menu.clearConsole();
            System.out.printf(TURN + "%n", turn++);
            //Variabile che salva il numero del golem sconfitto in questa battaglia
            fightCycle(currentGolem1, currentGolem2, game);

            //Se il golem sconfitto è quello del primo giocatore, il currentGolem1 dovrà aumentare, e viceversa
            //Inoltre, il relativo giocatore dovrà scegliere le pietre per il nuovo golem
            TamaGolem previousGolem1 = currentGolem1;
            currentGolem1 = game.getPlayer1().getCurrentGolem();
            currentGolem2 = game.getPlayer2().getCurrentGolem();

            if(currentGolem1 != null && currentGolem2 != null){
                if (previousGolem1 == currentGolem1) {
                    stoneSelection(currentGolem2, currentGolem1, game, game.getPlayer1());
                }
                else {
                    stoneSelection(currentGolem1, currentGolem2, game, game.getPlayer2());
                }
            }
        }

        Menu.clearConsole();

        if (currentGolem1 == null)
            System.out.println(PrettyStrings.colorString(Title.createTitle(
                    game.getPlayer2().getName() + " " + WIN_MESSAGE, true) + "%n",
                    game.getPlayer2().getColor()));
        else
            System.out.println(PrettyStrings.colorString(Title.createTitle(
                    game.getPlayer1().getName() + " " + WIN_MESSAGE, true) + "%n",
                    game.getPlayer1().getColor()));
    }
}
