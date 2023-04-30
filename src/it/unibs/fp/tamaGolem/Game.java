package it.unibs.fp.tamaGolem;

import java.util.ArrayList;
import java.util.List;


/**
 * Classe che memorizza i dati del game corrente, in modo da poter essere richiamati al momento opportuno
 */
public class Game {

    //Golem per giocatore
    private int golemNum;

    //Numero di pietre di ciascun elemento nella chest
    private int stonesPerElement;

    //Numero di pietre per golem nel game corrente
    private int stonesPerGolem;

    //Dimensione della chest comune
    private int chestDim;

    //Equilibrio della partita corrente
    private Balance balance;

    //Elementi che saranno disponibili per la partita corrente
    private List<String> elements = new ArrayList<>();

    //Chest da cui attingere in game
    private List<List<Stone>> chest = new ArrayList<>();
    private Player player1, player2;

    //Getter e setter

    public int getGolemNum() {
        return golemNum;
    }

    public int getStonesPerElement() {
        return stonesPerElement;
    }

    public int getStonesPerGolem() {
        return stonesPerGolem;
    }

    public int getChestDim() {
        return chestDim;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setGolemNum(int golemNum) {
        this.golemNum = golemNum;
    }

    public void setStonesPerElement(int stonesPerElement) {
        this.stonesPerElement = stonesPerElement;
    }

    public void setStonesPerGolem(int stonesNum) {
        this.stonesPerGolem = stonesNum;
    }

    public void setChestDim(int chestDim) {
        this.chestDim = chestDim;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public void addElement(String element) {
        elements.add(element);
    }

    public List<String> getElements() {
        return elements;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setChest(List<List<Stone>> chest) {
        this.chest = chest;
    }

    @Override
    public String toString() {
        return "Game{" +
                "golemNum=" + golemNum +
                ", stonesPerElement=" + stonesPerElement +
                ", stonesNum=" + stonesPerGolem +
                ", chestDim=" + chestDim +
                ", balance=" + balance +
                ", elements=" + elements +
                ", chest=" + chest +
                ", player1=" + player1 +
                ", player2=" + player2 +
                '}';
    }
}
