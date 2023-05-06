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
    
    /**
     * Metodo che inserisce nella lista fornita gli elementi scelti dal giocatore
     * @param game in cui ci si trova
     * @param golem a gui aggiungere la pietra scelta
     */
    public static void stoneChoices (Game game, TamaGolem golem) {
        List<Element> tempList = new ArrayList<>();

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
        while (hp1 > 0 && hp2 > 0){
            //Variabile di supporto che varierà da zero al numero massimo di pietre per golem -1
            //Serve per reiterare al'interno della lista di pietre di un golem, ripetendo i lanci ciclicamente
            elementIndex = elementIndex % game.getStonesPerGolem();

            int damageDealt = game.evaluateDamage(golem1.getStoneList().get(elementIndex),
                    golem2.getStoneList().get(elementIndex));

            //Se il damageDealt è positivo, vuol dire che golem1 infligge danni, in caso contrario li riceve
            if(damageDealt > 0)
                hp2 -= damageDealt;
            else
                hp1 += damageDealt;

            elementIndex++;
        }

        if(hp1 <= 0)
            return 1;
        else
            return 2;
    }

    /**
     * Metodo che gestisce lo scontro fra due giocatori. A ogni ciclo, chiede al giocatore di scegliere le pietre dalla
     * chest, le inserisce nei golem e inizia lo scontro fra i due
     * @param game il game in cui avviene lo scontro
     */
    public static void startMatch (Game game) {
        //Indici per posizionarsi sul golem corrente
        int currentGolem1, currentGolem2;
        currentGolem1 = currentGolem2 = 0;

        int golemNumber = game.getPlayer1().getGolemList().size();
        while(currentGolem1 < golemNumber && currentGolem2 < golemNumber){
            //Scelta delle pietre, prima del player1 e poi del player2
            Fight.stoneChoices(game, game.getPlayer1().getGolemList().get(currentGolem1));
            Fight.stoneChoices(game, game.getPlayer2().getGolemList().get(currentGolem2));

            int defeated = fightCycle(game.getPlayer1().getGolemList().get(currentGolem1),
                            game.getPlayer2().getGolemList().get(currentGolem2), game);

            //Se il golem sconfitto è quello del primo giocatore, il currentGolem1 dovrà aumentare, e viceversa
            if(defeated == 1)
                currentGolem1++;
            else
                currentGolem2++;
        }
        if (currentGolem1 == golemNumber)
            System.out.println("Player n.2 won! Amazing!");
        else
            System.out.println("Player n.1 won! What a fight!");
    }
}


