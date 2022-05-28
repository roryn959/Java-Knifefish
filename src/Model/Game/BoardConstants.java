package Model.Game;

import java.util.HashMap;
import Model.DataStructures.LinkedList_;

public class BoardConstants {
    public static final int EMPTY = 0;
    public static final int OUTSIDE_BOARD = 69;
    public static final int WHITE_PAWN = 1;
    public static final int WHITE_KNIGHT = 2;
    public static final int WHITE_BISHOP = 3;
    public static final int WHITE_ROOK = 4;
    public static final int WHITE_QUEEN = 5;
    public static final int WHITE_KING = 6;
    public static final int BLACK_PAWN = -1;
    public static final int BLACK_KNIGHT = -2;
    public static final int BLACK_BISHOP = -3;
    public static final int BLACK_ROOK = -4;
    public static final int BLACK_QUEEN = -5;
    public static final int BLACK_KING = -6;

    public static final int[] KNIGHTMOVES = {-8, -12, -19, -21, 8, 12, 19, 21};
    public static final int[] BISHOPMOVES = {-9, -11, 9, 11};
    public static final int[] ROOKMOVES = {-1, -10, 1, 10};
    public static final int[] KINGMOVES = {-1, -9, -10, -11, 1, 9, 10, 11};

    private static int[] startingBoard = {
            BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD,
            BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD,
            BoardConstants.OUTSIDE_BOARD, BoardConstants.BLACK_ROOK, BoardConstants.BLACK_KNIGHT, BoardConstants.BLACK_BISHOP, BoardConstants.BLACK_QUEEN, BoardConstants.BLACK_KING, BoardConstants.BLACK_BISHOP, BoardConstants.BLACK_KNIGHT, BoardConstants.BLACK_ROOK, BoardConstants.OUTSIDE_BOARD,
            BoardConstants.OUTSIDE_BOARD, BoardConstants.BLACK_PAWN, BoardConstants.BLACK_PAWN, BoardConstants.BLACK_PAWN, BoardConstants.BLACK_PAWN, BoardConstants.BLACK_PAWN, BoardConstants.BLACK_PAWN, BoardConstants.BLACK_PAWN, BoardConstants.BLACK_PAWN, BoardConstants.OUTSIDE_BOARD,
            BoardConstants.OUTSIDE_BOARD, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.OUTSIDE_BOARD,
            BoardConstants.OUTSIDE_BOARD, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.OUTSIDE_BOARD,
            BoardConstants.OUTSIDE_BOARD, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.OUTSIDE_BOARD,
            BoardConstants.OUTSIDE_BOARD, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.OUTSIDE_BOARD,
            BoardConstants.OUTSIDE_BOARD, BoardConstants.WHITE_PAWN, BoardConstants.WHITE_PAWN, BoardConstants.WHITE_PAWN, BoardConstants.WHITE_PAWN, BoardConstants.WHITE_PAWN, BoardConstants.WHITE_PAWN, BoardConstants.WHITE_PAWN, BoardConstants.WHITE_PAWN, BoardConstants.OUTSIDE_BOARD,
            BoardConstants.OUTSIDE_BOARD, BoardConstants.WHITE_ROOK, BoardConstants.WHITE_KNIGHT, BoardConstants.WHITE_BISHOP, BoardConstants.WHITE_QUEEN, BoardConstants.WHITE_KING, BoardConstants.WHITE_BISHOP, BoardConstants.WHITE_KNIGHT, BoardConstants.WHITE_ROOK, BoardConstants.OUTSIDE_BOARD,
            BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD,
            BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD, BoardConstants.OUTSIDE_BOARD,
    };

    public static int[] getStartingBoard(){
        return startingBoard.clone();
    }

    public static HashMap<Integer, LinkedList_<Integer>> getStartingPiecePositions(){
        HashMap<Integer, LinkedList_<Integer>> positions = new HashMap<>();

        // Black pieces
        LinkedList_<Integer> blackKingPositions = new LinkedList_<>();
        blackKingPositions.add(25);
        LinkedList_<Integer> blackQueenPositions = new LinkedList_<>();
        blackQueenPositions.add(24);
        LinkedList_<Integer> blackRookPositions = new LinkedList_<>();
        blackRookPositions.add(21);
        blackRookPositions.add(28);
        LinkedList_<Integer> blackBishopPositions = new LinkedList_<>();
        blackBishopPositions.add(23);
        blackBishopPositions.add(26);
        LinkedList_<Integer> blackKnightPositions = new LinkedList_<>();
        blackKnightPositions.add(22);
        blackKnightPositions.add(27);
        LinkedList_<Integer> blackPawnPositions = new LinkedList_<>();
        for (int i=31; i<39; i++){
            blackPawnPositions.add(i);
        }

        //White pieces
        LinkedList_<Integer> whiteKingPositions = new LinkedList_<>();
        whiteKingPositions.add(95);
        LinkedList_<Integer> whiteQueenPositions = new LinkedList_<>();
        whiteQueenPositions.add(94);
        LinkedList_<Integer> whiteRookPositions = new LinkedList_<>();
        whiteRookPositions.add(91);
        whiteRookPositions.add(98);
        LinkedList_<Integer> whiteBishopPositions = new LinkedList_<>();
        whiteBishopPositions.add(93);
        whiteBishopPositions.add(96);
        LinkedList_<Integer> whiteKnightPositions = new LinkedList_<>();
        whiteKnightPositions.add(92);
        whiteKnightPositions.add(97);
        LinkedList_<Integer> whitePawnPositions = new LinkedList_<>();
        for (int i=81; i<89; i++){
            whitePawnPositions.add(i);
        }

        positions.put(BoardConstants.BLACK_KING, blackKingPositions.clone());
        positions.put(BoardConstants.BLACK_QUEEN, blackQueenPositions.clone());
        positions.put(BoardConstants.BLACK_ROOK, blackRookPositions.clone());
        positions.put(BoardConstants.BLACK_BISHOP, blackBishopPositions.clone());
        positions.put(BoardConstants.BLACK_KNIGHT, blackKnightPositions.clone());
        positions.put(BoardConstants.BLACK_PAWN, blackPawnPositions.clone());

        positions.put(BoardConstants.WHITE_KING, whiteKingPositions.clone());
        positions.put(BoardConstants.WHITE_QUEEN, whiteQueenPositions.clone());
        positions.put(BoardConstants.WHITE_ROOK, whiteRookPositions.clone());
        positions.put(BoardConstants.WHITE_BISHOP, whiteBishopPositions.clone());
        positions.put(BoardConstants.WHITE_KNIGHT, whiteKnightPositions.clone());
        positions.put(BoardConstants.WHITE_PAWN, whitePawnPositions.clone());

        return positions;
    }
}
