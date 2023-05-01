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

    /**
     * Metodo di creazione della lista di golem appartenente a un giocatore
     * @param n numero di elementi della partita
     * @return una arraylist di TamaGolem
     */
    public static List<TamaGolem> createGolemList (int n){
        List<TamaGolem> list = new ArrayList<>();
        for (int i = 0; i < n; i++){
            TamaGolem golem = new TamaGolem();
            list.add(golem);
        }
        return list;
    }

    public void setStoneList(List<Stone> stoneList) {
        this.stoneList = stoneList;
    }

    public List<Stone> getStoneList() {
        return stoneList;
    }
}
