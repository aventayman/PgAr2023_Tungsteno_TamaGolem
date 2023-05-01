package it.unibs.fp.tamaGolem;

import it.kibo.fp.lib.AnsiColors;
import it.kibo.fp.lib.InputData;
import it.kibo.fp.lib.PrettyStrings;

import java.util.ArrayList;
import java.util.List;

public class Fight {
    private static final String RED_ATTENTION = AnsiColors.RED + "Attention!" + AnsiColors.RESET;
    private static final String SELECT_STONE =
            "\nSelect the stones you want to give to the TamaGolem from the chest: ";
    private static final String STONE_CHOICE =
            "\nInsert the index of the stone you want to give to your TamaGolem: ";
    private static final String STONES_FINISHED = "\n" + RED_ATTENTION +
            "\nThe chosen stone has ran out.";
    
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
        for (String element : game.getElements()) {
            viewChest.addRow(String.valueOf(index), element,
                    String.valueOf(game.getChest().get(index - 1).size()));
            index++;
        }
        viewChest.print();

        boolean validIndex = true;
        String chosenElement;
        for (int i = 0; i < game.getStonesPerGolem(); i++) {
            do {
                int choice = InputData.readIntegerBetween(STONE_CHOICE, 1, game.getElements().size()) - 1;
                chosenElement = game.getElements().get(choice);
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
