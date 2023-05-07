package it.unibs.fp.tamaGolem;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final List<TamaGolem> golemList;

    public Player(String name, int golemAmount, int golemHp) {
        this.name = name;
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

    public TamaGolem getCurrentGolem () {
        for (TamaGolem golem : golemList) {
            if (golem.getHp() > 0)
                return golem;
        }

        return null;
    }

    public String getName() {
        return name;
    }

    public List<TamaGolem> getGolemList() {
        return golemList;
    }
}
