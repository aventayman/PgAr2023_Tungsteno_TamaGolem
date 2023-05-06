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
        List<Stone> tempList = new ArrayList<>();

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

            Stone stone = new Stone(chosenElement);
            tempList.add(stone);
        }
        golem.setStoneList(tempList);
    }

}
