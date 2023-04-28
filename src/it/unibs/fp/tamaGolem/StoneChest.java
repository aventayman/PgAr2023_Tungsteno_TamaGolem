package it.unibs.fp.tamaGolem;

import java.util.ArrayList;
import java.util.List;

public class StoneChest {
    private int stoneAmount;
    private List<Stone> stoneList = new ArrayList<>();

    public StoneChest(List<Stone> stoneList) {
        this.stoneList = stoneList;
    }
}
