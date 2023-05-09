package it.unibs.fp.tamaGolem;

import it.ayman.fp.lib.AnsiColors;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final AnsiColors color;
    private final List<TamaGolem> golemList;

    public Player(String name, AnsiColors color, int golemAmount, int golemHp) {
        this.name = name;
        this.color = color;
        this.golemList = createGolemList(golemAmount, golemHp);
    }

    /**
     * Metodo di creazione della lista di golem appartenente a un giocatore
     * @param golemAmount numero di TamaGolem della partita
     * @return una list di TamaGolem, che non possiedono alcuna pietra e con l'hp indicato
     */
    private static List<TamaGolem> createGolemList (int golemAmount, int golemHp){
        List<TamaGolem> list = new ArrayList<>();
        for (int i = 0; i < golemAmount; i++){
            TamaGolem golem = new TamaGolem(new ArrayList<>(), golemHp);
            list.add(golem);
        }
        return list;
    }

    /**
     * Metodo che restituisce il primo golem nella lista del giocatore ad avere hp != 0
     * Se tutti i golem del pplayer sono morti, ritorna null
     * @return il primo golem con hp != 0, null se non ci sono golem vivi
     */
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

    public AnsiColors getColor() {
        return color;
    }
}
