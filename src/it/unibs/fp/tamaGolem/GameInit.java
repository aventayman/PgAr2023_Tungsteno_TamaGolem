package it.unibs.fp.tamaGolem;

import it.kibo.fp.lib.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Classe che genera i menu d'inizializzazione della partita, con i relativi dati che andranno a influenzare
 * numero di golem, pietre, ecc.
 */
public class GameInit {
    /**
     * Metodo che genera il menu iniziale e registra la scelta della difficoltà
     * @return il numero di elementi che verranno utilizzati nella partita e nella generazione dell'equilibrio
     */
    public static int startGame() {
        int amount = 0;
        String [] mode = {"Easy", "Normal", "Hardcore"};
        Menu modeMenu = new Menu("TamaGolem", mode, true, true, true);
        int choice = modeMenu.choose();

        switch (choice) {
            case 1 -> amount = 3;
            case 2 -> amount = 6;
            case 3 -> amount = 9;
        }

        return amount;
    }

    /**
     * Metodo che ritorna il numero di pietre per golem per questa partita
     * Utilizza le formule fornite dal regolamento per calcolare il valore richiesto
     * @param amount numero N di elementi della partita
     * @return il numero di pietre per golem per la partita
     */
    private static int stonesNum (int amount) {
        return (int)(Math.ceil((float)(amount+1)/3))+1;
    }

    /**
     * Metodo che ritorna il numero di golem per giocatore di questa partita
     * Utilizza le formule fornite dal regolamento per calcolare il valore richiesto
     * @param amount numero N di elementi della partita
     * @param stonesNum il numero di pietre per golem per la partita
     * @return il numero di golem per la partita
     */
    private static int golemNum (int amount, int stonesNum) {
        return (int)Math.ceil(((float)(amount-1)*(amount-2)/2*stonesNum));
    }

    /**
     * Metodo che ritorna il numero di pietre che saranno presenti nella sacca comune
     * Utilizza le formule fornite dal regolamento per calcolare il valore richiesto
     * @param amount numero N di elementi della partita
     * @param stonesNum il numero di pietre per golem per la partita
     * @param golemNum il numero di golem per la partita
     * @return il numero di pietre presenti nella sacca comune
     */
    private static int chestDim (int amount, int stonesNum, int golemNum) {
        return (int)(Math.ceil((float)(2*golemNum*stonesNum)/amount)) * amount;
    }

    /**
     * Metodo che fornisce il numero di pietre per ciascun elemento che ci saranno della sacca comune
     * Utilizza la formula fornita dal regolamento per calcolare il valore richiesto
     * @param chestDim il numero di pietre presenti nella sacca comune
     * @param amount numero N di elementi della partita
     * @return il numero di pietre per elemento
     */
    private static int stonesPerElement (int chestDim, int amount) {
        return chestDim/amount;
    }

    /**
     * Metodo che inizializza i dati inseriti tramite il valore amount fornito
     * @param amount numero N di elementi della partita
     * @param s il numero di pietre per golem per la partita
     * @param g il numero di golem per la partita
     * @param c il numero di pietre presenti nella sacca comune
     * @param spe il numero di pietre per elemento
     */
    public static void dataInit (int amount, int s, int g, int c, int spe){
        s = GameInit.stonesNum(amount);
        g = GameInit.golemNum(amount, s);
        c = GameInit.chestDim(amount, s, g);
        spe = GameInit.stonesPerElement(c, amount);
    }

    /**
     * Metodo per la scelta di un elemento per lo scontro
     * @param choice numero inserito dall'utente, che corrisponde ad un elemento
     * @return l'elemento sottoforma di string
     */
    private static Element choice (int choice) {
        Element element;
        switch(choice){
            case 1 -> element = Element.WATER;
            case 2 -> element = Element.AIR;
            case 3 -> element = Element.FIRE;
            case 4 -> element = Element.EARTH;
            case 5 -> element = Element.ETHER;
            case 6 -> element = Element.ELECTRO;
            case 7 -> element = Element.PLASMA;
            case 8 -> element = Element.ANTIMATTER;
            case 9 -> element = Element.LIGHT;
            case 10 -> element = Element.DARKNESS;
            default -> element = Element.ERROR;
        }
        return element;
    }

    /** Metodo di creazione della lista di elementi disponibili
     * @param n numero N di elementi della partita
     * @return gli elementi disponibili per la partita corrente
     */
    private static String[] availableElements (int n) {
        String [] elements = new String[n];
        for (int i = 0; i < n; i++){
            elements[i] = Arrays.asList(Element.values()).get(i).toString();
        }
        return elements;
    }

    /**
     * Metodo per verificare se il valore inserito è valido per essere assegnato a un elemento
     * @param value valore da controllare
     * @param n valore N degli elementi della partita in corso
     * @param list lista in cui fare il controllo, verificando che non vi sia presente, e quindi che sia stato scelto
     * @return true se il valore è valido, false altrimenti
     */
    private static boolean valueControl (int value, ArrayList<int> list, int n){
        for (int iValue : list){
            if(iValue == value)
                return false;
        }

        if (value > n)
            return false;

        return true;
    }
    /**
     * Metodo che inserisce nella lista fornita gli elementi scelti dal giocatore
     */
    public static void stoneElementsChoice (int n, ArrayList<Element> golemElements) {

        Scanner scanner = new Scanner(System.in);


        Menu choiceMenu = new Menu("Choose your elements", availableElements(n), true, true,
                true);

        //Stampa inizialmente il menu di scelta degli elementi, disponibili in numero pari ad n

        //Dichiara nextElement, variabile che serve a memorizzare il carattere inserito dal giocatore,
        //al fine di memorizzare la sua scelta
        int nextElement = choiceMenu.choose();

        //Se il valore inserito è maggiore di n, esso non corrisponderà ad alcun elemento, perciò viene visualizzato
        //un messaggio di errore assieme alla richiesta di reinserimento, fino a quando non viene inserito un
        //intero valido

        //NOTA: MANCANO I CONTROLLI SUL VALORE SCANNERIZZATO (SE è FLOAT, UN CARATTERE E NON UN NUMERO...)
        while (nextElement > n) {
            System.out.println("Incorrect value entered. Please insert another value for the element choice:");
            nextElement = scanner.nextInt();
        }
        golemElements.add(choice(nextElement));
        ArrayList<int> alreadyTaken = new ArrayList<int>();
        alreadyTaken.add(nextElement);

        int previousElement = nextElement;

        for (int i = 1; i < n; i++) {
            System.out.println("Choose the next element from the previous list");
            nextElement = scanner.nextInt();
            while (valueControl(nextElement, alreadyTaken, n)){
                System.out.println("Incorrect value entered. Please insert another value for the element choice:");
                nextElement = scanner.nextInt();

            }
            previousElement = nextElement;
            golemElements.add(choice(nextElement));
        }
    }
}
