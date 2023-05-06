package it.unibs.fp.tamaGolem;

import java.util.ArrayList;
import java.util.List;

public class TamaGolem {
    private List<Stone> stoneList = new ArrayList<>();
    private final int hp;

    public TamaGolem(List<Stone> stoneList, int hp) {
        this.stoneList = stoneList;
        this.hp = hp;
    }

    public void setStoneList(List<Stone> stoneList) {
        this.stoneList = stoneList;
    }

    public List<Stone> getStoneList() {
        return stoneList;
    }
}
