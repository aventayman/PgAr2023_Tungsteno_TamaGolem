package it.unibs.fp.tamaGolem;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<TamaGolem> golemList = new ArrayList<>();


    public Player(List<TamaGolem> golemList) {
        this.golemList = golemList;
    }
}
