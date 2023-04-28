package it.unibs.fp.tamaGolem;

import it.kibo.fp.lib.Menu;

public class Main {
    public static void main(String[] args) {

        int n = GameInit.startGame();
        Balance balance = new Balance(n);

        int g, s, c, spe;
        g = s = c = spe = 0;
        GameInit.dataInit(n, s, g, c, spe);
    }
}
