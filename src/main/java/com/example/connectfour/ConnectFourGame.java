package com.example.connectfour;

import java.util.Random;

public class ConnectFourGame {
    // Constants representing the game configuration and states
    public static final int ROW = 7;
    public static final int COL = 6;
    public static final int EMPTY = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;
    public static final int DISCS = 42;

    // Member variables representing the game board and the current player
    private int[][] boardGrid;
    private int player = BLUE;

    // Returns the current player as a string ("Blue" or "Red")
    public String getPlayer() {
        return (player == BLUE) ? "Red" : "Blue";
    }

    // Constructor to initialize the game board and start a new game
    public ConnectFourGame() {
        boardGrid = new int[ROW][COL];
        newGame();
    }

    // Initializes a new game by resetting the board and setting the first player to Blue
    public void newGame() {
        player = BLUE;  // Set Blue as the first player

        // Initialize the board to be empty
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                boardGrid[row][col] = EMPTY;
            }
        }
    }

    // Returns the current state of the game board as a string
    public String getState() {
        StringBuilder boardString = new StringBuilder();
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                boardString.append(boardGrid[row][col]);
            }
        }
        return boardString.toString();
    }

    // Returns the disc at the specified row and column
    public int getDisc(int row, int col) {
        return boardGrid[row][col];
    }

    // Checks if the game is over either by win condition or by all tiles being filled
    public boolean isGameOver() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (isWin()) {
                    return true;
                }
                if (boardGrid[row][col] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    // Checks if there is a winning condition on the board
    public boolean isWin() {
        return checkHorizontal() || checkVertical() || checkDiagonal();
    }

    // Checks for four consecutive discs of the same color horizontally
    private boolean checkHorizontal() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j <= COL - 4; j++) {
                if (boardGrid[i][j] != EMPTY &&
                        boardGrid[i][j + 1] == boardGrid[i][j] &&
                        boardGrid[i][j + 2] == boardGrid[i][j] &&
                        boardGrid[i][j + 3] == boardGrid[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    // Checks for four consecutive discs of the same color vertically
    private boolean checkVertical() {
        for (int i = 0; i <= ROW - 4; i++) {
            for (int j = 0; j < COL; j++) {
                if (boardGrid[i][j] != EMPTY &&
                        boardGrid[i + 1][j] == boardGrid[i][j] &&
                        boardGrid[i + 2][j] == boardGrid[i][j] &&
                        boardGrid[i + 3][j] == boardGrid[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    // Checks for four consecutive discs of the same color diagonally (both directions)
    private boolean checkDiagonal() {
        // Check diagonals from top-left to bottom-right
        for (int i = 0; i <= ROW - 4; i++) {
            for (int j = 0; j <= COL - 4; j++) {
                if (boardGrid[i][j] != EMPTY &&
                        boardGrid[i][j] == boardGrid[i + 1][j + 1] &&
                        boardGrid[i][j] == boardGrid[i + 2][j + 2] &&
                        boardGrid[i][j] == boardGrid[i + 3][j + 3]) {
                    return true;
                }
            }
        }

        // Check diagonals from top-right to bottom-left
        for (int i = 0; i <= ROW - 4; i++) {
            for (int j = 3; j < COL; j++) {
                if (boardGrid[i][j] != EMPTY &&
                        boardGrid[i][j] == boardGrid[i + 1][j - 1] &&
                        boardGrid[i][j] == boardGrid[i + 2][j - 2] &&
                        boardGrid[i][j] == boardGrid[i + 3][j - 3]) {
                    return true;
                }
            }
        }

        return false;
    }

    // Sets the game board state from a string representation
    public void setState(String gameState) {
        int index = 0;
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                boardGrid[row][col] = gameState.charAt(index) - '0'; // Convert char to int
                index++;
            }
        }
    }

    // Selects the highest possible tile in the specified column
    public void selectDisc(int row, int col) {
        // Start from the bottom and move upwards
        for (int i = ROW - 1; i >= 0; i--) {
            if (boardGrid[i][col] == EMPTY) {
                boardGrid[i][col] = player;  // Place the disc
                switchPlayer();  // Switch to the other player
                break;
            }
        }
    }

    // Switches the current player to the opposite color
    private void switchPlayer() {
        player = (player == BLUE) ? RED : BLUE;
    }
}
