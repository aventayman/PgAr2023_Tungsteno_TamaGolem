package it.unibs.fp.tamaGolem;

public class Game {
    private final int golemNum;
    private final int stonesPerElement;
    private final int stonesNum;
    private final int chestDim;
    private final Balance balance;

    public Game(int golemNum, int stonesPerElement, int stonesNum, int chestDim, Balance balance) {
        this.golemNum = golemNum;
        this.stonesPerElement = stonesPerElement;
        this.stonesNum = stonesNum;
        this.chestDim = chestDim;
        this.balance = balance;
    }

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
}
