// CREDIT: Values inside piece squares taken from PeSTO engine, (https://www.chessprogramming.org/PeSTO%27s_Evaluation_Function)

package Model.Players;

import Model.Game.Board;
import Model.Game.BoardConstants;

import java.util.HashMap;

public class AdvancedEvaluation implements EvaluationInterface {
    private HashMap<Integer, int[]> midGamePieceSquareTables, endGamePieceSquareTables;
    private Board board;
    private final HashMap<Integer, Integer> pieceValues;

    private final int[] endGamePawnPieceSquares = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 178, 173, 158, 134, 147, 132, 165, 187, 0,
            0, 94, 100,  85, 67, 56, 53, 82, 84, 0,
            0, 32, 24, 13, 5, -2, 4, 17, 17, 0,
            0, 13, 9, -3, -7, -7, -8, 3, -1, 0,
            0, 4, 7, -6, 1, 0, -5, -1, -8, 0,
            0, 13, 8, 8, 10, 13, 0, 2, -7, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    };

    private final int[] midGamePawnPieceSquares = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0,0, 0, 0, 0, 0, 0,0, 0,0, 0,
            0, 98, 134, 61, 95, 68, 126, 34, -11, 0,
            0, -6, 7, 26, 31, 65, 56, 25, -20, 0,
            0, -14, 13, 6, 21, 23, 12, 17, -23, 0,
            0, -27, -2, -5, 12, 17, 6, 10, -25, 0,
            0, -26, -4, -4, -10, 3, 3, 33, -12, 0,
            0, -35, -1, -20, -23, -15,  24, 38, -22, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    };

    private final int[] endGameKnightPieceSquares = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, -58, -38, -13, -28, -31, -27, -63, -99, 0,
            0, -25, -8, -25, -2, -9, -25, -24, -52, 0,
            0, -24, -20, 10, 9, -1, -9, -19, -41, 0,
            0, -17, 3, 22, 22, 22, 11, 8, -18, 0,
            0, -18, -6, 16, 25, 16, 17, 4, -18, 0,
            0, -23, -3, -1, 15, 10, -3, -20, -22, 0,
            0, -42, -20, -10, -5, -2, -20, -23, -44, 0,
            0, -29, -51, -23, -15, -22, -18, -50, -64, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    };

    private final int[] midGameKnightPieceSquares = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, -167, -89, -34, -49, 61, -97, -15, -107, 0,
            0, -73, -41, 72, 36, 23, 62, 7, -17, 0,
            0, -47, 60, 37, 65, 84, 129, 73, 44, 0,
            0, -9, 17, 19, 53, 37, 69, 18, 22, 0,
            0, -13, 4, 16, 13, 28, 19, 21, -8, 0,
            0, -23, -9, 12, 10, 19, 17, 25, -16, 0,
            0, -29, -53, -12, -3, -1, 18, -14, -19, 0,
            0, -105, -21, -58, -33, -17, -28, -19, -23, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    };

    private final int[] endGameBishopPieceSquares = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, -14, -21, -11, -8, -7, -9, -17, -24, 0,
            0, -8, -4, 7, -12, -3, -13, -4, -14, 0,
            0, 2, -8, 0, -1, -2, 6, 0, 4,0,
            0, -3, 9, 12, 9, 14, 10, 3, 2, 0,
            0, -6, 3, 13, 19, 7, 10, -3, -9, 0,
            0, -12, -3, 8, 10, 13, 3, -7, -15, 0,
            0, -14, -18, -7, -1, 4, -9, -15, -27, 0,
            0, -23, -9, -23, -5, -9, -16, -5, -17, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    };

    private final int[] midGameBishopPieceSquares = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, -29, 4, -82, -37, -25, -42, 7, -8, 0,
            0, -26, 16, -18, -13, 30, 59, 18, -47, 0,
            0, -16, 37, 43, 40, 35, 50, 37, -2, 0,
            0, -4, 5, 19, 50, 37, 37, 7, -2, 0,
            0, -6, 13, 13, 26, 34, 12, 10, 4, 0,
            0, 0, 15, 15, 15, 14, 27, 18, 10, 0,
            0, 4, 15, 16, 0, 7, 21, 33, 1, 0,
            0, -33, -3, -14, -21, -13, -12, -39, -21, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    };

    private final int[] endGameRookPieceSquares = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 13, 10, 18, 15, 12,  12, 8, 5, 0,
            0, 11, 13, 13, 11, -3, 3, 8, 3, 0,
            0, 7, 7, 7, 5, 4, -3, -5, -3, 0,
            0, 4, 3, 13, 1, 2, 1, -1, 2, 0,
            0, 3, 5, 8, 4, -5, -6, -8, -11, 0,
            0, -4, 0, -5, -1, -7, -12, -8, -16, 0,
            0, -6, -6, 0, 2, -9, -9, -11, -3, 0,
            0, -9, 2, 3, -1, -5, -13, 4, -20, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    };

    private final int[] midGameRookPieceSquares = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 32, 42, 32, 51, 63, 9, 31, 43, 0,
            0, 27, 32, 58, 62, 80, 67,  26,  44, 0,
            0, -5, 19, 26, 36, 17, 45, 61, 16, 0,
            0, -24, -11, 7, 26, 24, 35, -8, -20, 0,
            0, -36, -26, -12, -1, 9, -7, 6, -23, 0,
            0, -45, -25, -16, -17, 3, 0, -5, -33, 0,
            0, -44, -16, -20, -9, -1, 11, -6, -71, 0,
            0, -19, -13, 1, 17, 16, 7, -37, -26, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    };

    private final int[] endGameQueenPieceSquares = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, -9, 22, 22, 27, 27, 19, 10, 20, 0,
            0, -17, 20, 32, 41, 58, 25, 30, 0, 0,
            0, -20, 6, 9, 49, 47, 35, 19, 9, 0,
            0, 3, 22, 24, 45, 57, 40, 57, 36, 0,
            0, -18, 28, 19, 47, 31, 34, 39, 23, 0,
            0, -16, -27, 15, 6, 9, 17, 10, 5, 0,
            0, -22, -23, -30, -16, -16, -23, -36, -32, 0,
            0, -33, -28, -22, -43, -5, -32, -20, -41, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    };

    private final int[] midGameQueenPieceSquares = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, -28, 0, 29, 12, 59, 44, 43, 45, 0,
            0, -24, -39, -5, 1, -16, 57, 28, 54, 0,
            0, -13, -17, 7, 8, 29, 56, 47, 57, 0,
            0, -27, -27, -16, -16, -1, 17, -2, 1, 0,
            0, -9, -26, -9, -10, -2, -4, 3, -3, 0,
            0, -14, 2, -11, -2, -5, 2, 14, 5, 0,
            0, -35, -8, 11, 2, 8, 15, -3, 1, 0,
            0, -1, -18, -9, 10, -15, -25, -31, -50, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    };

    private final int[] endGameKingPieceSquares = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, -74, -35, -18, -18, -11, 15, 4,-17, 0,
            0, -12, 17, 14, 17, 17, 38, 23, 11, 0,
            0, 10, 17, 23, 15, 20, 45, 44, 13, 0,
            0, -8, 22, 24, 27, 26, 33, 26, 3, 0,
            0, -18, -4, 21, 24, 27, 23, 9, -11, 0,
            0, -19, -3, 11, 21, 23, 16, 7, -9, 0,
            0, -27, -11, 4, 13, 14, 4, -5, -17, 0,
            0, -53, -34, -21, -11, -28, -14, -24, -43, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    };

    private final int[] midGameKingPieceSquares = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, -65, 23, 16, -15, -56, -34, 2, 13, 0,
            0, 29, -1, -20, -7, -8, -4, -38, -29, 0,
            0, -9, 24, 2, -16, -20, 6,  22, -22, 0,
            0, -17, -20, -12, -27, -30, -25, -14, -36, 0,
            0, -49, -1, -27, -39, -46, -44, -33, -51, 0,
            0, -14, -14, -22, -46, -44, -30, -15, -27, 0,
            0, 1, 7, -8, -64, -43, -16, 9, 8, 0,
            0, -15, 36, 12, -54, 8, -28, 24, 14, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    };

    public AdvancedEvaluation(Board board){
        this.board = board;

        // Mid-game piece-square tables
        this.midGamePieceSquareTables = new HashMap<>();
        this.midGamePieceSquareTables.put(BoardConstants.WHITE_PAWN, this.midGamePawnPieceSquares);
        this.midGamePieceSquareTables.put(BoardConstants.BLACK_PAWN, this.invertPieceSquareTable(this.midGamePawnPieceSquares));
        this.midGamePieceSquareTables.put(BoardConstants.WHITE_KNIGHT, this.midGameKnightPieceSquares);
        this.midGamePieceSquareTables.put(BoardConstants.BLACK_KNIGHT, this.invertPieceSquareTable(this.midGameKnightPieceSquares));
        this.midGamePieceSquareTables.put(BoardConstants.WHITE_BISHOP, this.midGameBishopPieceSquares);
        this.midGamePieceSquareTables.put(BoardConstants.BLACK_BISHOP, this.invertPieceSquareTable(this.midGameBishopPieceSquares));
        this.midGamePieceSquareTables.put(BoardConstants.WHITE_ROOK, this.midGameRookPieceSquares);
        this.midGamePieceSquareTables.put(BoardConstants.BLACK_ROOK, this.invertPieceSquareTable(this.midGameRookPieceSquares));
        this.midGamePieceSquareTables.put(BoardConstants.WHITE_QUEEN, this.midGameQueenPieceSquares);
        this.midGamePieceSquareTables.put(BoardConstants.BLACK_QUEEN, this.invertPieceSquareTable(this.midGameQueenPieceSquares));
        this.midGamePieceSquareTables.put(BoardConstants.WHITE_KING, this.midGameKingPieceSquares);
        this.midGamePieceSquareTables.put(BoardConstants.BLACK_KING, this.invertPieceSquareTable(this.midGameKingPieceSquares));

        // End-game piece square tables
        this.endGamePieceSquareTables = new HashMap<>();
        this.endGamePieceSquareTables.put(BoardConstants.WHITE_PAWN, this.endGamePawnPieceSquares);
        this.endGamePieceSquareTables.put(BoardConstants.BLACK_PAWN, this.invertPieceSquareTable(this.endGamePawnPieceSquares));
        this.endGamePieceSquareTables.put(BoardConstants.WHITE_KNIGHT, this.endGameKnightPieceSquares);
        this.endGamePieceSquareTables.put(BoardConstants.BLACK_KNIGHT, this.invertPieceSquareTable(this.endGameKnightPieceSquares));
        this.endGamePieceSquareTables.put(BoardConstants.WHITE_BISHOP, this.endGameBishopPieceSquares);
        this.endGamePieceSquareTables.put(BoardConstants.BLACK_BISHOP, this.invertPieceSquareTable(this.endGameBishopPieceSquares));
        this.endGamePieceSquareTables.put(BoardConstants.WHITE_ROOK, this.endGameRookPieceSquares);
        this.endGamePieceSquareTables.put(BoardConstants.BLACK_ROOK, this.invertPieceSquareTable(this.endGameRookPieceSquares));
        this.endGamePieceSquareTables.put(BoardConstants.WHITE_QUEEN, this.endGameQueenPieceSquares);
        this.endGamePieceSquareTables.put(BoardConstants.BLACK_QUEEN, this.invertPieceSquareTable(this.endGameQueenPieceSquares));
        this.endGamePieceSquareTables.put(BoardConstants.WHITE_KING, this.endGameKingPieceSquares);
        this.endGamePieceSquareTables.put(BoardConstants.BLACK_KING, this.invertPieceSquareTable(this.endGameKingPieceSquares));

        this.pieceValues = new HashMap<>();
        this.pieceValues.put(1, 100);
        this.pieceValues.put(-1, -100);
        this.pieceValues.put(2, 320);
        this.pieceValues.put(-2, -320);
        this.pieceValues.put(3, 330);
        this.pieceValues.put(-3, -330);
        this.pieceValues.put(4, 500);
        this.pieceValues.put(-4, -500);
        this.pieceValues.put(5, 900);
        this.pieceValues.put(-5, -900);
        this.pieceValues.put(6, 20000);
        this.pieceValues.put(-6, -20000);

        // Pre-compute the final values of each position in the piece-square tables
        int[] table;
        for (Integer key : this.pieceValues.keySet()){
            table = this.midGamePieceSquareTables.get(key);
            for (int i=0; i<120; i++){
                table[i] = table[i] + this.pieceValues.get(key);
            }
            table = this.endGamePieceSquareTables.get(key);
            for (int i=0; i<120; i++){
                table[i] = table[i] + this.pieceValues.get(key);
            }
        }
    }

    private final int[] invertPieceSquareTable(int[] array){
        int[] newArray = new int[120];
        int originalRank, originalFile, newRank;
        for (int i=0; i<120; i++){
            originalRank = i/10;
            originalFile = i%10;
            newRank = 11-originalRank;
            newArray[newRank*10 + originalFile] = -1 * array[i];
        }
        return newArray;
    }

    private final boolean boardIsInMidGame(){
        // *** IMPLEMENT ***
        return true;
    }

    public final int evaluate(){
        // *** If statement can be extracted to be faster ***
        int total = 0;

        // Foreach piece
        for (Integer piece : this.board.piecePositions.keySet()){
            // For each position that the piece is in
            for (int position : this.board.piecePositions.get(piece)){
                if (this.boardIsInMidGame()){
                    total = total + this.midGamePieceSquareTables.get(piece)[position];
                } else {
                    total = total + this.endGamePieceSquareTables.get(piece)[position];
                }
            }
        }
        return total;
    }

    public final int guidedEval(){
        System.out.println("\n\n *** Guided Eval Starting ***");
        int total = 0;
        for (Integer piece : this.board.piecePositions.keySet()){
            System.out.print("--> new piece ");
            System.out.println(piece);
            for (int position : this.board.piecePositions.get(piece)){
                System.out.print(position);
                System.out.print("$");
                if (this.boardIsInMidGame()){
                    System.out.print(this.midGamePieceSquareTables.get(piece)[position]);
                    System.out.print("£");
                    total = total + this.midGamePieceSquareTables.get(piece)[position];
                    System.out.print(total);
                    System.out.println("");
                }
            }
        }
        return total;
    }
}
