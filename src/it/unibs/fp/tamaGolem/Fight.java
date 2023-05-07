package it.unibs.fp.tamaGolem;

import it.ayman.fp.lib.AnsiColors;
import it.ayman.fp.lib.InputData;
import it.ayman.fp.lib.PrettyStrings;
import it.ayman.fp.lib.CommandLineTable;

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
    private static String damageDealtMessage(int damage) {
        return "took " + damage + " damage!";
    }
    private static final String SAME_LIST = "\n" + COMMAND_SIGN + "The element chosen by the two players are the same.\n" +
            "Please choose again:" + "\n";
    private static final String DEAD_GOLEM = PrettyStrings.colorString(" died!", AnsiColors.RED);
    private static final String WIN_MESSAGE1 = " won! Amazing!\n";
    private static final String WIN_MESSAGE2 = " won! What a fight!\n";

    /**
     * Metodo che inserisce nella lista fornita gli elementi scelti dal giocatore
     * @param game in cui ci si trova
     * @param golem a gui aggiungere la pietra scelta
     */
    public static void stoneChoices (Game game, TamaGolem golem) {
        List<Element> tempList = new ArrayList<>();

        //Stampa di messaggi e tabella per interazione con l'utente
        System.out.println(SELECT_STONE);
        CommandLineTable viewChest = new CommandLineTable();
        viewChest.setShowVerticalLines(true);
        viewChest.setHeaders("Index", "Element", "Number of Stones");
        int index = 1;
        for (Element element : game.getElements()) {
            viewChest.addRow(String.valueOf(index), element.toString(),
                    String.valueOf(game.getChest().get(index - 1).size()));
            index++;
        }
        viewChest.print();

        //Scelta del giocatore
        boolean validIndex;
        Element chosenElement;

        for (int i = 0; i < game.getStonesPerGolem(); i++) {
            do {
                validIndex = true;
                int choice = InputData.readIntegerBetween(STONE_CHOICE, 1, game.getElements().length) - 1;
                chosenElement = game.getElements()[choice];
                try {
                    game.getChest().get(choice).remove(0);
                } catch (IndexOutOfBoundsException exception) {
                    validIndex = false;
                    System.out.println(STONES_FINISHED);
                }
            } while (!validIndex);

            tempList.add(chosenElement);
        }
        golem.setStoneList(tempList);
    }

    /**
     * Metodo che gestisce un ciclo di un fight
     * Per ciclo si intende una fase che inizia con il primo lancio di due pietre fra due golem e
     * finisce con la morte di uno di essi
     * @param golem1 primo golem combattente
     * @param golem2 secondo golem combattente
     * @param game il game in cui ci si trova durante questo ciclo
     * @return  1 -> golem1 è sconfitto
     *          2 -> golem2 è sconfitto
     */
    private static int fightCycle (TamaGolem golem1, TamaGolem golem2, Game game) {
        //Inizializza gli hp dei golem
        int hp1, hp2;
        hp1 = hp2 = golem1.getHp();

        int elementIndex = 0;
        //Ciclo while che si ripete fino a quando uno dei due golem non termina gli hp
        while (hp1 > 0 && hp2 > 0) {
            //Variabile di supporto che varierà da zero al numero massimo di pietre per golem -1
            //Serve per reiterare al'interno della lista di pietre di un golem, ripetendo i lanci ciclicamente
            elementIndex = elementIndex % game.getStonesPerGolem();

            int damageDealt = game.evaluateDamage(golem1.getStoneList().get(elementIndex),
                    golem2.getStoneList().get(elementIndex));

            //Se il damageDealt è positivo, vuol dire che golem1 infligge danni, in caso contrario li riceve
            if (damageDealt > 0) {
                hp2 -= damageDealt;
                System.out.println("Golem2" + damageDealtMessage(damageDealt));
            }
            else {
                hp1 += damageDealt;
                System.out.println("Golem1" + damageDealtMessage(-damageDealt));
            }
            elementIndex++;
        }

        if (hp1 <= 0){
            System.out.println("Golem 1" + DEAD_GOLEM);
            return 1;
        }

        else{
            System.out.println("Golem 2" + DEAD_GOLEM);
            return 2;
        }
    }

    private static boolean sameList (TamaGolem golem1, TamaGolem golem2) {
        if (golem1.getStoneList().equals(golem2.getStoneList())){
            System.out.println(SAME_LIST);
            return true;
        }
        return false;
    }

    /**
     * Metodo che gestisce lo scontro fra due giocatori. A ogni ciclo, chiede al giocatore di scegliere le pietre dalla
     * chest, le inserisce nei golem e inizia lo scontro fra i due
     * @param game il game in cui avviene lo scontro
     */
    public static void startMatch (Game game) {
        //Indici per posizionarsi sul golem corrente
        //Golem1 = tutti i golem del player1
        //Golem2 = tutti i golem del player2
        int currentGolem1, currentGolem2;
        currentGolem1 = currentGolem2 = 0;

        int golemNumber = game.getPlayer1().getGolemList().size();
        do {
            //Scelta delle pietre, prima del player1 e poi del player2
            Fight.stoneChoices(game, game.getPlayer1().getGolemList().get(currentGolem1));
            Fight.stoneChoices(game, game.getPlayer2().getGolemList().get(currentGolem2));
        } while(sameList(game.getPlayer1().getGolemList().get(currentGolem1),
                game.getPlayer2().getGolemList().get(currentGolem2)));

        //Boolean che memorizzano lo stato corrente di ogni player
        //Se uno dei due diventa true, il corrispettivo player non possiede più alcun golem in vita
        boolean noMoreGolem1 = false;
        boolean noMoreGolem2 = false;

        while(!noMoreGolem1 && !noMoreGolem2){
            //Variabile che salva il numero del golem sconfitto in questa battaglia
            int defeated = fightCycle(game.getPlayer1().getGolemList().get(currentGolem1),
                            game.getPlayer2().getGolemList().get(currentGolem2), game);

            //Se il golem sconfitto è quello del primo giocatore, il currentGolem1 dovrà aumentare, e viceversa
            //Inoltre, il relativo giocatore dovrà scegliere le pietre per il nuovo golem
            if(defeated == 1) {
                currentGolem1++;
                if(currentGolem1 < golemNumber)
                    Fight.stoneChoices(game, game.getPlayer1().getGolemList().get(currentGolem1));
                else
                    noMoreGolem1 = true;
            }
            else {
                currentGolem2++;
                if(currentGolem2 < golemNumber)
                    Fight.stoneChoices(game, game.getPlayer2().getGolemList().get(currentGolem2));
                else
                    noMoreGolem2 = true;
            }
        }
        if (noMoreGolem1)
            System.out.println("Player2" + WIN_MESSAGE2);
        else
            System.out.println("Player1" + WIN_MESSAGE1);
    }
}


