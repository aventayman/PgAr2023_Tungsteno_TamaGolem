package it.unibs.fp.tamaGolem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Classe che memorizza i dati del game corrente, in modo da poter essere richiamati al momento opportuno
 */
public class Game {
    private final int elementAmount;
    private final int maxHp;

    //Equilibrio della partita corrente
    private final Balance balance;

    //Elementi che saranno disponibili per la partita corrente
    private final Element [] elements;

    //Chest da cui attingere in game
    private final List<List<Element>> chest;
    private final Player player1;
    private final Player player2;

    public Game(int elementAmount, int maxHp) {
        this.elementAmount = elementAmount;
        this.maxHp = maxHp;
        this.elements = setElements(elementAmount);
        this.chest = createStoneChest();
        this.player1 = new Player(getGolemNum(), maxHp);
        this.player2 = new Player(getGolemNum(), maxHp);
        this.balance = new Balance(elementAmount, maxHp);
    }

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

    private Element [] setElements(int elementAmount) {
        var elementArray = new Element[elementAmount];
        for (int i = 0; i < elementAmount; i++) {
            elementArray[i] = Element.values()[i];
        }
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
    private int getGolemNum () {
        return (int)Math.ceil(((float)(elementAmount-1)*(elementAmount-2)/(2*getStonesPerGolem())));
    }

    /**
     * Metodo che ritorna il numero di pietre che saranno presenti nella sacca comune
     * Utilizza le formule fornite dal regolamento per calcolare il valore richiesto
     * @return il numero di pietre presenti nella sacca comune
     */
    private int getChestDim() {
        return (int)(Math.ceil((float)(2*getGolemNum()*getStonesPerGolem())/elementAmount)) * elementAmount;
    }

    /**
     * Metodo che fornisce il numero di pietre per ciascun elemento che ci saranno della sacca comune
     * Utilizza la formula fornita dal regolamento per calcolare il valore richiesto
     * @return il numero di pietre per elemento
     */
    private int getStonesPerElement() {
        return getChestDim()/elementAmount;
    }

    private List<List<Element>> createStoneChest() {
        List<List<Element>> chest = new ArrayList<>();
        for (int i = 0; i < elementAmount; i++) {
            chest.add(i, new ArrayList<>());
            for (int j = 0; j < getStonesPerElement(); j++)
                chest.get(i).add(getElements()[i]);
        }
        return chest;
    }


    public Element [] getElements() {
        return elements;
    }

    public List<List<Element>> getChest() {
        return chest;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Balance getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Game{" +
                ", balance=" + balance +
                ", elements=" + Arrays.toString(elements) +
                ", chest=" + chest +
                ", player1=" + player1 +
                ", player2=" + player2 +
                '}';
    }
}
