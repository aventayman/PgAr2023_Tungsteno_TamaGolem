package it.unibs.fp.tamaGolem;

import it.kibo.fp.lib.RandomDraws;

import java.util.Arrays;

public class Balance {
    int [][] balance;

    public Balance(int n, int hp) {
        this.balance = createBalance(n, hp);
    }

    public int[][] createBalance(int n, int hp) {
        final int GRID_SIZE = n;
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
        } while (!validRandomIndexes(indexMatrix, hp));

        do {
            for (int i = 0; i < indexMatrix.length; i++) {
                int randomNumber;
                do {
                    randomNumber = RandomDraws.drawInteger(-hp, hp);
                } while (randomNumber == 0);

                matrix[indexMatrix[i][0]][indexMatrix[i][1]] = randomNumber;
                matrix[indexMatrix[i][1]][indexMatrix[i][0]] = -randomNumber;
            }
        } while(!validRandomValues(matrix, hp));

        //Da rimuovere
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
    private boolean validRandomIndexes(int[][] indexMatrix, int hp) {

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

    /**
     * Metodo che controlla che su ogni riga, e di conseguenza colonna, la somma dei valori
     * già inizializzati non superi gli hp di un TamaGolem.
     * @param matrix la matrice da controllare
     * @param hp la vita massima del TamaGolem
     * @return ritorna true se la matrice è valida
     */
    private boolean validRandomValues(int [][] matrix, int hp) {
        for (int [] row : matrix) {
            int sum = 0;
            for (int value : row)
                sum += value;

            if (sum > hp)
                return false;
        }
        return true;
    }

    public int[][] getBalance() {
        return balance;
    }

    public void setBalance(int[][] balance) {
        this.balance = balance;
    }
}
