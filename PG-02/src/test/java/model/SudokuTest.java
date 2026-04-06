package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SudokuTest {

    @Test
    void testSudokuCompleto() {
        // Crear Sudoku
        Sudoku sudoku = new Sudoku();

        // Verificar que está vacío
        assertEquals(0, sudoku.getBoard()[0][0]);

        // Colocar un número válido
        boolean result1 = sudoku.placeNumber(0, 0, 5);
        assertTrue(result1);
        assertEquals(5, sudoku.getBoard()[0][0]);

        // Intentar colocar el mismo número en la misma fila (debe fallar)
        boolean result2 = sudoku.placeNumber(0, 1, 5);
        assertFalse(result2);

        // Limpiar la celda
        sudoku.clearCell(0, 0);
        assertEquals(0, sudoku.getBoard()[0][0]);

        // Verificar que no está resuelto
        assertFalse(sudoku.isSolved());

        // Crear un Sudoku completo
        int[][] tableroCompleto = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };

        Sudoku sudokuCompleto = new Sudoku(tableroCompleto);
        assertTrue(sudokuCompleto.isSolved());

        System.out.println("Todos los tests pasaron!");
    }
}
