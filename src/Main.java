import Model.DataStructures.LinkedList_;
import Model.DataStructures.Move;
import Model.Game.Board;
import Model.Players.RandomPlayer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        Board board = new Board();

        int sourceSquare, destinationSquare;
        boolean castleMove, enpassantMove;
        LinkedList_<Move> validMoves;
        while (true){
            board.display();

            // Get user's move
            System.out.println("Please enter the next move;");
            System.out.print("Source square: ");
            sourceSquare = myScanner.nextInt();
            System.out.print("Destination square: ");
            destinationSquare = myScanner.nextInt();
            System.out.print("Castle move: ");
            castleMove = myScanner.nextBoolean();
            System.out.print("En passant move: ");
            enpassantMove = myScanner.nextBoolean();
            Move userMove = new Move(sourceSquare, destinationSquare, castleMove, enpassantMove);
            validMoves = board.generateMoves();
            for (Move m : validMoves){
                if (userMove.isEquivalent(m)){
                    System.out.println("Move is valid.");
                    board.makeMove(m);
                    break;
                }
            }

            // Get CPU Move
            Move CPUMove = RandomPlayer.findMove(board);
            System.out.print("Random player found this move:");
            CPUMove.display();
            board.makeMove(CPUMove);
        }
    }
}
