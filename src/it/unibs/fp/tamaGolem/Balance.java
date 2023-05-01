package it.unibs.fp.tamaGolem;

import it.kibo.fp.lib.RandomDraws;

import java.util.Arrays;

public class Balance {
    int [][] balance;

    public static int[][] createBalance(Game game) {
        final int GRID_SIZE = game.getElements().size();
        int [][] matrix = new int[GRID_SIZE][GRID_SIZE];

        //Generazione di GRID_SIZE - 1 coppie di indici da inizializzare random
        int [][] indexMatrix = new int[GRID_SIZE-1][2];

        do {
            //Genera GRID_SIZE - 1 coppie di indici e verifica che siano posizioni valide
            for (int i = 0; i < GRID_SIZE - 1; i++) {
                int row = RandomDraws.drawInteger(0, GRID_SIZE - 1);
                int column = RandomDraws.drawInteger(0, GRID_SIZE - 1);
                indexMatrix[i] = new int[] {row, column};
            }
        } while (!validRandomIndexes(indexMatrix));

        for (int i = 0; i < indexMatrix.length; i++) {
            int randomNumber;
            do {
                randomNumber = RandomDraws.drawInteger(-10, 10);
            } while (randomNumber == 0);

            matrix[indexMatrix[i][0]][indexMatrix[i][1]] = randomNumber;
            matrix[indexMatrix[i][1]][indexMatrix[i][0]] = -randomNumber;
        }

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                System.out.printf("%3d", matrix[i][j]);
            }
            System.out.print("\n");
        }
        return matrix;
    }

    /**
     * Bisogna verificare che gli indici row e column non siano uguali tra di loro perché altrimenti
     * sarebbero valori della diagonale, poi verificare che le coppie di indici siano univoche all'interno
     * della matrice, per ultimo bisogna verificare che una riga non sia già del tutto determinata in seguito
     * alla generazione dei numeri casuali.
     * @param indexMatrix la matrice degli indici dove si esegue il controllo
     * @return se gli indici sono validi ritorna true
     */
    private static boolean validRandomIndexes(int[][] indexMatrix) {

        for (int[] indexes : indexMatrix) {
            if (indexes[0] == indexes[1])
                return false;
        }

        for (int i = 0; i < indexMatrix.length - 1; i++) {
            for (int j = i + 1; j < indexMatrix.length; j++) {
                //Controllo che le due posizioni non siano uguali
                if (indexMatrix[i] == indexMatrix[j])
                    return false;
                //Controllo che le due posizioni non siano simmetriche
                if (indexMatrix[i][0] == indexMatrix[j][1] && indexMatrix[i][1] == indexMatrix[j][0])
                    return false;
            }
        }

        //Array totale degli indici
        int [] indexArray = new int[indexMatrix.length * 2];

        for (int i = 0; i < indexMatrix.length * 2; i++) {
            indexArray[i] = indexMatrix[i / 2][i % 2];
        }

        int [] indexCounter = new int[indexMatrix.length + 1];

        //i rappresenta il numero di cui controllare la frequenza
        for (int i = 0; i < indexMatrix.length + 1; i++) {
            //j rappresenta il j-esimo indice nell'indexArray
            for (int j : indexArray) {
                if (i == j)
                    indexCounter[i]++;
            }
        }

        for (int i : indexCounter) {
            if (i >= indexMatrix.length)
                return false;
        }

        return true;
    }
}
