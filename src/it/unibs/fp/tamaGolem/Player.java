package it.unibs.fp.tamaGolem;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<TamaGolem> golemList = new ArrayList<>();

    public Player(int golemAmount) {
        this.golemList = createGolemList(golemAmount);
    }

    /**
     * Metodo di creazione della lista di golem appartenente a un giocatore
     * @param golemAmount numero di TamaGolem della partita
     * @return una arraylist di TamaGolem
     */
    private static List<TamaGolem> createGolemList (int golemAmount){
        List<TamaGolem> list = new ArrayList<>();
        for (int i = 0; i < golemAmount; i++){
            TamaGolem golem = new TamaGolem();
            list.add(golem);
        }
        return list;
    }
}
