package Model.Game;

import java.util.ArrayList;
import java.util.HashMap;

public class BoardConstants {
    /*
    White -> +
    Black -> -
    69: out of bounds
    0: empty
    1: pawn
    2: knight
    3: bishop
    4: rook
    5: queen
    6: king
     */

    public static final int[] KNIGHTMOVES = {-8, -12, -19, -21, 8, 12, 19, 21};
    public static final int[] BISHOPMOVES = {-9, -11, 9, 11};
    public static final int[] ROOKMOVES = {-1, -10, 1, -10};
    public static final int[] KINGMOVES = {-1, -9, -10, -11, 1, 9, 10, 11};

    private static int[] startingBoard = {
            69, 69, 69, 69, 69, 69, 69, 69, 69, 69,
            69, 69, 69, 69, 69, 69, 69, 69, 69, 69,
            69, -4, -2, -3, -5, -6, -3, -2, -4, 69,
            69, -1, -1, -1, -1, -1, -1, -1, -1, 69,
            69, 0, 0, 0, 0, 0, 0, 0, 0, 69,
            69, 0, 0, 0, 0, 0, 0, 0, 0, 69,
            69, 0, 0, 0, 0, 0, 0, 0, 0, 69,
            69, 0, 0, 0, 0, 0, 0, 0, 0, 69,
            69, 1, 1, 1, 1, 1, 1, 1, 1, 69,
            69, 4, 2, 3, 5, 6, 3, 2, 4, 69,
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

        positions.put(-6, (ArrayList<Integer>) blackKingPositions.clone());
        positions.put(-5, (ArrayList<Integer>) blackQueenPositions.clone());
        positions.put(-4, (ArrayList<Integer>) blackRookPositions.clone());
        positions.put(-3, (ArrayList<Integer>) blackBishopPositions.clone());
        positions.put(-2, (ArrayList<Integer>) blackKnightPositions.clone());
        positions.put(-1, (ArrayList<Integer>) blackPawnPositions.clone());

        positions.put(6, (ArrayList<Integer>) whiteKingPositions.clone());
        positions.put(5, (ArrayList<Integer>) whiteQueenPositions.clone());
        positions.put(4, (ArrayList<Integer>) whiteRookPositions.clone());
        positions.put(3, (ArrayList<Integer>) whiteBishopPositions.clone());
        positions.put(2, (ArrayList<Integer>) whiteKnightPositions.clone());
        positions.put(1, (ArrayList<Integer>) whitePawnPositions.clone());

        return positions;
    }
}
