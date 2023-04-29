package it.unibs.fp.tamaGolem;

public class Game {
    private int golemNum;
    private int stonesPerElement;
    private int stonesNum;
    private int chestDim;
    private Balance balance;
    private StoneChest chest;
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
}
