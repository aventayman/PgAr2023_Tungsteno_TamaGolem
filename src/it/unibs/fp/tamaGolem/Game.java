package it.unibs.fp.tamaGolem;

import it.ayman.fp.lib.AnsiColors;
import it.ayman.fp.lib.CommandLineTable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


/**
 * Classe che memorizza i dati del game corrente, in modo da poter essere richiamati al momento opportuno
 */
public class Game {
    private final int elementAmount;

    //Equilibrio della partita corrente
    private final Balance balance;

    //Elementi che saranno disponibili per la partita corrente
    private final Element [] elements;

    //Chest da cui attingere in game
    private final List<List<Element>> chest;
    private final Player player1;
    private final Player player2;
    private static final String INDEX = "Index";
    private static final String ELEMENT= "Element";
    private static final String STONE_NUMBER = "Number of stones";

    /**
     * Metodo d'inizializzazione di una partita. Acquisisce l'elementAmount della partita corrente e aggiorna tutti i
     * di conseguenza
     * @param player1Name Nome del primo player
     * @param player2Name Nome del secondo player
     * @param elementAmount numero di elementi per la partita corrente
     * @param maxHp vita massima dei golem
     */
    public Game(String player1Name, String player2Name, int elementAmount, int maxHp) {
        this.elementAmount = elementAmount;
        this.elements = setElements(elementAmount);
        this.chest = createStoneChest();
        this.player1 = new Player(player1Name, AnsiColors.RED, getGolemNum(), maxHp);
        this.player2 = new Player(player2Name, AnsiColors.BLUE, getGolemNum(), maxHp);
        this.balance = new Balance(elementAmount, maxHp);
    }

    /**
     * Metodo per calcolare i danni in base all'equilibrio generato
     * @param element1 il primo elemento del confronto
     * @param element2 il secondo elemento del confronto
     * @return il danno corrispondente alla casella balance[element1][element2]
     * Se il danno è positivo, allora element1 è efficace contro element2
     * Se il danno è negativo, allora element2 è efficace contro element1
     */
    public int evaluateDamage (Element element1, Element element2) {
        int firstIndex = 0;
        int secondIndex = 0;
        for (int i = 0; i < elements.length; i++) {
            if(elements[i].equals(element1))
                firstIndex = i;
            if(elements[i].equals(element2))
                secondIndex = i;
        }
        return balance.getBalance()[firstIndex][secondIndex];
    }

    /**
     * Metodo per ritornare un array contenente esattamente gli elementi che ci saranno nella partita in
     * base alla difficoltà
     * @param elementAmount il numero di elementi presenti nella partita
     * @return un array di elementi presenti nella partita
     */
    private Element [] setElements(int elementAmount) {
        var elementArray = new Element[elementAmount];
        System.arraycopy(Element.values(), 0, elementArray, 0, elementAmount);
        return elementArray;
    }


    /**
     * Metodo che ritorna il numero di pietre per golem per questa partita
     * Utilizza le formule fornite dal regolamento per calcolare il valore richiesto
     * @return il numero di pietre per golem per la partita
     */
    public int getStonesPerGolem() {
        return (int)(Math.ceil((float)(elementAmount+1)/3))+1;
    }

    /**
     * Metodo che ritorna il numero di golem per giocatore di questa partita
     * Utilizza le formule fornite dal regolamento per calcolare il valore richiesto
     * @return il numero di golem per la partita
     */
    public int getGolemNum () {
        return (int)Math.ceil(((float)(elementAmount-1)*(elementAmount-2)/(2*getStonesPerGolem())));
    }

    /**
     * Metodo che ritorna il numero di pietre che saranno presenti nella chest
     * Utilizza le formule fornite dal regolamento per calcolare il valore richiesto
     * @return il numero di pietre presenti nella chest
     */
    public int getChestDim() {
        return (int)(Math.ceil((float)(2*getGolemNum()*getStonesPerGolem())/elementAmount)) * elementAmount;
    }

    /**
     * Metodo che fornisce il numero di pietre per ciascun elemento che ci saranno della chest
     * Utilizza la formula fornita dal regolamento per calcolare il valore richiesto
     * @return il numero di pietre per elemento
     */
    public int getStonesPerElement() {
        return getChestDim()/elementAmount;
    }

    /**
     * Metodo ci creazione della stone chest
     * @return una lista di liste di element, tante quante sono le pietre di quell'element per quel game
     */
    private List<List<Element>> createStoneChest() {
        List<List<Element>> chest = new ArrayList<>();
        for (int i = 0; i < elementAmount; i++) {
            chest.add(i, new ArrayList<>());
            for (int j = 0; j < getStonesPerElement(); j++)
                chest.get(i).add(getElements()[i]);
        }
        return chest;
    }

    /**
     * Metodo di stampa a video dei contenuti presenti nella chest
     */
    public void printChest() {
        var viewChest = new CommandLineTable();
        viewChest.setShowVerticalLines(true);
        viewChest.setHeaders(INDEX, ELEMENT, STONE_NUMBER);
        int index = 1;
        for (Element element : elements) {
            viewChest.addRow(String.valueOf(index), element.toString(),
                    String.valueOf(chest.get(index - 1).size()));
            index++;
        }
        viewChest.print();
    }

    /**
     * Metodo per controllare che una pietra si trovi ancora dentro la chest
     * @param stone pietra da verificare
     * @return true se la chest contiene la pietra, false in caso contrario
     */
    public boolean isStoneInChest(Element stone, List<List<Element>> chest) {
        for (List<Element> elementCompartment: chest) {
            if (elementCompartment.contains(stone)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo per rimuovere una pietra dalla chest
     * @param stone la pietra da rimuovere
     */
    public void removeStoneFromChest(Element stone, List<List<Element>> currentChest) {
        for (List<Element> elementCompartment: currentChest) {
            elementCompartment.remove(stone);
        }
    }

    /**
     * Metodo per rimuovere una stoneList dalla chest
     * @param stones le pietre da rimuovere
     */
    public void removeStoneListFromChest(List<Element> stones) {
        for (Element stone : stones) {
            removeStoneFromChest(stone, chest);
        }
    }

    public Element [] getElements() {
        return elements;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    private static final String BALANCE_MESSAGE = "Here's the balance of the world, brave golem tamer!";

    /**
     * Metodo di stampa del balance della partita.
     */
    public void printBalance() {
        System.out.println(BALANCE_MESSAGE);
        var viewBalance = new CommandLineTable();
        viewBalance.setShowVerticalLines(true);
        List<String> headerArray = new ArrayList<>(Stream.of(elements).map(Element::name).toList());
        headerArray.add(0, " ");
        viewBalance.setHeaders(headerArray.toArray(new String[0]));


        int GRID_SIZE = balance.getBalance().length;
        for (int i = 0; i < GRID_SIZE; i++) {
            List<String> rowArray = new ArrayList<>();
            rowArray.add(elements[i].toString());
            for (int number : balance.getBalance()[i]) {
                rowArray.add(Integer.toString(number));
            }
            viewBalance.addRow(rowArray.toArray(new String[0]));
        }
        viewBalance.print();
        System.out.println();
    }

    public List<List<Element>> getChest() {
        return chest;
    }
}
