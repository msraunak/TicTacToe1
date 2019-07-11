package edu.loyola.raunak.tictactoe1;

import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button [][] buttons;
    private TicTacToeModel tttGame;
    private TextView status; // add textview to show the status

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        tttGame = new TicTacToeModel();
        buildGuiByCode();
    }

    public void buildGuiByCode() {

        // Get width fo the screen
        Point displaySize = new Point(); //Point holds two integer coordinates
        getWindowManager().getDefaultDisplay().getSize( displaySize );
        int width = displaySize.x / TicTacToeModel.SIDE;

        // create the LayoutManager and set its dimension
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount( TicTacToeModel.SIDE );
        gridLayout.setRowCount( TicTacToeModel.SIDE + 1); // the row count will have a status row at the bottom

        // Create the buttons and add them to gridLayout
        buttons = new Button[TicTacToeModel.SIDE][TicTacToeModel.SIDE];

        ButtonHandler bh = new ButtonHandler (); // a ButtonHandler object reference

        // create nine Button objects, set it's text size and connect a ButtonHandler lister
        for (int row = 0; row < TicTacToeModel.SIDE; row++) {
            for (int col = 0; col < TicTacToeModel.SIDE; col++) {
                buttons[row][col] = new Button(this);

                buttons[row][col].setTextSize( (int) (width * .2) );
                buttons[row][col].setOnClickListener( bh );

                gridLayout.addView( buttons[row][col], width, width );
            } // end for (inner loop)
        } // end for (outer loop)


        // setup layout parameters of the 4th row
        status = new TextView (this);
        GridLayout.Spec rowSpec = GridLayout.spec (TicTacToeModel.SIDE , 1);
        GridLayout.Spec colSpec = GridLayout.spec( 0, TicTacToeModel.SIDE ); //??

        GridLayout.LayoutParams lpStatus = new GridLayout.LayoutParams ( rowSpec, colSpec );
        status.setLayoutParams (lpStatus);


        // set up TextView Status's characteristics
        status.setWidth( TicTacToeModel.SIDE * width);
        status.setHeight( width );
        status.setGravity( Gravity.CENTER );
        status.setBackgroundColor(Color.BLUE);
        status.setTextSize ( (int) (width * .15) );

        status.setText( tttGame.result());

        gridLayout.addView( status );

        // set Grid Layout as the view of this activity
        setContentView ( gridLayout );
    }

    // update the view of the Button
    private void update (int row, int col){

        Log.w ( "MainActivity", "Inside update(): " + row + ", " + col);
        int play = tttGame.play (row, col);

        if (play == 1) // next play is for player 1
            buttons[row][col].setText("X");
        else if ( play == 2 )
            buttons[row][col].setText ("O");

        // when the game is over
        if ( tttGame.isGameOver() ) {
            enableButtons(false);
            status.setBackgroundColor( Color.RED );
            status.setText( tttGame.result() );
            showNewGameDialog();
        }
    }

    // enable or disable the buttons
    private void enableButtons( boolean isEnabled ) {
        for (int row = 0; row < TicTacToeModel.SIDE; row++) {
            for (int col = 0; col < TicTacToeModel.SIDE; col++) {
                buttons[row][col].setEnabled( isEnabled );
            }
        }
    }

    // reset the buttons by wiping out any X or O on them
    public void resetButtons() {
        for (int row = 0; row  < TicTacToeModel.SIDE; row++ )
            for (int col = 0; col < TicTacToeModel.SIDE; col++ )
                buttons[row][col].setText("");
    }


    private void showNewGameDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder (this);
        alert.setTitle ("Thank you for playing!");
        alert.setMessage ( "Play Again?" );

        PlayDialog playAgain = new PlayDialog(); // a listener/handler for the dialogbox buttons
        alert.setPositiveButton("YES", playAgain);
        alert.setNegativeButton("No", playAgain);
        alert.show();
    }
    // private inner class to hancle clicks on Buttons
    private class ButtonHandler implements View.OnClickListener {

        public void onClick (View view) {

            Log.w( "MainActivity", "Inside onClick, view: " + view);
            for ( int row = 0; row < TicTacToeModel.SIDE; row ++ ) {
                for ( int col = 0; col < TicTacToeModel.SIDE; col ++) {
                    if (view == buttons [row][col])
                        update (row, col); // update is a method in MainActivity
                }
            }
        }
    }

    // A listener to implment actions when a button in the Dialog box is clicked
    private class PlayDialog implements DialogInterface.OnClickListener {

        public void onClick (DialogInterface dialog, int id) {
            if ( id == -1 ) { // YES button
                tttGame.resetGame();
                enableButtons (true);
                resetButtons();
                status.setBackgroundColor (Color.GREEN);
                status.setText (tttGame.result ());
            } else if (id == -2) // NO button
                MainActivity.this.finish(); // finish this instance of the MainActivity

        }
    }
}


