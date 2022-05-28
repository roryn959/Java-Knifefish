package Controller;

import Model.DataStructures.Move;
import Model.DataStructures.Tuple;
import Model.Game.Board;
import Model.Players.BasicMinimaxPlayer;
import Model.Players.RandomPlayer;
import View.ChessGUI;

import java.util.InputMismatchException;

public class Hand {
    Board board;
    ChessGUI gui;
    public Hand(){
        this.board = new Board();
        this.gui = new ChessGUI(this);
    }

    public void makeCPUMove(){
        BasicMinimaxPlayer engine = new BasicMinimaxPlayer(this.board);
        Move engineMove = engine.findMove();
        //Move engineMove = RandomPlayer.findMove(this.board);
        this.board.makeMove(engineMove);
        this.board.display();

    }

    public void submitMove(int fromSquare, int toSquare){
        try {
            this.board.giveMove(fromSquare, toSquare);
            this.makeCPUMove();
            this.updateGUIBoard();
        } catch (InputMismatchException e){
            System.out.print("Invalid move. Squares in question: ");
            System.out.print(fromSquare);
            System.out.print(", ");
            System.out.println(toSquare);
        }
    }

    public void submitCastle(int side){
        int colourCastle = this.board.isWhiteTurn() ? 0 : 1;
        this.submitMove(colourCastle, side);
    }

    public void undoMove(){
        // Undo twice as to not change the colour of the player
        this.board.undoMove();
        this.board.undoMove();
        this.updateGUIBoard();
    }

    public void updateGUIBoard(){
        this.gui.updateBoard(this.board.getBoard());
    }
}
