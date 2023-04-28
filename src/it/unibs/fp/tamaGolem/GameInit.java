package it.unibs.fp.tamaGolem;

import it.kibo.fp.lib.Menu;

public class GameInit {
    public static void startGame() {
        String [] mode = {"Easy", "Medium", "Hardcore"};
        Menu modeMenu = new Menu("TamaGolem", mode, true, true, true);

    }
}
