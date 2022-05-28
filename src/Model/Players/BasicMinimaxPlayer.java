package Model.Players;

import Model.DataStructures.LinkedList_;
import Model.DataStructures.Move;
import Model.DataStructures.Tuple;
import Model.Game.Board;
import Model.Game.BoardConstants;

import java.util.HashMap;

public class BasicMinimaxPlayer {
    private HashMap<Integer, Integer> pieceValues;
    private Board board;
    public final int DEPTH = 4;

    public BasicMinimaxPlayer(Board board){
        this.board = board;
        this.pieceValues = new HashMap<>();
        this.pieceValues.put(BoardConstants.WHITE_PAWN, 100);
        this.pieceValues.put(BoardConstants.BLACK_PAWN, -100);
        this.pieceValues.put(BoardConstants.WHITE_KNIGHT, 320);
        this.pieceValues.put(BoardConstants.BLACK_KNIGHT, -320);
        this.pieceValues.put(BoardConstants.WHITE_BISHOP, 330);
        this.pieceValues.put(BoardConstants.BLACK_BISHOP, -330);
        this.pieceValues.put(BoardConstants.WHITE_ROOK, 500);
        this.pieceValues.put(BoardConstants.BLACK_ROOK, -500);
        this.pieceValues.put(BoardConstants.WHITE_QUEEN, 900);
        this.pieceValues.put(BoardConstants.BLACK_QUEEN, -900);
        this.pieceValues.put(BoardConstants.WHITE_KING, 20000);
        this.pieceValues.put(BoardConstants.BLACK_KING, -20000);
    }

    public int evaluate(){
        int total = 0;
        for (Integer key : board.piecePositions.keySet()){
            total = total + board.piecePositions.get(key).getLength() * this.pieceValues.get(key);
        }
        return total;
    }

    public Move findMove(){
        Tuple<Move, Integer> outcome = this.minimax(this.DEPTH);
        return outcome.x;
    }

    public Tuple<Move, Integer> minimax(int depth){
        //System.out.print("Minimax at depth ");
        //System.out.print(depth);
        //System.out.println(this.board.isWhiteTurn());
        if (depth==0 || this.board.gameOver()){
            return new Tuple<>(null, this.evaluate());
        }
        if (this.board.isWhiteTurn()){
            int bestScore = -40000;
            Move bestMove = null;
            Tuple<Move, Integer> outcome;
            LinkedList_<Move> possibleMoves = this.board.generateMoves();
            for (Move m : possibleMoves){
                this.board.makeMove(m);
                outcome = this.minimax(depth-1);
                this.board.undoMove();
                if (outcome.y > bestScore){
                    // If we have found a better move
                    bestScore = outcome.y;
                    bestMove = m;
                }
            }
            return new Tuple<>(bestMove, bestScore);
        } else {
            int bestScore = 40000;
            Move bestMove = null;
            Tuple<Move, Integer> outcome;
            LinkedList_<Move> possibleMoves = this.board.generateMoves();
            for (Move m : possibleMoves){
                this.board.makeMove(m);
                outcome = this.minimax(depth-1);
                this.board.undoMove();
                if (outcome.y < bestScore){
                    bestScore = outcome.y;
                    bestMove = m;
                }
            }
            return new Tuple<>(bestMove, bestScore);
        }
    }
}
