package it.unibs.fp.tamaGolem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;

public class TamaGolem {
    private List<Element> stoneList;
    private int hp;
    private final NameTamagolem[] nameList = NameTamagolem.values() ;

    private final String name;

    public TamaGolem(List<Element> stoneList, int hp) {
        this.stoneList = stoneList;
        this.hp = hp;
        this.name = nameList[(int)Math.floor(Math.random() *(9) + 0 )].toString();

    }

    public void setStoneList(List<Element> stoneList) {
        this.stoneList = stoneList;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public NameTamagolem[] getNameList() {
        return nameList;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public List<Element> getStoneList() {
        return stoneList;
    }
}
