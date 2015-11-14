import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Colin on 11/12/2015
 */
 
public class TicTacToe {
    int size;
    int dimensions;
    char[][] board;
    char[][][] board3D;

    public static final String PLAYER1 = "Player 1";
    public static final String PLAYER2 = "Player 2";

    public static final char O = 'O';
    public static final char X = 'X';
    public static final char BLANK = ' ';

    public static void main(String[] args) {
        new TicTacToe();
    }

    public TicTacToe() {
        this.initializeBoard();
        //creates character array with given length and width
        if (this.dimensions == 2)
            this.board = new char[this.size][this.size];
        else
            this.board3D = new char[this.size][this.size][this.size];
        //fills array with empty spaces
        if (this.dimensions == 2) {
            for (int i = 0; i < this.board.length; i++) {
                for (int j = 0; j < this.board[i].length; j++) {
                    this.board[i][j] = BLANK;
                }
            }
        } else {
            for (int i = 0; i < this.board3D.length; i++) {
                for (int j = 0; j < this.board3D[i].length; j++) {
                    for (int k = 0; k < this.board3D[i][j].length; k++) {
                        this.board3D[i][j][k] = BLANK;
                    }
                }
            }
        }
        this.play();
    }

    public void initializeBoard() {
        Scanner s = new Scanner(System.in);
        System.out.println("Would you like to play in 2 or 3 dimensions?");
        this.dimensions = s.nextInt();
        do {
            System.out.println("What would you like the length and height of the board to be?");
            if (!s.hasNextInt()) {
                System.out.println("Please enter a number greater than or equal to 3.");
                s.next();
            }
            try {
                this.size = s.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number, not a letter.");
            }
        } while (this.size < 3);

    }

    public void drawBoard(int dimension) {
        if (dimension == 2) {
            for (int i = 0; i < this.board.length; i++) {
                for (int j = 0; j < this.board[i].length; j++) {
                    if (j == this.board[i].length - 1) {
                        System.out.printf(" %c ", this.board[i][j]);
                        continue;
                    }
                    System.out.printf(" %c |", this.board[i][j]);
                }
                if (i != this.board.length - 1) {
                    System.out.printf("\n");
                    // print the line between each row
                    for (int k = 0; k < board[0].length; k++)
                        System.out.printf("----");
                    System.out.printf("\n");
                }
            }
            System.out.println();
        } else {
            for (int i = 0; i < this.board3D.length; i++) {
                System.out.println("Layer " + (i + 1));
                for (int j = 0; j < this.board3D[i].length; j++) {
                    for (int k = 0; k < this.board3D[i][j].length; k++) {
                        if (k == this.board3D[i][j].length - 1) {
                            System.out.printf(" %c ", this.board3D[i][j][k]);
                            continue;
                        }
                        System.out.printf(" %c |", this.board3D[i][j][k]);
                    }
                    if (j != this.board3D.length - 1) {
                        System.out.printf("\n");
                        // print the line between each row
                        for (int k = 0; k < board3D[0].length; k++)
                            System.out.printf("----");
                        System.out.printf("\n");
                    }
                }
                System.out.println();

            }
        }
    }

    public boolean placePiece(String coordinates, String player) {
        if (this.dimensions == 2) {
            int x = split(coordinates)[0];
            int y = split(coordinates)[1];
            if (isSpaceValid(coordinates)) {
                if (player.equals(PLAYER1)) {
                    this.board[x][y] = X;
                } else {
                    this.board[x][y] = O;
                }
                return true;
            }
            return false;
        } else {
            int x = split(coordinates)[0];
            int y = split(coordinates)[1];
            int z = split(coordinates)[2];
            if (isSpaceValid(coordinates)) {
                if (player.equals(PLAYER1)) {
                    this.board3D[x][y][z] = X;
                } else {
                    this.board3D[x][y][z] = O;
                }
                return true;
            }
            return false;
        }
    }

    public int[] split(String coordinates) {
        String[] split = coordinates.split(",");
        int x = Integer.parseInt(split[0]) - 1;
        int y = Integer.parseInt(split[split.length-1]) - 1;
        int[] res = new int[split.length];
        res[0] = x;
        res[1] = y;
        return res;
    }

    public boolean isSpaceValid(String currentMove) {
        if (this.dimensions == 2) {
            if (this.board[split(currentMove)[0]][split(currentMove)[1]] == BLANK)
                return true;
            return false;
        } else {
            if (this.board3D[split(currentMove)[0]][split(currentMove)[1]][split(currentMove)[2]] == BLANK)
                return true;
            return false;
        }
    }

    public int checkIfWinner2D(char[][] board) {
        char initial;
        if (this.size < 6) {
            //check rows
            int count = 0;
            for (char[] row : board) {
                initial = row[0];
                int totalPieces = 0;
                for (int i = 0; i < board[count].length; i++) {
                    if (initial == board[count][i] && board[count][i] != BLANK)
                        totalPieces++;
                }
                count++;
                if (totalPieces == board.length)
                    return 1;
            }

            //check columns
            count = 0;
            for (int i = 0; i < board.length; i++) {
                initial = board[0][i];
                int totalPieces = 0;
                for (int j = 0; j < board[count].length; j++) {
                    if (initial == board[j][count] && board[j][count] != BLANK)
                        totalPieces++;
                }
                count++;
                if (totalPieces == board.length)
                    return 1;
            }

            //check diagonal /
            count = 0;
            initial = board[0][2];
            int totalPieces = 0;
            for (int j = 0; j < board[count].length; j++) {
                if (initial == board[j][2 - j] && board[j][2 - j] != BLANK)
                    totalPieces++;
            }
            if (totalPieces == board.length)
                return 1;

            //check diagonal \
            count = 0;
            initial = board[0][0];
            totalPieces = 0;
            for (int j = 0; j < board[count].length; j++) {
                if (initial == board[j][j] && board[j][j] != BLANK)
                    totalPieces++;
            }
            if (totalPieces == board.length)
                return 1;
        }
        return 0;
    }

    public int checkIfWinner3D(char[][][] board) {
        for (int i = 0; i < this.board3D.length; i++) {
            if (this.checkIfWinner2D(board[i]) == 1)
                return 1;
        }
        return 0;
    }

    public void play() {
        Scanner s = new Scanner(System.in);
        String currentPlayer = PLAYER2;
        String currentMove;
        boolean winner = false;

        do {
            currentPlayer = currentPlayer.equals(PLAYER1) ? PLAYER2 : PLAYER1;
            System.out.println("Current Player: " + currentPlayer + "\n");
            this.drawBoard(this.dimensions);
            if (this.dimensions == 2)
                System.out.println("\nPlease enter the coordinates for where you'd like to place your piece (format " +
                        "'x, y'):");
            else
                System.out.println("\nPlease enter the coordinates for where you'd like to place your piece (format " +
                        "'x, y, z'):");
            currentMove = s.nextLine();
            this.placePiece(currentMove, currentPlayer);
            if (this.dimensions == 2) {
                if (checkIfWinner2D(this.board) == 1) {
                    this.drawBoard(this.dimensions);
                    winner = true;
                }
            } else {
                if (checkIfWinner3D(this.board3D) == 1) {
                    this.drawBoard(this.dimensions);
                    winner = true;
                }
            }
        } while (!winner);
        System.out.printf("\nGame over!\n" + currentPlayer + " wins!\n");
    }
}
