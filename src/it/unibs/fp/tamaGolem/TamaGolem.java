package it.unibs.fp.tamaGolem;

import java.util.ArrayList;
import java.util.List;

public class TamaGolem {
    private List<Element> stoneList;
    private int hp;

    public TamaGolem(List<Element> stoneList, int hp) {
        this.stoneList = stoneList;
        this.hp = hp;
    }

    public void setStoneList(List<Element> stoneList) {
        this.stoneList = stoneList;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public List<Element> getStoneList() {
        return stoneList;
    }
}
