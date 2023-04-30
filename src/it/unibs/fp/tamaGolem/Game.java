package it.unibs.fp.tamaGolem;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int golemNum;
    private int stonesPerElement;
    private int stonesNum;
    private int chestDim;
    private Balance balance;
    private List<String> elements = new ArrayList<>();
    private List<List<Stone>> chest = new ArrayList<>();
    private Player player1, player2;

    public int getGolemNum() {
        return golemNum;
    }

    public int getStonesPerElement() {
        return stonesPerElement;
    }

    public int getStonesNum() {
        return stonesNum;
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

    public void setStonesNum(int stonesNum) {
        this.stonesNum = stonesNum;
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
                ", stonesNum=" + stonesNum +
                ", chestDim=" + chestDim +
                ", balance=" + balance +
                ", elements=" + elements +
                ", chest=" + chest +
                ", player1=" + player1 +
                ", player2=" + player2 +
                '}';
    }
}
