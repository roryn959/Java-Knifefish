package Model.Players;

import Model.Game.Board;
import Model.Game.BoardConstants;

import java.util.HashMap;

public class SimpleEvaluation implements EvaluationInterface {
    private HashMap<Integer, Integer> pieceValues;
    private Board board;

    public SimpleEvaluation(Board board){
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
    public final int evaluate(){
        int total = 0;
        for (Integer key : board.piecePositions.keySet()){
            total = total + board.piecePositions.get(key).getLength() * this.pieceValues.get(key);
        }
        return total;
    }
}
