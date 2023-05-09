package it.unibs.fp.tamaGolem;

import it.ayman.fp.lib.*;

import java.util.ArrayList;
import java.util.List;

public class Fight {
    private static final String RED_ATTENTION = PrettyStrings.colorString("Attention!", AnsiColors.RED);
    private static final String COMMAND_SIGN = "> ";
    private static final String SELECT_STONE = COMMAND_SIGN +
                    "Select the stones you want to give to the TamaGolem from the chest:";
    private static final String STONE_CHOICE = "Insert the index of the stone you want to give to your TamaGolem:";
    private static final String STONES_FINISHED = COMMAND_SIGN + RED_ATTENTION + "\n" +
            COMMAND_SIGN + "The chosen stone has ran out.";
    private static final String DAMAGE_DEALT = COMMAND_SIGN + "%s with %s stone dealt %d damage to %s";
    private static final String NO_DAMAGE_DEALT = COMMAND_SIGN + "None of the two TamaGolems was hurt!";
    private static final String SAME_LIST = COMMAND_SIGN + RED_ATTENTION + "\n" + COMMAND_SIGN + "You chose the same elements as %s. Please choose again.";
    private static final String DEAD_GOLEM = PrettyStrings.colorString(" died!", AnsiColors.RED);
    private static final String WIN_MESSAGE = "won";

    /**
     * Metodo che inserisce nella lista fornita gli elementi scelti dal giocatore
     * @param game in cui ci si trova
     * @param golem a gui aggiungere la pietra scelta
     */
    public static List<Element> selectGolemStones (Game game, TamaGolem golem) {
        List<Element> tempList = new ArrayList<>();

        //Stampa di messaggi e tabella per interazione con l'utente
        System.out.println(SELECT_STONE);

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
    private static void fightCycle (TamaGolem golem1, TamaGolem golem2, Game game) {

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
                System.out.printf(DAMAGE_DEALT + "\n", PrettyStrings.colorString(golem1.getName(), AnsiColors.BLUE),
                        golem1.getStoneList().get(stoneIndex) , damageDealt, PrettyStrings.colorString(golem2.getName(), AnsiColors.YELLOW));
            }
            else if (damageDealt < 0) {
                golem1.setHp(golem1.getHp() + damageDealt);
                System.out.printf(DAMAGE_DEALT + "\n", PrettyStrings.colorString(golem2.getName(), AnsiColors.YELLOW),
                        golem2.getStoneList().get(stoneIndex), -damageDealt, PrettyStrings.colorString(golem1.getName(), AnsiColors.BLUE));
            }
            else
                System.out.println(NO_DAMAGE_DEALT);
            stoneIndex++;
        }

        if (golem1.getHp() <= 0){
            System.out.println(PrettyStrings.colorString(golem1.getName(), AnsiColors.BLUE) + " " + DEAD_GOLEM);
        }

        else{
            System.out.println(PrettyStrings.colorString(golem2.getName(), AnsiColors.YELLOW) + " " + DEAD_GOLEM);
        }
    }

    private static boolean sameList (TamaGolem golem1, TamaGolem golem2, Game game) {
        if (golem1.getStoneList().equals(golem2.getStoneList())){
            System.out.printf((SAME_LIST) + "%n", game.getPlayer1().getName());
            return true;
        }
        return false;
    }

    /**
     * Metodo che gestisce lo scontro fra due giocatori. A ogni ciclo, chiede al giocatore di scegliere le pietre dalla
     * chest, le inserisce nei golem e inizia lo scontro fra i due
     * @param game il game in cui avviene lo scontro
     */
    public static void battle (Game game) {
        TamaGolem currentGolem1 = game.getPlayer1().getCurrentGolem();
        TamaGolem currentGolem2 = game.getPlayer2().getCurrentGolem();

        //Scelta delle pietre, prima del player1 e poi del player2
        List<Element> list1 = selectGolemStones(game, currentGolem1);
        game.removeStoneListFromChest(list1);
        currentGolem1.setStoneList(list1);

        List<Element> list2 = selectGolemStones(game, currentGolem2);
        currentGolem2.setStoneList(list2);

        while(sameList(currentGolem1, currentGolem2, game)) {
            list2 = selectGolemStones(game, currentGolem2);
            currentGolem2.setStoneList(list2);
        }

        game.removeStoneListFromChest(list2);

        while(currentGolem1 != null && currentGolem2 != null) {
            //Variabile che salva il numero del golem sconfitto in questa battaglia
            fightCycle(currentGolem1, currentGolem2, game);

            //Se il golem sconfitto è quello del primo giocatore, il currentGolem1 dovrà aumentare, e viceversa
            //Inoltre, il relativo giocatore dovrà scegliere le pietre per il nuovo golem
            TamaGolem previousGolem1 = currentGolem1;
            currentGolem1 = game.getPlayer1().getCurrentGolem();
            currentGolem2 = game.getPlayer2().getCurrentGolem();

            if(currentGolem1 != null && currentGolem2 != null){
                if (previousGolem1 == currentGolem1) {
                    list2 = Fight.selectGolemStones(game, currentGolem2);
                    currentGolem2.setStoneList(list2);

                    while(sameList(currentGolem1, currentGolem2, game)) {
                        list2 = Fight.selectGolemStones(game, currentGolem2);
                        currentGolem2.setStoneList(list2);
                    }

                    game.removeStoneListFromChest(list2);
                }
                else {
                    list1 = Fight.selectGolemStones(game, currentGolem1);
                    currentGolem1.setStoneList(list1);

                    while (sameList(currentGolem1, currentGolem2, game)) {
                        list1 = Fight.selectGolemStones(game, currentGolem1);
                        currentGolem1.setStoneList(list1);
                    }

                    game.removeStoneListFromChest(list1);
                }
            }
        }

        if (currentGolem1 == null)
            System.out.println(Title.createTitle(game.getPlayer2().getName() + " " + WIN_MESSAGE, false));
        else
            System.out.println(Title.createTitle(game.getPlayer1().getName() + " " + WIN_MESSAGE, false));
    }
}
