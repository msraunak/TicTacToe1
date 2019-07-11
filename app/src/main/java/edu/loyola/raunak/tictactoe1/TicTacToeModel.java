package edu.loyola.raunak.tictactoe1;

/**
 * @author MSR
 */
public class TicTacToeModel {

    public static final int SIDE = 3;

    private int turn;
    private int [][] game; // A two dimensional int array

    public TicTacToeModel() {
        game = new int[SIDE][SIDE];
        resetGame();
    }

    /**
     * Place a move in the given row and col position
     * @param row the row index
     * @param col the col index
     * @return the turn value 1 or 2. If next turn is for player 2 return 2 else return 1
     */
    public int play(int row, int col) {
            int currentTurn = turn;
            if ( row >= 0 && col >= 0 && row < SIDE && col < SIDE && game[row][col] == 0){

                game [row][col] = turn;
                if (turn == 1)
                    turn = 2;
                else
                    turn = 1;

                return currentTurn;
            }
            else // row and col invalid or slot already played
                return 0;

    }

    /**
     * @return 0 if no winner (yet), 1 if player 1 has won, 2 if player 2 has won
     */
    public int whoWon(){
        int rows = checkRows();
        if (rows > 0)
            return rows;
        int cols = checkColumns();
        if (cols > 0)
            return cols;
        int diags = checkDiagonals();
        if (diags > 0)
            return diags;

        return 0; // no winner yet
    }

    /**
     * Check if there is a winner in a row. If there is winner return the winner (1 or 2),
     * if no winner return 0;
     * @return 0 if no winner, 1 if player 1 is a winner in a row, or 2 if player 2 is a winner in a row
     */
    private int checkRows() {
       for (int row = 0; row < SIDE; row++) {
           if (game[row][0] != 0 &&
                   game[row][0] == game[row][1] &&
                   game[row][1] == game[row][2])
               return game[row][0];
       }
       return 0; // no winner in rows
    }

    /**
     *
     * @return 0 if no winner, 1 if playe 1 is a winner in a column, or 2 if playe r2 is a winner in a column
     */
    private int checkColumns(){
        for (int col = 0; col < SIDE; col++) {
            if ( game[0][col] !=0
                    && game[0][col] == game[1][col]
                    && game[1][col] == game[2][col] )
            return game[0][col];
        }
        return 0; // no winner in cols
    }

    // check diagonal for winner
    private int checkDiagonals () {
        if ( game[0][0] != 0 &&
                game[0][0] == game[1][1] &&
                game[1][1] == game[2][2] )
            return game[0][0]; // top left to bottom right winner

        if ( game[0][2] != 0 &&
                game[0][2] == game[1][1] &&
                game[1][1] == game[2][0] )
            return game[2][0]; // bottom left to top right winner

        return 0; // no winner in diags
    }

    // getter
    public int getTurn(){
        return turn;
    }

    /** What is the current status of the result
     * @return "Play !" if the game is not over yet, "Tie Game" when it has ended with a tie
     */
    public String result() {
        if ( whoWon () > 0 )
            return "Player " + whoWon ( ) + " won";
        else if ( canNotPlay() )
            return "Tie Game";
        else
            return "Play !";
    }


    /**
     * When there is no more slots left to place a move, return true; otherwise return false.
     * @return true iff there is no more slots left to make a move, false otherwise
     */
    public boolean canNotPlay (){
        boolean result = true;
        for (int row =0; row < SIDE; row ++) {
            for (int col = 0; col < SIDE; col++) {
                if (game[row][col] == 0)
                    result = false;
            }
        }
        return result;
    }


    public boolean isGameOver(){
        return canNotPlay() || ( whoWon() > 0);
    }

    /**
     * Make all the board slots empty, ready to receive a move
     */
    public void resetGame(){
        for (int row=0; row < SIDE; row++)
            for (int col=0; col < SIDE; col++)
                game[row][col]=0;
    }

}