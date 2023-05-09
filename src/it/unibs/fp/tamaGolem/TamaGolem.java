package it.unibs.fp.tamaGolem;

import it.ayman.fp.lib.RandomDraws;

import java.util.List;

public class TamaGolem {
    private List<Element> stoneList;
    private int hp;
    private final String name;


    /**
     * Nomi belli dei Tamagolem che combatteranno
     */
    private enum tamagolemNames {
        PIPPO, FRANCO, FRANCESCO, ARTURO, GUIDO, FRANGO, ANNIBALE, BROK, CESCO, FRANCESCHINO
    }

    /**
     * Set di un tamagolem, con la sua lista di pietre, i suoi hp e il suo nome, scelto randomicamente
     * @param stoneList la lista di pietre da dare al golem
     * @param hp il suoi hp massimi
     */
    public TamaGolem(List<Element> stoneList, int hp) {
        this.stoneList = stoneList;
        this.hp = hp;
        this.name = tamagolemNames.values()[RandomDraws.drawInteger(0, tamagolemNames.values().length - 1)].toString();

    }

    public void setStoneList(List<Element> stoneList) {
        this.stoneList = stoneList;
    }

    public void setHp(int hp) {
        this.hp = hp;
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
