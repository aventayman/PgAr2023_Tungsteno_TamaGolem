package it.unibs.fp.tamaGolem;

import java.util.ArrayList;
import java.util.List;

public class Fight {
    private static final String SELECT_STONE =
            "Select the stones you want to give to the TamaGolem from the chest: ";
    private static final String STONE_CHOICE =
            "Insert the index of the stone you want to give to your TamaGolem: ";
    /**
     * Metodo che inserisce nella lista fornita gli elementi scelti dal giocatore
     */
    public static void stoneChoice (Game game, TamaGolem golem) {
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

    }



}
