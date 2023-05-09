package it.unibs.fp.tamaGolem;

import it.ayman.fp.lib.*;

import java.util.ArrayList;
import java.util.List;

public class Fight {
    private static final String RED_ATTENTION = PrettyStrings.colorString("Attention!", AnsiColors.RED);
    private static final String COMMAND_SIGN = "> ";
    private static final String SELECT_STONE = COMMAND_SIGN +
                    "%s, select the stones you want to give to %s from the chest:";
    private static final String STONE_CHOICE = "Insert the index of the stone you want to give to your TamaGolem:";
    private static final String STONES_FINISHED = COMMAND_SIGN + RED_ATTENTION + "\n" +
            COMMAND_SIGN + "The chosen stone has ran out.";
    private static final String DAMAGE_DEALT = COMMAND_SIGN + "%s with %s stone dealt %d damage to %s";
    private static final String NO_DAMAGE_DEALT = COMMAND_SIGN + "None of the two TamaGolems was hurt!";
    private static final String SAME_LIST = COMMAND_SIGN + RED_ATTENTION + "\n" + COMMAND_SIGN
            + "You chose the same elements as the other player. Please choose again.";
    private static final String DEAD_GOLEM = PrettyStrings.colorString("DIED!", AnsiColors.RED);
    private static final String WIN_MESSAGE = "won";
    private static final String TURN = COMMAND_SIGN + PrettyStrings.colorString("TURN %d", AnsiColors.GREEN);
    private static final String PRESS_INSERT = "Press anything and enter to continue:";
    private static final int DELAY = 1000;

    /**
     * Metodo che stampa la chest restituisce una lista di elementi scelti dal giocatore
     * @param game in cui ci si trova
     * @return la lista di elementi scelti dal giocatore
     */
    public static List<Element> selectGolemStones (Game game, Player player) {
        List<Element> tempList = new ArrayList<>();

        //Stampa di messaggi e tabella per interazione con l'utente
        System.out.printf(SELECT_STONE + "%n", PrettyStrings.colorString(player.getName(), player.getColor()),
                PrettyStrings.colorString(player.getCurrentGolem().getName(), player.getColor()));

        //Scelta del giocatore
        Element chosenElement;

        game.printChest();

        //Ripete l'inserimento di una scelta tante volte quante sono le pietre per golem del game corrente
        for (int i = 0; i < game.getStonesPerGolem(); i++) {
            int choice = InputData.readIntegerBetween(STONE_CHOICE, 1, game.getElements().length) - 1;
            chosenElement = game.getElements()[choice];

            //Controllo che la pietra scelta sia ancora presente nella chest.
            //In caso contrario stampo un messaggio d'errore e richiedo un nuovo inserimento
            while (!game.isStoneInChest(chosenElement)) {
                System.out.println(STONES_FINISHED);
                choice = InputData.readIntegerBetween(STONE_CHOICE, 1, game.getElements().length) - 1;
                chosenElement = game.getElements()[choice];
            }

            tempList.add(chosenElement);
        }

        Menu.clearConsole();
        return tempList;
    }

    /**
     * Metodo che gestisce un ciclo di un fight
     * Per ciclo si intende una fase che inizia con il primo lancio di due pietre fra due golem e
     * finisce con la morte di uno di essi
     * @param golem1 primo golem combattente
     * @param golem2 secondo golem combattente
     * @param game il game in cui ci si trova durante questo ciclo
     */
    private static void fightCycle (TamaGolem golem1, TamaGolem golem2, Game game) throws InterruptedException {

        int stoneIndex = 0;

        //Ciclo while che si ripete fino a quando uno dei due golem non termina gli hp
        while (golem1.getHp() > 0 && golem2.getHp() > 0) {
            //Variabile di supporto che varierà da zero al numero massimo di pietre per golem -1
            //Serve per reiterare al'interno della lista di pietre di un golem, ripetendo i lanci ciclicamente
            stoneIndex = stoneIndex % game.getStonesPerGolem();

            int damageDealt = game.evaluateDamage(golem1.getStoneList().get(stoneIndex),
                    golem2.getStoneList().get(stoneIndex));

            //Se il damageDealt è positivo, vuol dire che golem1 infligge danni, in caso contrario li riceve
            if (damageDealt > 0) {
                golem2.setHp(golem2.getHp() - damageDealt);
                System.out.printf(DAMAGE_DEALT + "\n", PrettyStrings.colorString(golem1.getName(), AnsiColors.RED),
                        golem1.getStoneList().get(stoneIndex) , damageDealt,
                        PrettyStrings.colorString(golem2.getName(), AnsiColors.BLUE));
            }
            else if (damageDealt < 0) {
                golem1.setHp(golem1.getHp() + damageDealt);
                System.out.printf(DAMAGE_DEALT + "\n", PrettyStrings.colorString(golem2.getName(), AnsiColors.BLUE),
                        golem2.getStoneList().get(stoneIndex), -damageDealt,
                        PrettyStrings.colorString(golem1.getName(), AnsiColors.RED));
            }
            //Caso in cui le pietre sono uguali, e quindi il danno è nullo
            else
                System.out.println(NO_DAMAGE_DEALT);
            stoneIndex++;
            Menu.wait(DELAY);
        }

        //Se il golem1 non ha più hp, compare il messaggio relativo...
        if (golem1.getHp() <= 0){
            System.out.println(COMMAND_SIGN + PrettyStrings.colorString(
                    golem1.getName(), AnsiColors.RED) + " " + DEAD_GOLEM);
        }

        //...in caso contrario, compare il messaggio relativo all'altro golem
        else{
            System.out.println(COMMAND_SIGN + PrettyStrings.colorString(
                    golem2.getName(), AnsiColors.BLUE) + " " + DEAD_GOLEM);
        }
        InputData.readString(PRESS_INSERT, false);
        Menu.clearConsole();
    }

    /**
     * Metodo per controllare se due liste di pietre dei golem sono uguali fra loro
     * @param golem1 primo golem del confronto
     * @param golem2 secondo golem del confronto
     * @return true se le liste sono uguali, con un messaggio in stampa che avverte della coincidenza,
     * false in caso contrario
     */
    private static boolean sameList (TamaGolem golem1, TamaGolem golem2) {
        if (golem1.getStoneList().equals(golem2.getStoneList())){
            System.out.println(SAME_LIST);
            return true;
        }
        return false;
    }

    /**
     * Metodo che viene richiamato quando occorre la selezione da parte del giocatore delle pietre da assegnare al
     * proprio golem
     * @param golem1 golem a cui assegnare la nuova lista
     * @param golem2 golem per il confronto, in caso le liste fossero uguali
     * @param game il game in cui avviene lo scontro
     * @param player riguardo cui verranno stampati i messaggi a schermo
     */
    private static void stoneSelection(TamaGolem golem1, TamaGolem golem2, Game game, Player player) {
        //Assegno una nuova list al golem1, con elementi scelti dal giocatore tramite il metodo selectGolemStones
        List<Element> stoneList = selectGolemStones(game, player);
        golem1.setStoneList(stoneList);

        //Ripeto la scelta fino a quando il giocatore sceglie una lista diversa da quella del golem dell'avversario,
        //che si trova già in campo in quel momento
        while(sameList(golem1, golem2)) {
            stoneList = selectGolemStones(game, player);
            golem1.setStoneList(stoneList);
        }

        //Se la lista rispetta i criteri necessari, viene rimossa dalla chest
        game.removeStoneListFromChest(stoneList);
    }

    /**
     * Metodo che gestisce lo scontro fra due giocatori. A ogni ciclo, chiede al giocatore di scegliere le pietre dalla
     * chest, le inserisce nei golem e inizia lo scontro fra i due.
     * Al termine dello scontro, viene stampato il nome del giocatore vincitore
     * @param game il game in cui avviene lo scontro
     */
    public static void battle (Game game) throws InterruptedException {
        //Inizializzo i golem dei giocatori
        TamaGolem currentGolem1 = game.getPlayer1().getCurrentGolem();
        TamaGolem currentGolem2 = game.getPlayer2().getCurrentGolem();

        //Inizializzo la list del golem del primo giocatore e la rimuovo dalla chest
        List<Element> stoneList = selectGolemStones(game, game.getPlayer1());
        game.removeStoneListFromChest(stoneList);
        currentGolem1.setStoneList(stoneList);

        //Inizializzo la list del secondo giocatore e la rimuovo dalla chest
        stoneSelection(currentGolem2, currentGolem1, game, game.getPlayer2());

        //Variabile a scopo grafico
        int turn = 1;
        //Fino a quando entrambe le liste di gole degli avversari presentano un golem con hp>0, gli scontri si
        //ripeteranno ciclicamente
        while(currentGolem1 != null && currentGolem2 != null) {
            Menu.clearConsole();
            System.out.printf(TURN + "%n", turn++);

            //Inizio dello scontro. Dopo questo metodo, la vita di uno dei due golem passati sarà zero
            fightCycle(currentGolem1, currentGolem2, game);

            //Golem di supporto. Serve a memorizzare i dati del primo golem: se esso sarà cambiato nel corso del prossimo
            //turno, allora vorrà dire che il golem1 è morto, altrimenti è cambiato il golem2
            TamaGolem previousGolem1 = currentGolem1;
            //Vengono aggiornati i golem dei player. Uno dei due cambierà, mentre l'altro rimmarrà quello precedente
            currentGolem1 = game.getPlayer1().getCurrentGolem();
            currentGolem2 = game.getPlayer2().getCurrentGolem();

            //Se entrambi i golem sono vivi, e quindi non ci troviamo alla fine di una partita, allora occorre
            //scegliere una nuova lista per il nuovo Tamagolem schierato
            if(currentGolem1 != null && currentGolem2 != null){
                //Se il golem precedente, ovvero quello in vita, è ancora il golem1, allora:
                if (previousGolem1 == currentGolem1) {
                    stoneSelection(currentGolem2, currentGolem1, game, game.getPlayer2());
                }
                //Altrimenti si tratta del golem2 quello ancora in vita, e quindi:
                else {
                    stoneSelection(currentGolem1, currentGolem2, game, game.getPlayer1());
                }
            }
        }

        Menu.clearConsole();

        //La partita è terminata. Se il golem1 non è più in vita, vuol dire che il player1 ha terminato i golem e quindi
        //è sconfitto. Di conseguenza verrà stampato il nome del player2
        if (currentGolem1 == null)
            System.out.println(PrettyStrings.colorString(Title.createTitle(
                    game.getPlayer2().getName() + " " + WIN_MESSAGE, true) + "%n",
                    game.getPlayer2().getColor()));

        //altrimenti verrà stampato il nome del player1, che avrà una lista di golem piena (e quindi non null)
        else
            System.out.println(PrettyStrings.colorString(Title.createTitle(
                    game.getPlayer1().getName() + " " + WIN_MESSAGE, true) + "%n",
                    game.getPlayer1().getColor()));
    }
}
