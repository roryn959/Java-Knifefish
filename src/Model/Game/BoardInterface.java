package Model.Game;

import Model.DataStructures.Move;

public interface BoardInterface {
    int[] getBoard();
    boolean[] getCastlePermissions();
    boolean isInProgress();
    int getResult();
    boolean isWhiteTurn();
    void makeMove(Move m);
    void undoMove();

}
