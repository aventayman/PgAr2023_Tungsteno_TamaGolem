package it.unibs.fp.tamaGolem;

import java.util.ArrayList;
import java.util.List;

public class TamaGolem {
    private List<Stone> listaPietre = new ArrayList<>();
    private int puntiSalute = 100;

    public TamaGolem() {}

    public TamaGolem(List<Stone> listaPietre) {
        this.listaPietre = listaPietre;
    }

}
