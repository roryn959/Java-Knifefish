package Controller;

import Model.Game.Board;
import Model.Players.RandomPlayer;
import View.ChessGUI;

import java.util.HashMap;
import java.util.InputMismatchException;

public class Hand {
    Board board;
    ChessGUI gui;
    public Hand(){
        this.board = new Board();
        this.gui = new ChessGUI(this);
    }

    public void submitMove(int fromSquare, int toSquare){
        try {
            this.board.giveMove(fromSquare, toSquare);
            this.board.makeMove(RandomPlayer.findMove(this.board));
            this.updateGUIBoard();
        } catch (InputMismatchException e){
            System.out.print("Invalid move. Squares in question: ");
            System.out.print(fromSquare);
            System.out.print(", ");
            System.out.println(toSquare);
        }
    }

    public void updateGUIBoard(){
        this.gui.updateBoard(this.board.getBoard());
    }
}
