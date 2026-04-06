package model;
public class Sudoku {
    private int[][] board;
    public static final int size = 9;
    public static final int EMPTY = 0;

    public Sudoku() {
        this.board = new int[size][size];
    }

    public Sudoku(int[][] initialBoard) {
        this.board = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(initialBoard[i], 0, this.board[i], 0, size);
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    /**
     * Intenta colocar un número en la posición dada.
     * @param row Fila (0-8)
     * @param col Columna (0-8)
     * @param num Número a colocar (1-9)
     * @return true si el movimiento es válido y se realizó, false si no cumple las reglas.
     */
    public boolean placeNumber(int row, int col, int num) {
        if (isValidMove(row, col, num)) {
            board[row][col] = num;
            return true;
        }
        return false;
    }

    /**
     * Elimina el número en la posición dada (lo pone a 0).
     */
    public void clearCell(int row, int col) {
        board[row][col] = EMPTY;
    }

    /**
     * Verifica si es válido colocar un número en una posición específica según las reglas del Sudoku.
     */
    public boolean isValidMove(int row, int col, int num) {
        // Verificar fila
        for (int c = 0; c < size; c++) {
            if (board[row][c] == num) {
                return false;
            }
        }

        // Verificar columna
        for (int r = 0; r < size; r++) {
            if (board[r][col] == num) {
                return false;
            }
        }

        // Verificar subcuadrícula 3x3
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r + startRow][c + startCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isSolved() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (board[r][c] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}
