package it.unibs.fp.tamaGolem;

import it.kibo.fp.lib.RandomDraws;

import java.awt.*;
import java.util.Arrays;

public class Balance {
    int [][] balance;

    public Balance(int n, int hp) {
        this.balance = createBalance(n, hp);
    }

    public int[][] createBalance(int n, int hp) {
        //int [][] matrix = new int[n][n];
    int [][] matrix = {
            {0, 4, 0, 6},
            {-4, 0, -2, 0},
            {0, 2, 0, 0},
            {-6, 0, 0, 0}
    };
        /*
        //Generazione di n - 1 coppie di indici da inizializzare random
        int [][] indexMatrix = new int[n-1][2];

        do {
            //Genera n - 1 coppie di indici e verifica che siano posizioni valide
            for (int i = 0; i < n - 1; i++) {
                int row = RandomDraws.drawInteger(0, n - 1);
                int column = RandomDraws.drawInteger(0, n - 1);
                indexMatrix[i] = new int[] {row, column};
            }
        } while (!validRandomIndexes(indexMatrix));

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
*/
        solveMatrix(matrix);

        //Da rimuovere
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
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
    private boolean validRandomIndexes(int[][] indexMatrix) {

        for (int[] indexes : indexMatrix) {
            if (indexes[0] == indexes[1])
                return false;
        }

        for (int i = 0; i < indexMatrix.length - 1; i++) {
            for (int j = i + 1; j < indexMatrix.length; j++) {
                //Controllo che le due posizioni non siano uguali
                if (indexMatrix[i][0] == indexMatrix[j][0] && indexMatrix[i][1] == indexMatrix[j][1])
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
        int n = matrix.length;
        for (int [] row : matrix) {
            int sum = 0;
            for (int value : row)
                sum += value;

            if (sum < -hp * (n - 1) / 2 || sum > hp * (n - 1) / 2)
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

    private boolean solveMatrix(int [][] matrix) {
        int n = matrix.length;
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                if (row != column && matrix[row][column] == 0) {
                    for (int numberToTry = -20; numberToTry <= 20; numberToTry++) {
                        if (numberToTry != 0) {
                            matrix[row][column] = numberToTry;
                            matrix[column][row] = -numberToTry;

                            if (isFullRow(matrix, row)) {
                                if (isValidRow(matrix, row)) {
                                    if (solveMatrix(matrix))
                                        return true;
                                }
                                else {
                                    matrix[row][column] = 0;
                                    matrix[column][row] = 0;
                                }
                            }
                            else {
                                solveMatrix(matrix);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isFullRow(int [][] matrix, int row) {
        int zeroCounter = 0;
        for (int element : matrix[row]) {
            if (element == 0)
                zeroCounter++;
        }

        return zeroCounter < 2;
    }

    private boolean isValidRow(int [][] matrix, int row) {
        int sum = 0;
        for (int number : matrix[row])
            sum += number;

        return sum == 0;
    }
}
