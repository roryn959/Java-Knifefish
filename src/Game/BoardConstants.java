package Game;

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
}
