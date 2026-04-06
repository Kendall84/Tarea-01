package model;

import org.junit.jupiter.api.Test;


class NQueenProblemTest {
    @Test
    void solveNQueensTest(){
        NQueenProblem nqueenProblem = new NQueenProblem();
        System.out.println("Problema de las N reinas para n=4 (Iniciando en columna 1):");
        System.out.println(nqueenProblem.solveNQueens(4, 1));

        System.out.println("Problema de las N reinas para n=8 (Iniciando en columna 3):");
        System.out.println(nqueenProblem.solveNQueens(8, 4));

    }

}
