package it.unibs.fp.tamaGolem;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<TamaGolem> golemList = new ArrayList<>();

    public Player(int golemAmount, int golemHp) {
        this.golemList = createGolemList(golemAmount, golemHp);
    }

    /**
     * Metodo di creazione della lista di golem appartenente a un giocatore
     * @param golemAmount numero di TamaGolem della partita
     * @return una arraylist di TamaGolem
     */
    private static List<TamaGolem> createGolemList (int golemAmount, int golemHp){
        List<TamaGolem> list = new ArrayList<>();
        for (int i = 0; i < golemAmount; i++){
            TamaGolem golem = new TamaGolem(new ArrayList<>(), golemHp);
            list.add(golem);
        }
        return list;
    }

    public List<TamaGolem> getGolemList() {
        return golemList;
    }
}
