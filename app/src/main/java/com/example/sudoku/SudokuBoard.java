package com.example.sudoku;

public class SudokuBoard {
    private int[][] board;

    public SudokuBoard(int[][] initialBoard) {
        this.board = initialBoard;
    }

    // Метод для получения текущего состояния игрового поля
    public int[][] getBoard() {
        return board;
    }

    public boolean isMoveValid(int row, int col, int value) {
        // Проверяем, что значение не находится в текущей строке и столбце
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == value || board[i][col] == value) {
                return false;
            }
        }

        // Проверяем, что значение не находится в текущем квадрате 3x3
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j] == value) {
                    return false;
                }
            }
        }

        return true;
    }

    public void setCellValue(int row, int col, int value) {
        board[row][col] = value;
    }

    public boolean isBoardFull() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void generateInitialBoard(int[][] solution) {
        // Копируем решение в начальное состояние поля
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = solution[i][j];
            }
        }

        // Другие методы будут добавлены позже
    }
}
