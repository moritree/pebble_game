import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // get number of rows in game from user
        Scanner scanner = new Scanner(System.in);
        boolean flag = false;
        int nRows = 0;
        while (!flag) {
            System.out.print("Number of rows in game (1-10): ");
            String input = scanner.nextLine();

            try {
                nRows = Integer.parseInt(input);
                if (nRows >=1 && nRows <= 10) flag = true;
                else System.out.println("Number must be between 1 and 10.");
            } catch(NumberFormatException e) { System.out.println("Must be an integer number."); }
        }

        // AI or human player toggle
        Boolean AI = false;
        flag = false;
        while (!flag) {
            System.out.print("AI as 2nd player? ");
            String input = scanner.nextLine();

            if (input.equals("true") || input.equals("False")) {
                System.out.println("AI enabled.");
                AI = true;
                flag = true;
            } else if (input.equals("false") || input.equals("False")) {
                System.out.println("AI disabled.");
                flag = true;
            } else { System.out.println("Must be a boolean (true/false)."); }
        }

        // initialize coordinate arrays for the game
        char[][] coords = new char[nRows][nRows+5];

        // initialize pebble positions
        for (int i = 0; i < nRows; i ++) {
            coords[i][0] = 'X';
            coords[i][i+5] = 'O';
        }

        // draw initial game
        drawGameState(coords, nRows);

        // run pebble game
        boolean running = true;
        int turn = 1;
        while (running) {
            // alternates turn between player1 as X and player2/AI as O
            if (turn % 2 != 0) userMove('X', 'O', coords, nRows);
            else if (!AI) userMove('O', 'X', coords, nRows);
            else AIMove(coords, nRows);

            // draw board after each turn
            drawGameState(coords, nRows);

            // check win condition - all pebbles in the opponent's home positions
            int countO = 0;
            int countX = 0;
            for (int i = 0; i < nRows; i++) {
                if (coords[i][0] == 'O') { countO += 1; }
                if (coords[i][i + 5] == 'X') { countX += 1; }
            }

            if (countO == nRows) {
                running = false;
                System.out.println("\nO wins!");
            } else if (countX == nRows) {
                running = false;
                System.out.println("\nX wins!");
            }

            turn ++;
        }
    }

    /** generate random AI movement */
    private static void AIMove(char[][] coords, int nRows) {
        try { TimeUnit.MILLISECONDS.sleep(500); }
        catch (InterruptedException e) { }

        System.out.println("");
        System.out.println("** O (A.I) **");

        Random rand = new Random();
        int row = 0;
        boolean flag = false;
        if (nRows != 1) while (!flag) {
            row = rand.nextInt(nRows);
            if (checkMove(coords[row], 'L', 'O', 'X', row)) flag = true;
        }

        // move 1 space to the left
        writeMove(coords[row], 'O', 'X', -1, row);

        System.out.println("Moved left on row " + (row+1) + ".");
    }

    /** get movement instruction from user */
    private static void userMove(char player, char opponent, char[][] coords, int nRows) {
        System.out.println("");
        System.out.println("** " + player + " **");
        Scanner scanner = new Scanner(System.in);

        // get row on which to move pebble
        int thisRow = 0;
        boolean flag = false;
        if (nRows != 1) {
            while (!flag) {
                System.out.print("Row: ");
                String input = scanner.nextLine();

                try {
                    thisRow = Integer.parseInt(input) - 1;
                    if (thisRow >= 0 && thisRow <= nRows) flag = true;
                    else System.out.println("Number must be between 1 and " + nRows + ".");
                } catch(NumberFormatException e) { System.out.println("Must be an integer number."); }
            }
        }

        // check which movements are possible on this row
        boolean canMoveLeft = checkMove(coords[thisRow], 'L', player, opponent, thisRow);
        boolean canMoveRight = checkMove(coords[thisRow], 'R', player, opponent, thisRow);

        // ask movement from user
        int dist = 1;
        flag = false;
        while (!flag) {
            System.out.print("Direction (L/R): ");
            String input = scanner.nextLine();

            if (input.equals("L")) {
                if (canMoveLeft) {
                    dist = -1;
                    flag = true;
                } else { System.out.println("Cannot move left"); }
            } else if (input.equals("R")) {
                if (canMoveRight) flag = true;
                else System.out.println("Cannot move right");
            } else { System.out.println("L or R."); }
        }

        // move pebble
        writeMove(coords[thisRow], player, opponent, dist, thisRow);
    }

    /** write a movement to the coordinate array */
    private static void writeMove(char[] coords, char player, char opponent, int dist, int thisRow) {
        boolean jump = false;

        int index = new String(coords).indexOf(player);
        coords[index] = ' ';
        if (coords[index + dist] == opponent) { // if jump over
            dist *= 2;
            if ((index + dist != thisRow + 5) && (index + dist != 0)) {  // if send home
                coords[index + (dist/2)] = ' '; // erase current position of opponent
                // send opponent home
                if (player == 'X') coords[thisRow + 5] = 'O';
                else if (player == 'O') coords[0] = 'X';
            }
        }

        coords[index + dist] = player;
    }

    /** checks whether a movement in direction dir (R/L) is legal */
    private static boolean checkMove(char[] coords, char dir, char player, char opponent, int thisRow) {
        int index = new String(coords).indexOf(player);
        if (dir == 'L' && (index > 0 && (coords[index - 1] != opponent || index > 1))) {
            return true;
        } else if (dir == 'R' && (index < thisRow + 5 && (coords[index + 1] != opponent || index < thisRow + 4))) {
            return true;
        } else return false;
    }

    /** draws current game state from coordinate array onto console */
    private static void drawGameState(char[][] coords, int nRows) {
        System.out.println();  // clear console

        for (int i = 0; i < nRows; i ++) {      // iterates each row
            for (int j = 0; j < i + 6; j ++) {  // iterates each column
                System.out.print("|");
                if (coords[i][j] == 'X') System.out.print(" X ");
                else if (coords[i][j] == 'O') System.out.print(" O ");
                else System.out.print("   ");
            }
            System.out.print("|\n");
        }
    }
}
