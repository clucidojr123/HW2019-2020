import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * This class represents a text-based game where two users play against each other in connect four.
 * The first player to place four consecutive chips of their color in a row, column, or diagonal wins.
 * If the board becomes filled with no winner, a tie is announced.
 * @author Cesare Lucido
 *     e-mail: clucidojr123@gmail.com
 */
public class ConnectFour {
    // Initialize global variables/objects
    public static char[][] board = new char[6][7];
    public static int[] count = new int[7];
    public static Scanner scan = new Scanner(System.in);
    public static int turn = 0;
    public static void main(String[] args){
        boolean status = true;
        // Turn sequence
        while(status) {
            printBoard();
            if(turn == 42) {
                System.out.println("There are no more slots to fill. It's a draw!");
                status = false;
                scan.close();
            } else {
                addChip();
                if (!checkWinner('R')) {
                    printBoard();
                    addChip();
                    if (checkWinner('Y')) {
                        printBoard();
                        System.out.println("The yellow player won!");
                        status = false;
                        scan.close();
                    }
                } else {
                    printBoard();
                    System.out.println("The red player won!");
                    status = false;
                    scan.close();
                }

            }
        }
    }

    /**
     * This method adds a chip to the board according to the user's column input.
     * The turn variable determines which color chip to drop.
     */
    public static void addChip(){
        boolean continueTurn = true;
        while(continueTurn) {
        try {
            String message = (turn % 2 == 0) ? "Drop a red disk at column (0-6): " : "Drop a yellow disk at column (0-6): ";
            System.out.println(message);
            int choice = scan.nextInt();
            if(count[choice] < 6) {
                count[choice]++;
                board[6 - count[choice]][choice] = (turn % 2 == 0) ? 'R' : 'Y';
                turn++;
                continueTurn = false;
            } else {
                System.out.println("That column is full. Enter a different column number.");
            }
        }
        // Exception handling
        catch(InputMismatchException ex) {
            System.out.println("Invalid input. Enter a valid, non-filled column number.");
            scan.next();
        }
        catch(IndexOutOfBoundsException ex) {
            System.out.println("Invalid column number. Enter a valid, non-filled column number.");
            }
        }
    }

    /**
     * This method checks if the board contains a winning combination of four consecutive
     * chips in a row, column, or diagonal.
     * @param c
     * The color of the chip to check for
     * @return
     * Returns a boolean value indicating if there is a winner or not
     */
    public static boolean checkWinner(char c){
        // Horizontal Check
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length - 3; j++) {
                if(board[i][j] == c && board[i][j + 1] == c && board[i][j + 2] == c && board[i][j + 3] == c) {
                    return true;
                }
            }
        }
        // Vertical Check
        for(int i = 0; i < board.length - 3; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] == c && board[i + 1][j] == c && board[i + 2][j] == c && board[i + 3][j] == c) {
                    return true;
                }
            }
        }
        // Diagonal Check West
        for(int i = 0; i < board.length - 3; i++) {
            for(int j = 0; j < board[i].length - 3; j++) {
                if(board[i][j] == c && board[i + 1][j + 1] == c && board[i + 2][j + 2] == c && board[i + 3][j + 3] == c){
                    return true;
                }
            }
        }
        // Diagonal Check East
        for(int i = 0; i < board.length - 3; i++) {
            for(int j = 3; j < board[i].length; j++) {
                if(board[i][j] == c && board[i + 1][j - 1] == c && board[i + 2][j - 2] == c && board[i + 3][j - 3] == c) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Prints the board
     */
    public static void printBoard(){
        for(int i = 0; i < board.length; i++) {
            System.out.print("|");
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] != '\u0000')
                    System.out.print((board[i][j]) + "|");
                else System.out.print(" |");
            }
            System.out.println();
        }
        System.out.println("................");
    }
}