package model;

public class NQueenProblem {

    public String solveNQueens(int n, int startCol) {
        if (startCol < 0 || startCol >= n) {
            return "Columna inicial inválida para un tablero de " + n + "x" + n;
        }

        int[][] board = new int[n][n];
        // Colocamos la reina inicial en la fila 0, columna startCol
        board[0][startCol] = 1;

        // Intentamos colocar las reinas restantes desde la fila 1
        if (placeQueens(board, 1)) {
            return "Solución encontrada iniciando en la columna " + startCol + ":\n" + printBoard(board);
        }

        return "No se encontró solución para un tablero de " + n + "x" + n + " iniciando en la columna " + startCol;
    }

    public String solveNQueens(int n) {
        for (int i = 0; i < n; i++) {
            String result = solveNQueens(n, i);
            if (!result.contains("No se encontró")) {
                return result;
            }
        }
        return "No se encontró solución para un tablero de " + n + "x" + n;
    }

    private boolean placeQueens(int[][] board, int row) {
        if (row == board.length) {
            return true;
        }

        for (int col = 0; col < board.length; col++) {
            if (isSafe(board, row, col)) {
                board[row][col] = 1;
                if (placeQueens(board, row + 1)) {
                    return true;
                }
                board[row][col] = 0;
            }
        }
        return false;
    }

    private boolean isSafe(int[][] board, int row, int col) {
        // Columna hacia arriba
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1) return false;
        }
        // Diagonal superior izquierda
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) return false;
        }
        // Diagonal superior derecha
        for (int i = row, j = col; i >= 0 && j < board.length; i--, j++) {
            if (board[i][j] == 1) return false;
        }
        return true;
    }

    private String printBoard(int[][] board) {
        String result = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                result += board[i][j] == 1 ? "Q " : ". ";
            }
            result += "\n";
        }
        return result;
    }
}
