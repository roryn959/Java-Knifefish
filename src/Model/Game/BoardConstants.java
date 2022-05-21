package Model.Game;

import java.util.ArrayList;
import java.util.HashMap;

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
    public static final int[] ROOKMOVES = {-1, -10, 1, -10};
    public static final int[] KINGMOVES = {-1, -9, -10, -11, 1, 9, 10, 11};

    private static int[] startingBoard = {
            69, 69, 69, 69, 69, 69, 69, 69, 69, 69,
            69, 69, 69, 69, 69, 69, 69, 69, 69, 69,
            69, BoardConstants.BLACK_ROOK, BoardConstants.BLACK_KNIGHT, BoardConstants.BLACK_BISHOP, BoardConstants.BLACK_QUEEN, BoardConstants.BLACK_KING, BoardConstants.BLACK_BISHOP, BoardConstants.BLACK_KNIGHT, BoardConstants.BLACK_ROOK, 69,
            69, BoardConstants.BLACK_PAWN, BoardConstants.BLACK_PAWN, BoardConstants.BLACK_PAWN, BoardConstants.BLACK_PAWN, BoardConstants.BLACK_PAWN, BoardConstants.BLACK_PAWN, BoardConstants.BLACK_PAWN, BoardConstants.BLACK_PAWN, 69,
            69, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, 69,
            69, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, 69,
            69, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, 69,
            69, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, BoardConstants.EMPTY, 69,
            69, BoardConstants.WHITE_PAWN, BoardConstants.WHITE_PAWN, BoardConstants.WHITE_PAWN, BoardConstants.WHITE_PAWN, BoardConstants.WHITE_PAWN, BoardConstants.WHITE_PAWN, BoardConstants.WHITE_PAWN, BoardConstants.WHITE_PAWN, 69,
            69, BoardConstants.WHITE_ROOK, BoardConstants.WHITE_KNIGHT, BoardConstants.WHITE_BISHOP, BoardConstants.WHITE_QUEEN, BoardConstants.WHITE_KING, BoardConstants.WHITE_BISHOP, BoardConstants.WHITE_KNIGHT, BoardConstants.WHITE_ROOK, 69,
            69, 69, 69, 69, 69, 69, 69, 69, 69, 69,
            69, 69, 69, 69, 69, 69, 69, 69, 69, 69,
    };

    public static int[] getStartingBoard(){
        return startingBoard.clone();
    }

    public static HashMap<Integer, ArrayList<Integer>> getStartingPiecePositions(){
        HashMap<Integer, ArrayList<Integer>> positions = new HashMap<>();

        // Black pieces
        ArrayList<Integer> blackKingPositions = new ArrayList<>();
        blackKingPositions.add(25);
        ArrayList<Integer> blackQueenPositions = new ArrayList<>();
        blackQueenPositions.add(24);
        ArrayList<Integer> blackRookPositions = new ArrayList<>();
        blackRookPositions.add(21);
        blackRookPositions.add(28);
        ArrayList<Integer> blackBishopPositions = new ArrayList<>();
        blackBishopPositions.add(23);
        blackBishopPositions.add(26);
        ArrayList<Integer> blackKnightPositions = new ArrayList<>();
        blackKnightPositions.add(22);
        blackKnightPositions.add(27);
        ArrayList<Integer> blackPawnPositions = new ArrayList<>();
        for (int i=31; i<39; i++){
            blackPawnPositions.add(i);
        }

        //White pieces
        ArrayList<Integer> whiteKingPositions = new ArrayList<>();
        whiteKingPositions.add(95);
        ArrayList<Integer> whiteQueenPositions = new ArrayList<>();
        whiteQueenPositions.add(94);
        ArrayList<Integer> whiteRookPositions = new ArrayList<>();
        whiteRookPositions.add(91);
        whiteRookPositions.add(98);
        ArrayList<Integer> whiteBishopPositions = new ArrayList<>();
        whiteBishopPositions.add(93);
        whiteBishopPositions.add(96);
        ArrayList<Integer> whiteKnightPositions = new ArrayList<>();
        whiteKnightPositions.add(92);
        whiteKnightPositions.add(97);
        ArrayList<Integer> whitePawnPositions = new ArrayList<>();
        for (int i=81; i<89; i++){
            whitePawnPositions.add(i);
        }

        positions.put(BoardConstants.BLACK_KING, (ArrayList<Integer>) blackKingPositions.clone());
        positions.put(BoardConstants.BLACK_QUEEN, (ArrayList<Integer>) blackQueenPositions.clone());
        positions.put(BoardConstants.BLACK_ROOK, (ArrayList<Integer>) blackRookPositions.clone());
        positions.put(BoardConstants.BLACK_BISHOP, (ArrayList<Integer>) blackBishopPositions.clone());
        positions.put(BoardConstants.BLACK_KNIGHT, (ArrayList<Integer>) blackKnightPositions.clone());
        positions.put(BoardConstants.BLACK_PAWN, (ArrayList<Integer>) blackPawnPositions.clone());

        positions.put(6, (ArrayList<Integer>) whiteKingPositions.clone());
        positions.put(BoardConstants.WHITE_QUEEN, (ArrayList<Integer>) whiteQueenPositions.clone());
        positions.put(BoardConstants.WHITE_ROOK, (ArrayList<Integer>) whiteRookPositions.clone());
        positions.put(BoardConstants.WHITE_BISHOP, (ArrayList<Integer>) whiteBishopPositions.clone());
        positions.put(BoardConstants.WHITE_KNIGHT, (ArrayList<Integer>) whiteKnightPositions.clone());
        positions.put(BoardConstants.WHITE_PAWN, (ArrayList<Integer>) whitePawnPositions.clone());

        return positions;
    }
}
