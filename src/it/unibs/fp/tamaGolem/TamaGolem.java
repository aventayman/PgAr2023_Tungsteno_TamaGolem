package it.unibs.fp.tamaGolem;

import java.util.ArrayList;
import java.util.List;

public class TamaGolem {
    private List<Stone> stoneList = new ArrayList<>();
    private final int puntiSalute = 10;

    public TamaGolem() {}

    public TamaGolem(List<Stone> stoneList) {
        this.stoneList = stoneList;
    }

    public void setStoneList(List<Stone> stoneList) {
        this.stoneList = stoneList;
    }

    public List<Stone> getStoneList() {
        return stoneList;
    }
}
